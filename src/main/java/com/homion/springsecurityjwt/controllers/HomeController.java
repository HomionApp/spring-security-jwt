package com.homion.springsecurityjwt.controllers;

import com.homion.springsecurityjwt.models.AuthenticationRequest;
import com.homion.springsecurityjwt.models.AuthenticationResponse;
import com.homion.springsecurityjwt.services.MyUserDetailsService.MyUserDetailsService;
import com.homion.springsecurityjwt.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @PreAuthorize("hasAuthority('CAN_VISIT_HOME')")
    @GetMapping("/")
    public String hello() {
        return "Hello World";
    }

    @PreAuthorize("hasAuthority('CAN_VISIT_DETAILS')")
    @GetMapping("/details")
    public String details() {
        return "Details";
    }

    @PreAuthorize("hasAuthority('CAN_VISIT_ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        return "Admin";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        System.out.println("before authenticate");

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
//            throw new Exception("Incorrect username or password", e);
            return ResponseEntity.badRequest().body("Incorrect username or password");
        }


        System.out.println("after authenticate");


        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
