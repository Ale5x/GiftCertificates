package com.epam.esm.filter;


import com.epam.esm.model.Dto.UserDto;
import com.epam.esm.security.AuthUserDetailsService;
import com.epam.esm.security.ConstantSecurity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

/**
 * Action class with token
 *
 * @author Alexander Pishchala
 */
@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secretWord = "secret";
    @Value("${jwt.token.expired}")
    private Long expireTokenTime;

    private final AuthUserDetailsService authUserDetailsService;

    @Autowired
    public JwtTokenProvider(AuthUserDetailsService authUserDetailsService) {
        this.authUserDetailsService = authUserDetailsService;
    }

    @PostConstruct
    protected void init() {
        secretWord = Base64.getEncoder().encodeToString(secretWord.getBytes());
    }

    /**
     * Method createToken helps to create the token
     * @param user User user
     * @return Token entity
     */
    public String createToken(UserDto user){
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put(ConstantSecurity.ROLES, user.getRoles());
        Date now = new Date();
        Date validity = new Date(now.getTime() + expireTokenTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretWord)
                .compact();
    }

    /**
     * Method getAuthentication assists in obtaining authentication for the transferred token
     * @param token String token
     * @return UsernamePasswordAuthenticationToken entity
     */
    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.authUserDetailsService.loadUserByUsername(getLogin(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * The getLogin method helps obtain the tokens login
     * @param token String token
     * @return String login
     */
    public String getLogin(String token) {
        return Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * The resolveToken method helps get the token body from the header "Authorization"
     * @param req HttpServletRequest request
     * @return String login
     */
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(ConstantSecurity.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(ConstantSecurity.BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * The validateToken method helps validate the token
     * @param token String token
     * @return boolean isValid
     */
    public boolean validateToken(String token){
        try{
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        }catch(JwtException | IllegalArgumentException exception){
            return false;
        }
    }
}