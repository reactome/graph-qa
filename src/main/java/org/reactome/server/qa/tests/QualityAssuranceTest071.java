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
public class QualityAssuranceTest071 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "ReactionsWithOnlyOneInputAndOutputWhereSchemaClassDoNotMatch";
    }

    @Override
    String getQuery() {
        return " MATCH (i:PhysicalEntity)<-[ri:input]-(rle:ReactionLikeEvent{isInferred:False})-[ro:output]->(o:PhysicalEntity) " +
                "WHERE NOT (rle:Depolymerisation) AND NOT (rle:Polymerisation) " + // AND NOT (rle)-[:catalystActivity]->()
                "WITH rle, COLLECT(i.schemaClass) AS inputs, COLLECT(o.schemaClass) AS outputs, SUM(ri.stoichiometry) AS is, SUM(ro.stoichiometry) AS os " +
                "WHERE is = 1 AND os = 1 AND NONE(sc IN inputs WHERE sc IN outputs)  " +
                "OPTIONAL MATCH (a)-[:created]->(rle) " +
                "OPTIONAL MATCH (m)-[:modified]->(rle) " +
                "RETURN DISTINCT rle.stId AS identifier, rle.displayName AS name, rle.schemaClass AS reaction_type, inputs, outputs, a.displayName AS created, m.displayName AS modified " +
                "ORDER BY created, modified, identifier";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "identifier", "name", "reaction_type", "inputs", "outputs", "created", "modified");
    }
}
