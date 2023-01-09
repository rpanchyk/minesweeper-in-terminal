package com.firetrot.games.msit.service;

import com.firetrot.games.msit.model.Board;
import com.firetrot.games.msit.model.Cell;
import com.firetrot.games.msit.model.State;
import com.firetrot.games.msit.model.StateName;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardManagerTest {

    private BoardManager manager;

    @Test
    public void createShouldReturnEmptyBoard() {
        // given and when
        manager = BoardManager.create(0, 0);

        // then
        assertThat(manager.getBoard())
            .isEqualTo(Board.of(0, Collections.emptyMap(), Collections.emptySet()));
    }

    @Test
    public void createShouldReturnNotEmptyBoard() {
        // given and when
        manager = BoardManager.create(1, 0);

        // then
        final Map<Cell, State> cellToState = Map.of(Cell.of(0, 0), State.empty());
        final Set<Cell> mines = Collections.emptySet();

        assertThat(manager.getBoard())
            .isEqualTo(Board.of(1, cellToState, mines));
    }

    @Test
    public void createShouldReturnBoardWithMines() {
        // given and when
        manager = BoardManager.create(2, 4);

        // then
        final Map<Cell, State> cellToState = Map.of(
            Cell.of(0, 0), State.empty(),
            Cell.of(0, 1), State.empty(),
            Cell.of(1, 0), State.empty(),
            Cell.of(1, 1), State.empty());

        final Set<Cell> mines = Set.of(
            Cell.of(0, 0),
            Cell.of(0, 1),
            Cell.of(1, 0),
            Cell.of(1, 1));

        assertThat(manager.getBoard())
            .isEqualTo(Board.of(2, cellToState, mines));
    }

    @Test
    public void openShouldReturnFalseForMine() {
        // given
        manager = BoardManager.create(1, 1);

        // when
        final boolean result = manager.open(Cell.of(0, 0));

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void openShouldReturnTrueForNotMine() {
        // given
        manager = BoardManager.create(1, 0);

        // when
        final boolean result = manager.open(Cell.of(0, 0));

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void openShouldReturnAllCellsVisibleIfNoMines() {
        // given
        manager = BoardManager.create(2, 0);

        // when
        final boolean result = manager.open(Cell.of(0, 0));

        // then
        assertThat(result).isTrue();

        final Map<Cell, State> cellToState = Map.of(
            Cell.of(0, 0), State.of(StateName.OPENED, 0),
            Cell.of(0, 1), State.of(StateName.OPENED, 0),
            Cell.of(1, 0), State.of(StateName.OPENED, 0),
            Cell.of(1, 1), State.of(StateName.OPENED, 0));

        assertThat(manager.getBoard())
            .isEqualTo(Board.of(2, cellToState, Collections.emptySet()));
    }

    @Test
    public void flagShouldSetCellAsFlagged() {
        // given
        manager = BoardManager.create(1, 0);

        // when
        manager.flag(Cell.of(0, 0));

        // then
        final Map<Cell, State> cellToState = Collections.singletonMap(
            Cell.of(0, 0), State.of(StateName.FLAGGED, 0));

        assertThat(manager.getBoard())
            .isEqualTo(Board.of(1, cellToState, Collections.emptySet()));
    }

    @Test
    public void isFinishedShouldReturnFalseOnClosedCells() {
        // given
        manager = BoardManager.create(1, 1);

        // when
        final boolean result = manager.isFinished();

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void isFinishedShouldReturnTrueIfExplodedOnMine() {
        // given
        manager = BoardManager.create(1, 1);
        manager.open(Cell.of(0, 0));

        // when
        final boolean result = manager.isFinished();

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void isFinishedShouldReturnTrueIfNoFlaggedLeft() {
        // given
        manager = BoardManager.create(1, 1);
        manager.flag(Cell.of(0, 0));

        // when
        final boolean result = manager.isFinished();

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void isWonShouldReturnFalseIfFlaggedLeft() {
        // given
        manager = BoardManager.create(1, 1);

        // when
        final boolean result = manager.isWon();

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void isWonShouldReturnTrueIfNoFlaggedLeft() {
        // given
        manager = BoardManager.create(1, 1);
        manager.flag(Cell.of(0, 0));

        // when
        final boolean result = manager.isWon();

        // then
        assertThat(result).isTrue();
    }
}
