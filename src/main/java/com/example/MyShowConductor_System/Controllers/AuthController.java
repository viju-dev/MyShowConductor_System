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
import com.example.MyShowConductor_System.Services.Impl.UserServiceImpl;
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
import com.example.MyShowConductor_System.Validators.EmailValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);
//try {
//
//        // Authentication logic
//        this.doAuthenticate(request.getEmail(), request.getPassword());
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
//        String token = this.helper.generateToken(userDetails);
//
//        JwtResponse response = JwtResponse.builder()
//                .jwtToken(token)
//                .username(userDetails.getUsername()).build();
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    } catch (BadCredentialsException e) {
//        // Handle authentication failure
//        return new ResponseEntity<>("Invalid Username or Password", HttpStatus.UNAUTHORIZED);
//    } catch (Exception e) {
//        return new ResponseEntity<>("An error occurred during login", HttpStatus.INTERNAL_SERVER_ERROR);
//    }
// if (!EmailValidator.isValidEmail(request.getEmail())){
//        throw new BadCredentialsException("Invalid Email...!!");
//    }
    // All apis who are not authenticated

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> createToken(@RequestBody JwtRequest request){

        this.authenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);


        JwtResponse jwtAuthResponse = new JwtResponse();
        jwtAuthResponse.setJwtToken(token);
        return new ResponseEntity<JwtResponse>(jwtAuthResponse, HttpStatus.OK);
    }

    private void authenticate(String username, String password) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
//        this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);//badcreadentils exception handlin
        System.out.println(usernamePasswordAuthenticationToken);
        try {

            this.manager.authenticate(usernamePasswordAuthenticationToken);

        } catch (BadCredentialsException e) {
            System.out.println("Invalid Detials !!");
            throw new ApiException("Invalid username or password !!");
        }
    }

    //    register new user api
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody UserEntryDTO user){
        UserResponseDTO userDto = this.userServiceImpl.registerUser(user);
        return new ResponseEntity<ApiResponse>(new ApiResponse("user registered ",true,new ResponseData(userDto)),HttpStatus.CREATED);
    }

    // get loggedin user data
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ModelMapper mapper;

    @GetMapping("/current-user/")
    public ResponseEntity<UserResponseDTO> getUser(Principal principal) {
        User user = this.userRepo.findByEmail(principal.getName()).get();
        return new ResponseEntity<>(this.mapper.map(user, UserResponseDTO.class), HttpStatus.OK);
    }

}
