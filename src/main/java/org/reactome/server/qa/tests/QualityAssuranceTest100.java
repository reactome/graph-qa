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
public class QualityAssuranceTest100 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "DiseasePathwayWithoutNormalPathwayOrNormalPathwayHasNoDiagram";
    }

    @Override
    String getQuery() {
        return " MATCH p = (dp:Pathway{isInDisease:True, hasDiagram:True})-[:hasEvent*]->(:ReactionLikeEvent)-[:normalReaction]->(:ReactionLikeEvent) " +
                "WHERE SINGLE(n IN NODES(p) WHERE EXISTS(n.hasDiagram) AND n.hasDiagram) AND (NOT (dp)-[:normalPathway]->() OR (dp)-[:normalPathway]->(:Pathway{hasDiagram:False})) " +
                "OPTIONAL MATCH (a)-[:created]->(dp) " +
                "OPTIONAL MATCH (m)-[:modified]->(dp) " +
                "RETURN DISTINCT dp.stId AS identifier, dp.displayName AS DiseasePathway, a.displayName AS created, m.displayName AS modified " +
                "ORDER BY created, modified, identifier";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "Identifier", "Reaction", "Created", "Modified");
    }
}
