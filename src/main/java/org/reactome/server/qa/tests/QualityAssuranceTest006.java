package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

/**
 * @author Florian Korninger <florian.korninger@ebi.ac.uk>
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest006 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "EwasWithoutReferenceEntity";
    }

    @Override
    String getQuery() {
        return " MATCH (n:EntityWithAccessionedSequence) " +
                "WHERE NOT (n)-[:referenceEntity]->() " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "RETURN n.dbId AS dbId, n.stId AS stId, n.displayName AS name, a.displayName AS author";
    }
}
