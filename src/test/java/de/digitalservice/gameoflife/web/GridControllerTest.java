package de.digitalservice.gameoflife.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.digitalservice.gameoflife.domain.BoundaryMode;
import de.digitalservice.gameoflife.domain.Grid;
import de.digitalservice.gameoflife.service.BoardService;
import de.digitalservice.gameoflife.service.BoardState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(GridController.class)
class GridControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BoardService boardService;

    private BoardState sampleState(int generation) {
        Grid grid = Grid.empty(3, 2);
        grid.set(0, 0, true);
        grid.set(2, 1, true);
        return new BoardState(grid, generation, BoundaryMode.TOROIDAL);
    }

    @Test
    void getGridReturnsCurrentState() throws Exception {
        given(boardService.currentState()).willReturn(sampleState(0));

        mockMvc.perform(get("/grid"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.generation").value(0))
                .andExpect(jsonPath("$.width").value(3))
                .andExpect(jsonPath("$.height").value(2))
                .andExpect(jsonPath("$.boundaryMode").value("TOROIDAL"))
                .andExpect(jsonPath("$.cells.length()").value(2))
                .andExpect(jsonPath("$.cells[0].length()").value(3))
                .andExpect(jsonPath("$.cells[0][0]").value(true))
                .andExpect(jsonPath("$.cells[1][2]").value(true));
    }

    @Test
    void postNextAdvancesGeneration() throws Exception {
        given(boardService.next()).willReturn(sampleState(1));

        mockMvc.perform(post("/grid/next"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.generation").value(1));
    }

    @Test
    void postResetReturnsGenerationZero() throws Exception {
        given(boardService.reset()).willReturn(sampleState(0));

        mockMvc.perform(post("/grid/reset"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.generation").value(0));
    }
}
