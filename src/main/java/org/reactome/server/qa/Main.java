package org.reactome.server.qa;

import com.martiansoftware.jsap.*;
import org.reactome.server.graph.service.GeneralService;
import org.reactome.server.qa.annotations.QATest;
import org.reactome.server.qa.config.GraphQANeo4jConfig;
import org.reactome.server.qa.tests.QualityAssurance;
import org.reactome.server.qa.tests.QualityAssuranceAbstract;
import org.reflections.Reflections;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * @author Florian Korninger <florian.korninger@ebi.ac.uk>
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("ALL")
@Configuration
public class Main {

    public static void main(String[] args) throws JSAPException {

        SimpleJSAP jsap = new SimpleJSAP(Main.class.getName(), "A tool for testing the integrity and consistency of the data imported in the existing graph database",
                new Parameter[]{
                        new FlaggedOption(  "host",     JSAP.STRING_PARSER, "localhost",     JSAP.REQUIRED,     'h', "host",     "The neo4j host"),
                        new FlaggedOption(  "port",     JSAP.STRING_PARSER, "7474",          JSAP.NOT_REQUIRED, 'b', "port",     "The neo4j port"),
                        new FlaggedOption(  "user",     JSAP.STRING_PARSER, "neo4j",         JSAP.REQUIRED,     'u', "user",     "The neo4j user"),
                        new FlaggedOption(  "password", JSAP.STRING_PARSER, "reactome",      JSAP.REQUIRED,     'p', "password", "The neo4j password"),
                        new QualifiedSwitch("verbose",  JSAP.BOOLEAN_PARSER, JSAP.NO_DEFAULT, JSAP.NOT_REQUIRED, 'v', "verbose",  "Requests verbose output")
                }
        );
        JSAPResult config = jsap.parse(args);
        if (jsap.messagePrinted()) System.exit(1);

        System.setProperty("neo4j.host", config.getString("host"));
        System.setProperty("neo4j.port", config.getString("port"));
        System.setProperty("neo4j.user", config.getString("user"));
        System.setProperty("neo4j.password", config.getString("password"));

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(GraphQANeo4jConfig.class); // Use annotated beans from the specified package
        GeneralService genericService = ctx.getBean(GeneralService.class);

        Reflections reflections = new Reflections(QualityAssuranceAbstract.class.getPackage().getName());
        Set<Class<?>> tests = reflections.getTypesAnnotatedWith(QATest.class);

        boolean verbose = config.getBoolean("verbose");
        int n = tests.size(), i = 1, count = 0;
        for (Class test : tests) {
            try {
                Object object = test.newInstance();
                QualityAssurance qATest = (QualityAssurance) object;
                if(verbose) System.out.print("\rRunning test " + (i++) + " of " + n);
                if(qATest.run(genericService)) count++;
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(verbose) System.out.println("\r" + count + " test generated reports.\nPlease check the files out to find out more.");
    }
}
