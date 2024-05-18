package com.example.MyShowConductor_System.ResponseDTOs;

import com.example.MyShowConductor_System.Enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
//    private String name;
//    private String mobNo;
//    private String email;
//    private String address;
//    private GenderEnum gender;
//    private String password;
//    private int age;


    private String name;
    private String mobNo;
    private String email;
    private String address;
    private GenderEnum gender;
    private boolean isEmailVerified;
//    private String password;
}
