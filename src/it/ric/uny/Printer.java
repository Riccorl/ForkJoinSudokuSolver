package it.ric.uny;

import it.ric.uny.single.Cell;


/**
 * Suodku Printer
 */

public class Printer {

    /**
     * Stampa la matrice di Cell[][] su terminale
     */
    public static void printf(Cell[][] sudoku) {
        System.out.print("- - - - - - - - - - -" + "\n");
        for (int j = 0; j < 9; j++) {
            System.out.print("|");
            for (int i = 0; i < 9; i++) {
                System.out.print(sudoku[j][i].getCurrent() + " ");
                if ((i + 1) % 3 == 0) {
                    System.out.print("|");
                }
            }
            System.out.print("\n");
            if ((j + 1) % 3 == 0) {
                System.out.print("- - - - - - - - - - -" + "\n");
            }
        }
    }

    /**
     * Stampa la matrice di int[][] su terminale
     */
    public static void intPrintf(int[][] sudoku) {
        System.out.print("- - - - - - - - - - -" + "\n");
        for (int j = 0; j < 9; j++) {
            System.out.print("|");
            for (int i = 0; i < 9; i++) {
                System.out.print(sudoku[j][i] + " ");
                if ((i + 1) % 3 == 0) {
                    System.out.print("|");
                }
            }
            System.out.print("\n");
            if ((j + 1) % 3 == 0) {
                System.out.print("- - - - - - - - - - -" + "\n");
            }
        }
    }
}
