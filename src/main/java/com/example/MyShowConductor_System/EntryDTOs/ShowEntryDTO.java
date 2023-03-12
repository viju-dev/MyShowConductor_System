package com.example.MyShowConductor_System.EntryDTOs;

import com.example.MyShowConductor_System.Enums.ScreenTypeEnum;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ShowEntryDTO {

    private LocalDate showDate;
    private LocalTime showTime;
//    private ScreenTypeEnum screenType;
//    private List<ScreenTypeEnum> screenType ;
    @Enumerated(value = EnumType.STRING)
    private ScreenTypeEnum[] screenType;
    private int movieId;
    private int theatreId;
    private int classicSeatPrice;
    private int premiumSeatPrice;
}
