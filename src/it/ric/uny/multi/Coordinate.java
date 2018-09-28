package it.ric.uny.multi;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Wrapper for coordinates of the matrix
 */

@Data
@AllArgsConstructor
public class Coordinate {

    private int i;
    private int j;
}
