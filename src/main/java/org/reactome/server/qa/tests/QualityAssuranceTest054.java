package org.reactome.server.qa.tests;

import org.neo4j.ogm.model.Result;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
//@QATest
@Deprecated
public class QualityAssuranceTest054 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "DuplicatedInferredComplexes";
    }

    @Override
    String getQuery() {
        return " MATCH (n1c)<-[:compartment]-(n1:Complex)-[:hasComponent]->(:PhysicalEntity)<-[:hasComponent]-(n2:Complex)-[:compartment]->(n2c) " +
                "WHERE ()-[:inferredTo]->(n1) AND NOT ()-[:inferredTo]->(n2) AND NOT n1 = n2 AND n1c = n2c " +
                "WITH DISTINCT n1, n2 " +
                "MATCH (n1)-[r1:hasComponent]->(n1pe:PhysicalEntity), " +
                "      (n2)-[r2:hasComponent]->(n2pe:PhysicalEntity) " +
                "WITH n1, COLLECT(DISTINCT {pe: n1pe, n: r1.stoichiometry}) AS n1pes, " +
                "     n2, COLLECT(DISTINCT {pe: n2pe, n: r2.stoichiometry}) AS n2pes " +
                "WHERE ALL(c IN n1pes WHERE c IN n2pes) AND ALL(c IN n2pes WHERE c IN n1pes) " +
                "OPTIONAL MATCH (an1)-[:created]->(n1) " +
                "OPTIONAL MATCH (an2)-[:created]->(n2) " +
                "RETURN DISTINCT n1.stId AS Complex1, n1.displayName AS Complex1_Name, an1.displayName AS Complex1_Created, " +
                "                n2.stId AS Complex2, n2.displayName AS Complex2_Name, an2.displayName AS Complex2_Created " +
                "ORDER BY Complex1_Created, Complex1";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "Complex1", "Complex1_Name", "Complex1_Created", "Complex2", "Complex2_Name", "Complex2_Created");
    }
}
