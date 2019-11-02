package com.icedevcloud.test.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.java.Log;
import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

import java.util.Date;

@Log
public class JwtTests {

    @Test
    public void jwtTest1() {
        JwtBuilder jwtBuilder = Jwts.builder().setId("123")
                .setSubject("XiaoBingBy")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "bing");
        log.info(jwtBuilder.compact());
    }

    @Test
    public void jwtTest2() {
        String key = "312321321321";
        //设置算法为HS256
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date now = new Date(System.currentTimeMillis());
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", "JWT")    //设置header
                .setHeaderParam("alg", "HS256")
                .setIssuedAt(now)     //设置iat
                .claim("name", "liuwillow")   //设置payload的键值对
                .claim("level", "100")
                .setIssuer("lwl")
                .signWith(signatureAlgorithm, key);    //签名，需要算法和key
        String jwt = builder.compact();
        System.out.println("生成的jwt:" + jwt);

        //获取claims
        Claims claims = Jwts.parser()
                .setSigningKey(key)     //此处的key要与之前创建的key一致
                .parseClaimsJws(jwt)
                .getBody();
        //获取name和level
        String name = (String) claims.get("name");
        String level = (String) claims.get("level");
        System.out.println("name:" + name + " level:" + level);
    }

    public void jwtTest3() {
        //Jwt decode = JwtHelper
    }

}
