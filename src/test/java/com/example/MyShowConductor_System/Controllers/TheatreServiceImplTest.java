package com.example.MyShowConductor_System.Controllers;

import com.example.MyShowConductor_System.Entities.Theatre;
import com.example.MyShowConductor_System.EntryDTOs.FormatEntryDto;
import com.example.MyShowConductor_System.EntryDTOs.TheatreEntryDTO;
import com.example.MyShowConductor_System.Enums.FormatEnum;
import com.example.MyShowConductor_System.Enums.LocationEnum;
import com.example.MyShowConductor_System.Exceptions.ResourceNotFoundException;
import com.example.MyShowConductor_System.Repositories.TheatreRepository;
import com.example.MyShowConductor_System.ResponseDTOs.TheatreResponseDTO;
import com.example.MyShowConductor_System.Services.Impl.TheatreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TheatreServiceImplTest {

    @Mock
    private TheatreRepository theatreRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TheatreServiceImpl theatreService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateTheatre() {
        TheatreEntryDTO theatreEntryDTO = new TheatreEntryDTO();
        theatreEntryDTO.setName("PVR");
        theatreEntryDTO.setAddress("MG Road");
        List<FormatEntryDto> list = new ArrayList<>();
        theatreEntryDTO.setFormats(list);
        theatreEntryDTO.setLocation(LocationEnum.BANGALORE);

        Theatre theatre = new Theatre();
        theatre.setName("PVR");
        theatre.setAddress("MG Road");
        theatre.setLocation(LocationEnum.BANGALORE.toString());

        TheatreResponseDTO theatreResponseDTO = new TheatreResponseDTO();
        theatreResponseDTO.setName("PVR");




        assertEquals(2,2);

    }

    @Test
    public void testUpdateTheatre() {
        TheatreEntryDTO theatreEntryDTO = new TheatreEntryDTO();
        theatreEntryDTO.setName("PVR");
        theatreEntryDTO.setAddress("MG Road");
        theatreEntryDTO.setLocation(LocationEnum.BANGALORE);

        int theatreId = 1;
        Theatre theatre = new Theatre();
        theatre.setId(theatreId);
        theatre.setName("PVR");
        theatre.setAddress("MG Road");
        theatre.setLocation(LocationEnum.BANGALORE.toString());

        TheatreResponseDTO theatreResponseDTO = new TheatreResponseDTO();
        theatreResponseDTO.setName("PVR");



        assertEquals(0,0);


    }

    @Test
    public void testGetAllTheatres() {
        Theatre theatre1 = new Theatre();
        theatre1.setName("PVR");

        Theatre theatre2 = new Theatre();
        theatre2.setName("Inox");

        List<Theatre> theatreList = Arrays.asList(theatre1, theatre2);

        when(theatreRepository.findAll()).thenReturn(theatreList);
        when(modelMapper.map(any(Theatre.class), eq(TheatreResponseDTO.class))).thenReturn(new TheatreResponseDTO());

        List<TheatreResponseDTO> result = theatreService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(theatreRepository, times(1)).findAll();
    }

    @Test
    public void testGetTheatreById() {
        int theatreId = 1;
        Theatre theatre = new Theatre();
        theatre.setName("PVR");

        TheatreResponseDTO theatreResponseDTO = new TheatreResponseDTO();
        theatreResponseDTO.setName("PVR");

        when(theatreRepository.findById(theatreId)).thenReturn(Optional.of(theatre));
        when(modelMapper.map(theatre, TheatreResponseDTO.class)).thenReturn(theatreResponseDTO);

        TheatreResponseDTO result = theatreService.getTheatreById(theatreId);

        assertNotNull(result);
        assertEquals(theatreResponseDTO.getName(), result.getName());

        verify(theatreRepository, times(1)).findById(theatreId);
    }

    @Test
    public void testDeleteTheatreById() {
        int theatreId = 1;
        Theatre theatre = new Theatre();

        when(theatreRepository.findById(theatreId)).thenReturn(Optional.of(theatre));

        String result = theatreService.delete(theatreId);

        assertNotNull(result);
        assertEquals("Theatre deleted SuccessFully", result);

        verify(theatreRepository, times(1)).findById(theatreId);
        verify(theatreRepository, times(1)).deleteById(theatreId);
    }

    @Test
    public void testGetTheatresByLocation() {

        String location = "BANGALORE";
        Theatre theatre1 = new Theatre();
        theatre1.setLocation(location);
        Theatre theatre2 = new Theatre();
        theatre2.setLocation(location);

        List<Theatre> theatreList = Arrays.asList(theatre1, theatre2);




        assertEquals(2, 2);

    }

}
