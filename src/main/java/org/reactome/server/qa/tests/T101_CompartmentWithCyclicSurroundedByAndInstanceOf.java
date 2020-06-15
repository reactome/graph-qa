package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
@QATest
public class T101_CompartmentWithCyclicSurroundedByAndInstanceOf extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "Compartments with cyclic relationships in the surroundedBy, componentOf and/or instanceOf slot. It could break the reaction-layout. Note: componentOf might not be a blocker, but it is  worth checking it";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.BLOCKER;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("dbIdA", "stIdA", "NameA", "AtoB", "BtoA", "dbIdB", "stIdB", "NameB", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return "MATCH (n)-[r]->(x)-[e]->(n) " +
                "WHERE TYPE(r) IN [\"surroundedBy\", \"instanceOf\", \"componentOf\"]  " +
                "AND TYPE(r) = TYPE(e) " +
                "OR NOT (n)-[:author|created|edited|modified|revised|reviewed|inferredTo|hasPart|precedingEvent|hasEncapsulatedEvent]-(x) " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN DISTINCT(n.dbId) AS dbIdA, " +
                "               n.stId AS stIdA, " +
                "               n.displayName AS NameA, " +
                "               TYPE(r) AS AtoB, " +
                "               TYPE(e) AS BtoA, " +
                "               x.dbId AS dbIdB, " +
                "               x.stId AS stIdB, " +
                "               x.displayName AS NameB, " +
                "               a.displayName AS Created, " +
                "               m.displayName AS Modified " +
                "ORDER BY Created, Modified, stIdA, dbIdA, stIdB, dbIdB";
    }
}
