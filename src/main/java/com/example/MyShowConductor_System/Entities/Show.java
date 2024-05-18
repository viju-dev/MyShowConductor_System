package com.example.MyShowConductor_System.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "shows")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private int id;

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate showDate;
    @Column(nullable = false)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime showTime; // "showTime": "07:30:00",

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

//    @Enumerated(EnumType.STRING)
//    private ScreenTypeEnum screenType;
//    @Column(nullable = false)
//    private String formats;

//    Mapping enum entities

//    @OneToMany(mappedBy ="show", cascade=CascadeType.ALL)
//    private List<Format> formats = new ArrayList<>();
//    @OneToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE})// fetch = FetchType.LAZY
//    @JoinColumn
//    private Format format;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "format_id")
    private Format format;

//        Mapping Show -> Theatre
    @ManyToOne()
    @JoinColumn
    private Theatre theatre;

//        Mapping Show -> Movie
    @ManyToOne
    @JoinColumn
    private Movie movie;
//privat

    //Mapping Show -> Ticket
    @OneToMany(mappedBy = "show",cascade = CascadeType.ALL)
    private List<Ticket> ticketList = new ArrayList<>();

    //Mapping Show -> ShowSeat
    @OneToMany(mappedBy = "show",cascade = CascadeType.ALL)
    private List<ShowSeat> showSeatList = new ArrayList<>();




}
