package de.digitalservice.gameoflife.domain;

/**
 * Computes the next generation of a {@link Grid}.
 *
 * <p>Stateless and framework-free. The next generation is computed into a fresh
 * buffer (double-buffering) so the current grid is never mutated mid-pass — the
 * classic source of Game of Life bugs.
 */
public class GenerationEngine {

    /** Returns a new grid holding the generation that follows {@code current}. */
    public Grid next(Grid current, BoundaryMode boundaryMode) {
        Grid buffer = current.emptyCopy();
        for (int y = 0; y < current.height(); y++) {
            for (int x = 0; x < current.width(); x++) {
                int neighbours = countNeighbours(current, x, y, boundaryMode);
                buffer.set(x, y, GameRules.aliveNext(current.get(x, y), neighbours));
            }
        }
        return buffer;
    }

    private int countNeighbours(Grid grid, int x, int y, BoundaryMode boundaryMode) {
        int count = 0;
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) {
                    continue;
                }
                if (isLiveNeighbour(grid, x + dx, y + dy, boundaryMode)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isLiveNeighbour(Grid grid, int x, int y, BoundaryMode boundaryMode) {
        return switch (boundaryMode) {
            // Math.floorMod (not %) so that -1 wraps to the far edge instead of staying negative.
            case TOROIDAL -> grid.get(Math.floorMod(x, grid.width()), Math.floorMod(y, grid.height()));
            case FINITE -> isInside(grid, x, y) && grid.get(x, y);
        };
    }

    private boolean isInside(Grid grid, int x, int y) {
        return x >= 0 && x < grid.width() && y >= 0 && y < grid.height();
    }
}
