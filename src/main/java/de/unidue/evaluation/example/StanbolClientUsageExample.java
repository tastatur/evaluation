package de.unidue.evaluation.example;


import de.unidue.misc.search.karatassis.BingSearchService;
import de.unidue.misc.search.karatassis.BingWebResult;
import de.unidue.proxyapi.connection.EnhancementClient;
import de.unidue.proxyapi.connection.impl.StanbolClient;
import de.unidue.proxyapi.data.entities.Entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Eine Beispielklasse, die dir zeigt, wie du die Stanbol-Bibliothek benutzen kannst
 * Es müssen auch folgende System Properties gesetzt werden (z.B. über die Option -D, wenn du dein Programm ausführst)!
 * 1)de.unidue.stanbol.address = die Addresse von Stanbol (http://eudora.is.informatik.uni-duisburg.de:8080 z.B.)
 * 2)de.unidue.search.api.key = Api-Schlüssel für dein Bing-Account
 * <p/>
 * Vereinfachte Version von BingSeqarchService ist der Arbeit von Karatassis entnommen.
 */
public class StanbolClientUsageExample {

    public static void main(String[] argv) throws Exception {
        final EnhancementClient stanbolClient = StanbolClient.getInstance();
        final List<BingWebResult> searchResults = BingSearchService.getInstance().executeSearchQuery("Präsident von Argentina");

        final Map<String, String> snippets = new HashMap<>();
        searchResults.forEach(bingSnippet -> snippets.put(bingSnippet.getUrl(), bingSnippet.getDescription()));

        final Map<String, List<Entity>> entitesInSearchResults = stanbolClient.getEntitiesForSnippets(snippets);
        System.out.println(entitesInSearchResults.size());
    }
}