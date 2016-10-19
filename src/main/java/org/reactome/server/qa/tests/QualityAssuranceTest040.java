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
public class QualityAssuranceTest040 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "HasMemberRelationshipDuplication";
    }

    @Override
    String getQuery() {
        return " MATCH (es:EntitySet)-[r:hasMember]->(m) " +
                "WHERE NOT (m:EntitySet) AND r.stoichiometry > 1 " +
                "OPTIONAL MATCH (a)-[:created]->(es) " +
                "OPTIONAL MATCH (b)-[:modified]->(es) " +
                "RETURN DISTINCT(es.dbId) AS dbIdA, es.stId AS stIdA, es.displayName AS nameA, m.dbId AS dbIdB, m.stId AS stIdB, m.displayName AS nameB, a.displayName AS created, b.displayName AS modified " +
                "ORDER BY created, modified, stIdA, dbIdA, stIdB, dbIdB";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "dbIdA", "stIdA", "nameA", "dbIdB", "stIdB", "nameB", "created", "modified");
    }
}


