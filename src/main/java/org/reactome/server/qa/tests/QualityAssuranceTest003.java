package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

/**
 * @author Florian Korninger <florian.korninger@ebi.ac.uk>
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest003 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "DatabaseIdentifierWithoutIdentifier";
    }

    @Override
    String getQuery() {
        return " MATCH (n:DatabaseIdentifier) " +
                "WHERE n.identifier is NULL " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN n.dbId AS dbId, n.stId AS stId, n.displayName AS name, a.displayName AS created, m.displayName AS modified " +
                "ORDER BY created, modified, stId, dbId";
    }
}