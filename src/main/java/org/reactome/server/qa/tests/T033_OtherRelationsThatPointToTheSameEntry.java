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
public class T033_OtherRelationsThatPointToTheSameEntry extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "Other relations that point to the same entry";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.LOW;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("dbIdA", "stIdA", "NameA", "dbIdB", "stIdB", "NameB", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (n)-[r]->(x), " +
                "      (n)-[e]->(x) " +
                "WHERE NOT (n)-[:author|created|edited|modified|revised|reviewed|input|output|entityOnOtherCell|" +
                "hasComponent|requiredInputComponent|physicalEntity|diseaseEntity|activeUnit|reverseReaction|precedingEvent|hasEvent|" +
                "goCellularComponent|compartment|referenceSequence|secondReferenceSequence|hasCandidate|hasMemberproteinMarker|RNAMarker]-(x) " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN DISTINCT n.dbId AS dbIdA, n.stId AS stIdA, n.displayName AS NameA, x.dbId AS dbIdB, x.stId AS stIdB, x.displayName AS NameB, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, stIdA, dbIdA, stIdB, dbIdB";
    }
}

