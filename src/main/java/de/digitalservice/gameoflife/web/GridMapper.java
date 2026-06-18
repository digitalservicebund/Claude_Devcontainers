package de.digitalservice.gameoflife.web;

import de.digitalservice.gameoflife.domain.Grid;
import de.digitalservice.gameoflife.service.BoardState;
import de.digitalservice.gameoflife.web.dto.GridResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Maps the domain {@link BoardState} to the {@link GridResponse} DTO, so domain
 * objects are never serialized directly.
 */
public final class GridMapper {

    private GridMapper() {
    }

    public static GridResponse toResponse(BoardState state) {
        Grid grid = state.grid();
        List<List<Boolean>> cells = new ArrayList<>(grid.height());
        for (int y = 0; y < grid.height(); y++) {
            List<Boolean> row = new ArrayList<>(grid.width());
            for (int x = 0; x < grid.width(); x++) {
                row.add(grid.get(x, y));
            }
            cells.add(row);
        }
        return new GridResponse(
                state.generation(),
                grid.width(),
                grid.height(),
                state.boundaryMode().name(),
                cells
        );
    }
}
