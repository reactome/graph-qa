package org.reactome.server.qa.tests;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.model.Result;
import org.reactome.server.graph.service.GeneralService;
import org.reactome.server.qa.utils.FileUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Florian Korninger <florian.korninger@ebi.ac.uk>
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public abstract class QualityAssuranceAbstract implements QualityAssurance {

    @Override
    public final String getName() {
        return getClass().getSimpleName().split("_", 2)[1];
    }

    @Override
    public final String getNumeratedName() {
        return getPrefix() + getName();
    }

    private String getOrder() {
        return getClass().getSimpleName().split("_", 2)[0].replaceAll("\\D+", "");
    }

    abstract String getQuery();

    @SuppressWarnings("WeakerAccess")
    protected Map getQueryParameters() {
        return Collections.EMPTY_MAP;
    }

    @SuppressWarnings("unchecked")
    @Override
    public int run(GeneralService genericService, String path) {
        Result result = genericService.query(getQuery(), getQueryParameters());
        if (result == null || !result.iterator().hasNext()) return 0;
        try {
            return report(result, FileUtils.getFilePath(path, getNumeratedName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int report(Result result, Path path) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("\"" + StringUtils.join(getHeader(), "\",\"") + "\"");
        for (Map<String, Object> map : result) {
            List<String> line = new ArrayList<>();
            for (String attribute : getHeader()) {
                // Some results might be list of elements. In some cases we use REDUCE and the output looks like
                // ["a", "b", "c", ] and we want it to look like ["a", "b", "c"].
                //               ^ we remove this comma and the space after it
                // That's why we replace ", ]" by "]"
                Object aux = map.get(attribute);
                if(aux instanceof Object[]){
                    StringBuilder rtn = new StringBuilder("[");
                    for (Object item : (Object[]) aux) {
                        rtn.append(item).append(", ");
                    }
                    aux = rtn.append("]").toString();
                }
                line.add("\"" + (aux == null ? null : ("" + aux).replaceAll(", ]$", "]")) + "\"");
            }
            lines.add(StringUtils.join(line, ","));
        }
        Files.write(path, lines, Charset.forName("UTF-8"));
        return lines.size() - 1;
    }

    private String getPrefix() {
        return "GT" + getOrder() + "-";
    }
}
