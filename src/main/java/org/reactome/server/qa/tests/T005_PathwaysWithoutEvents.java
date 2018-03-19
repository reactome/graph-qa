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
public class T005_PathwaysWithoutEvents extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "Pathway class instances where the hasEvent slot is empty";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.BLOCKER;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("dbId", "stId", "Name", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (n:Pathway) " +
                "WHERE NOT (n)-[:hasEvent]->() " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN n.dbId AS dbId, n.stId AS stId, n.displayName AS Name, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, stId, dbId";
    }
}
