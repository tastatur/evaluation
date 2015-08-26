package de.unidue.evaluation.webapp.data.impl;

import com.mongodb.async.client.FindIterable;
import de.unidue.evaluation.webapp.data.AvailableQueriesFromDatabaseService;
import de.unidue.evaluation.webapp.data.MongoDbCLient;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("availableQueriesFromDatabaseService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AvailableQueriesFromDatabaseServiceMongoImpl implements AvailableQueriesFromDatabaseService {

    private static final String AVAILABLE_QUERIES_COLLECTION = "available_search_queries";

    @Autowired
    private MongoDbCLient mongoDbClient;

    @Override
    public FindIterable<Document> getNewspapersQueriesFromDb() {
        Document newspapersalQuerieSearch = new Document().append("type", "newspapers");
        return mongoDbClient.findDocuments(AVAILABLE_QUERIES_COLLECTION, newspapersalQuerieSearch);
    }

    @Override
    public FindIterable<Document> getMiscQueriesFromDb() {
        Document newspapersalQuerieSearch = new Document().append("type", "misc");
        return mongoDbClient.findDocuments(AVAILABLE_QUERIES_COLLECTION, newspapersalQuerieSearch);
    }
}
