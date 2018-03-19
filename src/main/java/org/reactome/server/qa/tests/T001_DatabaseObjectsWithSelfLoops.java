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
public class T001_DatabaseObjectsWithSelfLoops extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "The instance contains itself in any of the slots";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.MEDIUM;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("dbId", "SchemaClass", "Name", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (n)-[r]-(o) " +
                "WHERE n = o " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN DISTINCT (n.dbId) AS dbId, n.schemaClass AS SchemaClass, n.displayName AS Name, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, dbId";
    }
}
