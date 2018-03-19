package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class T057_ComplexesWithOnlyOneComponent extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "Complex class instances with only one entry in the hasComponent slot";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.HIGH;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("Identifier", "Complex", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (c:Complex)-[hc:hasComponent]->(pe:PhysicalEntity) " +
//                "WHERE NOT ()-[:inferredTo]->(c) " +
                "WITH DISTINCT c, SUM(hc.stoichiometry) AS n, COUNT(pe) AS pes " +
                "WHERE pes = 1 AND n = 1 " +
                "OPTIONAL MATCH (a)-[:created]->(c) " +
                "OPTIONAL MATCH (m)-[:modified]->(c) " +
                "RETURN DISTINCT c.stId AS Identifier, c.displayName AS Complex, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, Identifier";
    }
}
