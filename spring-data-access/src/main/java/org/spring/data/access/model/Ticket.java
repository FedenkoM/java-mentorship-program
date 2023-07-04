package org.spring.data.access.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket implements BaseEntity {

    public enum Category {STANDARD, PREMIUM, BAR}

    @Id
    private long id;
    @Column(name = "user_id", nullable = false)
    private long userId;
    @Column(name = "event_id", nullable = false)
    private long eventId;
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;
    @Column(name = "place", nullable = false)
    private int place;

    public Ticket(long userId, long eventId, Category category, int place) {
        this.userId = userId;
        this.eventId = eventId;
        this.category = category;
        this.place = place;
    }
}
