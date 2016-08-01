package org.reactome.server.qa.tests;

import org.neo4j.ogm.model.Result;
import org.reactome.server.qa.annotations.QATest;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Florian Korninger <florian.korninger@ebi.ac.uk>
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest033 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "OtherRelationsThatPointToTheSameEntry";
    }

    @Override
    String getQuery() {
        return " MATCH (n)-[r]->(x), " +
                "      (n)-[e]->(x) " +
                "WHERE NOT (n)-[:author|created|edited|modified|revised|reviewed|input|output|entityOnOtherCell|" +
                "hasComponent|requiredInputComponent|physicalEntity|activeUnit|reverseReaction|precedingEvent|hasEvent|" +
                "goCellularComponent|compartment|referenceSequence|secondReferenceSequence|hasCandidate|hasMember]-(x) " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "RETURN DISTINCT(n.dbId) AS dbIdA,n.stId AS stIdA, n.displayName AS nameA, x.dbId AS dbIdB, x.stId AS stIdB, x.displayName AS nameB, a.displayName AS author";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "dbIdA", "stIdA", "nameA", "dbIdB", "stIdB", "nameB", "author");
    }
}

