package org.spring.data.access.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.spring.data.access.facade.BookingFacade;
import org.spring.data.access.model.Event;
import org.spring.data.access.util.ClientRequestMetaInfo;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/event")
public record EventController(BookingFacade bookingFacade) {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @GetMapping("/{id:[\\d]+}")
    public Event getEventById(@PathVariable Long id,
                              HttpServletRequest request) {
        ClientRequestMetaInfo.setClientIpAddress(request.getRemoteAddr());
        return bookingFacade.getEventById(id);
    }

    @GetMapping
    public List<Event> getEventsByTitle(@RequestParam String title,
                                        @RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "100") int size) {
        return bookingFacade.getEventsByTitle(title, size, page);
    }

    @GetMapping("/forDay")
    public List<Event> getEventsForDay(@RequestParam String day,
                                       @RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "100") int size) {
        var localDate = LocalDate.parse(day, DateTimeFormatter.ofPattern(DATE_PATTERN));
        var date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return bookingFacade.getEventsForDay(date, size, page);
    }

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return bookingFacade.createEvent(event);
    }

    @PatchMapping
    public Event updateEvent(@RequestBody Event event) {
        return bookingFacade.updateEvent(event);
    }

    @DeleteMapping("/{id:[\\d]+}")
    public void deleteEvent(@PathVariable Long id) {
        bookingFacade.deleteEvent(id);
    }

}
