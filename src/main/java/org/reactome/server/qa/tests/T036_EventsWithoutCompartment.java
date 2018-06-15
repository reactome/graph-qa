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
public class T036_EventsWithoutCompartment extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "Event class instances where the compartment slot is empty";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.HIGH;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("dbId", "stId", "Name", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (e:Event) " +
                "WHERE NOT (e)-[:compartment]->(:Compartment) " +
                "OPTIONAL MATCH (a)-[:created]->(e) " +
                "OPTIONAL MATCH (m)-[:modified]->(e) " +
                "RETURN e.dbId AS dbId, e.stId AS stId, e.displayName AS Name, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, dbId";
    }
}