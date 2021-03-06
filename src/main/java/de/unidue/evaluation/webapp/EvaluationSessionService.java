package de.unidue.evaluation.webapp;

import de.unidue.proxyapi.util.EnhancementEngine;

/**
 * Dieser Service hilft uns festzustellen, welcher Engine jetzt bewertet werden soll, und ob der Benutzer die Evaluierung beendet hat
 */
public interface EvaluationSessionService {

    /**
     * Läüft gerade eine Evaluierunbg?
     *
     * @return ob es eine Evaluierung gestartet wurde
     */
    Boolean isEvalutionRunning();

    /**
     * Ob die Evaluiton beendet wurde
     *
     * @return true, wenn die Evalution beendet wurde
     */
    Boolean isEvalutionFinished();

    /**
     * Falls die Evaluierung läuft, was für Engine soll jetzt gerade bewertet werden?
     *
     * @return Der Engine, der jetzt verwendet werden soll
     */
    EnhancementEngine getCurrentEngine();

    /**
     * Setzt den Evaluierung-Engine
     */
    void setCurrentEngine(EnhancementEngine engine);

    /**
     * Starte die Evalution
     */
    void startEvaluation();

    /**
     * Gehe zum nächsten Schritt der Evaluierung.
     */
    void nextEngine();

    /**
     * Beende die Evaluierung
     * Die Implementierung dieser Methode muss unbedingt das Engine, das auf der Finished-Page
     * verwendet wird, als currentEngine setzen. Jeder Benutzer bekommt dabei das nächste Engine.
     */
    void finishEvaluation();

    /**
     * Der Domain, der jetzt evaluiert werden soll
     * @return Der Name des Domains
     */
    String getCurrentDomain();

    String getSessionId();

    /**
     * Ein Benutzer kann den Feedback nmur ein mal abgeben
     */
    void toggleFeedbackWasSent();
    Boolean isFeedbackWasSent();
}
