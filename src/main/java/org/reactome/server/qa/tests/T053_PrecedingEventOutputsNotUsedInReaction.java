package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class T053_PrecedingEventOutputsNotUsedInReaction extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "ReactionLikeEvent (A) annotated as preceding of another one (B) where none outputs or A are present as input, catalyst or regulator of B";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.MEDIUM;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("PrecedingEvent", "P_Name", "FollowingEvent", "F_Name", "Created", "Modified");
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
}
