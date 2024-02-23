package com.example.MyShowConductor_System.EntryDTOs;

import com.example.MyShowConductor_System.Enums.FormatEnum;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ShowEntryDTO {
    @NotNull
    @FutureOrPresent
    private LocalDate showDate;
    //not necessary by my opinion

    private LocalTime showTime;
//    private ScreenTypeEnum screenType;
//    private List<ScreenTypeEnum> screenType ;
    @NotNull(message = "screenTypes can't be null")
    @Enumerated(value = EnumType.STRING)
    private FormatEnum[] formats;

    @NotNull(message = "movie can't be null")
    @Min(value = 0,message = "movieId can't be negative")
    private int movieId;

    @NotNull(message = "theatre can't be null")
    @Min(value = 0,message = "theatreId can't be negative")
    private int theatreId;

//    @NotNull(message ="classic seat price can't be null" )
//    @Min(value = 0,message = "classic seat price can't be negative")
    @Positive(message = "seat price should be positive")
    private int classicSeatPrice;

    @Positive(message = "seat price should be positive")
    private int premiumSeatPrice;
}
