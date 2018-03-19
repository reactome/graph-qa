package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class T090_CatalystActivityCompartmentDoesNotMatchReactionCompartment extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "CatalystActivity class instances where the compartment slot does not match with the associated Reaction compartment";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.HIGH;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("Identifier", "Reaction", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (crle:Compartment)<-[:compartment]-(rle:ReactionLikeEvent{isInferred:False})-[:catalystActivity|physicalEntity|compartment*]->(cac:Compartment) " +
                "WITH DISTINCT rle, COLLECT(crle) AS crles, COLLECT(cac) AS cacs " +
                "WHERE NONE(c IN cacs WHERE c IN crles) OR NONE(c IN crles WHERE c IN cacs) " +
                "OPTIONAL MATCH (a)-[:created]->(rle) " +
                "OPTIONAL MATCH (m)-[:modified]->(rle) " +
                "RETURN rle.stId AS Identifier, rle.displayName AS Reaction, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Identifier";
    }
}
