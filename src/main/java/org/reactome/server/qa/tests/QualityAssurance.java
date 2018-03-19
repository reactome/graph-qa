package org.reactome.server.qa.tests;

import org.reactome.server.graph.service.GeneralService;

import java.util.List;

/**
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 */
public interface QualityAssurance {

    String getName();

    String getNumeratedName();

    String getDescription();

    QAPriority getPriority();

    List<String> getHeader();

    int run(GeneralService genericService, String path);
}
