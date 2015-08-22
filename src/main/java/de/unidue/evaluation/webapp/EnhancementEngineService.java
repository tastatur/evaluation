package de.unidue.evaluation.webapp;

import de.unidue.proxyapi.util.EnhancementEngine;

public interface EnhancementEngineService {

    /**
     * Was für Engine soll als nächstes evaluiert werden?
     * @param currentEngine - Der Engine, der gerade bewertet wurde, darf nicht null sein.
     * @return Engine, der evaluiert werden soll oder null, wenn der Benutzer fertig ist.
     */
    EnhancementEngine getNextEngine(final EnhancementEngine currentEngine);

    /**
     * Was für Engine soll am Anfang bewertet werden
     * @return Engine, der als erstes evaluiert wird.
     */
    EnhancementEngine getFirstEngine();

    /**
     * Was für Engine soll dem nächsten Benutzer für seine Finished-Page zugeordnet werden.
     * Jeder Benutzer bekommt dabei das "nächste" Engine
     * @return Engine, das auf der Finished-Page als nächsts verwendet werden soll. Darf auf keinen Fall null sein.
     */
    EnhancementEngine getNextEngineForFinishedPage();
}
