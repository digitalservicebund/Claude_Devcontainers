package de.digitalservice.gameoflife.service;

import de.digitalservice.gameoflife.domain.BoundaryMode;
import de.digitalservice.gameoflife.domain.Grid;

/**
 * An immutable snapshot of the board returned by {@link BoardService}.
 *
 * <p>Holds the grid that was current at the moment of the call together with its
 * generation number and boundary mode, keeping the web layer decoupled from the
 * service's internal mutable state.
 */
public record BoardState(Grid grid, int generation, BoundaryMode boundaryMode) {
}
