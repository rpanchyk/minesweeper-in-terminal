package com.firetrot.games.msit.model;

import lombok.Value;

/**
 * Holds the coordinates for cell on game board.
 */
@Value(staticConstructor = "of")
public class Cell {

    int row;

    int col;
}
