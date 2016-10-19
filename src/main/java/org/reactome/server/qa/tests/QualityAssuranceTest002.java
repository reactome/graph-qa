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
public class QualityAssuranceTest002 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "PersonWithoutProperName";
    }

    @Override
    String getQuery() {
        return " MATCH (n:Person) " +
                "WHERE n.surname is NULL OR (n.firstname is NULL AND n.initial is NULL) " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN n.dbId AS dbId, n.displayName AS name, a.displayName AS created, m.displayName AS modified " +
                "ORDER BY created, modified, dbId";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "dbId", "name", "created", "modified");
    }
}
