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
public class T015_CatalystActivityWithoutPhysicalEntity extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "CatalystActivity class instances where the physicalEntity slot is empty";
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
        return " MATCH (n:CatalystActivity) " +
                "WHERE NOT (n)-[:physicalEntity]->() " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN n.dbId AS dbId, n.displayName AS Name, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, dbId";
    }
}