package de.unidue.evaluation.webapp.impl;

import de.unidue.evaluation.webapp.EntityExtractionService;
import de.unidue.evaluation.webapp.EvaluationSessionService;
import de.unidue.evaluation.webapp.data.FlatEntityRepresentation;
import de.unidue.misc.search.karatassis.BingSearchService;
import de.unidue.misc.search.karatassis.BingWebResult;
import de.unidue.proxyapi.connection.EnhancementClient;
import de.unidue.proxyapi.connection.impl.StanbolClient;
import de.unidue.proxyapi.data.entities.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("entityExtractionService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EntityExtractionServiceImpl implements EntityExtractionService {

    private final EnhancementClient stanbolClient = StanbolClient.getInstance();
    private final BingSearchService searchService = BingSearchService.getInstance();

    @Autowired
    private EvaluationSessionService evaluationSessionService;

    @Override
    public List<FlatEntityRepresentation> getEntitiesForSearchQuery(String searchQuery) throws Exception {
        final List<BingWebResult> searchResults = searchService.executeSearchQuery(searchQuery);
        final Map<String, String> snippets = new HashMap<>();
        searchResults.forEach(bingSnippet -> snippets.put(bingSnippet.getUrl(), bingSnippet.getDescription()));

        final Map<String, List<Entity>> entitesInSearchResults = stanbolClient.filterEmptyResults(stanbolClient.getEntitiesForSnippets(snippets,
                evaluationSessionService.getCurrentEngine()));

        return transformToFlatRepresentation(snippets, entitesInSearchResults);
    }

    private List<FlatEntityRepresentation> transformToFlatRepresentation(Map<String, String> snippets, Map<String, List<Entity>> entitesInSearchResults) {
        final List<FlatEntityRepresentation> flatEntityRepresentations = new ArrayList<>();
        snippets.forEach((url, snippet) -> {
            if (entitesInSearchResults.containsKey(url)) {
                entitesInSearchResults.get(url).forEach(entity -> {
                    final FlatEntityRepresentation flatEntity = new FlatEntityRepresentation();
                    flatEntity.setSiteUrl(url);
                    flatEntity.setSnippetText(snippet);
                    flatEntity.setExtractedEntity(entity);
                    flatEntityRepresentations.add(flatEntity);
                });
            }
        });
        return flatEntityRepresentations;
    }
}
