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
public class T040_HasMemberRelationshipDuplication extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "DatabaseObject class instances where the hasMember slot contains duplicated entries";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.HIGH;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("dbIdA", "stIdA", "NameA", "dbIdB", "stIdB", "NameB", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (es:EntitySet)-[r:hasMember]->(m) " +
                "WHERE NOT (m:EntitySet) AND r.stoichiometry > 1 " +
                "OPTIONAL MATCH (a)-[:created]->(es) " +
                "OPTIONAL MATCH (b)-[:modified]->(es) " +
                "RETURN DISTINCT es.dbId AS dbIdA, es.stId AS stIdA, es.displayName AS NameA, m.dbId AS dbIdB, m.stId AS stIdB, m.displayName AS NameB, a.displayName AS Created, b.displayName AS Modified " +
                "ORDER BY Created, Modified, stIdA, dbIdA, stIdB, dbIdB";
    }
}


