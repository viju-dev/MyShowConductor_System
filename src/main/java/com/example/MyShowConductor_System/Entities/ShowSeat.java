package com.example.MyShowConductor_System.Entities;

import com.example.MyShowConductor_System.Enums.SeatTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String seatNo;

    @Column(nullable = false)
    private boolean isBooked;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatTypeEnum seatType;

    @Column(nullable = false)
    private int price; //price of CLASSIC Seat for that particular
    //book Date

    private Date bookedAt;

//    Mapping ShowSeat -> Ticket
    @ManyToOne
    @JoinColumn
    private Ticket ticket;

    //Mapping ShowSeat -> Show
    //here we can set column unique i guess so that show will not have multiple seats with same name
    @ManyToOne
    @JoinColumn
    private Show show;

}
