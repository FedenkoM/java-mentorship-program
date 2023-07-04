package org.spring.data.access.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.spring.data.access.model.Event;
import org.spring.data.access.model.Ticket;
import org.spring.data.access.model.User;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class JsonConverter {

    public <T> List<T> fromJson(String filepath, Class<T> clazz) {
        ClassPathResource resource = new ClassPathResource(filepath);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return convetToList(clazz, resource, objectMapper);
        } catch (Exception ex) {
            log.error(String.format("Can't parse %s -> %s", resource.getPath(), clazz.getName()));
            log.error(ex.toString(), ex);
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    private static <T> List<T> convetToList(Class<T> clazz,
        ClassPathResource resource,
        ObjectMapper objectMapper) throws IOException {
        if (clazz.isAssignableFrom(User.class)) {
            List<User> ts = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {});
            return (List<T>) ts;
        }
        if (clazz.isAssignableFrom(Ticket.class)) {
            List<Ticket> ts = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {});
            return (List<T>) ts;
        }
        if (clazz.isAssignableFrom(Event.class)) {
            List<Event> ts = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {});
            return (List<T>) ts;
        }
        return objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {});
    }
}
