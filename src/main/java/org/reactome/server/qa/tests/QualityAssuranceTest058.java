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
public class QualityAssuranceTest058 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "ComplexesWhereCompartmentDoesNotMatchWithAnyOfTheParticipants";
    }

    @Override
    String getQuery() {
        return " MATCH (c:Complex)-[:compartment]->(cc:Compartment), " +
                "      (c)-[:hasComponent|hasMember|hasCandidate|repeatedUnit*]->(pe:PhysicalEntity)-[:compartment]->(pc:Compartment) " +
//                "WHERE NOT ()-[:inferredTo]->(c) " +
                "WITH c, COLLECT(DISTINCT cc) AS ccs, COLLECT(DISTINCT pc) AS pcs " +
                "WHERE NONE(c IN ccs WHERE c IN pcs) " +
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
