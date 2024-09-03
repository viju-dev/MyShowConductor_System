package com.example.MyShowConductor_System.Entities;

import com.example.MyShowConductor_System.Enums.FormatEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Format {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private FormatEnum name;


    @OneToMany(mappedBy = "format")//fetch = FetchType.LAZY
    private List<Show> shows;

    @ManyToMany(mappedBy = "formats")
    private List<Movie> movies;

    @ManyToMany(mappedBy = "formats")
    private List<Theatre> theatres;

}
