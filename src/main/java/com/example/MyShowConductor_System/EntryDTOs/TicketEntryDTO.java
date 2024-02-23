package com.example.MyShowConductor_System.EntryDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketEntryDTO {
    @NotNull
    @Positive
    private int showId;
    @NotNull
    @Positive
    private List<String> requestedSeats = new ArrayList<>();
    @NotNull
    @Positive
    private int userId;
}
