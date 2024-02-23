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
@Builder // it requires both NoArg and AllArg Constructors
@Data  //its combines @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor

public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true,nullable = false)
    private String title;

//    @Enumerated(EnumType.STRING) // in double
    private Double rating;

//    private String image;
    @Column(nullable = false)
    private String formats;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private String genres;

//    @Enumerated(EnumType.STRING) // ued to get enum value as string another one to get index of that value in integer
    //private LanguagesEnum languages;
    @Column(nullable = false)
    private String languages; // used languages coz we cant store enumarray or other array in database
//    private LanguagesEnum[] languages  ={LanguagesEnum.HINDI,LanguagesEnum.ENGLISH};


    //Mapping Movie -> Show (it means show of morning , or evening 4 , or night)
    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL)
    private List<Show> showList = new ArrayList<>();


}
