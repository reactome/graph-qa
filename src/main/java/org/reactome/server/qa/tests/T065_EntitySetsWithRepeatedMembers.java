package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class T065_EntitySetsWithRepeatedMembers extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "EntitySet class instances (excluding CandidateSet) where the same PhysicalEntity class instance is used more than once in the hasMember slot";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.MEDIUM;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("Identifier", "EntitySet", "Targets", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (es:EntitySet)-[hm:hasMember]->(pe:PhysicalEntity) " +
                "WHERE NOT (es:CandidateSet) AND hm.stoichiometry > 1 " +
//                "      AND NOT ()-[:inferredTo]->(es) " +
                "WITH DISTINCT es, COLLECT(pe.stId) AS Targets " +
                "OPTIONAL MATCH (a)-[:created]->(es) " +
                "OPTIONAL MATCH (m)-[:modified]->(es) " +
                "RETURN DISTINCT es.stId AS Identifier, es.displayName AS EntitySet, Targets, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, Identifier";
    }
}
