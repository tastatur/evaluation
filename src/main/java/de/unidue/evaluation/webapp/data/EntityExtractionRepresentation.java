package de.unidue.evaluation.webapp.data;

import de.unidue.proxyapi.data.entities.Entity;

import java.io.Serializable;
import java.util.List;

public class EntityExtractionRepresentation implements Serializable {

    private String siteUrl;
    private String snippetText;
    private List<Entity> extractedEntities;

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getSnippetText() {
        return snippetText;
    }

    public void setSnippetText(String snippetText) {
        this.snippetText = snippetText;
    }

    public List<Entity> getExtractedEntities() {
        return extractedEntities;
    }

    public void setExtractedEntities(List<Entity> extractedEntities) {
        this.extractedEntities = extractedEntities;
    }
}
