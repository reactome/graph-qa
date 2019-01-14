package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class T070_ReactionsWithoutRegulatorWithMoreCompartmentsThanItsParticipants extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "Reactions without regulator with more compartments than its participants";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.HIGH;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("Identifier", "Name", "ReactionCompartments", "EntitiesCompartments", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (rle:ReactionLikeEvent) " +
                "WHERE NOT (rle:BlackBoxEvent) AND NOT (rle)-[:regulatedBy]->() AND (rle)-[:compartment]->() " +
                "WITH DISTINCT rle " +
                "MATCH (rlec:Compartment)<-[:compartment]-(rle)-[:input|output|requiredInputComponent|catalystActivity|physicalEntity|entityFunctionalStatus|diseaseEntity|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(:PhysicalEntity)-[:compartment]->(pec:Compartment) " +
                "WITH rle, COLLECT(DISTINCT rlec.displayName) AS rlecs, COLLECT(DISTINCT pec.displayName) AS pecs " +
                "WHERE ANY(c IN rlecs WHERE NOT c IN pecs) " +
                "OPTIONAL MATCH (a)-[:created]->(rle) " +
                "OPTIONAL MATCH (m)-[:modified]->(rle) " +
                "RETURN DISTINCT rle.stId AS Identifier, rle.displayName AS Name, \"[\" + REDUCE(s = \"\", x IN rlecs | s + x + \", \") + \"]\" AS ReactionCompartments, \"[\" + REDUCE(s = \"\", x IN pecs | s + x + \", \") + \"]\" AS EntitiesCompartments, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, Identifier";
    }
}
