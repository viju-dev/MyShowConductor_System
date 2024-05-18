package com.example.MyShowConductor_System.Entities;

import com.example.MyShowConductor_System.Enums.MovieGenreEnum;
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
public class Genre {
    @Id
    private int id;

    @Enumerated(EnumType.STRING)
    private MovieGenreEnum name;

    @ManyToMany(mappedBy = "genres")
    private List<Movie> movies;
}
