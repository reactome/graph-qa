package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

/**
 * @author Florian Korninger <florian.korninger@ebi.ac.uk>
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest020 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "OpenSetsWithoutReferenceEntity";
    }

    @Override
    String getQuery() {
        return " MATCH (n:OpenSet) " +
                "WHERE NOT (n)-[:referenceEntity]->() " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "RETURN n.dbId AS dbId, n.stId AS stId, n.displayName AS name, a.displayName AS author";
    }
}

