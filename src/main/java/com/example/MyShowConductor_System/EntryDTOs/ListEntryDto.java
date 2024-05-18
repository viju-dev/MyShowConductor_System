package com.example.MyShowConductor_System.EntryDTOs;

import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ListEntryDto<T>{
    @NotNull(message = " list cannot be null")
    List<T> list;
}
