package com.example.MyShowConductor_System.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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
//    should we have ticket status true or false to solve transaction issue and old ticket issue
////there is no need of this attributes in tickets as its alredy mapped with everything but rethink in some cases its ok but still rethink in new way

//    @Column(nullable = false)
//    private String movieName;
//
//    @Column(nullable = false)
//    private LocalDate showDate;
//
//    @Column(nullable = false)
//    private LocalTime showTime;


    @Column(nullable = false)
    private int totalAmount;
//    private String transactionId = UUID.randomUUID().toString();
//    private boolean status;
    @Column(nullable = false)
    private String ticketId = UUID.randomUUID().toString(); //ticketid or booking id that will be printed on ticket
    //but not guaranteed to be unique so again rethink

//    @Column(nullable = false)
//    private String theaterName; // we can connect theatre here // or it will come throw show

//    @Column(nullable = false)
//    private String bookedSeats; // or we will use ths method again if dont work

    //Mapping Ticket -> User

    @OneToMany(mappedBy ="ticket",cascade = {CascadeType.PERSIST, CascadeType.MERGE})//we'll just save and merge associated entity but not delete
    private List<ShowSeat> bookedSeats = new ArrayList<>();

    @ManyToOne
    @JoinColumn
    private User user;

    //Mapping Ticket -> Show
    @ManyToOne
    @JoinColumn
    private Show show;

    @OneToMany(mappedBy = "ticket",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Transaction> transactions;

}
