package org.reactome.server.qa.tests;

import org.reactome.server.qa.annotations.QATest;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
@QATest
public class T102_FiguresURLInconsistence extends QualityAssuranceAbstract {

    @Override
    public String getDescription() {
        return "Figures (ehld/static) with potential mismatch or missing extension";
    }

    @Override
    public QAPriority getPriority() {
        return QAPriority.BLOCKER;
    }

    @Override
    public List<String> getHeader() {
        return Arrays.asList("dbId", "url");
    }

    @Override
    String getQuery() {
        return "MATCH (n:Figure) " +
                "    WHERE NOT ( " +
                "            n.url =~ '^/figures/.*\\\\.jpg' " +
                "            OR n.url =~ '^/figures/.*\\\\.JPG' " +
                "            OR n.url =~ '^/figures/.*\\\\.jpeg' " +
                "            OR n.url =~ '^/figures/.*\\\\.gif' " +
                "            OR n.url =~ '^/figures/.*\\\\.png' " +
                "            OR n.url =~ '^/figures/(ehld|static)/R-[A-Z]{3}-.*\\\\.svg'" +
                "    ) " +
                "    RETURN n.dbId as dbId, n.url as url";
    }
}
