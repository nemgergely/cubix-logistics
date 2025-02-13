package hu.cubix.logistics.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    public static final String ISSUER = "LogisticsApplication";
    public static final Algorithm ALGORITHM = Algorithm.HMAC256("someSecret");

    public String createJwt(UserDetails userDetails) {
        return JWT.create()
            .withSubject(userDetails.getUsername())
            .withArrayClaim("auth", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new))
            .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)))
            .withIssuer(ISSUER)
            .sign(ALGORITHM);
    }

    public UserDetails parseJwt(String jwtToken) {
        DecodedJWT decodedJWT = JWT.require(ALGORITHM)
            .withIssuer(ISSUER)
            .build()
            .verify(jwtToken);

        return new User(decodedJWT.getSubject(), "somePassword",
            decodedJWT.getClaim("auth").asList(String.class)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList()
        );
    }
}
