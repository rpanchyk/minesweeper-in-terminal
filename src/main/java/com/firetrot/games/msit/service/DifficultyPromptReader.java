package com.firetrot.games.msit.service;


import com.firetrot.games.msit.model.PromptDifficulty;

import java.util.Objects;
import java.util.Scanner;

/**
 * Reads and validates difficulty as user input.
 */
public class DifficultyPromptReader {

    private final PromptDifficulty defaultDifficulty;

    public DifficultyPromptReader(PromptDifficulty defaultDifficulty) {
        this.defaultDifficulty = Objects.requireNonNull(defaultDifficulty);
    }

    public PromptDifficulty read() {
        Scanner in = new Scanner(System.in);
        final String text = "Choose difficulty level [1 - easy, 2 - normal, 3 - hard]"
            + System.lineSeparator() + "or press Enter to use '" + defaultDifficulty.getValue() + "' as default"
            + ": ";

        String rawValue;
        PromptDifficulty difficulty;
        do {
            System.out.print(text);
            rawValue = in.nextLine();
            if (rawValue.isEmpty()) {
                return defaultDifficulty;
            }

            int valueAsInt = -1;
            try {
                valueAsInt = Integer.parseInt(rawValue);
            } catch (NumberFormatException e) {
                // ignored
            }

            difficulty = PromptDifficulty.of(valueAsInt);
        } while (isNotValid(difficulty, rawValue));

        return difficulty;
    }

    private boolean isNotValid(PromptDifficulty candidate, String rawValue) {
        final boolean notValid = candidate == null;
        if (notValid) {
            System.out.println("Value '" + rawValue + "' is not accepted, please try again.");
        }
        return notValid;
    }
}
