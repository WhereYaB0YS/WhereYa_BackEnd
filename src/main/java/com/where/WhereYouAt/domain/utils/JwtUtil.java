package com.where.WhereYouAt.domain.utils;

import com.where.WhereYouAt.exception.*;
import com.where.WhereYouAt.exception.enumclass.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;


@RequiredArgsConstructor
@Component
public class JwtUtil{

    @Value("${jwt.secret}")
    private String secret;

    private String key;

    private long tokenValidTime = 1*(1000*60*60*24);

    private static int check;


    @PostConstruct
    protected void init(){
        key = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(Long userId, String nickname){
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
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

    public void checkToken(Authentication authentication){
        if(check==1)  throw new ExpiredTokenException();
        if(check==2) throw new InvalidTokenException();
        if(authentication==null) throw new NotExistedTokenException();
    }

    // 토큰에서 인증 정보 조회
    public Authentication getAuthentication (String token){
        Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        return new UsernamePasswordAuthenticationToken(claims.getBody(),"");
    }

    // 토큰에서 회원 정보 추출
    public String getUserId(String token){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져온다. "Authorization":"Bearer TOKEN값"
    public String resolveToken(HttpServletRequest request){
        return request.getHeader("Authorization");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken){
        check=0;
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken);
            if(claims.getBody().getExpiration().before(new Date())){
                check=1;
                return false;
            }
            return true;
        }catch(JwtException ex){
            check=2;
            return false;
        }catch(Exception ex){
            return false;
        }
    }
}
