package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

import java.util.Arrays;
import java.util.List;

/**
 * Test updated after TC 13/12/2016. It now also takes into account the project field.
 *
 * @author Florian Korninger <florian.korninger@ebi.ac.uk>
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class T051_PersonsWithSameORCIDAndProject extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "Different instances of the Person class with the same ORCID identifier (and project)";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.MEDIUM;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("dbIdA", "NameA", "dbIdB", "NameB");
    }

    @Override
    String getQuery() {
        return " MATCH (n:Person), (p:Person) " +
                "WHERE NOT n = p " +
                "      AND n.orcidId = p.orcidId " +
                "      AND n.project = p.project " +
                "RETURN DISTINCT n.dbId AS dbIdA, n.displayName AS NameA, p.dbId AS dbIdB, p.displayName AS NameB";
    }
}
