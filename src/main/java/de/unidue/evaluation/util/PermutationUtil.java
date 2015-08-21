package de.unidue.evaluation.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse, die uns hilft, alle mögliche Permutationen für eine Liste zu finden
 * Die brauchen wir für die Evaluierung, damit wir die Domains der Frage permutieren könnten
 */
public final class PermutationUtil {

    private PermutationUtil() {
    }


    /**
     * Berechne alle mögliche Permutationen einer Liste
     *
     * @param listToPermutate Die Liste, die permutiert werden soll
     * @return Die Kombination aller möglichen Permutationen
     */
    public static List<List<String>> getAllPermutations(List<String> original) {
        if (original.size() == 0) {
            List<List<String>> result = new ArrayList<>();
            result.add(new ArrayList<>());
            return result;
        }

        final List<String> listToPermutate = new ArrayList<>(original);
        String firstElement = listToPermutate.remove(0);
        List<List<String>> returnValue = new ArrayList<>();
        List<List<String>> permutations = getAllPermutations(listToPermutate);
        for (List<String> smallerPermutated : permutations) {
            for (int index = 0; index <= smallerPermutated.size(); index++) {
                List<String> temp = new ArrayList<>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        return returnValue;
    }
}
