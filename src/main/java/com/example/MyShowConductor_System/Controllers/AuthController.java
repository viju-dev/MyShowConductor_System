package com.example.MyShowConductor_System.Controllers;

import com.example.MyShowConductor_System.Entities.User;
import com.example.MyShowConductor_System.EntryDTOs.UserEntryDTO;
import com.example.MyShowConductor_System.Exceptions.ApiException;
import com.example.MyShowConductor_System.Models.JwtRequest;
import com.example.MyShowConductor_System.Models.JwtResponse;
import com.example.MyShowConductor_System.Payloads.ApiResponse;
import com.example.MyShowConductor_System.Payloads.ResponseData;
import com.example.MyShowConductor_System.Repositories.UserRepository;
import com.example.MyShowConductor_System.ResponseDTOs.UserResponseDTO;
import com.example.MyShowConductor_System.Services.UserService;
import com.example.MyShowConductor_System.security.JwtHelper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Validated
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper helper;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ModelMapper mapper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> createToken(@RequestBody JwtRequest request){

        this.authenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);


        JwtResponse jwtAuthResponse = new JwtResponse();
        jwtAuthResponse.setJwtToken(token);
        jwtAuthResponse.setUsername(request.getEmail());
        return new ResponseEntity<JwtResponse>(jwtAuthResponse, HttpStatus.OK);
    }

    private void authenticate(String username, String password) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        System.out.println(usernamePasswordAuthenticationToken);
        try {

            this.manager.authenticate(usernamePasswordAuthenticationToken);

        } catch (BadCredentialsException e) {
            System.out.println("Invalid Details !!");
            throw new ApiException("Invalid username or password !!");
        }
    }

    //    register new user api
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody UserEntryDTO user){
        UserResponseDTO userDto = this.userService.registerUser(user);
        return new ResponseEntity<ApiResponse>(new ApiResponse("user registered ",true,new ResponseData(userDto)),HttpStatus.CREATED);
    }

    // get loggedin user data

    @GetMapping("/current-user/")
    public ResponseEntity<UserResponseDTO> getUser(Principal principal) {
        User user = this.userRepo.findByEmail(principal.getName()).get();
        return new ResponseEntity<>(this.mapper.map(user, UserResponseDTO.class), HttpStatus.OK);
    }

}
