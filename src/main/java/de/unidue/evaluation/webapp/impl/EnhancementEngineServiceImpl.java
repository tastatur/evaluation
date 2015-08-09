package de.unidue.evaluation.webapp.impl;

import de.unidue.evaluation.webapp.EnhancementEngineService;
import de.unidue.proxyapi.util.EnhancementEngine;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("enhancementEngineService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EnhancementEngineServiceImpl  implements EnhancementEngineService {
    @Override
    public EnhancementEngine getNextEngine(final EnhancementEngine currentEngine) {
        switch (currentEngine) {
            case STANFORD:
                return EnhancementEngine.TIGER;
            case TIGER:
                return EnhancementEngine.PIG;
            default:
                return null;
        }
    }

    @Override
    public EnhancementEngine getFirstEngine() {
        return EnhancementEngine.STANFORD;
    }
}
