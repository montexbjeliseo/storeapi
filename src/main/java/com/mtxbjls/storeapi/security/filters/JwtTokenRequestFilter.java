package com.mtxbjls.storeapi.security.filters;

import com.mtxbjls.storeapi.security.services.impl.CustomUserDetailsService;
import com.mtxbjls.storeapi.security.utils.JwtTokenUtil;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@Slf4j
public class JwtTokenRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    public JwtTokenRequestFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JwtTokenRequestFilter::doFilterInternal");
        try {
            final String authorizationHeader = request.getHeader("Authorization");
            String username = null;
            String jwtToken = null;

            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                log.info("JwtTokenRequestFilter::doFilterInternal::authHeader");
                jwtToken = authorizationHeader.substring(7);
                username = jwtTokenUtil.extractUsername(jwtToken);
            }

            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                log.info("JwtTokenRequestFilter::doFilterInternal::userDetails");
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if(jwtTokenUtil.validateToken(jwtToken, userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

            }
            filterChain.doFilter(request, response);
        } catch (ServletException | SignatureException | IOException ex) {
            resolver.resolveException(request, response, null, ex);
            log.error("Cannot set user authentication: {}", ex.getMessage());
        }
    }
}
