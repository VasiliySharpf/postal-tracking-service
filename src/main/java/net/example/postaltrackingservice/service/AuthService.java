package net.example.postaltrackingservice.service;

import net.example.postaltrackingservice.jwt.JwtRequest;
import net.example.postaltrackingservice.jwt.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest request);

}
