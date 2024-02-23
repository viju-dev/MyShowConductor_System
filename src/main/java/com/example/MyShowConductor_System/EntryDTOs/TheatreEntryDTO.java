package com.example.MyShowConductor_System.EntryDTOs;

import com.example.MyShowConductor_System.Enums.FormatEnum;
import com.example.MyShowConductor_System.Enums.LocationEnum;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class TheatreEntryDTO {
    //Attr that we require
    @NotBlank(message = "theatre name cannot be null")
    private String name;

    @NotBlank(message = "theatre address cannot be null")
    private String address;

    @NotNull(message = "theatre location cannot be null")
    private LocationEnum location;

    @NotNull(message = "theatre format can't be null")
    @Enumerated(value = EnumType.STRING)
    private FormatEnum[] formats;

    @Positive
    private int classicSeatCount;

//    @NotNull(message = "Username cannot be null")
    @Positive
    private int premiumSeatCount;
}
