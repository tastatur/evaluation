package de.unidue.evaluation.webapp.permutation;

import java.util.List;

/**
 * Dieses Service ist für die Permutation von Domain zuständig:
 * Erste Benutzer bekommt die Permutation ["wiki", "politik", "misc"], der zweite -
 * ["misc", "wiki", "politik"] und so weiter
 */
public interface DomainPermutationService {

    List<String> getNextDomainPermutation();
}
