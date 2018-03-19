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
public class T014_InstanceEditWithoutAuthor extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "InstanceEdit class instances where the author slot is empty";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.LOW;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("dbId", "Name");
    }

    @Override
    String getQuery() {
        return " MATCH (n:InstanceEdit) " +
                "WHERE NOT (n)<-[:author]-() " +
                "RETURN n.dbId AS dbId, n.displayName as Name";
    }
}