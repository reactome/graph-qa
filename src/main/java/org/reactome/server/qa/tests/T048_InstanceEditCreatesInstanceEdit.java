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
public class T048_InstanceEditCreatesInstanceEdit extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "InstanceEdit class instances that are created by other InstanceEdit class instances (it does not seem to make sense)";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.LOW;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("dbIdA", "NameA", "dbIdB", "NameB");
    }

    @Override
    String getQuery() {
        return " MATCH (n:InstanceEdit)-[r:created]-(m:InstanceEdit) " +
                "RETURN DISTINCT n.dbId AS dbIdA, n.displayName AS NameA, m.dbId AS dbIdB, m.displayName as NameB";
    }
}
