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
public class T020_OpenSetsWithoutReferenceEntity extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "OpenSet class instances where the referenceEntity slot is empty";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.MEDIUM;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("stId", "OpenSet", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (os:OpenSet) " +
                "WHERE NOT (os)-[:referenceEntity]->() " +
                "OPTIONAL MATCH (a)-[:created]->(os) " +
                "OPTIONAL MATCH (m)-[:modified]->(os) " +
                "RETURN DISTINCT os.stId AS stId, os.displayName AS OpenSet, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, stId";
    }
}

