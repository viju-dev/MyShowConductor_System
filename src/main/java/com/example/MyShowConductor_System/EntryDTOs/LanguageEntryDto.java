package com.example.MyShowConductor_System.EntryDTOs;

import com.example.MyShowConductor_System.Enums.LanguagesEnum;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class LanguageEntryDto {
    @Enumerated(EnumType.STRING)
    private LanguagesEnum name;

}
