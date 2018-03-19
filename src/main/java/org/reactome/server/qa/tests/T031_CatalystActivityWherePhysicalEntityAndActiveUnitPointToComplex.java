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
public class T031_CatalystActivityWherePhysicalEntityAndActiveUnitPointToComplex extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "CatalystActivity class instances where the activity and activeUnit slots point to the same Complex class instance";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.MEDIUM;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("CatalystID", "CatalystActivity", "ComplexID", "Complex", "Created", "Modified");
    }

    @Override
    String getQuery() {
        return " MATCH (n:CatalystActivity)-[r:physicalEntity]->(x:Complex)<-[:activeUnit]-(n) " +
                "WHERE NOT ()-[:inferredTo]->(x) " +
                "OPTIONAL MATCH (a)-[:created]->(n) " +
                "OPTIONAL MATCH (m)-[:modified]->(n) " +
                "RETURN DISTINCT n.dbId AS CatalystID, n.displayName AS CatalystActivity, x.stId AS ComplexID, x.displayName AS Complex, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, CatalystID, ComplexID";
    }
}
