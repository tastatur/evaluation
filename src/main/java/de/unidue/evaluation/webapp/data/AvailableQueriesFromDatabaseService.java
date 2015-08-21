package de.unidue.evaluation.webapp.data;

import com.mongodb.async.client.FindIterable;
import org.bson.Document;

/**
 * Diese Klasse lädt aus einer Datenbank die Fragen, die für den Benutzer zur Verfügung stehen sollen
 */
public interface AvailableQueriesFromDatabaseService {

    FindIterable<Document> getPoliticalQueriesFromDb();

    FindIterable<Document> getWikiQueriesFromDb();

    FindIterable<Document> getMiscQueriesFromDb();
}
