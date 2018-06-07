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
public class T022_PhysicalEntityWithoutCompartment extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "PhysicalEntity class instances where the compartment slot is empty";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.BLOCKER;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("dbId", "stId", "Name", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (pe:PhysicalEntity) " +
                "WHERE NOT (pe)-[:compartment]->(:Compartment) " +
                "OPTIONAL MATCH (a)-[:created]->(pe) " +
                "OPTIONAL MATCH (m)-[:modified]->(pe) " +
                "RETURN pe.dbId AS dbId, pe.stId AS stId, pe.displayName AS Name, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, dbId";
    }
}


