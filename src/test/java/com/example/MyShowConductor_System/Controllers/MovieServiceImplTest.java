package com.example.MyShowConductor_System.Controllers;

import com.example.MyShowConductor_System.EntryDTOs.MovieEntryDTO;
import com.example.MyShowConductor_System.Entities.Movie;
import com.example.MyShowConductor_System.Exceptions.ResourceNotFoundException;
import com.example.MyShowConductor_System.Repositories.MovieRepository;
import com.example.MyShowConductor_System.ResponseDTOs.MovieResponseDTO;
import com.example.MyShowConductor_System.Services.Impl.MovieServiceImpl;
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

public class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MovieServiceImpl movieService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateMovie() {
        // Test case for createMovie method
        // Mocking necessary objects
        MovieEntryDTO movieEntryDTO = new MovieEntryDTO();
        Movie movie = new Movie();
        MovieResponseDTO movieResponseDTO = new MovieResponseDTO();

        when(modelMapper.map(movieEntryDTO, Movie.class)).thenReturn(movie);
        when(movieRepository.save(movie)).thenReturn(movie);
        when(modelMapper.map(movie, MovieResponseDTO.class)).thenReturn(movieResponseDTO);

        // Call the method under test
        MovieResponseDTO result = movieService.createMovie(movieEntryDTO);

        // Verify the result
        assertNotNull(result);
        assertSame(movieResponseDTO, result);

        // Verify interactions with mocks
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    public void testUpdateMovie() {
        MovieEntryDTO movieEntryDTO = new MovieEntryDTO();
        int movieId = 1;
        Movie movie = new Movie();
        movie.setId(movieId);

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(movieRepository.save(movie)).thenReturn(movie);
        when(modelMapper.map(movieEntryDTO, Movie.class)).thenReturn(movie);
        when(modelMapper.map(movie, MovieResponseDTO.class)).thenReturn(new MovieResponseDTO());

        MovieResponseDTO result = movieService.updateMovie(movieEntryDTO, movieId);

        assertNotNull(result);

        verify(movieRepository, times(1)).findById(movieId);
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    public void testGetAllMovies() {
        // Test case for getAllMovies method
        // Mocking necessary objects
        Movie movie1 = new Movie();
        Movie movie2 = new Movie();
        List<Movie> movieList = Arrays.asList(movie1, movie2);

        when(movieRepository.findAll()).thenReturn(movieList);
        when(modelMapper.map(any(Movie.class), eq(MovieResponseDTO.class))).thenReturn(new MovieResponseDTO());

        // Call the method under test
        List<MovieResponseDTO> result = movieService.getAll();

        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.size());

        // Verify interactions with mocks
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    public void testGetMovieById() {
        // Test case for getMovieById method
        // Mocking necessary objects
        int movieId = 1;
        Movie movie = new Movie();

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(modelMapper.map(movie, MovieResponseDTO.class)).thenReturn(new MovieResponseDTO());

        // Call the method under test
        MovieResponseDTO result = movieService.getMovieById(movieId);

        // Verify the result
        assertNotNull(result);

        // Verify interactions with mocks
        verify(movieRepository, times(1)).findById(movieId);
    }

    @Test
    public void testDeleteMovie() {
        // Test case for deleteMovie method
        // Mocking necessary objects
        int movieId = 1;
        Movie movie = new Movie();

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        // Call the method under test
        String result = movieService.deleteMovieById(movieId);

        // Verify the result
        assertNotNull(result);
        assertEquals("Movie Deleted SuccessFully", result);

        // Verify interactions with mocks
        verify(movieRepository, times(1)).findById(movieId);
        verify(movieRepository, times(1)).deleteById(movieId);
    }
}
