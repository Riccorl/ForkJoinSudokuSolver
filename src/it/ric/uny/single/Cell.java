package it.ric.uny.single;

import java.util.HashSet;
import java.util.Set;


/**
 * Wrapper for cell matrix
 */
public class Cell {

    private Set<Integer> numSet = new HashSet<>();
    private int current;
    private int i;
    private int j;

    /**
     * @param i i position
     * @param j j position
     */
    public Cell(int i, int j) {
        current = 0;
        this.i = i;
        this.j = j;
    }

    /**
     * Add n to the set of legal value
     *
     * @param n value to add
     */
    public void add2Set(int n) {
        numSet.add(n);
    }

    /**
     * @return next element
     */
    public int getSingleElement() {
        return numSet.iterator().next();
    }

    /**
     * @return the set of legal value
     */
    public Set<Integer> getNumSet() {
        return numSet;
    }

    /**
     * @return the current value of the cell
     */
    public int getCurrent() {
        return current;
    }

    /**
     * @param current value to set
     */
    public void setCurrent(int current) {
        this.current = current;
    }

}
