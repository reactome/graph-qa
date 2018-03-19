package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
@Deprecated
public class T056_InferredComplexesWithOnlyOneComponent extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public QAPriority getPriority() {
        return null;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("Identifier", "Complex", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (c:Complex)-[hc:hasComponent]->(pe:PhysicalEntity) " +
                "WHERE ()-[:inferredTo]->(c) " +
                "WITH DISTINCT c, SUM(hc.stoichiometry) AS n, COUNT(pe) AS pes " +
                "WHERE pes = 1 AND n = 1 " +
                "OPTIONAL MATCH (a)-[:created]->(c) " +
                "OPTIONAL MATCH (m)-[:modified]->(c) " +
                "RETURN DISTINCT c.stId AS Identifier, c.displayName AS Complex, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, Identifier";
    }
}
