package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

/**
 * @author Florian Korninger <florian.korninger@ebi.ac.uk>
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest047 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "InferredOrphanEvents";
    }

    @Override
    String getQuery() {
        return " MATCH (n:Event{isInferred:True}) " +
                "WHERE NOT (n)<-[:hasEvent]-() AND NOT (n:TopLevelPathway) " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN DISTINCT(n.dbId) AS dbId, n.displayName AS name, n.stId as stId, a.displayName AS created, m.displayName AS modified " +
                "ORDER BY created, modified, stId, dbId";
    }
}