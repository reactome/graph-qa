package org.reactome.server.qa;

import com.martiansoftware.jsap.*;
import org.reactome.server.graph.service.GeneralService;
import org.reactome.server.graph.utils.ReactomeGraphCore;
import org.reactome.server.qa.annotations.QATest;
import org.reactome.server.qa.config.GraphQANeo4jConfig;
import org.reactome.server.qa.tests.QualityAssurance;
import org.reactome.server.qa.tests.QualityAssuranceAbstract;
import org.reflections.Reflections;
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
                        new FlaggedOption(  "host",     JSAP.STRING_PARSER,  "localhost",       JSAP.REQUIRED,     'h', "host",     "The neo4j host"          ),
                        new FlaggedOption(  "port",     JSAP.STRING_PARSER,  "7474",            JSAP.NOT_REQUIRED, 'b', "port",     "The neo4j port"          ),
                        new FlaggedOption(  "user",     JSAP.STRING_PARSER,  "neo4j",           JSAP.REQUIRED,     'u', "user",     "The neo4j user"          ),
                        new FlaggedOption(  "password", JSAP.STRING_PARSER,  "reactome",        JSAP.REQUIRED,     'p', "password", "The neo4j password"      ),
                        new FlaggedOption(  "output",   JSAP.STRING_PARSER,  null,              JSAP.REQUIRED,     'o', "output",   "Output folder"           ),
                        new FlaggedOption(  "test",     JSAP.STRING_PARSER,  null,              JSAP.NOT_REQUIRED, 't', "test",     "A specific task"         ),
                        new QualifiedSwitch("verbose",  JSAP.BOOLEAN_PARSER, JSAP.NO_DEFAULT,   JSAP.NOT_REQUIRED, 'v', "verbose",  "Requests verbose output" )
                }
        );
        JSAPResult config = jsap.parse(args);
        if (jsap.messagePrinted()) System.exit(1);

        //Initialising ReactomeCore Neo4j configuration
        ReactomeGraphCore.initialise(config.getString("host"), config.getString("port"), config.getString("user"), config.getString("password"), GraphQANeo4jConfig.class);

        GeneralService genericService = ReactomeGraphCore.getService(GeneralService.class);

        String qa = config.getString("test");
        Reflections reflections = new Reflections(QualityAssuranceAbstract.class.getPackage().getName());
        Set<Class<?>> tests = reflections.getTypesAnnotatedWith(QATest.class);

        String path = config.getString("output");
        if(!path.endsWith("/")) path += "/";

        boolean verbose = config.getBoolean("verbose");

        int n = tests.size(), i = 1, count = 0;
        for (Class test : tests) {
            try {
                Object object = test.newInstance();
                QualityAssurance qATest = (QualityAssurance) object;
                if (qa == null || qATest.getName().equals(qa)) {
                    if (verbose) {
                        if (qa == null) System.out.print("\rRunning test " + qATest.getName() + " [" + (i++) + " of " + n + "]");
                        else System.out.println("Running test " + qATest.getName());
                    }
                    if (qATest.run(genericService, path)) count++;
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (verbose) {
            if (qa == null) System.out.println("\rQA finished. " + count + " tests generated reports.\n\nPlease check the files to find out more.");
            else System.out.println("Test finished.");
        }
    }
}
