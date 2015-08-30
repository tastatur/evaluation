package de.unidue.evaluation.webapp;

import de.unidue.proxyapi.data.EnhancementResults;
import org.slf4j.Logger;

/**
 * Eine Utility-Klasse, die zuerst eine Suchanfrage an eine Suchmachiene sendet, und die Ergebnisse dann an ProxyAPI weiterleitet.
 */
public interface EntityExtractionService {

    default Logger getLogger() {
        return org.slf4j.LoggerFactory.getLogger(EntityExtractionService.class);
    }

    EnhancementResults getEntitiesForSearchQuery(String searchQuery) throws Exception;
}
