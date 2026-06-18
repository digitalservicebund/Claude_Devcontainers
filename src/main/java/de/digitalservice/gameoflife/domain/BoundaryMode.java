package de.digitalservice.gameoflife.domain;

/**
 * How the grid edges behave when counting a cell's neighbours.
 */
public enum BoundaryMode {

    /** Cells outside the grid are always dead; patterns die at the edges. */
    FINITE,

    /** Edges wrap around, so the board behaves as a torus. */
    TOROIDAL
}
