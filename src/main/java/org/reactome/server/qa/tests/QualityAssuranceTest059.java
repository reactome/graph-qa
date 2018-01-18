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
public class QualityAssuranceTest059 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "DuplicatedCuratedCandidateSets";
    }

    @Override
    String getQuery() {
        return " MATCH (n1c)<-[:compartment]-(n1:CandidateSet)-[:hasMember|hasCandidate]->(:PhysicalEntity)<-[:hasMember|hasCandidate]-(n2:CandidateSet)-[:compartment]->(n2c) " +
                "WHERE NOT ()-[:inferredTo]->(n1) AND NOT ()-[:inferredTo]->(n2) AND NOT n1 = n2 AND n1c = n2c " +
                "WITH DISTINCT n1, n2 " +
                "MATCH (n1)-[r1:hasMember|hasCandidate]->(n1pe:PhysicalEntity), " +
                "      (n2)-[r2:hasMember|hasCandidate]->(n2pe:PhysicalEntity) " +
                "WITH n1, COLLECT(DISTINCT {pe: n1pe, n: r1.stoichiometry}) AS n1pes, " +
                "     n2, COLLECT(DISTINCT {pe: n2pe, n: r2.stoichiometry}) AS n2pes " +
                "WHERE ALL(c IN n1pes WHERE c IN n2pes) AND ALL(c IN n2pes WHERE c IN n1pes) " +
                "OPTIONAL MATCH (an1)-[:created]->(n1) " +
                "OPTIONAL MATCH (an2)-[:created]->(n2) " +
                "RETURN DISTINCT n1.stId AS CandidateSet1, n1.displayName AS CandidateSet1_Name, an1.displayName AS CandidateSet1_Created, " +
                "                n2.stId AS CandidateSet2, n2.displayName AS CandidateSet2_Name, an2.displayName AS CandidateSet2_Created " +
                "ORDER BY CandidateSet1_Created, CandidateSet1";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "CandidateSet1", "CandidateSet1_Name", "CandidateSet1_Created", "CandidateSet2", "CandidateSet2_Name", "CandidateSet2_Created");
    }
}
