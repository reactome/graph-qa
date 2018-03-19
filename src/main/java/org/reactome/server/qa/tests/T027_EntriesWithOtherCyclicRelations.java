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
public class T027_EntriesWithOtherCyclicRelations extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "Same concept as the two above but checking for other slots (and not focusing only in Events)";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.MEDIUM;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("dbIdA", "stIdA", "NameA", "AtoB", "BtoA", "dbIdB", "stIdB", "NameB", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (n)-[r]->(x)-[e]->(n) " +
                "WHERE NOT TYPE(r) IN [\"hasEncapsulatedEvent\", \"precedingEvent\", \"inferredTo\"] " + //precedingEvent and inferredTo are reported in QA25 and AQ26
                "      AND TYPE(r) = TYPE(e) " +
                "      OR NOT (n)-[:author|created|edited|modified|revised|reviewed|inferredTo|hasPart|precedingEvent|hasEncapsulatedEvent]-(x) " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN DISTINCT(n.dbId) AS dbIdA,n.stId AS stIdA, n.displayName AS NameA, TYPE(r) AS AtoB, TYPE(e) AS BtoA, x.dbId AS dbIdB, x.stId AS stIdB, x.displayName AS NameB, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, stIdA, dbIdA, stIdB, dbIdB";
    }
}

