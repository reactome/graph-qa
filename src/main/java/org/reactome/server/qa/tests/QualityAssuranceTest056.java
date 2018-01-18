package org.reactome.server.qa.tests;

import org.neo4j.ogm.model.Result;
import org.reactome.server.qa.annotations.QATest;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest056 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "InferredComplexesWithOnlyOneComponent";
    }

    @Override
    String getQuery() {
        return " MATCH (c:Complex)-[hc:hasComponent]->(pe:PhysicalEntity) " +
                "WHERE ()-[:inferredTo]->(c) " +
                "WITH DISTINCT c, SUM(hc.stoichiometry) AS n, COUNT(pe) AS pes " +
                "WHERE pes = 1 AND n = 1 " +
                "OPTIONAL MATCH (a)-[:created]->(c) " +
                "OPTIONAL MATCH (m)-[:modified]->(c) " +
                "RETURN DISTINCT c.stId AS Identifier, c.displayName AS Complex, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, Identifier";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "Identifier", "Complex", "Created", "Modified");
    }
}
