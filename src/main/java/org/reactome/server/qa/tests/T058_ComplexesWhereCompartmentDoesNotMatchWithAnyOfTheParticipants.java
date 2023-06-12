package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class T058_ComplexesWhereCompartmentDoesNotMatchWithAnyOfTheParticipants extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "Complexes where the compartment does not matc wWith any of the participants";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.HIGH;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("Identifier", "Complex", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (c:Complex)-[:compartment]->(cc:Compartment), " +
                "      (c)-[:hasComponent|hasMember|hasCandidate|repeatedUnit|proteinMarker|RNAMarker*]->(pe:PhysicalEntity)-[:compartment]->(pc:Compartment) " +
//                "WHERE NOT ()-[:inferredTo]->(c) " +
                "WITH c, COLLECT(DISTINCT cc) AS ccs, COLLECT(DISTINCT pc) AS pcs " +
                "WHERE NONE(c IN ccs WHERE c IN pcs) " +
                "OPTIONAL MATCH (a)-[:created]->(c) " +
                "OPTIONAL MATCH (m)-[:modified]->(c) " +
                "RETURN DISTINCT c.stId AS Identifier, c.displayName AS Complex, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, Identifier";
    }
}
