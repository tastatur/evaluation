package de.unidue.evaluation.webapp.impl;

import de.unidue.evaluation.webapp.AvailableQueriesService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("availableQueriesService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AvailableQueriesServiceImpl implements AvailableQueriesService {

    private static final List<String> ALL_SEARCH_QUERIES = Arrays.asList("Geburtstag von Angela Merkel", "Bürgermeister von Duisburg",
            "Microsoft OS", "Fehler in Windows 98", "Steve Jobs Lebenslauf");

    @Override
    public List<String> getAllAvailableSearchQueries() {
        return ALL_SEARCH_QUERIES;
    }
}
