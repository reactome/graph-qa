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
public class QualityAssuranceTest026 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "CuratedEventsWithCyclicPrecedingEvents";
    }

    @Override
    String getQuery() {
        return " MATCH (n:Event)-[r:precedingEvent]->(x:Event), " +
                "      (n)<-[e]-(x) " +
                "WHERE NOT ()-[:inferredTo]->(n) " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN DISTINCT(n.dbId) AS DbIdA, n.stId AS StIdA, n.displayName AS NameA, x.dbId AS DbIdB, x.stId AS StIdB, x.displayName AS NameB, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, StIdA, DbIdA, StIdB, DbIdB";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "DbIdA", "StIdA", "NameA", "DbIdB", "StIdB", "NameB", "Created", "Modified");
    }
}

