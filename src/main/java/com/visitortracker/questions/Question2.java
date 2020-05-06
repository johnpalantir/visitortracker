package com.visitortracker.questions;

import org.springframework.stereotype.Component;

/**
 * Read message encrypted message with length;
 */
@Component
public class Question2 {

    public String sendMessageLetterLeftToRight(final int A, final String input) {

        if (input == null || input.length() <= 0) {
            return null;
        }

        StringBuilder result = new StringBuilder();

            char[] ch = input.toCharArray();
            int r = ch.length / A;
            char[][] grid = new char[r][A];
            int k = 0;

        for (int i = 0; i < r; i++) {
                if (i % 2 == 0) {
                    for (int j = 0; j < A; j++) {
                        grid[i][j] = ch[k];
                        k++;
                    }

                } else {
                    for (int j = A - 1; j >= 0; j--) {
                        grid[i][j] = ch[k];
                        k++;
                    }
                }
            }
            for (int i = 0; i < A; i++) {
                for (int j = 0; j < r; j++) {
                    result.append(grid[j][i]);
                }
            }

        return result.toString();
    }
}
