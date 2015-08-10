package de.unidue.evaluation.webapp;

import com.sun.istack.internal.NotNull;
import de.unidue.proxyapi.util.EnhancementEngine;

/**
 * Dieser Service hilft uns festzustellen, welcher Engine jetzt bewertet werden soll, und ob der Benutzer die Evaluierung beendet hat
 */
public interface EvaluationSessionService {

    /**
     * Läüft gerade eine Evaluierunbg?
     * @return ob es eine Evaluierung gestartet wurde
     */
    Boolean isEvalutionRunning();

    /**
     * Ob die Evaluiton beendet wurde
     * @return true, wenn die Evalution beendet wurde
     */
    Boolean isEvalutionFinished();

    /**
     * Falls die Evaluierung läuft, was für Engine soll jetzt gerade bewertet werden?
     * @return Der Engine, der jetzt verwendet werden soll
     */
    EnhancementEngine getCurrentEngine();

    /**
     * Setzt den Evaluierung-Engine
     */
    void setCurrentEngine(@NotNull EnhancementEngine engine);

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
     */
    void finishEvaluation();
}
