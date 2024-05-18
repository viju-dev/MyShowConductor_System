package com.example.MyShowConductor_System.Controllers;

import com.example.MyShowConductor_System.EntryDTOs.UserEntryDTO;
import com.example.MyShowConductor_System.Entities.User;
import com.example.MyShowConductor_System.Exceptions.ResourceNotFoundException;
import com.example.MyShowConductor_System.Repositories.UserRepository;
import com.example.MyShowConductor_System.ResponseDTOs.UserResponseDTO;
import com.example.MyShowConductor_System.Services.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser() {
        // Test case for createUser method
        // Mocking necessary objects
        UserEntryDTO userEntryDTO = new UserEntryDTO();
        User user = new User();
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        when(modelMapper.map(userEntryDTO, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserResponseDTO.class)).thenReturn(userResponseDTO);

        // Call the method under test
        UserResponseDTO result = userService.createUser(userEntryDTO);

        // Verify the result
        assertNotNull(result);
        assertSame(userResponseDTO, result);

        // Verify interactions with mocks
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUser() {
        // Test case for updateUser method
        // Mocking necessary objects
        UserEntryDTO userEntryDTO = new UserEntryDTO();
        userEntryDTO.setName("John");
        int userId = 1;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserResponseDTO.class)).thenReturn(new UserResponseDTO());

        // Call the method under test
        UserResponseDTO result = userService.updateUser(userEntryDTO, userId);

        // Verify the result
        assertNotNull(result);

        // Verify interactions with mocks
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetAll() {
        // Test case for getALl method
        // Mocking necessary objects
        User user1 = new User();
        User user2 = new User();
        List<User> userList = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(userList);
        when(modelMapper.map(any(User.class), eq(UserResponseDTO.class))).thenReturn(new UserResponseDTO());

        // Call the method under test
        List<UserResponseDTO> result = userService.getALl();

        // Verify the result
        assertNotNull(result);
        assertEquals(2, result.size());

        // Verify interactions with mocks
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById() {
        // Test case for getUserById method
        // Mocking necessary objects
        int userId = 1;
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserResponseDTO.class)).thenReturn(new UserResponseDTO());

        // Call the method under test
        UserResponseDTO result = userService.getUserById(userId);

        // Verify the result
        assertNotNull(result);

        // Verify interactions with mocks
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testDeleteUser() {
        // Test case for deleteUser method
        // Mocking necessary objects
        int userId = 1;
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Call the method under test
        String result = userService.deleteUser(userId);

        // Verify the result
        assertNotNull(result);
        assertEquals("User deleted Successfully", result);

        // Verify interactions with mocks
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testVerifyUserEmail() {
        // Test case for verifyUserEmail method
        // Call the method under test
        boolean result = userService.verifyUserEmail();

        // Verify the result
        assertFalse(result); // Assuming it should return false by default
    }

    @Test
    public void testUpdateUserLocation() {
        // Test case for updateUserLocation method
        // Mocking necessary objects
        String mob = "1234567890";
        String location = "New York";
        User user = new User();

        when(userRepository.findByMobNo(mob)).thenReturn(user);
        when(modelMapper.map(user, UserResponseDTO.class)).thenReturn(new UserResponseDTO());

        // Call the method under test
        UserResponseDTO result = userService.updateUserLocation(mob, location);

        // Verify the result
        assertNotNull(result);

        // Verify interactions with mocks
        verify(userRepository, times(1)).findByMobNo(mob);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testLoadUserByUsername() {
        // Test case for loadUserByUsername method
        // Mocking necessary objects
        String username = "test@example.com";
        User user = new User();

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));

        // Call the method under test
        assertDoesNotThrow(() -> userService.loadUserByUsername(username));

        // Verify interactions with mocks
        verify(userRepository, times(1)).findByEmail(username);
    }

    @Test
    public void testRegisterUser() {
        // Test case for registerUser method
        // Mocking necessary objects
        UserEntryDTO userDto = new UserEntryDTO();
        User user = new User();
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        when(modelMapper.map(userDto, User.class)).thenReturn(user);
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserResponseDTO.class)).thenReturn(userResponseDTO);

        // Call the method under test
        UserResponseDTO result = userService.registerUser(userDto);

        // Verify the result
        assertNotNull(result);
        assertSame(userResponseDTO, result);

        // Verify interactions with mocks
        verify(userRepository, times(1)).save(user);
    }
}
