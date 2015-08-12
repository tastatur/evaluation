package de.unidue.evaluation.webapp.impl.init;

import de.unidue.evaluation.webapp.EvaluationSessionService;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.util.Map;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FinishedPageInit extends WiringVariablesInitiator {

    @WireVariable
    private EvaluationSessionService evaluationSessonService;

    @Override
    public void doInit(Page page, Map<String, Object> map) throws Exception {
        super.doInit(page, map);

        if (evaluationSessonService.isEvalutionRunning()) {
            Executions.sendRedirect("/engine.zul");
        } else if (!evaluationSessonService.isEvalutionFinished()) {
            Executions.sendRedirect("/index.zul");
        }
    }
}
