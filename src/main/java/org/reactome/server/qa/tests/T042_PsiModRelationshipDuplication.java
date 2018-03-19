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
public class T042_PsiModRelationshipDuplication extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "DatabaseObject class instances where the psiMod slot contains duplicated entries";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.MEDIUM;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("dbIdA", "NameA", "dbIdB", "NameB", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (x)-[r:psiMod]->(y) " +
                "WHERE r.stoichiometry > 1 " +
                "OPTIONAL MATCH (a)-[:created]->(x) " +
                "OPTIONAL MATCH (m)-[:modified]->(x) " +
                "RETURN DISTINCT x.dbId AS dbIdA, x.displayName AS NameA, y.dbId AS dbIdB, y.displayName AS NameB, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, dbIdA, dbIdB";
    }
}

