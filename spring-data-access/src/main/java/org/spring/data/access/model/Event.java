package org.spring.data.access.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "date", nullable = false)
    private Date date;
    @Column(name = "ticket_price")
    private BigDecimal ticketPrice;

    public Event(String title, Date date) {
        this.title = title;
        this.date = date;
    }

    public Event(String title, Date date, BigDecimal ticketPrice) {
        this(title, date);
        this.ticketPrice = ticketPrice;
    }
}
