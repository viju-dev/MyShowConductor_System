package com.example.MyShowConductor_System.EntryDTOs;

import com.example.MyShowConductor_System.Enums.FormatEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormatEntryDto {
    @Enumerated(EnumType.STRING)
    private FormatEnum name;
}
