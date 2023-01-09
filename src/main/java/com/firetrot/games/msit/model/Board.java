package com.firetrot.games.msit.model;

import lombok.Value;

import java.util.Map;
import java.util.Set;

/**
 * Holds the state of game board.
 */
@Value(staticConstructor = "of")
public class Board {

    int size;

    Map<Cell, State> cellToState;

    Set<Cell> mines;
}
