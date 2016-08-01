package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

/**
 * @author Florian Korninger <florian.korninger@ebi.ac.uk>
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest046 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "OrphanEventsNotComputationalInferred";
    }

    @Override
    String getQuery() {
        return " MATCH (n:Event) " +
                "WHERE NOT (n)<-[:hasEvent]-() AND " +
                "      NOT (n:TopLevelPathway) AND " +
                "      n.isInferred = false " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "RETURN DISTINCT(n.dbId) AS dbId, n.displayName AS name, n.stId as stId, a.displayName AS author";
    }
}



