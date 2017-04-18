package it.ric.uny;

import it.ric.uny.multi.Coordinate;
import it.ric.uny.multi.SudokuMulti;
import it.ric.uny.single.SudokuSolver;
import it.ric.uny.single.Cell;

import it.ric.uny.utils.Extrapolator;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.TreeMap;
import java.util.concurrent.ForkJoinPool;

/**
 * Test Class
 */

public class SudokuTest {

    public static void main(String[] args) {
        Extrapolator extrapolator = new Extrapolator();
        NumberFormat formatterExp = new DecimalFormat("0.0000E0");

        int[][] m = extrapolator.extrapolator(args[0]);
        Cell[][] s = extrapolator.cellExtrapolator(args[0]);
        System.out.println("File: " + args[0]);

        TreeMap<Double, Float> results = new TreeMap<>(Double::compare);

        int empty = extrapolator.getEmptyCell();
        double fill = empty * 100 / 81;

        // Sequential Algorithm
        SudokuSolver ss = new SudokuSolver(s);
        long startTime = System.currentTimeMillis();
        ss.solverRic();
        long endTime = System.currentTimeMillis();

        System.out.println("Fill Factor: " + fill + "%");
        System.out.println("Solutions Space: " + formatterExp.format(ss.getSolDim()));
        System.out.println("\nSequential");
        System.out.println("Legal Solution: " + ss.getCounter());
        System.out.println("Sequential Execution Time: " + (endTime - startTime) + "ms\n");

        // Parallel Algorithm
        // sequential cutoff (i,j)
        int cuti;
        int cutj;

        /*
         * You can pass the cutoff values in command line.
         * Default values are:
         * i = 0,
         * j = 1.
         */

        try {
            cuti = Integer.parseInt(args[1]);
            cutj = Integer.parseInt(args[2]);
        } catch (ArrayIndexOutOfBoundsException e) {
            cuti = 0;
            cutj = 1;
        }

        int c = 0;      // Solutions counter

        System.out.println("Parallel");

        // Number of tentative
        int k = 10;

        for (int i = 0; i < k; i++) {
            long startTimeM = System.currentTimeMillis();
            c = ForkJoinPool.commonPool().invoke(new SudokuMulti(m, new Coordinate(cuti, cutj)));
            long endTimeM = System.currentTimeMillis();

            double time = endTimeM - startTimeM;

            results.put(time, (float) (endTime - startTime) / (endTimeM - startTimeM));
            System.out.println("Iteration: " + (i + 1));
            System.out.println("Parallel Execution Time: " + (endTimeM - startTimeM) + "ms");
            System.out
                .println(
                    "Speedup: " + (float) (endTime - startTime) / (endTimeM - startTimeM) + "\n");
        }

        System.out.println("\nLegal Solutions: " + c);
        System.out.println("Best Execution Time: " + results.firstKey() + "ms");
        System.out.println("Best Speedup: " + results.firstEntry().getValue());
    }
}