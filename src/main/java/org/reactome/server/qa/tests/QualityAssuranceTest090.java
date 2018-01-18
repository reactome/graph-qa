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
public class QualityAssuranceTest090 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "CatalystActivityCompartmentDoesNotMatchReactionCompartment";
    }

    @Override
    String getQuery() {
        return " MATCH (crle:Compartment)<-[:compartment]-(rle:ReactionLikeEvent{isInferred:False})-[:catalystActivity|physicalEntity|compartment*]->(cac:Compartment) " +
                "WITH DISTINCT rle, COLLECT(crle) AS crles, COLLECT(cac) AS cacs " +
                "WHERE NONE(c IN cacs WHERE c IN crles) OR NONE(c IN crles WHERE c IN cacs) " +
                "OPTIONAL MATCH (a)-[:created]->(rle) " +
                "OPTIONAL MATCH (m)-[:modified]->(rle) " +
                "RETURN rle.stId AS Identifier, rle.displayName AS Reaction, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Identifier";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "Identifier", "Reaction", "Created", "Modified");
    }
}
