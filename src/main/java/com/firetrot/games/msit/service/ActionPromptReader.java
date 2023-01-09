package com.firetrot.games.msit.service;

import com.firetrot.games.msit.model.PromptAction;

import java.util.Scanner;

/**
 * Reads and validates action as user input.
 */
public class ActionPromptReader {

    public PromptAction read() {
        Scanner in = new Scanner(System.in);
        final String text = "Do action on cell [o - open, f - flag, q - exit]: ";

        String rawValue;
        PromptAction action = null;
        do {
            System.out.print(text);
            rawValue = in.nextLine();
            if (rawValue.isEmpty()) {
                continue;
            }

            action = PromptAction.of(rawValue.toLowerCase());
        } while (isNotValid(action, rawValue));

        return action;
    }

    private boolean isNotValid(PromptAction candidate, String rawValue) {
        final boolean notValid = candidate == null;
        if (notValid) {
            System.out.println("Value '" + rawValue + "' is not accepted, please try again.");
        }
        return notValid;
    }
}
