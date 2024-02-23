package com.example.MyShowConductor_System.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Theatre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)//unique while creating theratre name + city
    private String name;

    @Column(nullable = false)
    private String address; //can set diffrent attr for location so that they can get filter using location

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String formats;

//    private String Facility;

    //Mapping Theatre -> Show
    @OneToMany(mappedBy = "theatre",cascade = CascadeType.ALL)
    private List<Show> showList = new ArrayList<>();

    //Mapping Theatre -> TheatreSeat
    @OneToMany(mappedBy = "theatre",cascade = CascadeType.ALL)
    private List<TheatreSeat> theatreSeatList = new ArrayList<>();

}
