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
public class T036_EventsWithoutCompartment extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "Event class instances where the compartment slot is empty";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.HIGH;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("dbId", "stId", "Name", "Type", "Created", "Modified");
    }

    @Override
    String getQuery() {
        //The reason why of the splitting is to promote reactions on the top of the list. Sorting by "Type DESC" didn't work because of the TopLevelPathway
        return " MATCH (e:ReactionLikeEvent) " +
                "WHERE NOT (e)-[:compartment]->(:Compartment) " +
                "OPTIONAL MATCH (a)-[:created]->(e) " +
                "OPTIONAL MATCH (m)-[:modified]->(e) " +
                "RETURN e.dbId AS dbId, e.stId AS stId, e.displayName AS Name, e.schemaClass AS Type, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, stId " +
                "UNION " +
                "MATCH (e:Pathway) " +
                "WHERE NOT (e)-[:compartment]->(:Compartment) " +
                "OPTIONAL MATCH (a)-[:created]->(e) " +
                "OPTIONAL MATCH (m)-[:modified]->(e) " +
                "RETURN e.dbId AS dbId, e.stId AS stId, e.displayName AS Name, e.schemaClass AS Type, a.displayName AS Created, m.displayName AS Modified " +
                "ORDER BY Created, Modified, stId " ;
    }
}