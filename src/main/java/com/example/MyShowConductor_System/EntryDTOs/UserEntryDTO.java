package com.example.MyShowConductor_System.EntryDTOs;

import com.example.MyShowConductor_System.Enums.GenderEnum;
import lombok.Data;

@Data
public class UserEntryDTO {
    private String name;
    private int age;
    private GenderEnum gender;
    private String mobNo;
    private String email;
    private String address;
    //private LocationEnum location;
}
