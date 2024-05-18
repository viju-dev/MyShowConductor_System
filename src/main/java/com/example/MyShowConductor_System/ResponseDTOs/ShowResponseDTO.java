package com.example.MyShowConductor_System.ResponseDTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowResponseDTO {
    private int id;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate showDate;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime showTime;
    private Date createdOn;
    private Date updatedOn;
//    private ScreenTypeEnum screenType;
//     List<Format> formats = new ArrayList<>(); /// i guess one show can have only one format as it related to one screen
    private FormatResponseDto format;
    private MovieResponseDTO movie;
    private TheatreResponseDTO theatre;
    private List<ShowSeatResponseDto> showSeatList;
    //showseatlist
    //ticketloist
//    private int classicSeatPrice;
//    private int premiumSeatPrice;

}
