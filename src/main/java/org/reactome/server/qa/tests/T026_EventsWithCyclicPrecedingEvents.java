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
public class T026_EventsWithCyclicPrecedingEvents extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "When an Event class instance (A) contains another instance (B) in the precedingEvent slot and A is used in any slot of B then both instances are reported";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.MEDIUM;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("DbIdA", "StIdA", "NameA", "DbIdB", "StIdB", "NameB", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (n:Event)-[r:precedingEvent]->(x:Event), " +
                "      (n)<-[e]-(x) " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN DISTINCT(n.dbId) AS DbIdA, n.stId AS StIdA, n.displayName AS NameA, x.dbId AS DbIdB, x.stId AS StIdB, x.displayName AS NameB, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, StIdA, DbIdA, StIdB, DbIdB";
    }
}

