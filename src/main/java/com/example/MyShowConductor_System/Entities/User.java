package com.example.MyShowConductor_System.Entities;

import com.example.MyShowConductor_System.Enums.GenderEnum;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // know the differencw
    private int id;

    private String name;
    @Column(unique = true,nullable = false)
    private String mobNo;
    @Column(unique = true,nullable = false)
    private String email;
    private String address;
//    private LocationEnum location;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;  // optional attribute

//    @JsonFormat(pattern = "dd-MM-yyyy")
//    private Date birthDate;
    private int age;


    //Mapping User -> Tickets
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Ticket> ticketList = new ArrayList<>();


}
