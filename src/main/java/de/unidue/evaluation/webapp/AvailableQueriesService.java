package de.unidue.evaluation.webapp;

import java.util.List;

/**
 * Liefere alle verfügbare Suchanfragen
 */
public interface AvailableQueriesService {

    List<String> getPoliticalQueries();

    List<String> getMiscQueries();

    List<String> getWikiQueries();
}
