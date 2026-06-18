package de.digitalservice.gameoflife.domain;

/**
 * A fixed-size rectangular board of cells.
 *
 * <p>The grid owns its dimensions and exposes {@code (x, y)} access, delegating
 * the actual cell storage to a {@link CellStorage}. Callers never see whether the
 * cells live in a {@code boolean[]}, a {@link java.util.BitSet}, etc.
 */
public final class Grid {

    private final int width;
    private final int height;
    private final CellStorage storage;

    public Grid(int width, int height, CellStorage storage) {
        validateDimensions(width, height);
        if (storage.size() != width * height) {
            throw new IllegalArgumentException(
                    "Storage size " + storage.size() + " does not match grid " + width + "x" + height);
        }
        this.width = width;
        this.height = height;
        this.storage = storage;
    }

    /** Creates an all-dead grid backed by the default {@link BooleanArrayCellStorage}. */
    public static Grid empty(int width, int height) {
        // Validate before computing width * height so a negative product can't reach the array allocation.
        validateDimensions(width, height);
        return new Grid(width, height, new BooleanArrayCellStorage(width * height));
    }

    private static void validateDimensions(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException(
                    "Grid dimensions must be positive but were " + width + "x" + height);
        }
    }

    public boolean get(int x, int y) {
        return storage.get(index(x, y));
    }

    public void set(int x, int y, boolean alive) {
        storage.set(index(x, y), alive);
    }

    /** A fresh, all-dead grid of the same dimensions and storage kind. */
    public Grid emptyCopy() {
        return new Grid(width, height, storage.emptyOfSameKind(storage.size()));
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    private int index(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IndexOutOfBoundsException(
                    "Cell (" + x + ", " + y + ") is outside grid " + width + "x" + height);
        }
        return y * width + x;
    }
}
