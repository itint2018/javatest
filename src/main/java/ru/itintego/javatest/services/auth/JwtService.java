package ru.itintego.javatest.services.auth;

public interface JwtService {
    String generateToken(String login);
    Boolean validateToken(String token);
    String getLoginFromToken(String token);
}
