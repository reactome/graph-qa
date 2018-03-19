package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class T092_PotentialTranslocationReactionChangesParticipantsSchemaClass extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "Potential translocation Reaction changes participants schemaClass";
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
        return " MATCH (ic:Compartment)<-[:compartment]-(i:PhysicalEntity)<-[ri:input]-(rle:ReactionLikeEvent{isInferred:False})-[ro:output]->(o:PhysicalEntity)-[:compartment]->(oc:Compartment) " +
                "WHERE NOT (rle:BlackBoxEvent) AND NOT (rle)-[:catalystActivity]->() " +
                "WITH DISTINCT rle, COLLECT(i.schemaClass) AS lis, COLLECT(DISTINCT ri) AS ris, COLLECT(o.schemaClass) AS los, COLLECT(DISTINCT ro) AS ros, COLLECT(ic) AS ics, COLLECT(oc) AS ocs " +
                "WHERE NONE(c IN ics WHERE c IN ocs) AND NONE(l IN lis WHERE l IN los) " +
                "WITH rle, REDUCE(n=0, e IN ris | n + e.stoichiometry) AS inputs, REDUCE(n=0, e IN ros | n + e.stoichiometry) AS outputs " +
                "WHERE inputs = 1 AND outputs = 1 " +
                "OPTIONAL MATCH (a)-[:created]->(rle) " +
                "OPTIONAL MATCH (m)-[:modified]->(rle) " +
                "RETURN DISTINCT rle.stId AS Identifier, rle.displayName AS Reaction, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, Identifier";
    }
}
