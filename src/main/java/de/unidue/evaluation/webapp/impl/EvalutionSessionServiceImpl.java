package de.unidue.evaluation.webapp.impl;

import com.google.common.collect.Iterables;
import de.unidue.evaluation.webapp.EnhancementEngineService;
import de.unidue.evaluation.webapp.EvaluationSessionService;
import de.unidue.evaluation.webapp.SessionAttributes;
import de.unidue.evaluation.webapp.permutation.DomainPermutationService;
import de.unidue.proxyapi.util.EnhancementEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;

/**
 * Verwende Session, um zu gucken, auf welchem Schritt der Evaluierung steht jetzt der Benutzer
 */
@Service("evaluationSessonService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EvalutionSessionServiceImpl implements EvaluationSessionService {

    @Autowired
    private EnhancementEngineService enhancementEngineService;

    @Autowired
    private DomainPermutationService domainPermutationService;

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
    public void setCurrentEngine(EnhancementEngine engine) {
        Sessions.getCurrent().setAttribute(SessionAttributes.CURRENT_ENGINE.name(), engine);
    }

    @Override
    public void startEvaluation() {
        Sessions.getCurrent().setAttribute(SessionAttributes.EVALUATION_STARTED.name(), true);
        setCurrentEngine(enhancementEngineService.getFirstEngine());

        setupDomainsPermutationIterator();
    }

    private void setupDomainsPermutationIterator() {
        final List<String> domains = domainPermutationService.getNextDomainPermutation();
        final Iterator<String> permutationIterator = Iterables.cycle(domains).iterator();
        Sessions.getCurrent().setAttribute(SessionAttributes.CURRENT_DOMAINS_PERMUTATION_ITER.name(), permutationIterator);
        Sessions.getCurrent().setAttribute(SessionAttributes.CURRENT_QUESTION_DOMAIN.name(), getNextDomain());
    }

    @Override
    public void nextEngine() {
        if (isEvalutionFinished()) {
            return;
        }

        final EnhancementEngine nextEngine = enhancementEngineService.getNextEngine(getCurrentEngine());
        if (nextEngine != null) {
            setCurrentEngine(nextEngine);
            Sessions.getCurrent().setAttribute(SessionAttributes.CURRENT_QUESTION_DOMAIN.name(), getNextDomain());
        } else {
            finishEvaluation();
        }
    }

    @Override
    public void finishEvaluation() {
        Sessions.getCurrent().setAttribute(SessionAttributes.EVALUATION_STARTED.name(), false);
        Sessions.getCurrent().setAttribute(SessionAttributes.EVALUATION_FINISHED.name(), true);
        Sessions.getCurrent().removeAttribute(SessionAttributes.CURRENT_DOMAINS_PERMUTATION_ITER.name());
        setCurrentEngine(enhancementEngineService.getNextEngineForFinishedPage());
    }

    @SuppressWarnings("unchecked")
    private String getNextDomain() {
        if (isEvalutionRunning()) {
            final String domain;
            final Iterator<String> permutationIterator = (Iterator<String>) Sessions.getCurrent().getAttribute(SessionAttributes
                    .CURRENT_DOMAINS_PERMUTATION_ITER.name());
            synchronized (permutationIterator) {
                domain = permutationIterator.next();
            }
            return domain;
        }

        return null;
    }

    @Override
    public String getCurrentDomain() {
        return (String) Sessions.getCurrent().getAttribute(SessionAttributes.CURRENT_QUESTION_DOMAIN.name());
    }

    @Override
    public String getSessionId() {
        return ((HttpSession) Sessions.getCurrent().getNativeSession()).getId();
    }

    @Override
    public void toggleFeedbackWasSent() {
        Sessions.getCurrent().setAttribute(SessionAttributes.FEEDBACK_WAS_SENT_FLAG.name(), true);
    }

    @Override
    public Boolean isFeedbackWasSent() {
        Boolean feedbackWasSent = false;
        final Object feedbackFlag = Sessions.getCurrent().getAttribute(SessionAttributes.FEEDBACK_WAS_SENT_FLAG.name());
        if (feedbackFlag != null) {
            feedbackWasSent = (Boolean) feedbackFlag;
        }
        return feedbackWasSent;
    }
}
