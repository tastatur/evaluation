package de.unidue.evaluation.webapp;

import java.util.List;

/**
 * Liefere alle verfügbare Suchanfragen
 */
public interface AvailableQueriesService {

    List<String> getAllAvailableSearchQueries();
}
