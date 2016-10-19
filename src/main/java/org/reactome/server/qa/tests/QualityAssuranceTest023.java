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
public class QualityAssuranceTest023 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "DatabaseIdentifierWithoutReferenceDatabase";
    }

    @Override
    String getQuery() {
        return " MATCH (n:DatabaseIdentifier) " +
                "WHERE NOT (n)-[:referenceDatabase]->() " +
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




