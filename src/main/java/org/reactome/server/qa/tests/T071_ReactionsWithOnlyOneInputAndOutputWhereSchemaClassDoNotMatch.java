package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class T071_ReactionsWithOnlyOneInputAndOutputWhereSchemaClassDoNotMatch extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "Reactions with only one input and output where schemaClass do not match";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.HIGH;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("Identifier", "Mame", "ReactionType", "Inputs", "Outputs", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (i:PhysicalEntity)<-[ri:input]-(rle:ReactionLikeEvent{isInferred:False})-[ro:output]->(o:PhysicalEntity) " +
                "WHERE NOT (rle:Depolymerisation) AND NOT (rle:Polymerisation) " + // AND NOT (rle)-[:catalystActivity]->()
                "WITH rle, COLLECT(i.schemaClass) AS inputs, COLLECT(o.schemaClass) AS outputs, SUM(ri.stoichiometry) AS is, SUM(ro.stoichiometry) AS os " +
                "WHERE is = 1 AND os = 1 AND NONE(sc IN inputs WHERE sc IN outputs)  " +
                "OPTIONAL MATCH (a)-[:created]->(rle) " +
                "OPTIONAL MATCH (m)-[:modified]->(rle) " +
                "RETURN DISTINCT rle.stId AS Identifier, rle.displayName AS Name, rle.schemaClass AS ReactionType, inputs AS Inputs, outputs AS Outputs, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, Identifier";
    }
}
