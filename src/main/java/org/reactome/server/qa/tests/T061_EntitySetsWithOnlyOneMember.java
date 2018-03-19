package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class T061_EntitySetsWithOnlyOneMember extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "EntitySet class instances (excluding CandidateSet and OpenSet) that has only one entry in the hasMember slot";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.MEDIUM;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("Identifier", "EntitySet", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (es:EntitySet)-[hm:hasMember]->(pe:PhysicalEntity) " +
                "WHERE NOT (es:CandidateSet) AND NOT (es:OpenSet) " +
//                "WHERE NOT ()-[:inferredTo]->(es) AND NOT (es:CandidateSet) AND NOT (es:OpenSet) " +
                "WITH DISTINCT es, COUNT(DISTINCT pe) AS pes " +
                "WHERE pes = 1 " +
                "OPTIONAL MATCH (a)-[:created]->(es) " +
                "OPTIONAL MATCH (m)-[:modified]->(es) " +
                "RETURN DISTINCT es.stId AS Identifier, es.displayName AS EntitySet, es.schemaClass AS SchemaClass, a.displayName AS Created, m.displayName AS Modified  " +
                "ORDER BY Created, Modified, Identifier";
    }
}
