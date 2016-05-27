package org.reactome.server.qa.tests;

import org.neo4j.ogm.model.Result;
import org.reactome.server.qa.QATest;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by flo on 27/05/16.
 */
    @SuppressWarnings("unused")
    @QATest
    public class QualityAssuranceTest052 extends QualityAssuranceAbstract {

        @Override
        String getName() {
            return "PersonsWithSameName";
        }

        @Override
        String getQuery() {
            return "Match (n:Person),(p:Person) WHERE NOT n=p AND n.surname = p.surname AND n.firstname = p.firstname " +
                    "RETURN DISTINCT n.dbId AS dbIdA, n.displayName AS nameA, p.dbId AS dbIdB, p.displayName AS nameB";
        }

        @Override
        void printResult(Result result, Path path) throws IOException {
            print(result,path,"dbIdA","nameA","dbIdB","nameB");
        }


    }
