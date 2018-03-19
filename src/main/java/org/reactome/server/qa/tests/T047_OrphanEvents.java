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
public class T047_OrphanEvents extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "Events that cannot be reached trhough the events hierarchy";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.MEDIUM;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("Identifier", "Name", "Inferred", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (n:Event) " +
                "WHERE NOT (n)<-[:hasEvent]-() AND NOT (n:TopLevelPathway) " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN DISTINCT n.stId AS Identifier, n.displayName AS Name, n.isInferred AS Inferred, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, Identifier";
    }
}