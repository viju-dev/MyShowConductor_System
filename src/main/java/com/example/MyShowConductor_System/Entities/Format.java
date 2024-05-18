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


//    mappings
//    @OneToOne(mappedBy = "format")
//    private Show show;


//    @OneToOne(mappedBy = "format",fetch = FetchType.LAZY)
//    private Show show;
//Unexpected Exception occur:More than one row with the given identifier was found: 1, for class: com.example.MyShowConductor_System.Entities.Show; nested exception is org.hibernate.HibernateException: More than one row with the given identifier was found: 1, for class: com.example.MyShowConductor_System.Entities.Show
//removing mapping from fomrat or cahnged mapping to below solve this
    @OneToMany(mappedBy = "format")//fetch = FetchType.LAZY
    private List<Show> shows;

    @ManyToMany(mappedBy = "formats")
    private List<Movie> movies;

    @ManyToMany(mappedBy = "formats")
    private List<Theatre> theatres;

}
