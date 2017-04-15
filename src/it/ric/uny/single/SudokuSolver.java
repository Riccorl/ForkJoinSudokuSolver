package it.ric.uny.single;


import java.math.BigInteger;

/**
 * Sequential Sudoku
 */
public class SudokuSolver {

    private Cell[][] sudoku;
    private int counter;
    private BigInteger solDim = BigInteger.ONE;

    /**
     * @param sudoku Cell[][]
     */
    public SudokuSolver(Cell[][] sudoku) {
        this.sudoku = sudoku;
        this.checkList();
    }


    /**
     * Wrapper
     *
     * @return the sudoku
     */
    public Cell[][] solverRic() {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudoku[i][j].getNumSet().size() == 1) {
                    sudoku[i][j].setCurrent(sudoku[i][j].getSingleElement());
                }
            }
        }

        solver(0, 0);
        return sudoku;
    }

    /**
     * Solver
     *
     * @param i row
     * @param j column
     * @return true if sudoku is done
     */
    private int solver(int i, int j) {

        if (i == 9) {
            i = 0;
            if (++j == 9) {
                return ++counter;
            }
        }

        // Skip not empty cells
        if (sudoku[i][j].getCurrent() != 0) {
            return solver(i + 1, j);
        }

        for (Integer v : sudoku[i][j].getNumSet()) {
            if (checkValueCorrect(i, j, v)) {
                sudoku[i][j].setCurrent(v);
                solver(i + 1, j);
                sudoku[i][j].setCurrent(0);
            }
        }
        sudoku[i][j].setCurrent(0);
        return 0;
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

        // checkv not in row
        for (int ii = 0; ii < 9; ii++) {
            if (sudoku[i][ii].getCurrent() == v) {
                return false;
            }
        }

        // check v not in column
        for (int jj = 0; jj < 9; jj++) {
            if (sudoku[jj][j].getCurrent() == v) {
                return false;
            }
        }

        // check v not in sub-matrix
        for (int ii = 0; ii < 3; ii++) {
            for (int jj = 0; jj < 3; jj++) {
                if (sudoku[(i / 3) * 3 + ii][(j / 3) * 3 + jj].getCurrent() == v) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Add all possible legal value
     */
    private void checkList() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int k = 1; k <= 9; k++) {
                    if (sudoku[i][j].getCurrent() != 0) {
                        continue;
                    }
                    if (checkValueCorrect(i, j, k)) {
                        sudoku[i][j].add2Set(k);
                    }
                }
                if (sudoku[i][j].getNumSet().size() != 0) {
                    solDim = solDim.multiply(BigInteger.valueOf(sudoku[i][j].getNumSet().size()));
                }
            }
        }
    }

    /**
     * @return solutions counter
     */
    public int getCounter() {
        return counter;
    }

    /**
     * @return possible solutions
     */
    public BigInteger getSolDim() {
        return solDim;
    }
}


