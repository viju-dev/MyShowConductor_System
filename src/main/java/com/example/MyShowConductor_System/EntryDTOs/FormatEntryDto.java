package com.example.MyShowConductor_System.EntryDTOs;

import com.example.MyShowConductor_System.Enums.FormatEnum;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class FormatEntryDto {
    @Enumerated(EnumType.STRING)
    private FormatEnum name;
}
