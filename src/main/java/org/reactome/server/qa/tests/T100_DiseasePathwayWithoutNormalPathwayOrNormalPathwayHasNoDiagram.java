package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class T100_DiseasePathwayWithoutNormalPathwayOrNormalPathwayHasNoDiagram extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "Disease Pathway instances without normalPathway or NormalPathway has no diagram";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.HIGH;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("Identifier", "DiseasePathway", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH p = (dp:Pathway{isInDisease:True, hasDiagram:True})-[:hasEvent*]->(:ReactionLikeEvent)-[:normalReaction]->(:ReactionLikeEvent) " +
                "WHERE SINGLE(n IN NODES(p) WHERE EXISTS(n.hasDiagram) AND n.hasDiagram) " +
                "      AND (NOT (dp)-[:normalPathway]->() " +
                "      OR (dp)-[:normalPathway]->(:Pathway{hasDiagram:False})) " +
                "OPTIONAL MATCH (a)-[:created]->(dp) " +
                "OPTIONAL MATCH (m)-[:modified]->(dp) " +
                "RETURN DISTINCT dp.stId AS Identifier, dp.displayName AS DiseasePathway, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, Identifier";
    }
}
