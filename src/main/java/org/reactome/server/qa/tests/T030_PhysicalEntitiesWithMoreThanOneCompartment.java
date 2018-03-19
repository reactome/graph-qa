package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class T030_PhysicalEntitiesWithMoreThanOneCompartment extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "PhysicalEntity class instances where the compartment slot contains more than one Compartment class instance";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.MEDIUM;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("PE", "Name", "SchemaClass", "Compartments", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (pe:PhysicalEntity)-[:compartment]->(c:Compartment) " +
                "WHERE NOT (pe)-[:entityOnOtherCell]->() AND NOT ()-[:inferredTo]->(pe) " +
                "OPTIONAL MATCH (a)-[:created]->(pe) " +
                "OPTIONAL MATCH (m)-[:modified]->(pe) " +
                "WITH pe, COUNT(c) as compartments, a, m " +
                "WHERE compartments > 1 " +
                "RETURN pe.stId AS PE, pe.displayName AS Name, pe.schemaClass AS SchemaClass, compartments AS Compartments, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, pe.speciesName";
    }
}

