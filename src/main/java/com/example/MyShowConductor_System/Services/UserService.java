package com.example.MyShowConductor_System.Services;

import com.example.MyShowConductor_System.Entities.User;
import com.example.MyShowConductor_System.EntryDTOs.UserEntryDTO;
import com.example.MyShowConductor_System.ResponseDTOs.UserResponseDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {


//    basic
    public UserResponseDTO createUser(UserEntryDTO userEntryDTO);

    public UserResponseDTO registerUser(UserEntryDTO user);

    public UserResponseDTO updateUser(UserEntryDTO userEntryDTO, Integer userId);

    public UserResponseDTO getUserById(int id) ;
    public User getUserEntityById(int id);
    public User getUserEntityByEmail(String email);

    public List<UserResponseDTO> getALl();

    public String deleteUser(int id) throws RuntimeException ;

    boolean verifyUserEmail();



//    additional

    public UserResponseDTO updateUserLocation(String mob, String location); // will to save location on server not in db

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

//    email update, mobile update separate one maybe needed


//    additional methods to fullfill absence of some attributes in response

}
