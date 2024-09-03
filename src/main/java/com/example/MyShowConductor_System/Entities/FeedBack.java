    package com.example.MyShowConductor_System.Entities;

    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import org.hibernate.annotations.CreationTimestamp;

    import javax.persistence.*;
    import java.time.LocalDateTime;

    //to get feedback about app
    @Entity
    @Table
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class FeedBack {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;


        @Column(nullable = false)
        private String message;

        @CreationTimestamp()
        @Column(nullable = false)
        private LocalDateTime createdAt;

        //ratings
        @Column(nullable = false)
        private Double rating;

        @ManyToOne
        @JoinColumn
        private User user;

    }
