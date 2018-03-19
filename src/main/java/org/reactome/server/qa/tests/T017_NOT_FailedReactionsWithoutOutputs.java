package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class T017_NOT_FailedReactionsWithoutOutputs extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "ReactionLikeEvent class instance (excluding FailedReaction class instances) where the output slot is empty";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.HIGH;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("stId", "Name", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (rle:ReactionLikeEvent) " +
                "WHERE NOT (rle:FailedReaction) AND NOT (rle)-[:output]->() " +
                "OPTIONAL MATCH (a)-[:created]->(rle) " +
                "OPTIONAL MATCH (m)-[:modified]->(rle)  " +
                "RETURN rle.stId AS stId, rle.displayName as Name, rle.schemaClass AS SchemaClass, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, stId";
    }
}

