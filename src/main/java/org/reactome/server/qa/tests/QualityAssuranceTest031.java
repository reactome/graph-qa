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
        return "CatalystActivityWherePhysicalEntityAndActiveUnitPointToComplex";
    }

    @Override
    String getQuery() {
        return " MATCH (n:CatalystActivity)-[r:physicalEntity]->(x:Complex)<-[:activeUnit]-(n) " +
                "WHERE NOT ()-[:inferredTo]->(x) " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN DISTINCT(n.dbId) AS CatalystID, n.displayName AS CatalystActivity, x.stId AS ComplexID, x.displayName AS Complex, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, CatalystID, ComplexID";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "CatalystID", "CatalystActivity", "ComplexID", "Complex", "Created", "Modified");
    }
}
