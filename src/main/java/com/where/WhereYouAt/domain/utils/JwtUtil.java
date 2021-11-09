package com.where.WhereYouAt.domain.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;


@RequiredArgsConstructor
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private String key;

    private long tokenValidTime = 1*(1000*60*60*24);


    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init(){
//        this.key = Keys.hmacShaKeyFor(this.secret.getBytes());
        key = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(Long userId, String nickname){
        Claims claims = Jwts.claims().setSubject(nickname);
        claims.put("userId",userId);
        claims.put("nickname",nickname);
        Date now = new Date();

        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidTime))
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
        return token;
    }

    // 토큰에서 인증 정보 조회
    public Authentication getAuthentication (String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserNickname(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserNickname(String token){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져온다. "Authorization":"Bearer TOKEN값"
    public String resolveToken(HttpServletRequest request){
        return request.getHeader("Authorization");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        }catch(Exception ex){
            return false;
        }
    }
}
