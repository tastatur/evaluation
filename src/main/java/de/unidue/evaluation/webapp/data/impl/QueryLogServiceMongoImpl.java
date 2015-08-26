package de.unidue.evaluation.webapp.data.impl;

import de.unidue.evaluation.webapp.data.MongoDbCLient;
import de.unidue.evaluation.webapp.data.QueryLogService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("queryLogService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class QueryLogServiceMongoImpl implements QueryLogService {

    private static final String QUERY_LOGS_COLLECTION = "query_logs";

    @Autowired
    private MongoDbCLient mongoDbClient;

    @Override
    public void addQueryLog(String sessionId, String query, String engine) {
        final Document queryLog = new Document().append("session", sessionId)
                .append("query", query)
                .append("engine", engine);
        mongoDbClient.saveDocument(QUERY_LOGS_COLLECTION, queryLog);
    }
}
