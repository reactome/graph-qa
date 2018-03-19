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
public class T050_DuplicatedLiteratureReferences extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "Different instances of the LiteratureReference class with the same PubMed identifier";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.HIGH;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("dbIdA", "NameA", "dbIdB", "NameB");
    }

    @Override
    String getQuery() {
        return " MATCH (a:LiteratureReference),(b:LiteratureReference) " +
                "WHERE NOT a = b AND a.pubMedIdentifier = b.pubMedIdentifier  " +
                "RETURN DISTINCT a.dbId AS dbIdA, a.displayName AS NameA, b.dbId AS dbIdB, b.displayName AS NameB";
    }
}
