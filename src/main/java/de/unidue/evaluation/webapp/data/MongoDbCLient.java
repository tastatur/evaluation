package de.unidue.evaluation.webapp.data;

import com.mongodb.async.client.FindIterable;
import org.bson.Document;

/**
 * Helper-Klasse, die sich um die Verbindung zum MongoDB kümmert
 */
public interface MongoDbCLient {

    void saveRating(final Document rating);

    FindIterable<Document> findDocuments(final String collection, final Document searchQuery);
}
