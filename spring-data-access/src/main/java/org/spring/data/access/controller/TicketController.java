package org.spring.data.access.controller;

import org.spring.data.access.dto.TicketBookingRequest;
import org.spring.data.access.facade.BookingFacade;
import org.spring.data.access.model.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public record TicketController(BookingFacade bookingFacade) {

    @GetMapping("/user/{userId:[\\d]+}")
    @ResponseStatus(HttpStatus.OK)
    public List<Ticket> getTicketByUser(@PathVariable Long userId,
                                        @RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "100") int size) {
        var user = bookingFacade.getUserById(userId);
        return bookingFacade.getBookedTickets(user, size, page);
    }

    @GetMapping("/event/{eventId:[\\d]+}")
    @ResponseStatus(HttpStatus.OK)
    public List<Ticket> getTicketByEvent(@PathVariable Long eventId,
                                         @RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "100") int size) {
        var event = bookingFacade.getEventById(eventId);
        return bookingFacade.getBookedTickets(event, size, page);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ticket bookTicket(@RequestBody TicketBookingRequest bookingRequest) {
        return bookingFacade.bookTicket(bookingRequest.userId(),
                                        bookingRequest.eventId(),
                                        bookingRequest.place(),
                                        bookingRequest.category());
    }

    @DeleteMapping("/ticketId")
    @ResponseStatus(HttpStatus.OK)
    public void cancelTicket(@PathVariable Long ticketId) {
        bookingFacade.cancelTicket(ticketId);
    }
}
