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
public class JwtAuthentificationFilter extends GenericFilterBean{

    private final JwtUtil jwtUtil;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        String token = jwtUtil.resolveToken((HttpServletRequest) request);

        // 유효한 토큰인지 확인
        if(token != null){
            token=token.substring(("Bearer ".length()));
            if(jwtUtil.validateToken(token)){
                //토큰이 유효하면 토큰으로부터 유저 정보를 받아온다
                Authentication authentication = jwtUtil.getAuthentication(token);

                // SecurityContext에 Authentication 객체를 저장한다
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
        chain.doFilter(request,response);
    }
}

