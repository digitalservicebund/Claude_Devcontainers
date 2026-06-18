package de.digitalservice.gameoflife.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class GridTest {

    @Test
    void storesAndReadsCells() {
        Grid grid = Grid.empty(3, 2);

        grid.set(2, 1, true);

        assertThat(grid.get(2, 1)).isTrue();
        assertThat(grid.get(0, 0)).isFalse();
        assertThat(grid.width()).isEqualTo(3);
        assertThat(grid.height()).isEqualTo(2);
    }

    @Test
    void emptyCopyIsAllDeadAndIndependent() {
        Grid grid = Grid.empty(3, 3);
        grid.set(1, 1, true);

        Grid copy = grid.emptyCopy();

        assertThat(copy.get(1, 1)).isFalse();
        copy.set(0, 0, true);
        assertThat(grid.get(0, 0)).isFalse();
    }

    @Test
    void rejectsOutOfBoundsAccess() {
        Grid grid = Grid.empty(2, 2);

        assertThatThrownBy(() -> grid.get(2, 0)).isInstanceOf(IndexOutOfBoundsException.class);
        assertThatThrownBy(() -> grid.set(0, -1, true)).isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void rejectsNonPositiveDimensions() {
        assertThatThrownBy(() -> Grid.empty(0, 5)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Grid.empty(5, -1)).isInstanceOf(IllegalArgumentException.class);
    }
}
