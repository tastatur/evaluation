package de.unidue.evaluation.webapp.impl;

import de.unidue.evaluation.webapp.EnhancementEngineService;
import de.unidue.evaluation.webapp.EvaluationSessionService;
import de.unidue.evaluation.webapp.SessionAttributes;
import de.unidue.proxyapi.util.EnhancementEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

/**
 * Verwende Session, um zu gucken, auf welchem Schritt der Evaluierung steht jetzt der Benutzer
 */
@Service("evaluationSessonService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EvalutionSessionServiceImpl implements EvaluationSessionService {

    @Autowired
    private EnhancementEngineService enhancementEngineService;

    @Override
    public Boolean isEvalutionRunning() {
        final Session currentSession = Sessions.getCurrent();
        return (currentSession.hasAttribute(SessionAttributes.EVALUATION_STARTED.name()) &&
                ((Boolean) currentSession.getAttribute(SessionAttributes.EVALUATION_STARTED.name())));
    }

    @Override
    public Boolean isEvalutionFinished() {
        final Session currentSession = Sessions.getCurrent();
        return (currentSession.hasAttribute(SessionAttributes.EVALUATION_FINISHED.name()) &&
                ((Boolean) currentSession.getAttribute(SessionAttributes.EVALUATION_FINISHED.name())));
    }

    @Override
    public EnhancementEngine getCurrentEngine() {
        return (EnhancementEngine) Sessions.getCurrent().getAttribute(SessionAttributes.CURRENT_ENGINE.name());
    }

    @Override
    public void startEvaluation() {
        Sessions.getCurrent().setAttribute(SessionAttributes.EVALUATION_STARTED.name(), true);
        Sessions.getCurrent().setAttribute(SessionAttributes.CURRENT_ENGINE.name(), enhancementEngineService.getFirstEngine());
    }
}
