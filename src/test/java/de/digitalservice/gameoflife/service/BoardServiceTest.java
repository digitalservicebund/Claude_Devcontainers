package de.digitalservice.gameoflife.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.digitalservice.gameoflife.config.GameOfLifeProperties;
import de.digitalservice.gameoflife.domain.BoundaryMode;
import de.digitalservice.gameoflife.domain.GenerationEngine;
import de.digitalservice.gameoflife.domain.Grid;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class BoardServiceTest {

    private BoardService newService(Long seed) {
        GameOfLifeProperties properties =
                new GameOfLifeProperties(6, 5, 0.5, BoundaryMode.TOROIDAL, seed);
        BoardService service = new BoardService(properties, new GenerationEngine());
        service.reset(); // mimic the @PostConstruct that Spring would invoke
        return service;
    }

    @Test
    void resetStartsAtGenerationZeroWithConfiguredDimensions() {
        BoardState state = newService(42L).currentState();

        assertThat(state.generation()).isZero();
        assertThat(state.grid().width()).isEqualTo(6);
        assertThat(state.grid().height()).isEqualTo(5);
        assertThat(state.boundaryMode()).isEqualTo(BoundaryMode.TOROIDAL);
    }

    @Test
    void nextAdvancesGeneration() {
        BoardService service = newService(42L);

        assertThat(service.next().generation()).isEqualTo(1);
        assertThat(service.next().generation()).isEqualTo(2);
    }

    @Test
    void resetReturnsToGenerationZero() {
        BoardService service = newService(42L);
        service.next();

        assertThat(service.reset().generation()).isZero();
    }

    @Test
    void fixedSeedProducesDeterministicBoard() {
        List<Boolean> first = cellsOf(newService(42L).currentState());
        List<Boolean> second = cellsOf(newService(42L).currentState());

        assertThat(first).isEqualTo(second);
    }

    private List<Boolean> cellsOf(BoardState state) {
        Grid grid = state.grid();
        List<Boolean> cells = new ArrayList<>();
        for (int y = 0; y < grid.height(); y++) {
            for (int x = 0; x < grid.width(); x++) {
                cells.add(grid.get(x, y));
            }
        }
        return cells;
    }
}
