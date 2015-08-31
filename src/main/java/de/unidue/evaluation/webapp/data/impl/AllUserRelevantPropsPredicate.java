package de.unidue.evaluation.webapp.data.impl;

import com.hp.hpl.jena.util.iterator.Filter;
import com.hp.hpl.jena.vocabulary.RDF;
import de.unidue.proxyapi.data.entities.EntityProperty;
import de.unidue.proxyapi.data.vocabulary.EnhancementResultVocabulary;

public class AllUserRelevantPropsPredicate extends Filter<EntityProperty> {

    @Override
    public boolean accept(EntityProperty property) {
        return !property.getUri().equals(RDF.type.getURI()) &&
                !property.getUri().equals(EnhancementResultVocabulary.INDIVIDUALISED_GND.getURI()) &&
                !property.getName().equals("confidence") && !property.getName().equals("entityRank");
    }
}