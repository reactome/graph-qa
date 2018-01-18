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
public class QualityAssuranceTest007 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "EntitiesWithoutStId";
    }

    @Override
    String getQuery() {
        return " MATCH (n) " +
                "WHERE n.stId IS NULL AND (n:PhysicalEntity OR n:Event OR n:Regulation) " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN n.dbId AS Identifier, n.displayName AS Name, n.schemaClass AS SchemaClass, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, Identifier";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "Identifier", "Name", "SchemaClass", "Created", "Modified");
    }
}
