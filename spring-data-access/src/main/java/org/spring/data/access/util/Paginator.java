package org.spring.data.access.util;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Setter
@Component
public class Paginator<T> {

    @Value("${service.paginator.message}")
    private String message;

    public List<T> getPage(List<T> sourceList, int pageNumber, int pageSize) {
        if(pageSize <= 0 || pageNumber <= 0) {
            log.warn(message, pageNumber, pageSize);
            throw new IllegalArgumentException("Page size and Page number should be greater than '0'");
        }

        int fromIndex = (pageNumber - 1) * pageSize;
        if(sourceList == null || sourceList.size() <= fromIndex){
            return Collections.emptyList();
        }

        return sourceList.stream()
            .skip(fromIndex)
            .limit(pageSize)
            .toList();
    }
}
