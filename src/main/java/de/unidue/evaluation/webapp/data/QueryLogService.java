package de.unidue.evaluation.webapp.data;

/**
 * Loggiere die Benutzeranfragen
 */
public interface QueryLogService {

    void addQueryLog(final String sessionId, final String query);
}
