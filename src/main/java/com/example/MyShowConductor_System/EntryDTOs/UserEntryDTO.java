package com.example.MyShowConductor_System.EntryDTOs;

import com.example.MyShowConductor_System.Enums.GenderEnum;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserEntryDTO {
    @NotEmpty
    private String name;

    @NotEmpty
    @Pattern(regexp = "\\+[0-9]+", message = "Invalid mobile number format")
    @Size(min = 10, max = 10, message = "Mobile number must be 10 digits long")
    private String mobNo;

    @NotEmpty
    @Email
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+$")
    private String email;

    @NotEmpty
    @Size(min = 8,max = 15,message = "Password must be between 8 to 15 characters long")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=]).*$",
            message = "Password must contain at least one letter, one number, and one special character")
    private String password;

    private String address;
    private GenderEnum gender;

//    @Positive
//    private int age;
    //private LocationEnum location;
}
