package com.example.JobApp;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

    @Value("${jwt.secret}")
    private String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authheader = request.getHeader("Authorization");

        if(authheader != null && authheader.startsWith("Bearer")){
            String token = authheader.substring(7);

            try{

                Key key = Keys.hmacShaKeyFor(secret.getBytes());

                Claims claim = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

                String email = claim.getSubject();

                String role = (String) claim.get("role");

                if(email != null && SecurityContextHolder.getContext().getAuthentication()==null){

                    UsernamePasswordAuthenticationToken t = new UsernamePasswordAuthenticationToken
                                                     (email, null, Collections.singletonList(new SimpleGrantedAuthority(role)));

                    t.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(t);

                }
            }
            catch (ExpiredJwtException e){

                 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                 response.getWriter().write("Expired Token");
                 return;
            }
            catch(Exception e){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid Token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
