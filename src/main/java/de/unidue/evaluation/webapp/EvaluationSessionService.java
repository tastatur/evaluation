package de.unidue.evaluation.webapp;

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
     * @return
     */
    EnhancementEngine getCurrentEngine();

    /**
     * Starte die Evalution
     */
    void startEvaluation();
}
