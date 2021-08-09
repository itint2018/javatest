package ru.itintego.javatest.services.auth.impl;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itintego.javatest.services.auth.JwtService;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    private final String token;
    private final Logger logger;

    public JwtServiceImpl(@Value("$(meeting-room.token)") String token, Logger logger) {
        this.token = token;
        this.logger = logger;
    }

    @Override
    public String generateToken(String login) {
        Date date =  Date.from(LocalDateTime.now().plusHours(2).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, token)
                .compact();
    }

    @Override
    public Boolean validateToken(String token1) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(token).parseClaimsJws(token1);
            return true;
        } catch (ExpiredJwtException expEx) {
            logger.info("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            logger.info("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            logger.info("Malformed jwt");
        } catch (SignatureException sEx) {
            logger.info("Invalid signature");
        } catch (Exception e) {
            logger.info("invalid token");
        }
        return false;
    }

    @Override
    public String getLoginFromToken(String token1) {
        Claims claims = Jwts.parser().setSigningKey(token).parseClaimsJws(token1).getBody();
        return claims.getSubject();
    }
}
