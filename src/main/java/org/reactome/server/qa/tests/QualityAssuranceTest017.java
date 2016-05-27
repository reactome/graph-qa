package org.reactome.server.qa.tests;

import org.reactome.server.qa.QATest;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 08.03.16.
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest017 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "BlackBoxEventsWithoutHasEvent";
    }

    @Override
    String getQuery() {
        return "Match (n:BlackboxEvent)<-[:created]-(a) Where NOT (n)-[:hasEvent]->() RETURN n.dbId AS dbId, " +
                "n.stId AS stId, n.displayName AS name, a.displayName as author\n";
    }
}

