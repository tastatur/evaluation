package de.unidue.evaluation.webapp.data;

import de.unidue.proxyapi.data.entities.Entity;

import java.io.Serializable;

public class FlatEntityRepresentation implements Serializable {

    private String siteUrl;
    private String snippetText;
    private Entity extractedEntity;

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

    public Entity getExtractedEntity() {
        return extractedEntity;
    }

    public void setExtractedEntity(Entity extractedEntity) {
        this.extractedEntity = extractedEntity;
    }
}
