/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Heuristics;

import Heuristics.Heuristic;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author C. Levallois
 */
public class HeuristicsLoader {

    BufferedReader br;
    FileReader fr;
    String folderPath = "heuristics//";
    String string;
    Heuristic heuristic;
    Map<String, Heuristic> mapHeuristics;
    Map<String, Heuristic> mapH1;
    Map<String, Heuristic> mapH2;
    Map<String, Heuristic> mapH3;
    Map<String, Heuristic> mapH4;
    Map<String, Heuristic> mapH5;
    Map<String, Heuristic> mapH6;
    Map<String, Heuristic> mapH7;
    Map<String, Heuristic> mapH8;
    Map<String, Heuristic> mapH9;
    Map<String, Heuristic> mapH10;
    Map<String, Heuristic> mapH11;
    Map<String, Heuristic> mapH12;
    Set<String> setNegations;
    Set<String> setTimeTokens;

    public void load() throws FileNotFoundException, IOException {


        File folder = new File("D:\\Docs Pro Clement\\NetBeansProjects\\Umigon\\private\\heuristics\\");
//        System.out.println("folder is: " + folder.getCanonicalPath());
        File[] arrayFiles = folder.listFiles();
        mapHeuristics = new HashMap();
        setNegations = new HashSet();
        setTimeTokens = new HashSet();
        mapH1 = new HashMap();
        mapH2 = new HashMap();
        mapH4 = new HashMap();
        mapH3 = new HashMap();
        mapH5 = new HashMap();
        mapH6 = new HashMap();
        mapH7 = new HashMap();
        mapH8 = new HashMap();
        mapH9 = new HashMap();
        mapH10 = new HashMap();
        mapH11 = new HashMap();
        mapH12 = new HashMap();

        for (File file : arrayFiles) {
            br = new BufferedReader(new FileReader(file));
            br.readLine();
            if (!file.getName().contains("_")) {
                continue;
            }
            int map = Integer.parseInt(StringUtils.left(file.getName(), file.getName().indexOf("_")));
            if (map == 0) {
                continue;
            }
            System.out.println("map: " + map);
            System.out.println("loading " + file.getName());
            System.out.println("folder is: " + folder.getCanonicalPath());

            String term = null;
            String featureString;
            String feature;
            String rule = null;
            String fields[];
            String[] parametersArray;
            String field0;
            String field1;
            String field2;
            TreeMap<String, Set<String>> mapFeatures;
            while ((string = br.readLine()) != null) {
                fields = string.split("\t", -1);
                mapFeatures = new TreeMap();

                //sometimes the heuristics is just a term, not followed by a feature or a rule
                //in this case put a null value to these fields
                field0 = fields[0];
                field1 = (fields.length < 2) ? null : fields[1];
                field2 = (fields.length < 3) ? null : fields[2];

                term = field0;
                featureString = field1;
                rule = field2;

                //parse the "feature" field to disentangle the feature from the parameters
                //this parsing rule will be extended to allow for multiple features
                if (featureString.contains("///")) {
                    parametersArray = StringUtils.substringAfter(featureString, "///").split("|");
                    feature = StringUtils.substringBefore(featureString, "///");
                    mapFeatures.put(feature, new HashSet(Arrays.asList(parametersArray)));
                } else if (featureString != null) {
                    mapFeatures.put(featureString, null);
                }


//                if (term.equals("I was wondering")){
//                    System.out.println("HERE!!!!");
//                }
//                System.out.println("feature: "+feature);

                heuristic = new Heuristic(term, mapFeatures, rule);
                mapHeuristics.put(term, heuristic);
                //positive
                if (map == 1) {
                    mapH1.put(term, heuristic);
                    continue;
                }
                //negative
                if (map == 2) {
                    mapH2.put(term, heuristic);
                    continue;
                }
                //strong
                if (map == 3) {
                    mapH3.put(term, heuristic);
                    continue;
                }
                //time
                if (map == 4) {
                    mapH4.put(term, heuristic);
                    continue;
                }
                //question
                if (map == 5) {
                    mapH5.put(term, heuristic);
                    continue;
                }
                //subjective
                if (map == 6) {
                    mapH6.put(term, heuristic);
                    continue;
                }
                //address
                if (map == 7) {
                    mapH7.put(term, heuristic);
                    continue;
                }
                //humor
                if (map == 8) {
                    mapH8.put(term, heuristic);
                    continue;
                }
                //commercial offer
                if (map == 9) {
                    mapH9.put(term, heuristic);
                    continue;
                }
                //negations
                if (map == 10) {
                    setNegations.add(term);
                    continue;
                }
                //hints difficulty
                if (map == 11) {
                    mapH11.put(term, heuristic);
                    continue;
                }
                //time indications
                if (map == 12) {
                    setTimeTokens.add(term);
                    continue;
                }
            }
        }

        System.out.println(
                "total number heuristics used: " + mapHeuristics.keySet().size());
        System.out.println(
                "--------------------------------------------");

        System.out.println(
                "positive tone: " + mapH1.keySet().size());
        System.out.println(
                "negative tone: " + mapH2.keySet().size());
        System.out.println(
                "strength of opinion: " + mapH3.keySet().size());
        System.out.println(
                "time related: " + mapH4.keySet().size());
        System.out.println(
                "question: " + mapH5.keySet().size());
        System.out.println(
                "self turned: " + mapH6.keySet().size());
        System.out.println(
                "humor or light: " + mapH8.keySet().size());
        System.out.println(
                "direct address: " + mapH7.keySet().size());
        System.out.println(
                "commercial offer: " + mapH9.keySet().size());

    }

