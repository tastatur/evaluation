package de.unidue.evaluation.webapp.data;

import de.unidue.proxyapi.util.EnhancementEngine;

import java.util.Map;

/**
 * Der Service, der deie Bewertungen von Qualität und Geschwindigkeit in einer Datenbank speichert
 */
public interface EngineRatingService {

    void rateEngine(final EnhancementEngine engine, int qualityRating, int speedRating, int helpQualityRating, String domainRated);
}
