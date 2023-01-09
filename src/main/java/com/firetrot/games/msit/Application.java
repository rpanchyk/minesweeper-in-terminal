package com.firetrot.games.msit;

import com.firetrot.games.msit.helper.MinesCountDefiner;
import com.firetrot.games.msit.model.Cell;
import com.firetrot.games.msit.model.PromptAction;
import com.firetrot.games.msit.model.PromptDifficulty;
import com.firetrot.games.msit.service.ActionPromptReader;
import com.firetrot.games.msit.service.BoardManager;
import com.firetrot.games.msit.service.DifficultyPromptReader;
import com.firetrot.games.msit.service.NumberPromptReader;
import com.firetrot.games.msit.util.PrintUtils;

/**
 * Entry point.
 */
public class Application {

    public static void main(String[] args) {
        final NumberPromptReader boardSizeReader = new NumberPromptReader("Enter board size as N*N", 2, 9, 8);
        final int boardSize = boardSizeReader.read();

        final DifficultyPromptReader difficultyReader = new DifficultyPromptReader(PromptDifficulty.NORMAL);
        final PromptDifficulty difficulty = difficultyReader.read();

        final int minesCount = MinesCountDefiner.define(boardSize, difficulty);
        final BoardManager manager = BoardManager.create(boardSize, Math.max(1, minesCount));

        final ActionPromptReader actionReader = new ActionPromptReader();
        final NumberPromptReader rowReader = new NumberPromptReader("Row", 1, boardSize);
        final NumberPromptReader colReader = new NumberPromptReader("Col", 1, boardSize);

        boolean canDoNextTurn;
        do {
            canDoNextTurn = doTurn(manager, actionReader, rowReader, colReader);
        } while (canDoNextTurn);
    }

    private static boolean doTurn(BoardManager manager,
                                  ActionPromptReader actionReader,
                                  NumberPromptReader rowReader,
                                  NumberPromptReader colReader) {

        PrintUtils.printBoardWithKnownCells(manager.getBoard());

        final PromptAction action = actionReader.read();
        if (action == PromptAction.QUIT) {
            return false;
        }

        final int row = rowReader.read() - 1;
        final int col = colReader.read() - 1;
        final Cell cell = Cell.of(row, col);

        if (action == PromptAction.OPEN) {
            manager.open(cell);
        } else if (action == PromptAction.FLAG) {
            manager.flag(cell);
        }

        if (manager.isFinished()) {
            PrintUtils.printBoardWithKnownCells(manager.getBoard());

            final boolean won = manager.isWon();
            System.out.println(won ? "Good job, you've found all mines!" : "Oops, seems you've exploded on mine!");

            PrintUtils.printBoardWithMines(manager.getBoard());
            System.out.println(won ? "Congratulations, you won!" : "Game is over, sorry... try again!");

            return false;
        }

        return true;
    }
}
