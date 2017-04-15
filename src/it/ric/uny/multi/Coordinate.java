package it.ric.uny.multi;


/**
 * Wrapper for coordinates of the matrix
 */

public class Coordinate {

    private int i;
    private int j;

    /**
     * @param i i position
     * @param j j position
     */
    public Coordinate(int i, int j) {
        this.i = i;
        this.j = j;
    }

    /**
     * @return i value
     */
    public int getI() {
        return i;
    }

    /**
     * @return j value
     */
    public int getJ() {
        return j;
    }
}
