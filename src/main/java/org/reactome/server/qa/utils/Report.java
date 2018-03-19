package org.reactome.server.qa.utils;

import org.reactome.server.qa.tests.QualityAssurance;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class Report implements Comparable<Report> {

    private static NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.UK);
    private static final String RESET_COLOUR = "\033[0m";

    private QualityAssurance test;
    public final Integer count;

    public Report(QualityAssurance test, Integer count) {
        this.test = test;
        this.count = count;
    }

    public static void printColoured(Report p) {
        String entries = (p.count == 1) ? "entry" : "entries";
        String line = String.format("\t%7s %s: %s %s", p.test.getPriority().name, p.test.getNumeratedName(), numberFormat.format(p.count), entries);
        if (p.count == 0) System.out.println(line);
        else System.out.println(p.test.getPriority().colour + line + RESET_COLOUR);
    }

    public static String getCSVHeader(){
        return "Priority,Code,Name,Entries,Description";
    }

    public String getCSV() {
        String[] name = test.getNumeratedName().split("-", 2);
        return String.format("%s,\"%s\",\"%s\",%d,\"%s\"",
                test.getPriority().order + "_" + test.getPriority().name,
                name[0],
                name[1],
                count,
                test.getDescription()
        );
    }

    @Override
    public int compareTo(Report o) {
        return (test.getPriority().order + test.getNumeratedName()).compareTo(o.test.getPriority().order + o.test.getNumeratedName());
    }
}
