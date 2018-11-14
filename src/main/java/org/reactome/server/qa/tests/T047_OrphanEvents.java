package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 * @author Florian Korninger <florian.korninger@ebi.ac.uk>
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
        return " MATCH (e:Event) " +
                "WHERE NOT (e)-[:inferredTo]->() " +
                "      AND NOT (e)<-[:hasEvent]-() " +
                "      AND NOT (e:TopLevelPathway) " +
                "OPTIONAL MATCH (a)-[:created]->(e) " +
                "OPTIONAL MATCH (m)-[:modified]->(e) " +
                "RETURN DISTINCT e.stId AS Identifier, e.displayName AS Name, e.isInferred AS Inferred, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, Identifier";
    }
}