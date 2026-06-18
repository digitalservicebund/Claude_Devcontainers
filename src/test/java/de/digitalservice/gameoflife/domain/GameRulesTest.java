package de.digitalservice.gameoflife.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class GameRulesTest {

    @Test
    void liveCellWithFewerThanTwoNeighboursDies() {
        assertThat(GameRules.aliveNext(true, 0)).isFalse();
        assertThat(GameRules.aliveNext(true, 1)).isFalse();
    }

    @Test
    void liveCellWithTwoOrThreeNeighboursSurvives() {
        assertThat(GameRules.aliveNext(true, 2)).isTrue();
        assertThat(GameRules.aliveNext(true, 3)).isTrue();
    }

    @Test
    void liveCellWithMoreThanThreeNeighboursDies() {
        assertThat(GameRules.aliveNext(true, 4)).isFalse();
        assertThat(GameRules.aliveNext(true, 8)).isFalse();
    }

    @Test
    void deadCellWithExactlyThreeNeighboursIsBorn() {
        assertThat(GameRules.aliveNext(false, 3)).isTrue();
    }

    @Test
    void deadCellWithoutThreeNeighboursStaysDead() {
        assertThat(GameRules.aliveNext(false, 2)).isFalse();
        assertThat(GameRules.aliveNext(false, 4)).isFalse();
    }
}
