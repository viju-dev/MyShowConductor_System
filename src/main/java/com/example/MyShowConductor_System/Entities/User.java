package com.example.MyShowConductor_System.Entities;

import com.example.MyShowConductor_System.Enums.GenderEnum;
import com.example.MyShowConductor_System.Enums.UserRoleEnum;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // know the differencw
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true,nullable = false)
    private String mobNo;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private boolean isEmailVerified;


    @Column(nullable = false)
    private String password;

    private String address;
//    private LocationEnum location;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;  // optional attribute

    @Enumerated(EnumType.STRING)
    private UserRoleEnum userRole;

//    @JsonFormat(pattern = "dd-MM-yyyy")
//    private Date birthDate;
    private int age;

    private String location;


    //Mapping User -> Tickets
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Ticket> ticketList = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<FeedBack> feedBackList = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }//false

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }//false
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }//false

    @Override
    public boolean isEnabled() {
        return true;
    }
}
