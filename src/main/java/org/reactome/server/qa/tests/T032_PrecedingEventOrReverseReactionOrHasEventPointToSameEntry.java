package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Florian Korninger <florian.korninger@ebi.ac.uk>
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class T032_PrecedingEventOrReverseReactionOrHasEventPointToSameEntry extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "PrecedingEvent or ReverseReaction or HasEvent point to same Event instance";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.MEDIUM;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("dbIdA", "stIdA", "NameA", "dbIdB", "stIdB", "NameB", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (n)-[r:precedingEvent|reverseReaction|hasEvent]->(x), " +
                "      (n)-[e]->(x) " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN DISTINCT n.dbId AS dbIdA, n.stId AS stIdA, n.displayName AS NameA, x.dbId AS dbIdB, x.stId AS stIdB, x.displayName AS NameB, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, stIdA, dbIdA, stIdB, dbIdB";
    }
}

