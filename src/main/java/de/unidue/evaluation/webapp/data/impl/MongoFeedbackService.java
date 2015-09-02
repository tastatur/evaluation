package de.unidue.evaluation.webapp.data.impl;

import de.unidue.evaluation.webapp.data.FeedbackService;
import de.unidue.evaluation.webapp.data.MongoDbCLient;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("feedbackService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MongoFeedbackService implements FeedbackService {

    private static final String FEEDBACKS_COLLECTION_NAME = "feedbacks";

    @Autowired
    private MongoDbCLient mongoDbClient;

    @Override
    public void sendFeedback(final String sessionId, final String feedbackText) {
        final Document feedback = new Document("session", sessionId).append("feedback", feedbackText);
        mongoDbClient.saveDocument(FEEDBACKS_COLLECTION_NAME, feedback);
    }
}
