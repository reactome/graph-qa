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
public class T007_EntitiesWithoutStId extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "Event, PhysicalEntity or Regulation classes intances where stableIdentifier slot is empty";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.BLOCKER;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("Identifier", "Name", "SchemaClass", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (n) " +
                "WHERE n.stId IS NULL AND (n:PhysicalEntity OR n:Event) " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN n.dbId AS Identifier, n.displayName AS Name, n.schemaClass AS SchemaClass, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, Identifier";
    }
}
