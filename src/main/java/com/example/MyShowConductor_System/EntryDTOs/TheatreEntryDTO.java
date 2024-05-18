package com.example.MyShowConductor_System.EntryDTOs;

import com.example.MyShowConductor_System.Entities.Format;
import com.example.MyShowConductor_System.Enums.LocationEnum;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.util.List;

@Data
public class TheatreEntryDTO {
    //Attr that we require
    @NotEmpty(message = "theatre name cannot be null")
    private String name;

    @NotEmpty(message = "theatre address cannot be null")
    private String address;

    @NotEmpty(message = "theatre location cannot be null")
    private LocationEnum location;

    @NotEmpty(message = "theatre format can't be null")
    @Enumerated(value = EnumType.STRING)
    private List<FormatEntryDto> formats; // we'll make dto


//    same here we'll keep this attributes different
    @Positive
    @Min(0)
    private int classicSeatCount;

//    @NotNull(message = "Username cannot be null")
    @Positive
    @Min(0)
    private int premiumSeatCount;
}
