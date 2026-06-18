package de.digitalservice.gameoflife.web.dto;

import java.util.List;

/**
 * API response describing the board state.
 *
 * <p>{@code cells} is row-major: {@code cells.get(y).get(x)} is {@code true} when
 * the cell at column {@code x}, row {@code y} is alive.
 */
public record GridResponse(
        int generation,
        int width,
        int height,
        String boundaryMode,
        List<List<Boolean>> cells
) {
}
