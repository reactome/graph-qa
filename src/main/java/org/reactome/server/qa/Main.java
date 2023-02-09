package org.reactome.server.qa;

import com.martiansoftware.jsap.*;
import org.reactome.server.graph.service.GeneralService;
import org.reactome.server.graph.utils.ReactomeGraphCore;
import org.reactome.server.qa.annotations.QATest;
import org.reactome.server.qa.tests.QAPriority;
import org.reactome.server.qa.tests.QualityAssurance;
import org.reactome.server.qa.tests.QualityAssuranceAbstract;
import org.reactome.server.qa.utils.FileUtils;
import org.reactome.server.qa.utils.ProgressBar;
import org.reactome.server.qa.utils.Report;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {

    private static Boolean VERBOSE;

    private static GeneralService genericService;
    private static List<Class<?>> sortedTests;
    private static String path;

    public static void main(String[] args) throws JSAPException {

        SimpleJSAP jsap = new SimpleJSAP(Main.class.getName(), "A tool for testing the integrity and consistency of the data imported in the existing graph database",
                new Parameter[]{
                        new FlaggedOption("host", JSAP.STRING_PARSER, "bolt://localhost:7687", JSAP.REQUIRED, 'h', "host", "The neo4j host"),
                        new FlaggedOption("user", JSAP.STRING_PARSER, "neo4j", JSAP.REQUIRED, 'u', "user", "The neo4j user"),
                        new FlaggedOption("password", JSAP.STRING_PARSER, "reactome", JSAP.REQUIRED, 'p', "password", "The neo4j password"),
                        new FlaggedOption("databaseName", JSAP.STRING_PARSER, "graph.db", JSAP.NOT_REQUIRED, 'n', "dbName", "The neo4j database name"),
                        new FlaggedOption("output", JSAP.STRING_PARSER, "./reports", JSAP.REQUIRED, 'o', "output", "Output folder"),
                        new FlaggedOption("test", JSAP.STRING_PARSER, null, JSAP.NOT_REQUIRED, 't', "test", "A specific task"),
                        new QualifiedSwitch("verbose", JSAP.BOOLEAN_PARSER, JSAP.NO_DEFAULT, JSAP.NOT_REQUIRED, 'v', "verbose", "Requests verbose output")
                }
        );
        JSAPResult config = jsap.parse(args);
        if (jsap.messagePrinted()) System.exit(1);

        initialise(config);

        String qa = config.getString("test");
        long start = System.currentTimeMillis();
        List<Report> reports = (qa == null) ? runAllTests() : runSingleTest(qa);
        //Reports have to be stored and printed in the screen (when VERBOSE)
        storeReports(path, "GraphQA_Summary_v" + genericService.getDBInfo().getVersion(), reports);
        long time = System.currentTimeMillis() - start;

        printReports(reports, time);

        System.exit(0);
    }

    private static List<Report> runAllTests() {
        List<Report> reports = new ArrayList<>();
        if (VERBOSE) System.out.println();
        int n = sortedTests.size(), i = 1;
        for (Class<?> test : sortedTests) {
            try {
                Object object = test.getDeclaredConstructor().newInstance();
                QualityAssurance qATest = (QualityAssurance) object;
                if (VERBOSE) ProgressBar.updateProgressBar(qATest.getName(), i++, n);
                int result = qATest.run(genericService, path);
                reports.add(new Report(qATest, result));
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return reports;
    }

    private static List<Report> runSingleTest(String test) {
        List<Report> reports = new ArrayList<>();
        Class<?> clazz = sortedTests.stream().filter(t -> t.getSimpleName().contains(test)).findFirst().orElse(null);
        if (clazz != null) {
            try {
                Object object = clazz.getDeclaredConstructor().newInstance();
                QualityAssurance qATest = (QualityAssurance) object;
                if (VERBOSE) System.out.print("\nRunning single test '" + qATest.getName() + "'...");
                int result = qATest.run(genericService, path);
                reports.add(new Report(qATest, result));
                if (VERBOSE) System.out.print(" Done.");
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("\nThe test '" + test + "' cannot be found. Please check the name and try again.");
            System.exit(1);
        }
        return reports;
    }

    private static void printReports(List<Report> reports, long time) {
        if (!VERBOSE) return;
        //Printing the reports sorted by name
        System.out.printf("\n\n· Report%s:%n", reports.size() > 1 ? "s" : "");
        reports.stream().sorted().forEach(Report::printColoured);
        System.out.println("\n· Priorities meaning:");
        QAPriority.list().forEach(QAPriority::printColoured);
        long c = reports.stream().filter(r -> r.count > 0).count();
        System.out.printf("\nQA finished. %s test%s generated reports (%s)%n", c, c == 1 ? "" : "s", getTimeFormatted(time));
    }

    private static void storeReports(String path, String fileName, List<Report> reports) {
        if (reports.isEmpty()) return;
        List<String> lines = new ArrayList<>();
        lines.add(Report.getCSVHeader());
        reports.stream().sorted().forEach(r -> lines.add(r.getCSV()));

        try {
            Files.write(FileUtils.getFilePath(path, fileName), lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initialise(JSAPResult config) {
        VERBOSE = config.getBoolean("verbose");

        //Initialising ReactomeCore Neo4j configuration
        ReactomeGraphCore.initialise(
                config.getString("host"),
                config.getString("user"),
                config.getString("password"),
                config.getString("databaseName"));

        //ReactomeGraphCore has to be initialised before services can be instantiated
        genericService = ReactomeGraphCore.getService(GeneralService.class);

        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(QualityAssuranceAbstract.class.getPackage().getName()));
        Set<Class<?>> tests = reflections.getTypesAnnotatedWith(QATest.class);
        sortedTests = tests.stream().filter(c -> c.getAnnotation(Deprecated.class) == null)
                .sorted(Comparator.comparing(Class::getSimpleName)) // Sorting tests by name
                .collect(Collectors.toList());

        if (VERBOSE) {  //Report test initialisation
            System.out.println("· Quality Assestment tests initialisation:");
            System.out.print("\t>Initialising tests to be performed...");
            long t = tests.size();
            System.out.printf("\r\t> %d test%s found: %n", t, t == 1 ? "" : "s");
            long r = sortedTests.size();
            System.out.printf("\t\t-%3d test%s active%n", r, r == 1 ? "" : "s");
            long d = tests.stream().filter(c -> c.getAnnotation(Deprecated.class) != null).count();
            System.out.printf("\t\t-%3d test%s excluded ('@Deprecated')%n", d, d == 1 ? "" : "s");
        }

        //Cleans up previous reports
        path = config.getString("output");
        if (!path.endsWith("/")) path += "/";

        try {
            org.apache.commons.io.FileUtils.cleanDirectory(new File(path));
            if (VERBOSE) System.out.println("\t> Reports folder cleanup -> (done)");
        } catch (Exception e) {
            System.out.println("\t> No reports folder found -> it will be created.");
        }
    }

    private static String getTimeFormatted(Long millis) {
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }
}
