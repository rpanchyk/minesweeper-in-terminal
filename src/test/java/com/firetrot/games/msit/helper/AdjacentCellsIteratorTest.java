package com.firetrot.games.msit.helper;

import com.firetrot.games.msit.model.Cell;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

public class AdjacentCellsIteratorTest {

    private Cell centerCell;

    private AdjacentCellsIterator iterator;

    @Before
    public void setUp() {
        centerCell = Cell.of(0, 0);
    }

    @Test
    public void hasNextShouldReturnFalseForEmptyCells() {
        // given
        final Set<Cell> cells = Collections.emptySet();
        iterator = new AdjacentCellsIterator(cells, centerCell);

        // when
        final boolean result = iterator.hasNext();

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void hasNextShouldReturnFalseForSingleCell() {
        // given
        final Set<Cell> cells = Collections.singleton(Cell.of(0, 0));
        iterator = new AdjacentCellsIterator(cells, centerCell);

        // when
        final boolean result = iterator.hasNext();

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void hasNextShouldReturnTrueForNotEmptyCells() {
        // given
        final Set<Cell> cells = Set.of(
            Cell.of(0, 0),
            Cell.of(0, 1));
        iterator = new AdjacentCellsIterator(cells, centerCell);

        // when
        final boolean result = iterator.hasNext();

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void nextShouldFailForEmptyCells() {
        // given
        final Set<Cell> cells = Collections.emptySet();
        iterator = new AdjacentCellsIterator(cells, centerCell);

        // when and then
        assertThatIllegalStateException().isThrownBy(() -> iterator.next());
    }

    @Test
    public void nextShouldFailForSingleCell() {
        // given
        final Set<Cell> cells = Collections.singleton(Cell.of(0, 0));
        iterator = new AdjacentCellsIterator(cells, centerCell);

        // when and then
        assertThatIllegalStateException().isThrownBy(() -> iterator.next());
    }

    @Test
    public void nextShouldReturnCellForNotEmptyCells() {
        // given
        final Set<Cell> cells = Set.of(
            Cell.of(0, 0),
            Cell.of(0, 1));
        iterator = new AdjacentCellsIterator(cells, centerCell);

        // when
        final Cell result = iterator.next();

        // then
        assertThat(result).isEqualTo(Cell.of(0, 1));
    }
}
