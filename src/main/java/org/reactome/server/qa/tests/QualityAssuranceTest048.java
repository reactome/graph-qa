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
public class QualityAssuranceTest048 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "InstanceEditCreatesInstanceEdit";
    }

    @Override
    String getQuery() {
        return " MATCH (n:InstanceEdit)-[r:created]-(m:InstanceEdit)" +
                "RETURN DISTINCT(n.dbId) AS dbIdA, n.displayName AS nameA, m.dbId AS dbIdB, m.displayName as nameB";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "dbIdA", "nameA", "dbIdB", "nameB");
    }
}
