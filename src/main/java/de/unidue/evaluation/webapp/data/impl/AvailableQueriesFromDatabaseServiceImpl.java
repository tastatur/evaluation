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
public class AvailableQueriesFromDatabaseServiceImpl implements AvailableQueriesFromDatabaseService {

    private static final String AVAILABLE_QUERIES_COLLECTION = "available_search_queries";

    @Autowired
    private MongoDbCLient mongoDbClient;

    @Override
    public FindIterable<Document> getPoliticalQueriesFromDb() {
        Document politicalQuerieSearch = new Document().append("type", "politic");
        return mongoDbClient.findDocuments(AVAILABLE_QUERIES_COLLECTION, politicalQuerieSearch);
    }

    @Override
    public FindIterable<Document> getWikiQueriesFromDb() {
        Document politicalQuerieSearch = new Document().append("type", "wiki");
        return mongoDbClient.findDocuments(AVAILABLE_QUERIES_COLLECTION, politicalQuerieSearch);
    }

    @Override
    public FindIterable<Document> getMiscQueriesFromDb() {
        Document politicalQuerieSearch = new Document().append("type", "misc");
        return mongoDbClient.findDocuments(AVAILABLE_QUERIES_COLLECTION, politicalQuerieSearch);
    }
}
