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
public class QualityAssuranceTest050 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "DuplicatedLiteratureReferences";
    }

    @Override
    String getQuery() {
        return " MATCH (a:LiteratureReference),(b:LiteratureReference) " +
                "WHERE NOT a = b AND a.pubMedIdentifier = b.pubMedIdentifier  " +
                "RETURN DISTINCT a.dbId AS dbIdA, a.displayName AS nameA, b.dbId AS dbIdB, b.displayName AS nameB";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "dbIdA", "nameA", "dbIdB", "nameB");
    }
}
