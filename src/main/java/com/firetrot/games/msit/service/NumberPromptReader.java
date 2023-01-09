package com.firetrot.games.msit.service;

import java.util.Objects;
import java.util.Scanner;

/**
 * Reads and validates number as user input.
 */
public class NumberPromptReader {

    private final String prefix;
    private final int min;
    private final int max;
    private final Integer defaultValue;

    public NumberPromptReader(String prefix, int min, int max) {
        this(prefix, min, max, null);
    }

    public NumberPromptReader(String prefix, int min, int max, Integer defaultValue) {
        this.prefix = Objects.requireNonNull(prefix);
        this.min = min;
        this.max = max;
        this.defaultValue = defaultValue;
    }

    public int read() {
        Scanner in = new Scanner(System.in);

        final String defaultOption = defaultValue != null
            ? System.lineSeparator() + "or press Enter to use '" + defaultValue + "' as default"
            : "";
        final String text = prefix + " [" + min + "-" + max + "]"
            + defaultOption
            + ": ";

        String rawValue;
        int number = 0;
        do {
            System.out.print(text);
            rawValue = in.nextLine();
            if (defaultValue != null && rawValue.isEmpty()) {
                return defaultValue;
            }

            try {
                number = Integer.parseInt(rawValue);
            } catch (NumberFormatException e) {
                // ignored
            }
        } while (isNotValid(number, rawValue));

        return number;
    }

    private boolean isNotValid(int candidate, String rawValue) {
        final boolean notValid = candidate < min || candidate > max;
        if (notValid) {
            System.out.println("Value '" + rawValue + "' is not accepted, please try again.");
        }
        return notValid;
    }
}
