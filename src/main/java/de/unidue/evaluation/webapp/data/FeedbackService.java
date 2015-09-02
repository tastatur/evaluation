package de.unidue.evaluation.webapp.data;

/**
 * Gebe dem Benutzer die Möglichkeit, einen persönlichen Feedback abzugeben.
 */
public interface FeedbackService {

    /**
     * Gebe den Feedback ab
     * @param sessionId Id der Session
     * @param feedback Text des Feedbacks
     */
    void sendFeedback(final String sessionId, final String feedback);
}
