package de.unidue.evaluation.example;


import de.unidue.misc.search.karatassis.BingSearchService;
import de.unidue.misc.search.karatassis.BingWebResult;
import de.unidue.proxyapi.connection.EnhancementClient;
import de.unidue.proxyapi.connection.impl.StanbolClient;
import de.unidue.proxyapi.data.entities.Entity;
import de.unidue.proxyapi.util.EnhancementEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        final List<BingWebResult> searchResults = BingSearchService.getInstance().executeSearchQuery("Geburtstag von Angela Merkel");

        final Map<String, String> snippets = new HashMap<>();
        searchResults.forEach(bingSnippet -> snippets.put(bingSnippet.getUrl(), bingSnippet.getDescription()));

        //So bittest du Stanbol deine Suchergebnisse anzureichern.
        //Da wir mehrere Engines unterstützen, kannst du dem Stanbol auch mitteilen, welche Engine du testen möchtest
        //Es ist auch ganz nützlich, nur die Ergebnisse liefern zu lassen, wo Entitäten auch tatsächlich gefunden wurden.
        final Map<String, List<Entity>> entitesInSearchResults = stanbolClient.filterEmptyResults(stanbolClient.getEntitiesForSnippets(snippets,
                EnhancementEngine.STANFORD));
        System.out.println(entitesInSearchResults.size());

        //Und so kannst du für jede Webseite über die verlinkte Entitäten iterieren.
        //Da kannst du dir auch gleich den Sicherheitsgrad der Entität angucken, und bei Bedarf alle "unwichtige" Entitäten rausschmeisen.
        entitesInSearchResults.forEach((url, entities) -> {
            final List<Entity> importantEntities = entities.stream().filter(entity -> entity.getConfidence() >= 0.7).collect(Collectors.toList());
            System.out.println(String.format("Auf der Seite %s wurden %d wichtigen Entitäten gefudnden", url, entities.size()));

            //So arbeitest du mit Eigenschaften
            //Wenn die Entität einen bekannten Typ macht, hat die eine bestimmte Klasse aus dem data Package
            //Ansonsten wird die Basisklasse Entity verwendet
            importantEntities.forEach(entity -> {
                System.out.println(String.format("Abstract = %s", entity.getAbstract()));
                entity.getAllProperties().forEach(prop -> {
                    System.out.println(String.format("Property %s, Wert = %s", prop.getUri(), prop.getValue()));
                });
            });
        });
    }
}