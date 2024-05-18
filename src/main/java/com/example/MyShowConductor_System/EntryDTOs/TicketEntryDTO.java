package com.example.MyShowConductor_System.EntryDTOs;

import com.example.MyShowConductor_System.Entities.ShowSeat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketEntryDTO {
    @NotEmpty
    @Positive
    private int showId;

    @NotEmpty
    @Positive
    private List<ShowSeat> requestedSeats = new ArrayList<>();
//    private List<String> requestedSeats = new ArrayList<>();

    @NotEmpty
    @Positive
    private int userId;
}
