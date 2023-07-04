package org.spring.data.access.dto;

import org.spring.data.access.model.Ticket;

public record TicketBookingRequest(Long userId,
                                   Long eventId,
                                   Integer place,
                                   Ticket.Category category) {
}
