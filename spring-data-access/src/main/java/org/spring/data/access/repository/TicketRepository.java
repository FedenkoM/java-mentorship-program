package org.spring.data.access.repository;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.spring.data.access.annotation.LogMethod;
import org.spring.data.access.model.Event;
import org.spring.data.access.model.User;
import org.spring.data.access.model.Ticket;
import org.spring.data.access.util.IDGenerator;
import org.spring.data.access.util.Paginator;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Slf4j
@Setter
@Repository
public class TicketRepository {
    private final Map<Long, Ticket> ticketMap = new HashMap<>();
    private IDGenerator idGenerator;
    private Paginator<Ticket> paginator;

    @LogMethod
    public boolean isTicketExistByUserIdAndEventId(long userId, long eventId) {
        return ticketMap
            .values()
            .stream()
            .anyMatch(ticket -> ticket.getEventId() == eventId && ticket.getUserId() == userId);
    }

    @LogMethod
    public Ticket create(Ticket ticket) {
        long id = idGenerator.generate(Ticket.class);
        ticket.setId(id);
        ticketMap.put(id, ticket);
        return ticket;
    }

    @LogMethod
    public List<Ticket> getByUser(User user, int pageSize, int pageNum) {
        List<Ticket> filteredTickets = filter(ticket -> ticket.getUserId() == user.getId());
        return paginator.getPage(filteredTickets, pageNum, pageSize);
    }

    @LogMethod
    public List<Ticket> getByEvent(Event evet, int pageSize, int pageNum) {
        List<Ticket> filteredTickets = filter(ticket -> ticket.getEventId() == evet.getId());
        return paginator.getPage(filteredTickets, pageNum, pageSize);
    }

    @LogMethod
    public boolean deleteById(long ticketId) {
        if (ticketMap.containsKey(ticketId)) {
            ticketMap.remove(ticketId);
            return true;
        }
        log.info("Ticket with id: {} not found!", ticketId);
        return false;
    }


    private List<Ticket> filter(Predicate<Ticket> predicate) {
        return ticketMap.values().stream().filter(predicate).toList();
    }
}
