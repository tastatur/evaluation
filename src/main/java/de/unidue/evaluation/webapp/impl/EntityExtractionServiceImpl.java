package de.unidue.evaluation.webapp.impl;

import de.unidue.evaluation.webapp.EntityExtractionService;
import de.unidue.evaluation.webapp.EvaluationSessionService;
import de.unidue.misc.search.karatassis.BingSearchService;
import de.unidue.misc.search.karatassis.BingWebResult;
import de.unidue.proxyapi.connection.EnhancementClient;
import de.unidue.proxyapi.connection.impl.StanbolClient;
import de.unidue.proxyapi.data.EnhancementResults;
import de.unidue.proxyapi.data.SearchSnippet;
import de.unidue.proxyapi.data.SearchSnippets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service("entityExtractionService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EntityExtractionServiceImpl implements EntityExtractionService {

    private EnhancementClient stanbolClient;

    @Autowired
    private BingSearchService searchService;

    @Autowired
    private EvaluationSessionService evaluationSessionService;

    @Value("${de.unidue.stanbol.address}")
    private String stanbolAdress;

    @PostConstruct
    public void setupStanbolClient() {
        stanbolClient = new StanbolClient(stanbolAdress);
    }

    @Override
    public EnhancementResults getEntitiesForSearchQuery(String searchQuery) throws Exception {
        final List<BingWebResult> searchResults = searchService.executeSearchQuery(searchQuery);
        final SearchSnippets snippets = new SearchSnippets();
        searchResults.stream().map(bingSearchResult -> {
            try {
                final URL siteUrl = new URL(bingSearchResult.getUrl());
                final String snippetText = bingSearchResult.getDescription();
                return new SearchSnippet(siteUrl, snippetText);
            } catch (MalformedURLException ex) {
                getLogger().error("Ich kann keine Entitäten extrahieren", ex);
                return null;
            }
        }).forEach(snippets::addSearchSnippet);

        return stanbolClient.getEntitiesForSnippets(snippets, evaluationSessionService.getCurrentEngine());
    }
}
