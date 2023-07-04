package org.spring.data.access.service;

import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.spring.data.access.model.Event;
import org.spring.data.access.repository.EventRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@Disabled("Not implemented. Require starting application context. Tests disabled in pom.xml configuration")
@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    @Mock
    EventRepository eventRepository;
    @InjectMocks
    private EventService eventService;

    @Test
    public void whenCreateTestTheOneIsCreated() {
        Event event = new Event("testEvent", new Date(), new BigDecimal(BigInteger.ONE));
        when(eventRepository.create(any())).thenReturn(event);
        Event result = eventService.createEvent(event);
        assertEquals(event, result);
    }

    @Test
    public void whenDeleteEventTheOneIsDeleted() {
        when(eventRepository.deleteEvent(anyLong())).thenReturn(true);
        boolean result = eventService.deleteEvent(22);
        assertTrue(result);
    }

    @Test
    public void whenGetEventByIdTheEventIsReturned() {
        Event event = new Event("testEvent", new Date(), new BigDecimal(BigInteger.ONE));
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));
        Event result = eventService.getEventById(22);
        assertEquals(event, result);
    }

    @Test
    public void whenUpdateTestTheOneIsUpdated() {
        Event event = new Event("testEvent", new Date(), new BigDecimal(BigInteger.ONE));
        when(eventRepository.update(any())).thenReturn(event);
        Event result = eventService.udateEvent(event);
        assertEquals(event, result);
    }

    @Test
    public void whenGetEventsByTitleThenEventsAreReturned() {
        Event event = new Event("event", new Date(9999), new BigDecimal(BigInteger.ONE));
        when(eventRepository.findByTitle(anyString(), anyInt(), anyInt())).thenReturn(List.of(event));
        assertEquals(event.getTitle(), eventService.getEventsByTitle("event", 1, 1).get(0).getTitle());
    }

    @Test
    public void whenGetEventsForDayThenEventsAreReturned() {
        Event event = new Event("event", new Date(9999), new BigDecimal(BigInteger.ONE));
        when(eventRepository.getEventsForDay(anyObject(), anyInt(), anyInt())).thenReturn(List.of(event));
        List<Event> events = eventService.getEventsForDay(new Date(9999), 1, 1);
        assertEquals(event.getDate(), events.get(0).getDate());
    }
}