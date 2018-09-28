package it.ric.uny.single;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * Wrapper for cell matrix
 */
@Data
public class Cell {

    private Set<Integer> numSet = new HashSet<>();
    private int current;
    private int i;
    private int j;

    public Cell(int i, int j) {
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

}
