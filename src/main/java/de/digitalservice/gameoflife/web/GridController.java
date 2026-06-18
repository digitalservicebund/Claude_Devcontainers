package de.digitalservice.gameoflife.web;

import de.digitalservice.gameoflife.service.BoardService;
import de.digitalservice.gameoflife.web.dto.GridResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API for the Game of Life board.
 *
 * <ul>
 *   <li>{@code GET  /grid}        — current board state</li>
 *   <li>{@code POST /grid/next}   — advance one generation</li>
 *   <li>{@code POST /grid/reset}  — reset with a new random seed</li>
 * </ul>
 */
@RestController
@RequestMapping("/grid")
public class GridController {

    private final BoardService boardService;

    public GridController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public GridResponse currentGrid() {
        return GridMapper.toResponse(boardService.currentState());
    }

    @PostMapping("/next")
    public GridResponse nextGeneration() {
        return GridMapper.toResponse(boardService.next());
    }

    @PostMapping("/reset")
    public GridResponse reset() {
        return GridMapper.toResponse(boardService.reset());
    }
}
