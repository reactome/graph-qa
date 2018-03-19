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
public class T038_HasModifiedResidueRelationshipDuplication extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "DatabaseObject class instances where the hasModifiedResidue slot contains duplicated entries and the coordinate is not empty";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.HIGH;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("Identifier", "Entity", "Modification", "ModificationName", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (x)-[r:hasModifiedResidue]->(y) " +
                "WHERE NOT ()-[:inferredTo]->(x) AND r.stoichiometry > 1 AND NOT y.coordinate IS NULL " +
                "OPTIONAL MATCH (a)-[:created]->(x) " +
                "OPTIONAL MATCH (m)-[:modified]->(x) " +
                "RETURN x.stId AS Identifier, x.displayName AS Entity, y.dbId AS Modification, y.displayName AS ModificationName, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, Identifier, Modification";
    }
}



