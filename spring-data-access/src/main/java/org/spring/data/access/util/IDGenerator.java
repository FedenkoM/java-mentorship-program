package org.spring.data.access.util;

import org.spring.data.access.model.BaseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class IDGenerator {

    private static final Map<Class<? extends BaseEntity>, Long> counters = new ConcurrentHashMap<>();

    public long generate(Class<? extends BaseEntity> clazz) {
        long counterValue = counters.computeIfAbsent(clazz, id -> 0L);
        counterValue++;
        counters.put(clazz, counterValue);
        return counterValue;
    }
}

