package de.unidue.evaluation.webapp.impl;

import de.unidue.evaluation.webapp.AvailableQueriesService;
import de.unidue.evaluation.webapp.data.AvailableQueriesFromDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

@Service("availableQueriesService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AvailableQueriesServiceImpl implements AvailableQueriesService {

    private List<String> newspapersalQueries = new ArrayList<>();
    private List<String> miscQueries = new ArrayList<>();

    @Autowired
    private AvailableQueriesFromDatabaseService availableQueriesFromDatabaseService;

    @PostConstruct
    private void initQueries() {
        availableQueriesFromDatabaseService.getNewspapersQueriesFromDb().forEach(document -> newspapersalQueries.add(document.getString("query")), (aVoid, throwable) -> {
        });
        availableQueriesFromDatabaseService.getMiscQueriesFromDb().forEach(document -> miscQueries.add(document.getString("query")), (aVoid,
                                                                                                                                     throwable) -> {
        });
    }

    private List<String> getNewspapersQueries() {
        return newspapersalQueries;
    }

    private List<String> getMiscQueries() {
        return miscQueries;
    }

    @Override
    public List<String> getQueriesForSearchDomain(String searchDomain) {
        switch (searchDomain) {
            case "newspapers":
                return getNewspapersQueries();
            case "misc":
                return getMiscQueries();
            default:
                throw new InvalidParameterException();
        }
    }
}
