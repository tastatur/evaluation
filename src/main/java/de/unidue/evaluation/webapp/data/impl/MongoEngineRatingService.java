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
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Benutze MongoDB, um Evaluierungsdaten zu speichern
 */

@Service("engineRatingService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MongoEngineRatingService implements EngineRatingService {

    @Autowired
    private MongoDbCLient mongoDbClient;

    @Autowired
    private EvaluationSessionService evaluationSessonService;

    @Override
    public void rateEngine(EnhancementEngine engine, int qualityRating, int speedRating) {
        Document rating = new Document("session", evaluationSessonService.getSessionId())
                .append("engine", evaluationSessonService.getCurrentEngine().toString())
                .append("qualityRating", qualityRating).append("speedRating", speedRating);
        mongoDbClient.saveRating(rating);
    }
}
