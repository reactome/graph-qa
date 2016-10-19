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
public class QualityAssuranceTest016 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "CatalystActivityWithoutActivity";
    }

    @Override
    String getQuery() {
        return " MATCH (n:CatalystActivity) " +
                "WHERE NOT (n)-[:activity]->() " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN n.dbId AS dbId, n.stId AS stId, n.displayName AS name, a.displayName AS created, m.displayName AS modified " +
                "ORDER BY created, modified, stId, dbId";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "dbId", "name", "created", "modified");
    }
}