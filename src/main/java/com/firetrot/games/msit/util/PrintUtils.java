package com.firetrot.games.msit.util;


import com.firetrot.games.msit.model.Board;
import com.firetrot.games.msit.model.Cell;
import com.firetrot.games.msit.model.State;

import java.util.Map;
import java.util.Set;

public class PrintUtils {

    private PrintUtils() {
    }

    public static void printBoardWithMines(Board board) {
        printBoard("Board with mines", board, true);
    }

    public static void printBoardWithKnownCells(Board board) {
        printBoard("Board with cells", board, false);
    }

    private static void printBoard(String prefix, Board board, boolean unleashed) {
        final int size = board.getSize();
        final Set<Cell> mines = board.getMines();
        final Map<Cell, State> cellToState = board.getCellToState();

        final StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator());
        sb.append(prefix);
        sb.append(":");
        sb.append(System.lineSeparator());
        sb.append("   ");
        for (int col = 0; col < size; col++) {
            sb.append(" ");
            sb.append(col + 1);
            sb.append(" ");
        }
        sb.append(System.lineSeparator());
        sb.append("   ");
        sb.append("---".repeat(size));
        sb.append(System.lineSeparator());

        for (int row = 0; row < size; row++) {
            sb.append(row + 1);
            sb.append(" |");

            for (int col = 0; col < size; col++) {
                final Cell cell = Cell.of(row, col);
                final State state = cellToState.get(cell);

                sb.append(" ");

                if (unleashed) {
                    if (mines.contains(cell)) {
                        sb.append("M");
                    } else {
                        sb.append(state.getAdjacentMinesCount());
                    }
                } else {
                    sb.append(stateAsString(state));
                }

                sb.append(" ");
            }

            sb.append(System.lineSeparator());
        }

        System.out.println(sb);
    }

    private static String stateAsString(State state) {
        return switch (state.getName()) {
            case CLOSED -> ".";
            case OPENED -> String.valueOf(state.getAdjacentMinesCount());
            case FLAGGED -> "F";
            case EXPLODED -> "X";
        };
    }
}
