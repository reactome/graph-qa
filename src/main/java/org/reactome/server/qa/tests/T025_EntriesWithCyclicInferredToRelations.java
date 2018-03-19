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
public class T025_EntriesWithCyclicInferredToRelations extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "When DatabaseObjec class instance (A) is used to infer another instance (B), if A is used in any slot of B then both instances are reported";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.MEDIUM;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("dbIdA", "stIdA", "nameA", "dbIdB", "stIdB", "nameB", "created", "modified");
    }

    @Override
    String getQuery() {
        return " MATCH (n)-[r:inferredTo]->(x), " +
                "      (n)<-[e]-(x) " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN DISTINCT(n.dbId) AS dbIdA, n.stId AS stIdA, n.displayName AS NameA, x.dbId AS dbIdB, x.stId AS stIdB, x.displayName AS NameB, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, stIdA, dbIdA, stIdB, dbIdB";
    }
}