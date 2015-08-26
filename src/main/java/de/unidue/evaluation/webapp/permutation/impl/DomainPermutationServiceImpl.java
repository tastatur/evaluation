package de.unidue.evaluation.webapp.permutation.impl;

import de.unidue.evaluation.util.PermutationIndexOperator;
import de.unidue.evaluation.util.PermutationUtil;
import de.unidue.evaluation.webapp.permutation.DomainPermutationService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

@Service("domainPermutationService")
public class DomainPermutationServiceImpl implements DomainPermutationService {

    private static final List<String> availableQueryDomains = Arrays.asList("newspapers", "misc");
    private List<List<String>> availablePermutations;
    private AtomicInteger atomicPermutationIndex = new AtomicInteger(0);
    private IntUnaryOperator permutationIndexOperator;

    @PostConstruct
    public void initAvailablePermutations() {
        final List<List<String>> permutations = new ArrayList<>();
        PermutationUtil.getAllPermutations(availableQueryDomains).forEach(permutation -> permutations.add(Collections.unmodifiableList(permutation)));
        availablePermutations = Collections.unmodifiableList(permutations);
        permutationIndexOperator = new PermutationIndexOperator(availablePermutations.size());
    }

    @Override
    public List<String> getNextDomainPermutation() {
        return availablePermutations.get(atomicPermutationIndex.getAndUpdate(permutationIndexOperator));
    }
}
