package de.unidue.evaluation.webapp;

import de.unidue.evaluation.webapp.data.EntityExtractionRepresentation;

import java.util.List;

/**
 * Eine Utility-Klasse, die zuerst eine Suchanfrage an eine Suchmachiene sendet, und die Ergebnisse dann an ProxyAPI weiterleitet.
 */
public interface EntityExtractionService {

    List<EntityExtractionRepresentation> getEntitiesForSearchQuery(String searchQuery) throws Exception;
}
