package com.example.MyShowConductor_System.Controllers;

import com.example.MyShowConductor_System.EntryDTOs.ShowEntryDTO;
import com.example.MyShowConductor_System.Entities.Show;
import com.example.MyShowConductor_System.Exceptions.ResourceNotFoundException;
import com.example.MyShowConductor_System.Repositories.ShowRepository;
import com.example.MyShowConductor_System.ResponseDTOs.ShowResponseDTO;
import com.example.MyShowConductor_System.Services.ShowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ShowServiceImplTest {

    @Mock
    private ShowRepository showRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ShowService showService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateShow() {
        ShowEntryDTO showEntryDTO = new ShowEntryDTO();
        Show show = new Show();
        ShowResponseDTO showResponseDTO = new ShowResponseDTO();

        when(modelMapper.map(showEntryDTO, Show.class)).thenReturn(show);
        when(showRepository.save(show)).thenReturn(show);
        when(modelMapper.map(show, ShowResponseDTO.class)).thenReturn(showResponseDTO);

        ShowResponseDTO result = showService.createShow(showEntryDTO);

        assertNotNull(result);
        assertSame(showResponseDTO, result);

        verify(showRepository, times(1)).save(show);
    }

    @Test
    public void testUpdateShow() {
        ShowEntryDTO showEntryDTO = new ShowEntryDTO();
        int showId = 1;
        Show show = new Show();
        show.setId(showId);

        when(showRepository.findById(showId)).thenReturn(Optional.of(show));
        when(showRepository.save(show)).thenReturn(show);
        when(modelMapper.map(show, ShowResponseDTO.class)).thenReturn(new ShowResponseDTO());

        ShowResponseDTO result = showService.updateShow(showEntryDTO, showId);

        assertNotNull(result);

        verify(showRepository, times(1)).findById(showId);
        verify(showRepository, times(1)).save(show);
    }

    @Test
    public void testGetAllShows() {
        Show show1 = new Show();
        Show show2 = new Show();
        List<Show> showList = Arrays.asList(show1, show2);

        when(showRepository.findAll()).thenReturn(showList);
        when(modelMapper.map(any(Show.class), eq(ShowResponseDTO.class))).thenReturn(new ShowResponseDTO());

        List<ShowResponseDTO> result = showService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(showRepository, times(1)).findAll();
    }

    @Test
    public void testGetShowById() {
        int showId = 1;
        Show show = new Show();

        when(showRepository.findById(showId)).thenReturn(Optional.of(show));
        when(modelMapper.map(show, ShowResponseDTO.class)).thenReturn(new ShowResponseDTO());

        ShowResponseDTO result = showService.getShowById(showId);

        assertNotNull(result);

        verify(showRepository, times(1)).findById(showId);
    }

    @Test
    public void testDeleteShow() {
        int showId = 1;
        Show show = new Show();

        when(showRepository.findById(showId)).thenReturn(Optional.of(show));

        String result = showService.deleteShowById(showId);

        assertNotNull(result);
        assertEquals("Show deleted Successfully", result);

        verify(showRepository, times(1)).findById(showId);
        verify(showRepository, times(1)).deleteById(showId);
    }

//    @Test
//    public void testSearchShows() {
//        String keyword = "concert";
//        Show show1 = new Show();
//        Show show2 = new Show();
//        List<Show> shows = Arrays.asList(show1, show2);
//
//        when(showRepository.searchShowsByKeyword(keyword)).thenReturn(shows);
//        when(modelMapper.map(any(Show.class), eq(ShowResponseDTO.class))).thenReturn(new ShowResponseDTO());
//
//        List<ShowResponseDTO> result = showService.searchShows(keyword);
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//
//        verify(showRepository, times(1)).searchShowsByKeyword(keyword);
//    }

//    @Test
//    public void testBookShow() {
//        int showId = 1;
//        int userId = 2;
//        Show show = new Show();
//
//        when(showRepository.findById(showId)).thenReturn(Optional.of(show));
//
//        String result = showService.bookShow(showId, userId);
//
//        assertNotNull(result);
//        assertEquals("Show booked successfully", result);
//
//        verify(showRepository, times(1)).findById(showId);
//        verify(showRepository, times(1)).save(show);
//    }
//}
}
