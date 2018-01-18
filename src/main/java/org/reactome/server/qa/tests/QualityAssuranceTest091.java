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
public class QualityAssuranceTest091 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "ReactionsWithNoLiteratureReferenceAnywhere";
    }

    @Override
    String getQuery() {
        return " MATCH (rle:ReactionLikeEvent{isInferred:False})-[:summation]->(s:Summation) " +
                "WHERE NOT (rle)-[:literatureReference]->() AND NOT (s)-[:literatureReference*]->() " +
                "OPTIONAL MATCH (a)-[:created]->(rle) " +
                "OPTIONAL MATCH (m)-[:modified]->(rle) " +
                "RETURN rle.stId AS Identifier, rle.displayName AS Reaction, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Identifier";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "Identifier", "Reaction", "Created", "Modified");
    }
}
