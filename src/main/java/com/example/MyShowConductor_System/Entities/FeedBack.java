package com.example.MyShowConductor_System.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//to get feedback about app
@Entity
@Table
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // know the difference
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String message;

    //ratings
    @Column(nullable = false)
    private Double appRating;

    @ManyToOne
    @JoinColumn
    private User user;

    //rating column out of 5
}
