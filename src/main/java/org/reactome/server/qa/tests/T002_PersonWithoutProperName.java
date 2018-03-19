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
public class T002_PersonWithoutProperName extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "Surname is empty or the Firstname and the Initial are also empty";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.LOW;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("dbId", "Name", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (n:Person) " +
                "WHERE n.surname is NULL OR (n.firstname is NULL AND n.initial is NULL) " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN n.dbId AS dbId, n.displayName AS Name, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, dbId";
    }
}
