package de.unidue.evaluation.webapp.data;

import de.unidue.proxyapi.util.EnhancementEngine;

/**
 * Der Service, der deie Bewertungen von Qualität und Geschwindigkeit in einer Datenbank speichert
 */
public interface EngineRatingService {

    void rateEngine(final EnhancementEngine engine, int qualityRating, int speedRating);
}
