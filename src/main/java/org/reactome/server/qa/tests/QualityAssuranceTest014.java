package org.reactome.server.qa.tests;

import org.neo4j.ogm.model.Result;
import org.reactome.server.qa.annotations.QATest;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Florian Korninger <florian.korninger@ebi.ac.uk>
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest014 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "InstanceEditWithoutAuthor";
    }

    @Override
    String getQuery() {
        return " MATCH (n:InstanceEdit) " +
                "WHERE NOT (n)<-[:author]-() " +
                "RETURN n.dbId AS dbId";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "dbId");
    }
}