package com.example.MyShowConductor_System.Entities;

import com.example.MyShowConductor_System.Enums.RatingEnum;
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
    private String movieName;

    @Enumerated(EnumType.STRING)
    private RatingEnum rating;

    private int duration;

    private String genre;

//    @Enumerated(EnumType.STRING) // ued to get enum value as string another one to get index of that value in integer
    //private LanguagesEnum languages;
    private String languages; // used languages coz we cant store enumarray or other array in database
//    private LanguagesEnum[] languages  ={LanguagesEnum.HINDI,LanguagesEnum.ENGLISH};


    //Mapping Movie -> Show (it means show of morning , or evening 4 , or night)
    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL)
    private List<Show> showList = new ArrayList<>();


}
