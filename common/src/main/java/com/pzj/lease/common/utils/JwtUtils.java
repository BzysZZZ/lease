package com.pzj.lease.common.utils;

import com.pzj.lease.common.exception.LeaseException;
import com.pzj.lease.common.result.Result;
import com.pzj.lease.common.result.ResultCodeEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import javax.crypto.SecretKey;
import java.rmi.dgc.Lease;
import java.util.Date;

public class JwtUtils {

    private static SecretKey secretKey = Keys.hmacShaKeyFor("passwordpasswordpasswordpasswordpassword".getBytes());

    public static String createToken(Long userId,String username){

        return Jwts.builder().setExpiration(new Date(System.currentTimeMillis() + 3600000*24*2000L)).
                setSubject("LOGIN_USER").
                claim("userId", userId).
                claim("username", username).
                signWith(secretKey, SignatureAlgorithm.HS256).compact();
    }

    public static Claims parseToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return claimsJws.getBody();
        } catch (ExpiredJwtException e) {
            throw new LeaseException(ResultCodeEnum.TOKEN_EXPIRED);
        } catch (JwtException e) {
            throw new LeaseException(ResultCodeEnum.TOKEN_INVALID);
        }
    }

    public static void main(String[] args) {
        System.out.println(createToken(4L,"myuser"));
    }
}
