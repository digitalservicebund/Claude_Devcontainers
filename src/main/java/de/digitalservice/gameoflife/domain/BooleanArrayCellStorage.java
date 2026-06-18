package de.digitalservice.gameoflife.domain;

/**
 * Default {@link CellStorage} backed by a single contiguous {@code boolean[]}.
 *
 * <p>A flat, row-major array is cache-friendly (sequential access in the
 * generation loop) and the simplest to reason about. Swapping in a more compact
 * {@link java.util.BitSet}-based store later only requires another
 * {@link CellStorage} implementation — no caller changes.
 */
public final class BooleanArrayCellStorage implements CellStorage {

    private final boolean[] cells;

    public BooleanArrayCellStorage(int size) {
        this.cells = new boolean[size];
    }

    @Override
    public boolean get(int index) {
        return cells[index];
    }

    @Override
    public void set(int index, boolean alive) {
        cells[index] = alive;
    }

    @Override
    public int size() {
        return cells.length;
    }

    @Override
    public CellStorage emptyOfSameKind(int size) {
        return new BooleanArrayCellStorage(size);
    }
}
