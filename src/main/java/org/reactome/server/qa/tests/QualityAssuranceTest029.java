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
public class QualityAssuranceTest029 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "ReactionsLikeEventWithoutInput";
    }

    @Override
    String getQuery() {
        return " MATCH (rle:ReactionLikeEvent{isInferred:False}) " +
                "WHERE NOT (rle)-[:input]->() " +
                "OPTIONAL MATCH (a)-[:created]->(rle) " +
                "OPTIONAL MATCH (m)-[:modified]->(rle) " +
                "RETURN rle.stId AS stId, rle.displayName AS name, rle.schemaClass AS SchemaClass, a.displayName AS created, m.displayName AS modified " +
                "ORDER BY created, modified, stId";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "stId", "name", "SchemaClass", "created", "modified");
    }
}


