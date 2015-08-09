package de.unidue.evaluation.webapp.impl.viewmodels;

import de.unidue.evaluation.webapp.EvaluationSessionService;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.io.Serializable;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class IntroViewModel implements Serializable {

    @WireVariable
    private EvaluationSessionService evalutionSessonService;

    @Command
    public void startEvaluation() {
        evalutionSessonService.startEvaluation();
        Executions.sendRedirect("/engine.zul");
    }
}
