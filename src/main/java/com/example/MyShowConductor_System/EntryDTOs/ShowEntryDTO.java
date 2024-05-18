package com.example.MyShowConductor_System.EntryDTOs;

import com.example.MyShowConductor_System.Entities.Format;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class ShowEntryDTO {
    @NotEmpty
    @FutureOrPresent
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate showDate;
    //not necessary by my opinion
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime showTime;
//    private ScreenTypeEnum screenType;
//    private List<ScreenTypeEnum> screenType ;
    @NotEmpty(message = "screenTypes can't be null")
//    @Enumerated(value = EnumType.STRING)
    private FormatEntryDto format; // one show one format

    @NotEmpty(message = "movie can't be null")
    @Min(value = 0,message = "movieId can't be negative")
    private int movieId;

    @NotEmpty(message = "theatre can't be null")
    @Min(value = 0,message = "theatreId can't be negative")
    private int theatreId;

//    @NotNull(message ="classic seat price can't be null" )
//    @Min(value = 0,message = "classic seat price can't be negative")
    @Positive(message = "seat price should be positive")
    private int classicSeatPrice;

    @Positive(message = "seat price should be positive")
    private int premiumSeatPrice;

//    so instead of showseat prces are mentioned here ofcourse pricing are depends on show
}
