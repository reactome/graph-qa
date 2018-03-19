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
public class T034_ModifiedRelationshipDuplication extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "DatabaseObject class instances where the modified slot contains duplicated entries";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.LOW;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("dbIdA", "NameA", "dbIdB", "NameB", "Created");
    }

    @Override
    String getQuery() {
        return " MATCH (n)<-[r:modified]-(m) " +
                "WHERE r.stoichiometry > 1 " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "RETURN DISTINCT n.dbId AS dbIdA, n.displayName AS NameA, m.dbId AS dbIdB, m.displayName AS NameB, a.displayName AS Created " +
                "ORDER BY Created";
    }
}
