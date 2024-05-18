package com.example.MyShowConductor_System.Entities;

import com.example.MyShowConductor_System.Enums.LanguagesEnum;
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
public class Language {
    @Id
    private int id;

    @Enumerated(EnumType.STRING)
    private LanguagesEnum name;

    @ManyToMany(mappedBy = "languages")
    private List<Movie> movies;// show will also have language right ?


}

