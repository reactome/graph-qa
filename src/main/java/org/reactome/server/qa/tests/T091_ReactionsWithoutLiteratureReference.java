package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class T091_ReactionsWithoutLiteratureReference extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "Reactions with no LiteratureReference class instances in any of the possible locations for it";
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
        return " MATCH (rle:ReactionLikeEvent{isInferred:False})-[:summation]->(s:Summation) " +
                "WHERE NOT (rle)-[:literatureReference]->() AND NOT (s)-[:literatureReference*]->() " +
                "OPTIONAL MATCH (a)-[:created]->(rle) " +
                "OPTIONAL MATCH (m)-[:modified]->(rle) " +
                "RETURN rle.stId AS Identifier, rle.displayName AS Reaction, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Identifier";
    }
}
