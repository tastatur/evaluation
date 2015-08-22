package de.unidue.evaluation.webapp.impl;

import de.unidue.evaluation.webapp.EnhancementEngineService;
import de.unidue.proxyapi.util.EnhancementEngine;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service("enhancementEngineService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EnhancementEngineServiceImpl implements EnhancementEngineService {

    private AtomicReference<EnhancementEngine> lastEngineOnFinishedPage = new AtomicReference<>(EnhancementEngine.STANFORD_BOTH);

    @Override
    public EnhancementEngine getNextEngine(final EnhancementEngine currentEngine) {
        switch (currentEngine) {
            case STANFORD_BOTH:
                return EnhancementEngine.STANFORD_DEWAC;
            case STANFORD_DEWAC:
                return EnhancementEngine.STANFORD_HGC;
            case STANFORD_HGC:
                return EnhancementEngine.MITIE_PIG;
            case MITIE_PIG:
                return EnhancementEngine.MITIE_TIGER;
            case MITIE_TIGER:
                return EnhancementEngine.TIGER;
            case TIGER:
                return EnhancementEngine.PIG;
            default:
                return null;
        }
    }

    @Override
    public EnhancementEngine getFirstEngine() {
        return EnhancementEngine.STANFORD_BOTH;
    }

    @Override
    public EnhancementEngine getNextEngineForFinishedPage() {
        return lastEngineOnFinishedPage.getAndUpdate(currentFinishedPageEngine -> {
            EnhancementEngine nextFinishedPageEngine = getNextEngine(currentFinishedPageEngine);
            if (nextFinishedPageEngine == null) {
                nextFinishedPageEngine = getFirstEngine();
            }
            return nextFinishedPageEngine;
        });
    }
}
