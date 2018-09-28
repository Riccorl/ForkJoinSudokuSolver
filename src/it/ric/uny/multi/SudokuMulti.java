package it.ric.uny.multi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;


/**
 * Parallel Solver
 */

public class SudokuMulti extends RecursiveTask<Integer> {

    private int[][] sudoku;
    private int i;
    private int j;
    private int counter;            // Solutions counter
    private Coordinate coor;        // Wrapper for matrix coordinates
    private Coordinate cutoff;      // Sequential cutoff wrapper
    private Set<Integer> legal;     // Legal values per cell

    /**
     * Parallel solver.
     *
     * @param sudoku sudoku matrix
     * @param cutoff cutoff coordinates
     */
    public SudokuMulti(int[][] sudoku, Coordinate cutoff) {
        this.sudoku = sudoku;
        this.cutoff = cutoff;

        this.coor = getNextEmpty();                         // Firts empty cell
        if (coor != null) {
            this.i = this.coor.getI();                      // empty cell index i
            this.j = this.coor.getJ();                      // empty cell index j
            this.legal = checkList(this.i, this.j);         // legal values set
        }
    }

    /**
     * Private C
     *
     * @param sudoku sudoku matrix
     * @param i i position
     * @param j j position
     * @param v value to check
     * @param cutoff cutoff coordinates
     */
    private SudokuMulti(int[][] sudoku, int i, int j, int v, Coordinate cutoff) {
        this.sudoku = this.createCopy(sudoku);
        this.sudoku[i][j] = v;
        this.cutoff = cutoff;

        this.coor = getNextEmpty();                          // Firts empty cell
        if (coor != null) {
            this.i = this.coor.getI();                      // empty cell index i
            this.j = this.coor.getJ();                      // empty cell index j
            this.legal = checkList(this.i, this.j);
        }
    }

    /**
     * Override of ForkJoin compute method
     *
     * @return il numero di soluzioni legali
     */
    @Override
    protected Integer compute() {
        var forkedThreads = new ArrayList<ForkJoinTask<Integer>>();
        // if coor is NULL, the sudoku is done
        if (coor == null) {
            return ++counter;
        }
        // if cutoff is true, do sequential
        if (this.i == cutoff.getI() && this.j == cutoff.getJ()) {
            return solver(i, j) + counter;
        }

        for (var v : legal) {
            if (checkValueCorrect(i, j, v)) {
                // Fork
                forkedThreads.add(new SudokuMulti(sudoku, i, j, v, cutoff).fork());
            }
        }
        for (var forkedThread : forkedThreads) {
            // Join
            counter += forkedThread.join();
        }
        return counter;
    }

    /**
     * Sequential solver
     *
     * @param i row
     * @param j column
     * @return true if sudoku is done
     */
    private int solver(int i, int j) {

        // end of matrix
        if (i == 9) {
            i = 0;
            if (++j == 9) {
                return ++counter;
            }
        }

        // Skip not empty cells
        if (sudoku[i][j] != 0) {
            return solver(i + 1, j);
        }

        for (var v = 1; v <= 9; ++v) {
            if (checkValueCorrect(i, j, v)) {
                sudoku[i][j] = v;
                solver(i + 1, j);
                sudoku[i][j] = 0;
            }
        }

        sudoku[i][j] = 0;
        return 0;
    }

    /**
     * Matrix copy
     *
     * @return copied sudoku.
     */
    private int[][] createCopy(int[][] sudoku) {
        var sudokuCopy = new int[9][9];
        for (var i = 0; i < 9; i++) {
            for (var j = 0; j < 9; j++) {
                sudokuCopy[j][i] = sudoku[j][i];
            }
        }
        return sudokuCopy;
    }

    /**
     * Search first empty cell
     *
     * @return coordinates of first empty cell
     */
    private Coordinate getNextEmpty() {
        for (var i = 0; i < sudoku.length; i++) {
            for (var j = 0; j < sudoku[i].length; j++) {
                // if cell is empty, return coordinates
                if (sudoku[j][i] == 0) {
                    return new Coordinate(j, i);
                }
            }
        }
        return null;
    }

    /**
     * Check if input is correct
     *
     * @param i row
     * @param j column
     * @param v value to check
     * @return true if v is legal
     */
    private boolean checkValueCorrect(int i, int j, int v) {
        // check v not in row
        for (var k = 0; k < 9; k++) {
            if (sudoku[i][k] == v) {
                return false;
            }
        }
        // check v not in column
        for (var l = 0; l < 9; l++) {
            if (sudoku[l][j] == v) {
                return false;
            }
        }
        // check v not in sub-matrix
        for (var ii = 0; ii < 3; ii++) {
            for (var jj = 0; jj < 3; jj++) {
                if (sudoku[(i / 3) * 3 + ii][(j / 3) * 3 + jj] == v) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Add all possible legal value
     *
     * @return legal value set.
     */
    private Set<Integer> checkList(int i, int j) {
        var legal = new HashSet<Integer>();
        for (var k = 1; k <= 9; k++) {
            if (checkValueCorrect(i, j, k)) {
                legal.add(k);
            }
        }
        return legal;
    }
}
