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
public class T004_EntriesWithoutDisplayName extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "The instance's displayName slot is empty";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.HIGH;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("dbId", "SchemaClass", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (n) " +
                "WHERE NOT (n:DBInfo) AND n.displayName is NULL OR SIZE(TRIM(n.displayName))=0 " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN n.dbId AS dbId, n.schemaClass AS SchemaClass, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, dbId";
    }
}
