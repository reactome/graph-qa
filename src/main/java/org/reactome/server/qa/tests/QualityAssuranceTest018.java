package org.reactome.server.qa.tests;

import org.neo4j.ogm.model.Result;
import org.reactome.server.qa.annotations.QATest;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Florian Korninger <florian.korninger@ebi.ac.uk>
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 *         TODO Why do those Instances have no created attribute
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest018 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "DatabaseObjectsWithoutCreated";
    }

    @Override
    String getQuery() {
        return " MATCH (n:DatabaseObject) " +
                "WHERE NOT ((n:InstanceEdit) OR (n:DatabaseIdentifier) OR (n:Taxon) OR (n:Person) OR (n:ReferenceEntity)) " +
                "      AND NOT (n)-[:created]-() " +
                "RETURN n.dbId AS dbId, n.displayName AS name";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "dbId", "name");
    }
}