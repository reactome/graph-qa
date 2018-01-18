package org.reactome.server.qa.tests;

import org.neo4j.ogm.model.Result;
import org.reactome.server.qa.annotations.QATest;

import java.io.IOException;
import java.nio.file.Path;

/**
 *
 * Reactions that are binding but something else at the same time
 *
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
//@QATest
@Deprecated
public class QualityAssuranceTest081 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "InconsistentlyAnnotatedBindingReactions";
    }

    @Override
    String getQuery() {
        return " MATCH (i:PhysicalEntity)<-[ri:input]-(rle:ReactionLikeEvent{isInferred:False})-[ro:output]->(o:PhysicalEntity) " +
                "WHERE NOT (rle:BlackBoxEvent) AND NOT (rle)-[:catalystActivity]->() " +
                "WITH DISTINCT rle, COLLECT(i) AS is, COLLECT(DISTINCT ri) AS ris, COLLECT(o) AS os, COLLECT(DISTINCT ro) AS ros " +
                "WHERE ANY(pe IN os WHERE (pe:Complex)) " +
                "WITH rle, REDUCE(n=0, e IN ris | n + e.stoichiometry) AS inputs, REDUCE(n=0, e IN ros | n + e.stoichiometry) AS outputs " +
                "WHERE inputs > outputs AND outputs > 1 " +
                "OPTIONAL MATCH (a)-[:created]->(rle) " +
                "OPTIONAL MATCH (m)-[:modified]->(rle) " +
                "RETURN DISTINCT rle.stId AS Identifier, rle.displayName AS Reaction, inputs, outputs, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, Identifier";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "Identifier", "Reaction", "inputs", "outputs", "Created", "Modified");
    }
}
