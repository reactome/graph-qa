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
public class QualityAssuranceTest053 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "PrecedingEventOutputsNotUsedInReaction";
    }

    @Override
    String getQuery() {
        return " MATCH (f:ReactionLikeEvent) WHERE NOT ()-[:inferredTo]->(f) " +
                "WITH f " +
                "MATCH (p:ReactionLikeEvent)<-[:precedingEvent]-(f)," +
                "      (p)-[:output|hasMember|hasCandidate*]->(o:PhysicalEntity), " +
                "      (f)-[:input|hasMember|hasCandidate*]->(i:PhysicalEntity) " +
                "OPTIONAL MATCH (f)-[:catalystActivity]->()-[:physicalEntity|hasMember|hasCandidate*]->(cp:PhysicalEntity) " +
                "OPTIONAL MATCH (f)-[:regulatedBy]->()-[:regulator|hasMember|hasCandidate*]->(rp:PhysicalEntity) " +
                "WITH p, f, collect(i) AS iss, collect(cp) AS cps, collect(rp) AS rps, collect(o) AS oss " +
                "WHERE NONE (x IN oss WHERE x IN iss) " +
                "      AND NONE(x IN oss WHERE x IN cps) " +
                "      AND NONE(x IN oss WHERE x IN rps) " +
                "OPTIONAL MATCH (a)-[:created]->(f) " +
                "OPTIONAL MATCH (m)-[:modified]->(f) " +
                "RETURN DISTINCT p.stId AS PrecedingEvent, p.displayName AS P_Name, f.stId AS FollowingEvent, f.displayName AS F_Name, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, PrecedingEvent";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "PrecedingEvent", "P_Name", "FollowingEvent", "F_Name", "Created", "Modified");
    }
}
