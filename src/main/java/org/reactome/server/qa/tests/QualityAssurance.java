package org.reactome.server.qa.tests;

import org.reactome.server.graph.service.GeneralService;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 */
public interface QualityAssurance {

    String getName();

    boolean run(GeneralService genericService, String path);
}
