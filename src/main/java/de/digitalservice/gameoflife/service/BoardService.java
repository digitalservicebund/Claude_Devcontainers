package de.digitalservice.gameoflife.service;

import de.digitalservice.gameoflife.config.GameOfLifeProperties;
import de.digitalservice.gameoflife.domain.GenerationEngine;
import de.digitalservice.gameoflife.domain.Grid;
import jakarta.annotation.PostConstruct;
import java.util.Random;
import org.springframework.stereotype.Service;

/**
 * Holds the single, application-wide board and drives it forward.
 *
 * <p>This is the one piece of mutable, shared state. {@code current} and
 * {@code generation} are guarded by {@code synchronized} so concurrent GET/POST
 * requests on the Tomcat worker threads see a consistent board. Each generation
 * replaces {@code current} with a brand-new {@link Grid} (it is never mutated in
 * place after publication), so a snapshot may safely share that reference.
 */
@Service
public class BoardService {

    private final GameOfLifeProperties properties;
    private final GenerationEngine engine;
    private final Random random;

    private Grid current;
    private int generation;

    public BoardService(GameOfLifeProperties properties, GenerationEngine engine) {
        this.properties = properties;
        this.engine = engine;
        this.random = properties.seed() != null ? new Random(properties.seed()) : new Random();
    }

    @PostConstruct
    void initialise() {
        reset();
    }

    public synchronized BoardState currentState() {
        return snapshot();
    }

    public synchronized BoardState next() {
        current = engine.next(current, properties.boundaryMode());
        generation++;
        return snapshot();
    }

    public synchronized BoardState reset() {
        current = randomGrid();
        generation = 0;
        return snapshot();
    }

    private Grid randomGrid() {
        Grid grid = Grid.empty(properties.width(), properties.height());
        for (int y = 0; y < grid.height(); y++) {
            for (int x = 0; x < grid.width(); x++) {
                grid.set(x, y, random.nextDouble() < properties.fillProbability());
            }
        }
        return grid;
    }

    private BoardState snapshot() {
        return new BoardState(current, generation, properties.boundaryMode());
    }
}
