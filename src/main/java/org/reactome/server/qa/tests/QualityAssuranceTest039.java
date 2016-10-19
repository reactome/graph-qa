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
public class QualityAssuranceTest039 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "PrecedingEventRelationshipDuplication";
    }

    @Override
    String getQuery() {
        return " MATCH (x{isInferred:False})-[r:precedingEvent]->(y) " +
                "WHERE r.stoichiometry > 1 " +
                "OPTIONAL MATCH (a)-[:created]->(x) " +
                "OPTIONAL MATCH (m)-[:modified]->(x) " +
                "RETURN DISTINCT(x.dbId) AS dbIdA,x.stId AS stIdA, x.displayName AS nameA, y.dbId AS dbIdB, y.stId AS stIdB, y.displayName AS nameB, a.displayName AS created, m.displayName AS modified " +
                "ORDER BY created, modified, stIdA, dbIdA, stIdB, dbIdB";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "dbIdA", "stIdA", "nameA", "dbIdB", "stIdB", "nameB", "created", "modified");
    }
}
