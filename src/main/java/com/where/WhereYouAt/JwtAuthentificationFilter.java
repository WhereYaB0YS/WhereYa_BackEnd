package com.where.WhereYouAt;


import com.where.WhereYouAt.domain.utils.JwtUtil;
import com.where.WhereYouAt.exception.enumclass.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthentificationFilter extends GenericFilterBean {

    private final JwtUtil jwtUtil;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtUtil.resolveToken((HttpServletRequest) request);

        // 유효한 토큰인지 확인
        if(token != null && jwtUtil.validateToken(token)){
            //토큰이 유효하면 토큰으로부터 유저 정보를 받아온다
            Authentication authentication = jwtUtil.getAuthentication(token);

            // SecurityContext에 Authentication 객체를 저장한다
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request,response);
    }

//    public JwtAuthentificationFilter(
//            AuthenticationManager authenticationManager,JwtUtil jwtUtil){
//        super(authenticationManager);
//        this.jwtUtil = jwtUtil;
//    }

//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain chain
//    ) throws IOException, ServletException{
//        Authentication authentification = getAuthentification(request);
//
//
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(authentification);
//
//        //token 유무 상관없이 dofilter는 항상 실행
//        chain.doFilter(request,response);
//    }


    private Authentication getAuthentification(HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        System.out.println(token);
        if(token == null){
            return null;
        }
        Claims claims = null;
        try{
            claims = (Claims) jwtUtil.getAuthentication(token.substring("Bearer ".length())).getPrincipal();
        }catch(ExpiredJwtException ex){
            ex.printStackTrace();
            request.setAttribute("exception", ErrorCode.EXPIRED_TOKEN.getErrorCode());
        }catch(JwtException ex){
            ex.printStackTrace();
            request.setAttribute("exception",ErrorCode.INVALID_TOKEN.getErrorCode());
        }

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(claims,null);
        return authentication;
    }
}

