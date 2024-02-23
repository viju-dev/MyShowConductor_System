package com.example.MyShowConductor_System.EntryDTOs;

import com.example.MyShowConductor_System.Enums.GenderEnum;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserEntryDTO {
    @NotBlank
    private String name;
    @Positive
    private int age;
    private GenderEnum gender;
    @NotBlank
    @Pattern(regexp = "\\+[0-9]+", message = "Invalid mobile number format")
    @Size(min = 10, max = 10, message = "Mobile number must be 10 digits long")
    private String mobNo;
    @NotBlank
    @Email
    private String email;
    private String address;
    @NotBlank
    @Size(min = 8,max = 15,message = "Password must be between 8 to 15 characters long")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=]).*$",
            message = "Password must contain at least one letter, one number, and one special character")
    private String password;
    //private LocationEnum location;
}
