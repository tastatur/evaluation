package de.unidue.evaluation.webapp.impl;

import de.unidue.evaluation.webapp.EntityExtractionService;
import de.unidue.evaluation.webapp.EvaluationSessionService;
import de.unidue.evaluation.webapp.data.EntityExtractionRepresentation;
import de.unidue.misc.search.karatassis.BingSearchService;
import de.unidue.misc.search.karatassis.BingWebResult;
import de.unidue.proxyapi.connection.EnhancementClient;
import de.unidue.proxyapi.connection.impl.StanbolClient;
import de.unidue.proxyapi.data.entities.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<EntityExtractionRepresentation> getEntitiesForSearchQuery(String searchQuery) throws Exception {
        final List<BingWebResult> searchResults = searchService.executeSearchQuery(searchQuery);
        final Map<String, String> snippets = new HashMap<>();
        searchResults.forEach(bingSnippet -> snippets.put(bingSnippet.getUrl(), bingSnippet.getDescription()));

        final Map<String, List<Entity>> entitiesInSearchResults = stanbolClient.filterEmptyResults(stanbolClient.getEntitiesForSnippets(snippets,
                evaluationSessionService.getCurrentEngine()));

        return transformApiResultsForView(snippets, entitiesInSearchResults);
    }

    private List<EntityExtractionRepresentation> transformApiResultsForView(Map<String, String> snippets, Map<String, List<Entity>> entitesInSearchResults) {
        final List<EntityExtractionRepresentation> extractedEntities = new ArrayList<>();
        entitesInSearchResults.forEach((url, entities) -> {
            final EntityExtractionRepresentation extractionResult = new EntityExtractionRepresentation();
            extractionResult.setSiteUrl(url);
            extractionResult.setSnippetText(snippets.get(url));
            extractionResult.setExtractedEntities(entities);
            extractedEntities.add(extractionResult);
        });
        return extractedEntities;
    }
}
