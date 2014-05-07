package com.hidetomo;

import java.util.List;
import java.util.function.Supplier;

/**
 * Created by hidetomo on 5/2/2014.
 */
public final class Timed {
    // Supplier is a type of FunctionalInterface with 1 return type, no parameter
    public static void executionTime(String methodName,
                                     int ntimes,
                                     Supplier<List<String>> supplier){
        // Loop 100 times to stabilize result
        for(int i = 0 ; i < 100; i++){
            supplier.get();
        }
        System.out.printf("%s Process start\n", methodName);
        List<String> result = null;
        long start = System.currentTimeMillis();
        for(int i = 0 ; i < ntimes; i++){
            result = supplier.get();
        }
        System.out.printf("1st Result: %s, 2nd Result: %s\n", result.get(0), result.get(1));
        System.out.printf("%s Process %d times took: %dms%n\n", methodName, ntimes, System.currentTimeMillis() - start);
    }
}
