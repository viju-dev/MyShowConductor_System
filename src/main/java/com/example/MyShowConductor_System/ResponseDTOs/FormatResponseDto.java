package com.example.MyShowConductor_System.ResponseDTOs;


import com.example.MyShowConductor_System.Enums.FormatEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormatResponseDto {

    private FormatEnum name;

//    private ShowResponseDTO show;

//    private List<MovieResponseDTO> movies;

//    private List<TheatreResponseDto> theatre;
}
