package org.reactome.server.qa.tests;

import org.neo4j.ogm.model.Result;
import org.reactome.server.qa.annotations.QATest;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest062 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "DuplicatedReferenceEntities";
    }

    @Override
    String getQuery() {
        return " MATCH (pe1:PhysicalEntity)-[:referenceEntity]->(re1:ReferenceEntity),  " +
                "      (pe2:PhysicalEntity)-[:referenceEntity]->(re2:ReferenceEntity) " +
//                "WHERE NOT ()-[:inferredTo]->(pe1) AND NOT ()-[:inferredTo]->(pe2) " +
                "WHERE re1.databaseName = re2.databaseName " +
                "      AND re1.identifier = re2.identifier " +
                "      AND (re1.variantIdentifier = re2.variantIdentifier OR (re1.variantIdentifier IS NULL AND re2.variantIdentifier IS NULL)) " +
                "      AND re1.dbId < re2.dbId " +
                "WITH DISTINCT re1, re2 " +
                "OPTIONAL MATCH (a1)-[:created]->(re1) " +
                "OPTIONAL MATCH (m1)-[:modified]->(re1) " +
                "OPTIONAL MATCH (a2)-[:created]->(re2) " +
                "OPTIONAL MATCH (m2)-[:modified]->(re2) " +
                "RETURN re1.dbId AS RE1_dbId, re1.displayName AS RE1_Name, a1.displayName AS RE1_Created, m1.displayName AS RE1_Modified, " +
                "       re2.dbId AS RE2_dbId, re2.displayName AS RE2_Name, a2.displayName AS RE2_Created, m2.displayName AS RE2_Modified " +
                "ORDER BY RE1_Created, RE1_Modified";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "RE1_dbId", "RE1_Name", "RE1_Created", "RE1_Modified", "RE2_dbId", "RE2_Name", "RE2_Created", "RE2_Modified");
    }
}
