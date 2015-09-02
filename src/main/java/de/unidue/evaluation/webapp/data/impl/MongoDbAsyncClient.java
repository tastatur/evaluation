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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service("mongoDbClient")
public class MongoDbAsyncClient implements MongoDbCLient {

    private static final Logger log = LoggerFactory.getLogger(MongoDbAsyncClient.class);
    private static final String DATABASE_NAME = "evaluation";

    @Value("${de.unidue.mongoaddress}")
    private String mongoConnectionString;

    private MongoDatabase mongoDatabase;

    @PostConstruct
    public void initDbClient() {
        final MongoClient mongoClient = MongoClients.create(mongoConnectionString);
        mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);
        createIndexesIfNotExist();
    }

    private void createIndexesIfNotExist() {
        Document ratingsIndex = new Document().append("session", 1).append("engine", 1);
        mongoDatabase.getCollection("ratings").createIndex(ratingsIndex, new IndexOptions().unique(true),
                (s, throwable) -> log.debug("Index für ratings wurde erzeugt"));
        Document feetbackIndex = new Document().append("session", 1);
        mongoDatabase.getCollection("feedbacks").createIndex(feetbackIndex, new IndexOptions().unique(true),
                (s, throwable) -> log.debug("Index für feedbacks wurde auch erzeugt"));
    }

    @Override
    public FindIterable<Document> findDocuments(String collection, Document searchQuery) {
        return mongoDatabase.getCollection(collection).find(searchQuery);
    }

    @Override
    public void saveDocument(String collection, Document document) {
        mongoDatabase.getCollection(collection).insertOne(document,
                (result, throwable) -> log.debug("Document wurde gespeichert"));

    }
}
