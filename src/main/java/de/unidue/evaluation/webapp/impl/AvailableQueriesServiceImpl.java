package de.unidue.evaluation.webapp.impl;

import de.unidue.evaluation.webapp.AvailableQueriesService;
import de.unidue.evaluation.webapp.data.AvailableQueriesFromDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("availableQueriesService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AvailableQueriesServiceImpl implements AvailableQueriesService {

    private static final List<String> AVAILABLE_DOMAINS = Arrays.asList("politic", "wiki", "misc");
    private List<String> politicalQueries = new ArrayList<>();
    private List<String> miscQueries = new ArrayList<>();
    private List<String> wikiQueries = new ArrayList<>();

    @Autowired
    private AvailableQueriesFromDatabaseService availableQueriesFromDatabaseService;

    @PostConstruct
    private void initQueries() {
        availableQueriesFromDatabaseService.getPoliticalQueriesFromDb().forEach(document -> politicalQueries.add(document.getString("query")), (aVoid, throwable) -> {
        });
        availableQueriesFromDatabaseService.getWikiQueriesFromDb().forEach(document -> wikiQueries.add(document.getString("query")), (aVoid,
                                                                                                                                     throwable) -> {
        });
        availableQueriesFromDatabaseService.getMiscQueriesFromDb().forEach(document -> miscQueries.add(document.getString("query")), (aVoid,
                                                                                                                                     throwable) -> {
        });
    }

    @Override
    public List<String> getPoliticalQueries() {
        return politicalQueries;
    }

    @Override
    public List<String> getAvailableDomains() {
        return AVAILABLE_DOMAINS;
    }

    @Override
    public List<String> getMiscQueries() {
        return miscQueries;
    }

    @Override
    public List<String> getWikiQueries() {
        return wikiQueries;
    }
}
