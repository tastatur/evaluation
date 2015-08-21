package de.unidue.evaluation.webapp.data.impl;

import de.unidue.evaluation.webapp.EvaluationSessionService;
import de.unidue.evaluation.webapp.data.EngineRatingService;
import de.unidue.evaluation.webapp.data.MongoDbCLient;
import de.unidue.proxyapi.util.EnhancementEngine;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Benutze MongoDB, um Evaluierungsdaten zu speichern
 */

@Service("engineRatingService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EngineRatingServiceMongoImpl implements EngineRatingService {

    private static final String RATINGS_COLLECTION_NAME = "ratings";

    @Autowired
    private MongoDbCLient mongoDbClient;

    @Autowired
    private EvaluationSessionService evaluationSessonService;

    @Override
    public void rateEngine(EnhancementEngine engine, Map<String, Integer> qualityRatings, int speedRating, Integer helpQualityOfEngine) {
        Document rating = new Document("session", evaluationSessonService.getSessionId())
                .append("engine", evaluationSessonService.getCurrentEngine().toString())
                .append("qualityRatingPolitic", qualityRatings.get("politic"))
                .append("qualityRatingWiki", qualityRatings.get("wiki"))
                .append("qualityRatingMisc", qualityRatings.get("misc"))
                .append("speedRating", speedRating)
                .append("helpQuality", helpQualityOfEngine);
        mongoDbClient.saveDocument(RATINGS_COLLECTION_NAME, rating);
    }
}
