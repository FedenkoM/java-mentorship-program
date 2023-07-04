package org.spring.data.access.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.data.access.model.Event;
import org.spring.data.access.model.User;
import org.spring.data.access.model.Ticket;
import org.spring.data.access.repository.EventRepository;
import org.spring.data.access.repository.TicketRepository;
import org.spring.data.access.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        if (userRepository.findById(userId).isPresent() && eventRepository.findById(eventId).isPresent()) {
            if (ticketRepository.isTicketExistByUserIdAndEventId(userId, eventId)) {
                log.warn("User with id:{} and Event with id:{} already booked ticket", userId, eventId);
                throw new IllegalArgumentException("User already booked ticket for this event!");
            }

            Ticket ticket = new Ticket(userId, eventId, category, place);
            return ticketRepository.create(ticket);
        }

        throw new IllegalArgumentException(String.format("User with id:%s or Event with id:%s does not exist!", userId, eventId));
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return ticketRepository.getByUser(user, pageSize, pageNum);
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return ticketRepository.getByEvent(event, pageSize, pageNum);
    }

    public boolean cancelTicket(long ticketId) {
        return ticketRepository.deleteById(ticketId);
    }
}
