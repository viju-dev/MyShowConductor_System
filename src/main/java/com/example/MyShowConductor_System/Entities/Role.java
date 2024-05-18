package com.example.MyShowConductor_System.Entities;

import com.example.MyShowConductor_System.Enums.UserRoleEnum;
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
public class Role {
    @Id
    private int id;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();
}
