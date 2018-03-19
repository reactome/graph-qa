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
public class T045_OtherRelationshipDuplication extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "DatabaseObject class instances with duplicated instances in a multivalued slot (omitting the relationships where duplicates are allowed)";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.LOW;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("dbIdA", "NameA", "Relationship", "dbIdB", "NameB", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (n)<-[r]-(m) " +
                "WHERE NOT ()-[r:hasComponent|input|output|repeatedUnit|modified|" +
                "crossReference|author|literatureReference|hasModifiedResidue|precedingEvent|hasMember|summation|psiMod|" +
                "hasCandidate|hasEvent]-() AND r.stoichiometry > 1 " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN DISTINCT n.dbId AS dbIdA, n.displayName AS NameA, TYPE(r) AS Relationship, m.dbId AS dbIdB, m.displayName AS NameB, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, dbIdA, dbIdB";
    }
}



