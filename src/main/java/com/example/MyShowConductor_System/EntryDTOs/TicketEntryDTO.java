package com.example.MyShowConductor_System.EntryDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketEntryDTO {
    private int showId;
    private List<String> requestedSeats = new ArrayList<>();
    private int userId;
}
