package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
@Deprecated
public class T080_WrongAnnotatedBindingReactions extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public QAPriority getPriority() {
        return null;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("Identifier", "Reaction", "inputs", "outputs", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (i:PhysicalEntity)<-[ri:input]-(rle:ReactionLikeEvent{isInferred:False})-[ro:output]->(o:PhysicalEntity) " +
                "WHERE NOT(rle:BlackBoxEvent) AND (rle)-[:catalystActivity]->() " +
                "WITH DISTINCT rle, COLLECT(DISTINCT ri) AS ris, COLLECT(DISTINCT ro) AS ros, COLLECT(DISTINCT o) AS os " +
                "WHERE ANY(pe IN os WHERE NOT (pe:SimpleEntity)) " +
                "WITH rle, REDUCE(n=0, e IN ris | n + e.stoichiometry) AS inputs, REDUCE(n=0, e IN ros | n + e.stoichiometry) AS outputs " +
                "WHERE inputs > outputs " +
                "OPTIONAL MATCH (a)-[:created]->(rle) " +
                "OPTIONAL MATCH (m)-[:modified]->(rle) " +
                "RETURN DISTINCT rle.stId AS Identifier, rle.displayName AS Reaction, inputs, outputs, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, Identifier";
    }
}
