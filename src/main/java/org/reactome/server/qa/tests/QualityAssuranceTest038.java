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
public class QualityAssuranceTest038 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "HasModifiedResidueRelationshipDuplication";
    }

    @Override
    String getQuery() {
        return " MATCH (x)-[r:hasModifiedResidue]->(y) " +
                "WHERE NOT ()-[:inferredTo]->(x) AND r.stoichiometry > 1  " +
                "OPTIONAL MATCH (a)-[:created]->(x) " +
                "OPTIONAL MATCH (m)-[:modified]->(x) " +
                "RETURN DISTINCT(x.dbId) AS dbIdA, x.stId AS stIdA, x.displayName AS nameA, y.dbId AS dbIdB, y.displayName AS nameB, a.displayName AS created, m.displayName AS modified " +
                "ORDER BY created, modified, stIdA, dbIdA, dbIdB";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "dbIdA", "stIdA", "nameA", "dbIdB", "nameB", "created", "modified");
    }
}



