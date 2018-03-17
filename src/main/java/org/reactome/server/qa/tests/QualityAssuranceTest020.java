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
public class QualityAssuranceTest020 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "OpenSetsWithoutReferenceEntity";
    }

    @Override
    String getQuery() {
        return " MATCH (os:OpenSet) " +
                "WHERE NOT (os)-[:referenceEntity]->() " +
                "OPTIONAL MATCH (a)-[:created]->(os) " +
                "OPTIONAL MATCH (m)-[:modified]->(os) " +
                "RETURN DISTINCT os.stId AS Identifier, os.displayName AS OpenSet, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, Identifier";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "Identifier", "OpenSet", "Created", "Modified");
    }
}

