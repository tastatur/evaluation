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
    }

    @Override
    public void nextEngine() {
        if (isEvalutionFinished()) {
            return;
        }

        final EnhancementEngine nextEngine = enhancementEngineService.getNextEngine(getCurrentEngine());
        if (nextEngine != null) {
            setCurrentEngine(nextEngine);
        } else {
            finishEvaluation();
        }
    }

    @Override
    public void finishEvaluation() {
        Sessions.getCurrent().setAttribute(SessionAttributes.EVALUATION_STARTED.name(), false);
        Sessions.getCurrent().setAttribute(SessionAttributes.EVALUATION_FINISHED.name(), true);
        Sessions.getCurrent().removeAttribute(SessionAttributes.CURRENT_DOMAINS_PERMUTATION_ITER.name());
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getNextDomain() {
        final String domain;
        final Iterator<String> permutationIterator = (Iterator<String>) Sessions.getCurrent().getAttribute(SessionAttributes
                .CURRENT_DOMAINS_PERMUTATION_ITER.name());
        synchronized (permutationIterator) {
            domain = permutationIterator.next();
        }
        return domain;
    }

    @Override
    public String getSessionId() {
        return ((HttpSession) Sessions.getCurrent().getNativeSession()).getId();
    }
}
