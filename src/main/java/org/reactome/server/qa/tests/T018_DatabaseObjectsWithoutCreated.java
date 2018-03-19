package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Florian Korninger <florian.korninger@ebi.ac.uk>
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 *         TODO Why do those Instances have no created attribute
 */
@SuppressWarnings("unused")
@QATest
public class T018_DatabaseObjectsWithoutCreated extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "DatabaseObject class instances (excluding InstanceEdit, DatabaseIdentifier, Taxon, Person and ReferenceEntity class instances) where the created slot is empty";
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
        return " MATCH (n:DatabaseObject) " +
                "WHERE NOT ((n:InstanceEdit) OR (n:DatabaseIdentifier) OR (n:Taxon) OR (n:Person) OR (n:ReferenceEntity)) " +
                "      AND NOT (n)-[:created]-() " +
                "RETURN n.dbId AS dbId, n.displayName AS Name " +
                "ORDER by dbId";
    }
}