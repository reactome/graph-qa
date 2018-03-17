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
public class QualityAssuranceTest045 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "OtherRelationshipDuplication";
    }

    @Override
    String getQuery() {
        return " MATCH (a)-[:created]->(n)<-[r]-(m) " +
                "WHERE NOT ()-[r:hasComponent|input|output|repeatedUnit|modified|" +
                "crossReference|author|literatureReference|hasModifiedResidue|precedingEvent|hasMember|summation|psiMod|" +
                "hasCandidate|hasEvent]-() AND r.stoichiometry > 1 " +
                "RETURN DISTINCT(n.dbId) AS dbIdA, n.displayName AS nameA, TYPE(r) AS relationship, m.dbId AS dbIdB, m.displayName AS nameB, a.displayName AS created " +
                "ORDER BY created, dbIdA, dbIdB";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "dbIdA", "nameA", "relationship", "dbIdB", "nameB", "created");
    }
}



