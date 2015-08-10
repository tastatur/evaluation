package de.unidue.evaluation.webapp.impl.viewmodels;

import de.unidue.evaluation.webapp.AvailableQueriesService;
import de.unidue.evaluation.webapp.EntityExtractionService;
import de.unidue.evaluation.webapp.data.EntityExtractionRepresentation;
import de.unidue.proxyapi.data.entities.Entity;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Radiogroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EngineViewModel implements Serializable {

    private Integer qualityOfEngine = 1;
    private Integer speedOfEngine = 1;
    private String searchQuery;
    private List<EntityExtractionRepresentation> snippets = new ArrayList<>();
    private EntityExtractionRepresentation selectedSnippet = new EntityExtractionRepresentation();
    private Entity selectedEntity;

    @WireVariable
    private AvailableQueriesService availableQueriesService;

    @WireVariable
    private EntityExtractionService entityExtractionService;

    @Command
    public void rateQuality(@BindingParam("checked") Radiogroup qualityRadioGroup) {
        qualityOfEngine = Integer.valueOf(qualityRadioGroup.getSelectedItem().getLabel());
    }

    @Command
    public void rateSpeed(@BindingParam("checked") Radiogroup speedRadioGroup) {
        speedOfEngine = Integer.valueOf(speedRadioGroup.getSelectedItem().getLabel());
    }

    @Command
    @NotifyChange("snippets")
    public void extractEntities() throws Exception {
        snippets = entityExtractionService.getEntitiesForSearchQuery(getSearchQuery());
    }

    public List<String> getSearchQueries() {
        return availableQueriesService.getAllAvailableSearchQueries();
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public List<EntityExtractionRepresentation> getSnippets() {
        return snippets;
    }

    public void setSnippets(List<EntityExtractionRepresentation> snippets) {
        this.snippets = snippets;
    }

    public EntityExtractionRepresentation getSelectedSnippet() {
        return selectedSnippet;
    }

    public void setSelectedSnippet(EntityExtractionRepresentation selectedSnippet) {
        this.selectedSnippet = selectedSnippet;
    }

    public Entity getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(Entity selectedEntity) {
        this.selectedEntity = selectedEntity;
    }
}
