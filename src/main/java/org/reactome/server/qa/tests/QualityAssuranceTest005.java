package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

/**
 * @author Florian Korninger <florian.korninger@ebi.ac.uk>
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest005 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "PathwaysWithoutEvents";
    }

    @Override
    String getQuery() {
        return " MATCH (n:Pathway) " +
                "WHERE NOT (n)-[:hasEvent]->() " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN n.dbId AS dbId, n.stId AS stId, n.displayName AS name, a.displayName AS created, m.displayName AS modified " +
                "ORDER BY created, modified, stId, dbId";
    }
}
