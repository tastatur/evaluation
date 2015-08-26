package de.unidue.evaluation.webapp.permutation;

import java.util.List;

/**
 * Dieses Service ist für die Permutation von Domain zuständig:
 * Erste Benutzer bekommt die Permutation ["politik", "misc"], der zweite -
 * ["misc", "politik"] und so weiter
 */
public interface DomainPermutationService {

    List<String> getNextDomainPermutation();
}
