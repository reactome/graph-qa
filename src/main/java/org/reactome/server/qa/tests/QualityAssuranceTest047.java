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
public class QualityAssuranceTest047 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "OrphanEvents";
    }

    @Override
    String getQuery() {
        return " MATCH (n:Event) " +
                "WHERE NOT (n)<-[:hasEvent]-() AND NOT (n:TopLevelPathway) " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN DISTINCT n.stId AS Identifier, n.displayName AS Name, n.isInferred AS Inferred, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, Identifier";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "Identifier", "Name", "Inferred", "Created", "Modified");
    }
}