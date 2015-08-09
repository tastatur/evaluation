package de.unidue.evaluation.webapp.impl.viewmodels;

import de.unidue.evaluation.webapp.AvailableQueriesService;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Radiogroup;

import java.io.Serializable;
import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EngineViewModel implements Serializable {

    private Integer qualityOfEngine = 1;
    private Integer speedOfEngine = 1;

    @WireVariable
    private AvailableQueriesService availableQueriesService;

    public List<String> getSearchQueries() {
        return availableQueriesService.getAllAvailableSearchQueries();
    }

    @Command
    public void rateQuality(@BindingParam("checked") Radiogroup qualityRadioGroup) {
        qualityOfEngine = Integer.valueOf(qualityRadioGroup.getSelectedItem().getLabel());
    }

    @Command
    public void rateSpeed(@BindingParam("checked") Radiogroup speedRadioGroup) {
        speedOfEngine = Integer.valueOf(speedRadioGroup.getSelectedItem().getLabel());
    }
}
