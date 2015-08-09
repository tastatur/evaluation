package de.unidue.evaluation.webapp.impl.init;

import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.Initiator;

import java.util.Map;

/**
 * Diese Klasse initialisiert Spring Beans für ZK Initiator
 */
public class WiringVariablesInitiator implements Initiator {
    @Override
    public void doInit(Page page, Map<String, Object> map) throws Exception {
        Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    }
}
