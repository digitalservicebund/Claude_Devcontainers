package de.digitalservice.gameoflife.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class GenerationEngineTest {

    private final GenerationEngine engine = new GenerationEngine();

    @Test
    void blockIsAStillLife() {
        // A 2x2 block has every cell with exactly three neighbours: it never changes.
        Grid grid = Grid.empty(4, 4);
        grid.set(1, 1, true);
        grid.set(2, 1, true);
        grid.set(1, 2, true);
        grid.set(2, 2, true);

        Grid next = engine.next(grid, BoundaryMode.FINITE);

        assertThat(next.get(1, 1)).isTrue();
        assertThat(next.get(2, 1)).isTrue();
        assertThat(next.get(1, 2)).isTrue();
        assertThat(next.get(2, 2)).isTrue();
        assertThat(countAlive(next)).isEqualTo(4);
    }

    @Test
    void blinkerOscillatesWithPeriodTwo() {
        // A vertical 3-cell blinker (away from edges) becomes horizontal, then vertical again.
        Grid grid = Grid.empty(5, 5);
        grid.set(2, 1, true);
        grid.set(2, 2, true);
        grid.set(2, 3, true);

        Grid horizontal = engine.next(grid, BoundaryMode.FINITE);

        assertThat(horizontal.get(1, 2)).isTrue();
        assertThat(horizontal.get(2, 2)).isTrue();
        assertThat(horizontal.get(3, 2)).isTrue();
        assertThat(horizontal.get(2, 1)).isFalse();
        assertThat(horizontal.get(2, 3)).isFalse();
        assertThat(countAlive(horizontal)).isEqualTo(3);

        Grid backToVertical = engine.next(horizontal, BoundaryMode.FINITE);

        assertThat(backToVertical.get(2, 1)).isTrue();
        assertThat(backToVertical.get(2, 2)).isTrue();
        assertThat(backToVertical.get(2, 3)).isTrue();
        assertThat(countAlive(backToVertical)).isEqualTo(3);
    }

    @Test
    void emptyGridStaysEmpty() {
        Grid grid = Grid.empty(4, 4);

        assertThat(countAlive(engine.next(grid, BoundaryMode.TOROIDAL))).isZero();
    }

    @Test
    void toroidalBoundaryWrapsNeighbours() {
        // Three live cells along the top row. Under TOROIDAL wrap, the cell in the
        // bottom row directly below the centre sees those three as neighbours and is born.
        Grid grid = topRowSegment();

        Grid next = engine.next(grid, BoundaryMode.TOROIDAL);

        assertThat(next.get(2, 4)).isTrue();
    }

    @Test
    void finiteBoundaryTreatsOutsideAsDead() {
        // Same top-row segment: with a FINITE boundary the bottom row stays dead (no wrap),
        // while the interior cell directly below the centre is still born.
        Grid grid = topRowSegment();

        Grid next = engine.next(grid, BoundaryMode.FINITE);

        assertThat(next.get(2, 4)).isFalse();
        assertThat(next.get(2, 1)).isTrue();
    }

    private Grid topRowSegment() {
        Grid grid = Grid.empty(5, 5);
        grid.set(1, 0, true);
        grid.set(2, 0, true);
        grid.set(3, 0, true);
        return grid;
    }

    private int countAlive(Grid grid) {
        int count = 0;
        for (int y = 0; y < grid.height(); y++) {
            for (int x = 0; x < grid.width(); x++) {
                if (grid.get(x, y)) {
                    count++;
                }
            }
        }
        return count;
    }
}
