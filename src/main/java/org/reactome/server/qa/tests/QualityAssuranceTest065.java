package org.reactome.server.qa.tests;

import org.neo4j.ogm.model.Result;
import org.reactome.server.qa.annotations.QATest;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("unused")
@QATest
public class QualityAssuranceTest065 extends QualityAssuranceAbstract {

    @Override
    public String getName() {
        return "EntitySetsWithRepeatedMembers";
    }

    @Override
    String getQuery() {
        return " MATCH (es:EntitySet)-[hm:hasMember]->(pe:PhysicalEntity) " +
                "WHERE NOT ()-[:inferredTo]->(es) AND NOT (es:CandidateSet) AND hm.stoichiometry > 1 " +
                "WITH DISTINCT es, COLLECT(pe.stId) AS Targets " +
                "OPTIONAL MATCH (a)-[:created]->(es) " +
                "OPTIONAL MATCH (m)-[:modified]->(es) " +
                "RETURN DISTINCT es.stId AS Identifier, es.displayName AS EntitySet, Targets, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, Identifier";
    }

    @Override
    void printResult(Result result, Path path) throws IOException {
        print(result, path, "Identifier", "EntitySet", "Targets", "Created", "Modified");
    }
}
