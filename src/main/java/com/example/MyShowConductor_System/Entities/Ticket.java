    package com.example.MyShowConductor_System.Entities;

    import com.example.MyShowConductor_System.Enums.TicketStatusEnum;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import org.hibernate.annotations.CreationTimestamp;

    import javax.persistence.*;
    import java.time.LocalDateTime;
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

        @Column(nullable = false)
        private boolean isActive;

        @CreationTimestamp
        private LocalDateTime createdAt;

        @Column(nullable = false)
        private int totalAmount;

        @Column(nullable = false)
        private TicketStatusEnum status;

        @Column(nullable = false)
        private String ticketId = UUID.randomUUID().toString(); //ticketid or booking id that will be printed on ticket

        @OneToMany(mappedBy ="ticket",cascade = {CascadeType.PERSIST, CascadeType.MERGE})//we'll just save and merge associated entity but not delete
        private List<ShowSeat> bookedSeats = new ArrayList<>();

        @ManyToOne
        @JoinColumn
        private User user;

        //Mapping Ticket -> Show
        @ManyToOne
        @JoinColumn
        private Show show;

        @OneToOne(mappedBy = "ticket")
        private Transaction transaction;

    }
