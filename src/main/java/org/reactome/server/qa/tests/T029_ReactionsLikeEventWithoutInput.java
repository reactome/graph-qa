package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class T029_ReactionsLikeEventWithoutInput extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "ReactionLikeEvent instances where the input slot is empty";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.HIGH;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("stId", "Name", "SchemaClass", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (rle:ReactionLikeEvent{isInferred:False}) " +
                "WHERE NOT (rle)-[:input]->() " +
                "OPTIONAL MATCH (a)-[:created]->(rle) " +
                "OPTIONAL MATCH (m)-[:modified]->(rle) " +
                "RETURN rle.stId AS stId, rle.displayName AS Name, rle.schemaClass AS SchemaClass, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, stId";
    }
}


