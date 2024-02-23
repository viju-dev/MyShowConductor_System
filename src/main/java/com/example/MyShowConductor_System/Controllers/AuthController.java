package com.example.MyShowConductor_System.Controllers;

import com.example.MyShowConductor_System.Entities.User;
import com.example.MyShowConductor_System.Models.JwtRequest;
import com.example.MyShowConductor_System.Models.JwtResponse;
import com.example.MyShowConductor_System.ResponseDTOs.UserResponseDTO;
import com.example.MyShowConductor_System.Services.UserService;
import com.example.MyShowConductor_System.security.JwtHelper;
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
import org.springframework.web.bind.annotation.*;
import com.example.MyShowConductor_System.Validators.EmailValidator;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    // All apis who are not authenticated

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody JwtRequest request) {
//        throwing exception if credentials are Invalid,Null or Empty
        if (isNullOrEmpty(request.getEmail()) ||  isNullOrEmpty(request.getPassword())){
            throw new NullPointerException("User fields can't be Empty or Null...!");
        }
        if (!EmailValidator.isValidEmail(request.getEmail())){
            throw new BadCredentialsException("Invalid Email...!!");
        }
        try {

            // Authentication logic
            this.doAuthenticate(request.getEmail(), request.getPassword());

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            String token = this.helper.generateToken(userDetails);

            JwtResponse response = JwtResponse.builder()
                    .jwtToken(token)
                    .username(userDetails.getUsername()).build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            // Handle authentication failure
            return new ResponseEntity<>("Invalid Username or Password", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred during login", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Perform user authentication
    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            // Invoke authentication manager to authenticate user credentials
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            // Throw exception for invalid credentials
            throw new BadCredentialsException("Invalid Username or Password!!");
        }
    }

    // Exception handler for BadCredentialsException
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity exceptionHandler() {
        return new ResponseEntity<>("Credentials Invalid...!",HttpStatus.NOT_ACCEPTABLE);
    }

    // Signup endpoint for creating a new user
    @PostMapping("/SignUp")
    public ResponseEntity createUser(@RequestBody User user) {

        //checking if user data is empty or null and throwing exception
        if (isNullOrEmpty(user.getName()) || isNullOrEmpty(user.getEmail()) || isNullOrEmpty(user.getPassword())){
            throw new NullPointerException("User fields can't be Empty or Null...!");
        }
        //checks if email is valid
        if (!EmailValidator.isValidEmail(user.getEmail())){
            throw new BadCredentialsException("Invalid Email...!!");
        }

        // Invoke the userService to create the user
//        UserResponseDTO user1 = userService.createUser(user);
        UserResponseDTO user1 = userService.createUser(user);
        // returned userResponseDto
        return new ResponseEntity<>(user1,HttpStatus.CREATED);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity nullExceptionHandler() {
        return new ResponseEntity("User fields can't be Empty or Null...!",HttpStatus.NOT_ACCEPTABLE);
    }

    //checks if the value is not null or  if the string is empty.
    public boolean isNullOrEmpty(String value) {
        return !Optional.ofNullable(value).isPresent() || value.isEmpty();
    }

}
