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
public class T024_ReferenceDatabaseWithoutUrls extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "ReferenceDatabase class instances where accessUrl slot or url slot are empty (either of them or both)";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.HIGH;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("dbId", "Name", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (n:ReferenceDatabase) " +
                "WHERE n.accessUrl is NULL OR n.url is NULL " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN n.dbId AS dbId, n.displayName AS Name, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, dbId";
    }
}




