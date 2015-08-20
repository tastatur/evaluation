package de.unidue.evaluation.webapp.data;

import de.unidue.proxyapi.util.EnhancementEngine;

import java.util.Map;

/**
 * Der Service, der deie Bewertungen von Qualität und Geschwindigkeit in einer Datenbank speichert
 */
public interface EngineRatingService {

    void rateEngine(final EnhancementEngine engine, Map<String, Integer> qualityRatings, int speedRating, Integer helpQualityOfEngine);
}
