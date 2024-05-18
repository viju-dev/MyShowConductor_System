package com.example.MyShowConductor_System.ResponseDTOs;

import com.example.MyShowConductor_System.Entities.Format;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TheatreResponseDTO {
    private int id;
    private String name;
    private String address;
    private String location;
    private List<TheatreSeatResponseDto> seatList;
    private List<FormatResponseDto> formats;
//    for some reasons here we need shows as well
//    can create difernet apis for ohers attributes like shows in this theatre by its id
//    mot returning shows as they might be oler
//    private String movieName; // already using in movies so irrelevant and we'll give all records
//    private String theatreName;


//    private int classicSeatCount;
//    private int premiumSeatCount;
//    seats are related to scrren 1 or 2 not theatre so might needed that entity as well
}
