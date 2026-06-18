package de.digitalservice.gameoflife.domain;

/**
 * The canonical Conway's Game of Life rules (B3/S23), expressed as a single pure
 * function so they can be reasoned about and tested in isolation.
 */
public final class GameRules {

    private GameRules() {
    }

    /**
     * Decides a cell's next state from its current state and live-neighbour count.
     *
     * <ul>
     *   <li>A live cell with fewer than two live neighbours dies (underpopulation).</li>
     *   <li>A live cell with two or three live neighbours survives.</li>
     *   <li>A live cell with more than three live neighbours dies (overpopulation).</li>
     *   <li>A dead cell with exactly three live neighbours becomes alive (birth).</li>
     * </ul>
     */
    public static boolean aliveNext(boolean alive, int neighbours) {
        return alive ? (neighbours == 2 || neighbours == 3) : (neighbours == 3);
    }
}
