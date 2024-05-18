package com.example.MyShowConductor_System.ResponseDTOs;

import com.example.MyShowConductor_System.Enums.LanguagesEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LanguageResponseDto {

    private LanguagesEnum name;

    //private List<MovieResponseDTO> movies; // show will also have language right ?
}
