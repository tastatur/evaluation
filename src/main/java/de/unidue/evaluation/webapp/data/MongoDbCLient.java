package de.unidue.evaluation.webapp.data;

import org.bson.Document;

/**
 * Helper-Klasse, die sich um die Verbindung zum MongoDB kümmert
 */
public interface MongoDbCLient {

    void saveRating(final Document rating);
}
