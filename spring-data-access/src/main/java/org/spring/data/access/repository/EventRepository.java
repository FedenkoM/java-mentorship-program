package org.spring.data.access.repository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.spring.data.access.model.Event;
import org.spring.data.access.util.IDGenerator;
import org.spring.data.access.util.JsonConverter;
import org.spring.data.access.util.Paginator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Setter
@Repository
@RequiredArgsConstructor
public class EventRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventRepository.class);
    private final Map<Long, Event> eventStorage = new HashMap<>();
    private final Paginator<Event> paginator;
    private final IDGenerator idGenerator;
    private final JsonConverter jsonConverter;
    @Value("${source.event.filepath}")
    private String filepath;

    //bind file data to hashmap storage
    @PostConstruct
    void init() {
        LOGGER.info("Start Initiate event storage");
        List<Event> events = jsonConverter.fromJson(filepath, Event.class);
        events.forEach(event -> {
            long id = idGenerator.generate(Event.class);
            event.setId(id);
            eventStorage.put(id, event);
        });
        LOGGER.info("Data binding is successfully finished!");
    }

    public Optional<Event> findById(Long id) {
        LOGGER.info("Getting event by id: {}", id);
        return Optional.ofNullable(eventStorage.get(id));
    }

    public List<Event> findByTitle(String title, int pageSize, int pageNum) {
        LOGGER.info("Getting events by title: {}. Passed page size - {}, page number - {}", title, pageSize, pageNum);
        return paginator.getPage(filter(event -> event.getTitle().toLowerCase().contains(title.toLowerCase())), pageNum, pageSize);
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        LOGGER.info("Getting events by day: {}. Passed page size - {}, page number - {}", day, pageSize, pageNum);
        return paginator.getPage(filter(event -> event.getDate().equals(day)), pageNum, pageSize);
    }

    public Event create(Event event) {
        long id = idGenerator.generate(Event.class);
        event.setId(id);
        LOGGER.info("Creating new event: title={} date={}...", event.getTitle(), event.getDate());
        eventStorage.put(id, event);
        LOGGER.info("Event was successfully created!");
        return event;
    }

    public boolean deleteEvent(long eventId) {
        LOGGER.info("Deleting an event by id {}.", eventId);
        if (!eventStorage.containsKey(eventId)) {
            LOGGER.warn("The event was not found with such id");
            return false;
        }
        eventStorage.remove(eventId);
        LOGGER.info("The event was deleted successfully");
        return true;
    }

    public Event update(Event event) {
        if (eventStorage.containsKey(event.getId())) {
            LOGGER.info("Updating event with id:{}. Event[title={} date={}]", event.getId(), event.getTitle(), event.getDate());
            eventStorage.put(event.getId(), event);
            LOGGER.info("Event was successfully updated!");
        }
        LOGGER.info("Event not found for id:{}", event.getId());
        return null;
    }

    private List<Event> filter(Predicate<Event> predicate) {
        return eventStorage
            .values()
            .stream()
            .filter(predicate)
            .toList();
    }
}
