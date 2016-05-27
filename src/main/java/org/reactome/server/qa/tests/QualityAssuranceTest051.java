package org.reactome.server.qa.tests;

import org.neo4j.ogm.model.Result;
import org.reactome.server.qa.QATest;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by flo on 26/05/16.
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest051 extends QualityAssuranceAbstract {

    @Override
    String getName() {
        return "PersonsWithSameORCID";
    }

    @Override
    String getQuery() {
        return "Match (a:LiteratureReference),(b:LiteratureReference) " +
                "WHERE NOT a=b AND a.pubMedIdentifier = b.pubMedIdentifier  " +
                "RETURN DISTINCT a.dbId AS dbIdA, a.displayName AS nameA, b.dbId AS dbIdB, b.displayName AS nameB";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result,path,"dbIdA","nameA","dbIdB","nameB");
    }


}
