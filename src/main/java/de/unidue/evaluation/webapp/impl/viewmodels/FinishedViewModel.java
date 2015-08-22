package de.unidue.evaluation.webapp.impl.viewmodels;

import de.unidue.proxyapi.util.EnhancementEngine;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FinishedViewModel extends EngineViewModel {

    @NotifyChange("*")
    public void setCurrentEngine(Integer engineNumber) {
        evaluationSessonService.setCurrentEngine(EnhancementEngine.values()[engineNumber - 1]);
        clearState();
    }
}
