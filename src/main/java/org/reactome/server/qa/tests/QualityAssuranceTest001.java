package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

/**
 * @author Florian Korninger <florian.korninger@ebi.ac.uk>
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest001 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "DatabaseObjectsWithSelfLoops";
    }

    @Override
    String getQuery() {
        return " MATCH (n)-[r]-(o) " +
                "WHERE n = o " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN DISTINCT (n.dbId) AS dbId, n.stId AS stId, n.displayName AS name, a.displayName AS created, m.displayName AS modified " +
                "ORDER BY created, modified, stId, dbId";
    }
}
