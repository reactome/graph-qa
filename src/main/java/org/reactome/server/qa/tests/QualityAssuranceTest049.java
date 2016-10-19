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
public class QualityAssuranceTest049 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "InstanceEditModifiesInstanceEdit";
    }

    @Override
    String getQuery() {
        return " MATCH (n:InstanceEdit)-[r:modified]-(m:InstanceEdit) " +
                "RETURN DISTINCT(n.dbId) AS dbIdA, n.displayName AS nameA, m.dbId AS dbIdB, m.displayName AS nameB";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "dbIdA", "nameA", "dbIdB", "nameB");
    }
}