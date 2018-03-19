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
public class T019_PublicationsWithoutAuthor extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "Publication class instances where the author slot is empty";
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
        return " MATCH (n:Publication) " +
                "WHERE NOT (n)-[:author]-() " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN n.dbId AS dbId, n.displayName AS Name, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Modified, Created, dbId";
    }
}

