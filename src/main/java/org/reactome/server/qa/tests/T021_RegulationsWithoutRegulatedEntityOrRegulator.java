package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Florian Korninger <florian.korninger@ebi.ac.uk>
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class T021_RegulationsWithoutRegulatedEntityOrRegulator extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "Regulation class instances where the regulatedEntity or the regulator slots is empty (either of them or both)";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.HIGH;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("dbId", "stId", "Name", "Created", "Modified");
    }
    @Override
    String getQuery() {
        return " MATCH (n:Regulation) " +
                "WHERE NOT (n)-[:regulatedBy|regulator]-() " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN n.dbId AS dbId, n.stId AS stId, n.displayName AS Name, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, stId";
    }
}
