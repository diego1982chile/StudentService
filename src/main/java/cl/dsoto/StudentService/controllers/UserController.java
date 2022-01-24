package cl.dsoto.StudentService.controllers;


import cl.dsoto.StudentService.models.User;
import cl.dsoto.StudentService.services.StudentService;
import cl.dsoto.StudentService.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dsoto on 12-12-19.
 */

/**
 * Endpoint para API students
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("authenticate")
    public User authenticate(@RequestParam("username") String username,
                             @RequestParam("password") String password) {
        try {
            User authenticated = userService.authenticate(username, password);
            String token = getJWTToken(username);
            authenticated.setToken(token);
            return authenticated;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @PostMapping("new")
    public User getAccountByClientAndHolding(@RequestParam("username") String username,
                                             @RequestParam("password") String password) {
        try {
            User persisted = userService.create(username, password);
            return persisted;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private String getJWTToken(String username) {

        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }


}
