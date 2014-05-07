package com.hidetomo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hidetomo on 5/2/2014.
 */
public class Java8StreamBench {
    private static final int NUM_LASTNAME = 100000;
    private static final int NUM_LOOP = 1000;
    private static String [] LASTNAMEARRAY = new String[NUM_LASTNAME];
    private static List<String> LASTNAMELIST = null;

    private static final Long LIMIT_RESULT = 2L;

    public static void main(String [] args) throws Exception{
        loadData();
        Timed.executionTime("Sequential Lambda", NUM_LOOP, () -> benchSequentialLambda() );
        Timed.executionTime("Parallel Lambda", NUM_LOOP, () -> benchParallelLambda() );
        Timed.executionTime("Traditional For Loop", NUM_LOOP, () -> benchTraditionalForLoop() );
    }

    private static List<String> benchParallelLambda(){
        List<String> dedupeNameList = LASTNAMELIST
                .parallelStream()    // PARALLEL!!
                .filter(s -> s.startsWith("m"))
                .map(whatever -> "["+whatever+"]")
                .sorted((s1, s2) -> s2.compareTo(s1))
                .distinct()
                .limit(LIMIT_RESULT)
                .collect(Collectors.toList());     // collect is a terminal operator.
        return dedupeNameList;
    }

    private static List<String> benchSequentialLambda(){
        List<String> dedupeNameList = LASTNAMELIST
                .stream()
                .filter(s -> s.startsWith("m"))
                .map(s -> "["+s+"]")
                .sorted((s1, s2) -> s2.compareTo(s1))
                .distinct()
                .limit(LIMIT_RESULT)
                .collect(Collectors.toList());
        return dedupeNameList;
    }

    private static List<String> benchTraditionalForLoop(){
        // Dedupe by Set
        List<String> dedupeNameList = new ArrayList<>();
        Set<String> dedupeNameSet = new HashSet<String>();
        for(String lname: LASTNAMEARRAY){
            if(lname.startsWith("m")){
                dedupeNameSet.add("["+lname+"]");
            }
        }
        dedupeNameList.addAll(dedupeNameSet);
        Collections.sort(dedupeNameList, Collections.reverseOrder());
        List<String> result = new LinkedList<>();
        // Limit result
        result.addAll(dedupeNameList.subList(0, 2));
        return dedupeNameList;
    }

    /**
     * Pre load 100,000 last name from resource.
     */
    private static final String FILE_NAME = "lastname.txt";
    private static void loadData() throws Exception{
        String file = Java8StreamBench.class.getResource(FILE_NAME).getFile();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        int counter = 0;
        while ((line = br.readLine()) != null) {
            // process the line.
            LASTNAMEARRAY[counter++] = line;
        }
        br.close();
        LASTNAMELIST = Arrays.asList(LASTNAMEARRAY);
    }
}
