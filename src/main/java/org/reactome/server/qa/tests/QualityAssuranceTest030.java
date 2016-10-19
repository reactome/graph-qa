package org.reactome.server.qa.tests;

import org.neo4j.ogm.model.Result;
import org.reactome.server.qa.annotations.QATest;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest030 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "PhysicalEntitiesWithMoreThanOneCompartment";
    }

    @Override
    String getQuery() {
        return " MATCH (pe:PhysicalEntity)-[:compartment]->(c:Compartment) " +
                "WHERE NOT (pe)-[:entityOnOtherCell]->() AND NOT ()-[:inferredTo]->(pe) " +
                "OPTIONAL MATCH (a)-[:created]->(pe) " +
                "OPTIONAL MATCH (m)-[:modified]->(pe) " +
                "WITH pe, COUNT(c) as compartments, a, m " +
                "WHERE compartments > 1 " +
                "RETURN pe.stId AS PE, pe.displayName AS Name, pe.simpleLabel AS SchemaClass, compartments AS Compartments, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, pe.speciesName";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "PE", "Name", "SchemaClass", "Compartments", "Created", "Modified");
    }
}

