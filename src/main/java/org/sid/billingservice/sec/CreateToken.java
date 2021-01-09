package org.sid.billingservice.sec;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CreateToken {
    String temporary_access_token;

    public String getTemporary_access_token() {
        return temporary_access_token;
    }

    public CreateToken() {
        List<String> a = new ArrayList<>();
        String user = "USER";
        a.add(user);
        Algorithm algorithm=Algorithm.HMAC256("mySecret1234");
        String jwtAccessToken= JWT.create()
                .withSubject("admin")
                .withExpiresAt(new Date(System.currentTimeMillis()+1*10*1000))
                .withIssuer("temporary_access_token")
                .withClaim("roles",a)
                .sign(algorithm);

        System.out.printf(a.toString());
        this.temporary_access_token = jwtAccessToken;
    }
}
