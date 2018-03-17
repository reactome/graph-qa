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
public class QualityAssuranceTest070 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "ReactionsWithoutRegulatorWithMoreCompartmentsThanItsParticipants";
    }

    @Override
    String getQuery() {
        return " MATCH (rle:ReactionLikeEvent) " +
                "WHERE NOT (rle:BlackBoxEvent) AND NOT (rle)-[:regulatedBy]->() AND (rle)-[:compartment]->() " +
//                "WHERE NOT ()-[:inferredTo]->(rle) AND NOT (rle:BlackBoxEvent) AND NOT (rle)-[:regulatedBy]->() AND (rle)-[:compartment]->() " +
                "WITH DISTINCT rle " +
//                "MATCH (rlec:Compartment)<-[:compartment]-(rle)-[:input|output|requiredInputComponent|catalystActivity|entityFunctionalStatus|physicalEntity|hasComponent|hasMember|hasCandidate|repeatedUnit|compartment*]->(pec:Compartment) " +
                "MATCH (rlec:Compartment)<-[:compartment]-(rle)-[:input|output|requiredInputComponent|catalystActivity|entityFunctionalStatus|physicalEntity|hasComponent|hasMember|hasCandidate|repeatedUnit*]->(:PhysicalEntity)-[:compartment]->(pec:Compartment) " +
                "WITH rle, COLLECT(DISTINCT rlec.displayName) AS rlecs, COLLECT(DISTINCT pec.displayName) AS pecs " +
                "WHERE ANY(c IN rlecs WHERE NOT c IN pecs) " +
                "OPTIONAL MATCH (a)-[:created]->(rle) " +
                "OPTIONAL MATCH (m)-[:modified]->(rle) " +
                "RETURN DISTINCT rle.stId AS identifier, rle.displayName AS name, \"[\" + REDUCE(s = \"\", x IN rlecs | s + x + \", \") + \"]\" AS reaction_compartments, \"[\" + REDUCE(s = \"\", x IN pecs | s + x + \", \") + \"]\" AS entities_compartments, a.displayName AS created, m.displayName AS modified " +
//                "RETURN DISTINCT rle.stId AS identifier, rle.displayName AS name, rlecs AS reaction_compartments, pecs AS entities_compartments, a.displayName AS created, m.displayName AS modified " +
                "ORDER BY created, modified, identifier";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "identifier", "name", "reaction_compartments", "entities_compartments", "created", "modified");
    }
}
