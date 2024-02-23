package com.example.MyShowConductor_System.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String movieName;

    @Column(nullable = false)
    private LocalDate showDate;

    @Column(nullable = false)
    private LocalTime showTime;

    @Column(nullable = false)
    private int totalAmount;
//    private String transactionId = UUID.randomUUID().toString();
//    private boolean status;
    @Column(nullable = false)
    private String ticketId = UUID.randomUUID().toString(); //ticketid or booking id that will be printed on ticket

    @Column(nullable = false)
    private String theaterName;

    @Column(nullable = false)
    private String bookedSeats;

    //Mapping Ticket -> User
    @ManyToOne
    @JoinColumn
    private User user;

    //Mapping Ticket -> Show
    @ManyToOne
    @JoinColumn
    private Show show;


}
