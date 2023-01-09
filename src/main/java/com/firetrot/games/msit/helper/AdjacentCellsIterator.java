package com.firetrot.games.msit.helper;

import com.firetrot.games.msit.model.Cell;
import lombok.AllArgsConstructor;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * Iterates over adjacent cells in order:
 * <pre>
 * 1. north (N)         => (row - 1, col)
 * 2. north-east (NE)   => (row - 1, col + 1)
 * 3. east (E)          => (row, col + 1)
 * 4. south-east (SE)   => (row + 1, col + 1)
 * 5. south (S)         => (row + 1, col)
 * 6. south-west (SW)   => (row + 1, col - 1)
 * 7. west (W)          => (row, col - 1)
 * 8. north-west (NW)   => (row - 1, col - 1)
 * </pre>
 * Visually (iterates clock-wise from N to NW):
 * <pre>
 * +----+----+----+
 * | NW | N  | NE |
 * +----+----+----+
 * | W  |    | E  |
 * +----+----+----+
 * | SW | S  | SE |
 * +----+----+----+
 * </pre>
 */
public class AdjacentCellsIterator implements Iterator<Cell> {

    private final Set<Cell> cells;
    private final Cell centerCell;

    private Side currentSide;

    public AdjacentCellsIterator(Set<Cell> cells, Cell centerCell) {
        this.cells = Objects.requireNonNull(cells);
        this.centerCell = Objects.requireNonNull(centerCell);
    }

    @Override
    public boolean hasNext() {
        return findNextSide() != null;
    }

    @Override
    public Cell next() {
        currentSide = findNextSide();
        if (currentSide == null) {
            throw new IllegalStateException("Out of cells");
        }

        final Cell cell = createCellFrom(centerCell, currentSide);
        if (cells.contains(cell)) {
            return cell;
        }

        throw new IllegalStateException("Unexpected cell");
    }

    private Cell createCellFrom(Cell centerCell, Side side) {
        final int row = centerCell.getRow() + side.rowShift;
        final int col = centerCell.getCol() + side.colShift;

        return Cell.of(row, col);
    }

    private Side findNextSide() {
        Side side = shiftNextSide(currentSide);

        while (side != null) {
            final Cell cell = createCellFrom(centerCell, side);
            if (cells.contains(cell)) {
                return side;
            }

            side = shiftNextSide(side);
        }

        return null;
    }

    private static Side shiftNextSide(Side side) {
        if (side == null) {
            return Side.NORTH;
        }
        return switch (side) {
            case NORTH -> Side.NORTH_EAST;
            case NORTH_EAST -> Side.EAST;
            case EAST -> Side.SOUTH_EAST;
            case SOUTH_EAST -> Side.SOUTH;
            case SOUTH -> Side.SOUTH_WEST;
            case SOUTH_WEST -> Side.WEST;
            case WEST -> Side.NORTH_WEST;
            case NORTH_WEST -> null;
        };
    }

    @AllArgsConstructor
    private enum Side {

        NORTH(-1, 0),
        NORTH_EAST(-1, 1),
        EAST(0, 1),
        SOUTH_EAST(1, 1),
        SOUTH(1, 0),
        SOUTH_WEST(1, -1),
        WEST(0, -1),
        NORTH_WEST(-1, -1);

        private final int rowShift;
        private final int colShift;
    }
}
