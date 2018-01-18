package org.reactome.server.qa.tests;

import org.neo4j.ogm.model.Result;
import org.reactome.server.qa.annotations.QATest;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Florian Korninger <florian.korninger@ebi.ac.uk>
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest038 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "HasModifiedResidueRelationshipDuplication";
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

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "Identifier", "Entity", "Modification", "ModificationName", "Created", "Modified");
    }
}



