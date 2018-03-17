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
public class QualityAssuranceTest017 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "NOT_FailedReactionsWithoutOutputs";
    }

    @Override
    String getQuery() {
        return " MATCH (rle:ReactionLikeEvent) " +
                "WHERE NOT (rle:FailedReaction) AND NOT (rle)-[:output]->() " +
                "OPTIONAL MATCH (a)-[:created]->(rle) " +
                "OPTIONAL MATCH (m)-[:modified]->(rle)  " +
                "RETURN rle.stId AS identifier, rle.displayName as name, rle.schemaClass AS SchemaClass, a.displayName AS created, m.displayName AS modified " +
                "ORDER BY created, modified, identifier";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "identifier", "name", "SchemaClass", "created", "modified");
    }
}

