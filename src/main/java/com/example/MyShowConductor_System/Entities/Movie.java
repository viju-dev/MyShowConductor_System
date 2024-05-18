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
// relsease date might require
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false) // unique = true, it cant be unique as their can be multiple movies with same name
    private String title;

//    @Enumerated(EnumType.STRING) // in double
    private Double rating;

//    private String image;
//    @Column(nullable = false)
//    private String formats;

    @Column(nullable = false)
    private int duration;
//private string description

//    @Column(nullable = false)
//    private String genres;

//    @Enumerated(EnumType.STRING) // ued to get enum value as string another one to get index of that value in integer
    //private LanguagesEnum languages;

//    @Column(nullable = false)
//    private String languages; // used languages coz we cant store enumarray or other array in database
//    private LanguagesEnum[] languages  ={LanguagesEnum.HINDI,LanguagesEnum.ENGLISH};



//    Mapping for enum enities
    @ManyToMany
    @JoinTable(
        name = "movie_genre",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "movie_language",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    private List<Language> languages = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "movie_format",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "format_id"))
    private List<Format> formats = new ArrayList<>();


//    Mapping Movie -> Show (it means show of morning , or evening 4 , or night)
    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL)
    private List<Show> movieShowList = new ArrayList<>();


}