    public Map<String, Heuristic> getMapHeuristics() {
        return mapHeuristics;
    }

    public void setMapHeuristics(Map<String, Heuristic> mapHeuristics) {
        this.mapHeuristics = mapHeuristics;
    }

    public Map<String, Heuristic> getMapH1() {
        return mapH1;
    }

    public void setMapH1(Map<String, Heuristic> mapH1) {
        this.mapH1 = mapH1;
    }

    public Map<String, Heuristic> getMapH2() {
        return mapH2;
    }

    public void setMapH2(Map<String, Heuristic> mapH2) {
        this.mapH2 = mapH2;
    }

    public Map<String, Heuristic> getMapH3() {
        return mapH3;
    }

    public void setMapH3(Map<String, Heuristic> mapH3) {
        this.mapH3 = mapH3;
    }

    public Map<String, Heuristic> getMapH4() {
        return mapH4;
    }

    public void setMapH4(Map<String, Heuristic> mapH4) {
        this.mapH4 = mapH4;
    }

    public Map<String, Heuristic> getMapH5() {
        return mapH5;
    }

    public void setMapH5(Map<String, Heuristic> mapH5) {
        this.mapH5 = mapH5;
    }

    public Map<String, Heuristic> getMapH6() {
        return mapH6;
    }

    public void setMapH6(Map<String, Heuristic> mapH6) {
        this.mapH6 = mapH6;
    }

    public Map<String, Heuristic> getMapH7() {
        return mapH7;
    }

    public void setMapH7(Map<String, Heuristic> mapH7) {
        this.mapH7 = mapH7;
    }

    public Map<String, Heuristic> getMapH8() {
        return mapH8;
    }

    public void setMapH8(Map<String, Heuristic> mapH8) {
        this.mapH8 = mapH8;
    }

    public Map<String, Heuristic> getMapH9() {
        return mapH9;
    }

    public void setMapH9(Map<String, Heuristic> mapH9) {
        this.mapH9 = mapH9;
    }

    public Map<String, Heuristic> getMapH10() {
        return mapH10;
    }

    public void setMapH10(Map<String, Heuristic> mapH10) {
        this.mapH10 = mapH10;
    }

    public Map<String, Heuristic> getMapH11() {
        return mapH11;
    }

    public void setMapH11(Map<String, Heuristic> mapH11) {
        this.mapH11 = mapH11;
    }

    public Map<String, Heuristic> getMapH12() {
        return mapH12;
    }

    public void setMapH12(Map<String, Heuristic> mapH12) {
        this.mapH12 = mapH12;
    }

    public Set<String> getSetNegations() {
        return setNegations;
    }

    public void setSetNegations(Set<String> setNegations) {
        this.setNegations = setNegations;
    }
}