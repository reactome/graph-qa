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
public class QualityAssuranceTest031 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "PhysicalEntityAndActiveUnitPointToSameEntry";
    }

    @Override
    String getQuery() {
        return " MATCH (n)-[r:physicalEntity|activeUnit]->(x), " +
                "      (n)-[e]->(x) " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN DISTINCT(n.dbId) AS dbIdA,n.stId AS stIdA, n.displayName AS nameA, x.dbId AS dbIdB, x.stId AS stIdB, x.displayName AS nameB, a.displayName AS created, m.displayName AS modified " +
                "ORDER BY created, modified, stIdA, dbIdA, stIdB, dbIdB";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "dbIdA", "stIdA", "nameA", "dbIdB", "stIdB", "nameB", "created", "modified");
    }
}
