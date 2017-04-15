package it.ric.uny;

import it.ric.uny.single.Cell;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;


/**
 * Input Reader
 */

public class Extrapolator {

    private int emptyCell;

    /**
     * il metodo extrapolator ritorna un'istanza di un sudoku letto da un file passato da input
     * tramite il path indicato nella stringa "filename".
     *
     * @param filename, ovvero il path del file da caricare
     * @return un'istanza del sudoku caricato da input
     */
    public int[][] extrapolator(String filename) {
        int[][] matrix = new int[9][9];
        String extracted = null;
        try {
            /*
               Leggo il file del sudoku che ricevo da input
             */
            extracted = Files
                .lines(Paths
                    .get(filename))
                .map(Object::toString)
                .collect(Collectors.joining("\n"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String[] array = extracted.split("\n");

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (array[i].charAt(j)
                    != '.') {
                    matrix[i][j] = Character.getNumericValue(array[i].charAt(
                        j));
                    emptyCell++;
                }
            }
        }
        return matrix;
    }

    /**
     * il metodo cellExtrapolator ritorna un'istanza di un sudoku letto da un file passato da input
     * tramite il path indicato nella stringa "filename".
     *
     * @param filename source file
     * @return sudoku instance from input file
     */
    public Cell[][] cellExtrapolator(String filename) {
        Cell[][] matrix = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                matrix[i][j] = new Cell(i,
                    j);
            }
        }

        String extracted = null;
        try {
            /*
            Leggo il file del sudoku che ricevo da input
             */
            extracted = Files
                .lines(Paths
                    .get(filename))
                .map(Object::toString)
                .collect(Collectors.joining("\n"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] array = extracted.split("\n");

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (array[i].charAt(j)
                    != '.') {
                    matrix[i][j].setCurrent(Character.getNumericValue(array[i].charAt(
                        j)));
                }
            }
        }

        return matrix;
    }

    /**
     * Ritorna il numero di celle vuote dell'istanza di sudoku caricata.
     *
     * @return il numero di celle vuote
     */
    public int getEmptyCell() {

        return emptyCell;
    }
}