package de.digitalservice.gameoflife.domain;

/**
 * Abstraction over the backing store for a grid's cells, indexed row-major.
 *
 * <p>This is the dependency-inversion seam that keeps the concrete storage
 * (a flat {@code boolean[]}, a {@link java.util.BitSet}, ...) an implementation
 * detail invisible to {@link Grid} callers.
 */
public interface CellStorage {

    /** Returns whether the cell at the given flat index is alive. */
    boolean get(int index);

    /** Sets the alive state of the cell at the given flat index. */
    void set(int index, boolean alive);

    /** Number of cells this storage can hold. */
    int size();

    /** Creates a fresh, all-dead storage of the same concrete kind. */
    CellStorage emptyOfSameKind(int size);
}
