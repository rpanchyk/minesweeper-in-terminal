package com.firetrot.games.msit.service;

import com.firetrot.games.msit.helper.AdjacentCellsIterator;
import com.firetrot.games.msit.model.Board;
import com.firetrot.games.msit.model.Cell;
import com.firetrot.games.msit.model.State;
import com.firetrot.games.msit.model.StateName;
import lombok.Getter;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Controls the state of board and allows to play the game.
 */
public class BoardManager {

    @Getter
    private final Board board;

    private BoardManager(Board board) {
        this.board = Objects.requireNonNull(board);
    }

    public static BoardManager create(int size, int minesCount) {
        final Map<Cell, State> cellToState = createCells(size);
        final Set<Cell> mines = createMines(size, minesCount);

        updateCellsWithAdjacentMines(cellToState, mines);

        final Board board = Board.of(size, cellToState, mines);
        return new BoardManager(board);
    }

    private static Map<Cell, State> createCells(int size) {
        final Map<Cell, State> cellToState = new HashMap<>(size * size);

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                cellToState.put(Cell.of(row, col), State.empty());
            }
        }

        return cellToState;
    }

    private static Set<Cell> createMines(int size, int minesCount) {
        final Set<Cell> mines = new HashSet<>(minesCount);

        final int cellsCount = size * size;
        final Random randomizer = new Random();

        while (mines.size() < minesCount) {
            final int random = randomizer.nextInt(cellsCount);

            final int row = random / size;
            final int col = random - row * size;

            final Cell cell = Cell.of(row, col);
            mines.add(cell);
        }

        return mines;
    }

    private static void updateCellsWithAdjacentMines(Map<Cell, State> cellToState, Set<Cell> mines) {
        final Set<Cell> cells = cellToState.keySet();

        for (Cell cell : cells) {
            if (mines.contains(cell)) {
                continue;
            }

            final int adjacentMinesCount = computeAdjacentMinesCount(cells, cell, mines);
            final State updatedState = State.of(StateName.CLOSED, adjacentMinesCount);
            cellToState.replace(cell, updatedState);
        }
    }

    private static int computeAdjacentMinesCount(Set<Cell> cells, Cell centerCell, Set<Cell> mines) {
        int adjacentMinesCount = 0;

        final AdjacentCellsIterator iterator = new AdjacentCellsIterator(cells, centerCell);
        while (iterator.hasNext()) {
            if (mines.contains(iterator.next())) {
                adjacentMinesCount++;
            }
        }

        return adjacentMinesCount;
    }

    /**
     * Returns true if cell is successfully opened, otherwise - false (exploded on mine).
     */
    public boolean open(Cell cell) {
        final Set<Cell> mines = board.getMines();
        if (mines.contains(cell)) {
            setCellStateName(cell, StateName.EXPLODED);
            return false;
        }

        open(cell, board.getCellToState());
        return true;
    }

    private void setCellStateName(Cell cell, StateName name) {
        final Map<Cell, State> cellToState = board.getCellToState();
        final State state = cellToState.get(cell);

        final State updatedState = State.of(name, state.getAdjacentMinesCount());
        cellToState.replace(cell, updatedState);
    }

    private void open(Cell cell, Map<Cell, State> cellToState) {
        final State state = cellToState.get(cell);
        if (state.getName() == StateName.OPENED) {
            return;
        }

        setCellStateName(cell, StateName.OPENED);

        if (state.getAdjacentMinesCount() == 0) {
            final AdjacentCellsIterator iterator = new AdjacentCellsIterator(cellToState.keySet(), cell);
            while (iterator.hasNext()) {
                open(iterator.next(), cellToState);
            }
        }
    }

    /**
     * Marks cell with flag.
     */
    public void flag(Cell cell) {
        setCellStateName(cell, StateName.FLAGGED);
    }

    /**
     * Returns true if no turns left.
     */
    public boolean isFinished() {
        final Predicate<Collection<State>> explodedOnMine = states -> states.stream()
            .map(State::getName)
            .anyMatch(name -> name == StateName.EXPLODED);

        final Predicate<Collection<State>> noFlaggedLeft = states -> states.stream()
            .map(State::getName)
            .filter(name -> name == StateName.FLAGGED)
            .count() == board.getMines().size();

        final Predicate<Collection<State>> noClosedLeft = states -> states.stream()
            .map(State::getName)
            .noneMatch(name -> name == StateName.CLOSED);

        final Collection<State> states = board.getCellToState().values();
        return explodedOnMine.test(states) || noFlaggedLeft.test(states) || noClosedLeft.test(states);
    }

    /**
     * Returns true if all mines are flagged.
     */
    public boolean isWon() {
        final Map<Cell, State> cellToState = board.getCellToState();
        final Set<Cell> mines = board.getMines();

        return mines.stream()
            .map(cellToState::get)
            .map(State::getName)
            .allMatch(name -> name == StateName.FLAGGED);
    }
}
