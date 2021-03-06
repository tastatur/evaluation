package de.unidue.evaluation.webapp.impl.viewmodels;

import de.unidue.evaluation.webapp.AvailableQueriesService;
import de.unidue.evaluation.webapp.EntityExtractionService;
import de.unidue.evaluation.webapp.EvaluationSessionService;
import de.unidue.evaluation.webapp.data.EngineRatingService;
import de.unidue.evaluation.webapp.data.FeedbackService;
import de.unidue.evaluation.webapp.data.QueryLogService;
import de.unidue.proxyapi.data.EnhancementResultEntry;
import de.unidue.proxyapi.data.entities.Entity;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radiogroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EngineViewModel implements Serializable {

    private Integer qualityOfEngine = 3;
    private Integer speedOfEngine = 3;
    private Integer helpQualityOfEngine = 3;

    private String searchQuery;
    private List<EnhancementResultEntry> snippets = new ArrayList<>();
    private EnhancementResultEntry selectedSnippet = null;
    private Entity selectedEntity;
    private String searchDomain;
    private String feedbackText;

    @WireVariable
    private AvailableQueriesService availableQueriesService;

    @WireVariable
    private EntityExtractionService entityExtractionService;

    @WireVariable
    protected EvaluationSessionService evaluationSessonService;

    @WireVariable
    private EngineRatingService engineRatingService;

    @WireVariable
    private QueryLogService queryLogService;

    @WireVariable
    private FeedbackService feedbackService;

    @Init
    public void init() {
        searchDomain = evaluationSessonService.getCurrentDomain();
    }

    @Command
    public void rateQuality(@BindingParam("checked") Radiogroup qualityRadioGroup) {
        qualityOfEngine = Integer.valueOf(qualityRadioGroup.getSelectedItem().getLabel());
    }

    @Command
    public void rateSpeed(@BindingParam("checked") Radiogroup speedRadioGroup) {
        speedOfEngine = Integer.valueOf(speedRadioGroup.getSelectedItem().getLabel());
    }

    @Command
    public void rateHelpQualityOfEngine(@BindingParam("checked") Radiogroup helpQualityRadioGroup) {
        helpQualityOfEngine = Integer.valueOf(helpQualityRadioGroup.getSelectedItem().getLabel());
    }

    @Command
    public void extractEntities(@BindingParam("snippetsBox") Listbox snippetsBox, @BindingParam("logRequest") Boolean logRequest) throws Exception {
        Clients.showBusy("Suche nach Entitäten...");
        snippetsBox.addEventListener(Events.ON_CLIENT_INFO, event -> {
            snippets = entityExtractionService.getEntitiesForSearchQuery(getSearchQuery()).getEnhancementResults();
            if (logRequest) {
                queryLogService.addQueryLog(evaluationSessonService.getSessionId(), getSearchQuery(),
                        evaluationSessonService.getCurrentEngine().toString());
            }
            Clients.clearBusy();
            selectedSnippet = null;
            selectedEntity = null;
            BindUtils.postNotifyChange(null, null, this, "*");
        });
        Events.echoEvent(Events.ON_CLIENT_INFO, snippetsBox, null);
    }

    @Command
    @NotifyChange("*")
    public void nextEngine() {
        engineRatingService.rateEngine(evaluationSessonService.getCurrentEngine(), qualityOfEngine, speedOfEngine, helpQualityOfEngine, searchDomain);
        evaluationSessonService.nextEngine();
        if (evaluationSessonService.isEvalutionFinished()) {
            Executions.sendRedirect("/finished.zul");
        } else {
            Clients.showNotification("Danke für die Bewertung, bitte bewerte jetzt nächsten Engine.");
            clearState();
            searchDomain = evaluationSessonService.getCurrentDomain();
        }
    }

    @Command
    public void sendFeedback() {
        if (evaluationSessonService.isFeedbackWasSent()) {
            Clients.showNotification("Du hast den Feedback schon abgegeben");
            return;
        }
        feedbackService.sendFeedback(evaluationSessonService.getSessionId(), feedbackText);
        evaluationSessonService.toggleFeedbackWasSent();
        Clients.showNotification("Dein Feedback wurde erfolgreich gespeichert");
    }

    protected void clearState() {
        snippets.clear();
        selectedSnippet = null;
        selectedEntity = null;
    }

    public List<String> getSearchQueries() {
        return availableQueriesService.getQueriesForSearchDomain(searchDomain);
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public List<EnhancementResultEntry> getSnippets() {
        return snippets;
    }

    public void setSnippets(List<EnhancementResultEntry> snippets) {
        this.snippets = snippets;
    }

    public EnhancementResultEntry getSelectedSnippet() {
        return selectedSnippet;
    }

    public void setSelectedSnippet(EnhancementResultEntry selectedSnippet) {
        this.selectedSnippet = selectedSnippet;
    }

    public Entity getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(Entity selectedEntity) {
        this.selectedEntity = selectedEntity;
    }

    public void setFeedbackText(final String feedbackText) {
        this.feedbackText = feedbackText;
    }
}
