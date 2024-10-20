package com.innogent.training.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.innogent.training.config.EmployeeUserDetailsService;
import com.innogent.training.empServiceImpl.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private EmployeeUserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			username = jwtService.extractUsername(token);
		}
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if (jwtService.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
		
//		String username;
//		String password;
//		String base64Credentials = authHeader.substring("Basic ".length());
//        String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
//        final String[] values = credentials.split(":", 2);
//        username = values[0];
//        password = values[1];
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                    userDetails, password, userDetails.getAuthorities());
//            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            Authentication authenticatedUser = authenticationManager.authenticate(authenticationToken);
//            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
//        }
//        filterChain.doFilter(request, response);
		
	}
}