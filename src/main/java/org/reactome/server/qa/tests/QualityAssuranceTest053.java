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
    String getName() {
        return "PrecedingEventOutputsNotUsedInReaction";
    }

    @Override
    String getQuery() {
        return " MATCH (p:ReactionLikeEvent)<-[:precedingEvent]-(f:ReactionLikeEvent{isInferred:False}), " +
                "      (p)-[:output|hasMember*1..2]->(o:PhysicalEntity), " +
                "      (f)-[:input|hasMember*1..2]->(i:PhysicalEntity) " +
                "OPTIONAL MATCH (f)-[:catalystActivity]->()-[:physicalEntity|hasMember*1..2]->(cp:PhysicalEntity) " +
                "OPTIONAL MATCH (f)-[:regulatedBy]->()-[:regulator|hasMember*1..2]->(rp:PhysicalEntity) " +
                "WITH p, f, collect(i) AS iss, collect(cp) AS cps, collect(rp) AS rps, collect(o) AS oss " +
                "WHERE NONE (x IN oss WHERE x IN iss) " +
                "      AND NONE(x IN oss WHERE x IN cps) " +
                "      AND NONE(x IN oss WHERE x IN rps) " +
                "OPTIONAL MATCH (a)-[:created]->(f) " +
                "RETURN DISTINCT p.stId AS PrecedingEvent, p.displayName AS P_Name, f.stId AS FollowingEvent, f.displayName AS F_Name, a.displayName AS CuratedBy";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "PrecedingEvent", "P_Name", "FollowingEvent", "F_Name", "CuratedBy");
    }
}
