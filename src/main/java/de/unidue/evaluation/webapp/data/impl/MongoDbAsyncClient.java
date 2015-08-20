package de.unidue.evaluation.webapp.data.impl;

import com.mongodb.async.client.FindIterable;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import de.unidue.evaluation.webapp.data.MongoDbCLient;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service("mongoDbClient")
public class MongoDbAsyncClient implements MongoDbCLient {

    private static final Logger log = LoggerFactory.getLogger(MongoDbAsyncClient.class);

    private static final String MONGO_CONNECTION_STRING = System.getProperty("de.unidue.mongoaddress");
    private static final String DATABASE_NAME = "evaluation";
    private static final String RATINGS_COLLECTION_NAME = "ratings";
    private static final String QUERY_LOGS_COLLECTION = "query_logs";

    private MongoDatabase mongoDatabase;

    @PostConstruct
    public void initDbClient() {
        final MongoClient mongoClient = MongoClients.create(MONGO_CONNECTION_STRING);
        mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);
        createIndexIfNotExist();
    }

    private void createIndexIfNotExist() {
        Document ratingsIndex = new Document().append("session", 1).append("engine", 1);
        mongoDatabase.getCollection(RATINGS_COLLECTION_NAME).createIndex(ratingsIndex, new IndexOptions().unique(true),
                (s, throwable) -> log.debug("Index wurde erzeugt"));
    }

    @Override
    public void saveRating(Document rating) {
        mongoDatabase.getCollection(RATINGS_COLLECTION_NAME).insertOne(rating,
                (result, throwable) -> log.debug("Bewertung wurde gespeichert"));
    }

    @Override
    public void saveQueryLog(Document queryLog) {
        mongoDatabase.getCollection(QUERY_LOGS_COLLECTION).insertOne(queryLog, (result, throwable) -> log.debug("Anfrage wurde in den Logs gespeichert"));
    }

    @Override
    public FindIterable<Document> findDocuments(String collection, Document searchQuery) {
        return mongoDatabase.getCollection(collection).find(searchQuery);
    }
}
