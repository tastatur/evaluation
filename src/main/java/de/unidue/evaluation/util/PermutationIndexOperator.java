package de.unidue.evaluation.util;

import java.util.function.IntUnaryOperator;

/**
 * Gibt uns das nächste mögliche Wert, wenn es kleiner als Größe des Arrays ist,
 * oder 0, damit wir von vorne anfangen könnten
 */
public class PermutationIndexOperator implements IntUnaryOperator {
    private int arraySize;

    public PermutationIndexOperator(int arraySize) {
        this.arraySize = arraySize;
    }

    @Override
    public int applyAsInt(int operand) {
        if (operand >= arraySize -1) {
            return 0;
        }
        return operand + 1;
    }
}
