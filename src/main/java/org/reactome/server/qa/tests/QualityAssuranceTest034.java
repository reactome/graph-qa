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
public class QualityAssuranceTest034 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "ModifiedRelationshipDuplication";
    }

    @Override
    String getQuery() {
        return " MATCH (n)<-[r:modified]-(m) " +
                "WHERE r.stoichiometry > 1 " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "RETURN DISTINCT(n.dbId) AS dbIdA, n.displayName AS nameA, m.dbId AS dbIdB, m.displayName AS nameB, a.displayName AS created " +
                "ORDER BY created";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "dbIdA", "nameA", "dbIdB", "nameB", "created");
    }
}
