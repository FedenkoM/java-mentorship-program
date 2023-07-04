package org.spring.data.access.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.data.access.model.Event;
import org.spring.data.access.repository.EventRepository;
import org.spring.data.access.util.ClientRequestMetaInfo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final EventRepository eventRepository;

    public Event getEventById(long eventId) {
        var clientIpAddress = ClientRequestMetaInfo.getClientIpAddress();
        log.info("Client: {} request event data with id {}", clientIpAddress, eventId);
        return eventRepository.findById(eventId).orElseThrow(NoSuchElementException::new);
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return eventRepository.findByTitle(title, pageSize, pageNum);
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return eventRepository.getEventsForDay(day, pageSize, pageNum);
    }

    public Event createEvent(Event event) {
        return eventRepository.create(event);
    }

    public Event udateEvent(Event event) {
        return eventRepository.update(event);
    }

    public boolean deleteEvent(long eventId) {
        return eventRepository.deleteEvent(eventId);
    }
}
