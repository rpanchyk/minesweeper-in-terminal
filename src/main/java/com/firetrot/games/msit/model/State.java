package com.firetrot.games.msit.model;

import lombok.Value;

/**
 * Holds the state of cell.
 */
@Value(staticConstructor = "of")
public class State {

    private static State EMPTY_STATE = State.of(StateName.CLOSED, 0);

    StateName name;

    int adjacentMinesCount;

    public static State empty() {
        return EMPTY_STATE;
    }
}
