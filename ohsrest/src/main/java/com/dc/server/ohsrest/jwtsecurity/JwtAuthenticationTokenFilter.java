package com.dc.server.ohsrest.jwtsecurity;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter
{

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.header}")
	private String tokenHeader;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException
	{
		final String authToken = request.getHeader(this.tokenHeader);

		String username = null;
		
		logger.info("=====JwtAuthenticationTokenFilter.doFilterInternal retreived authToken -- " + authToken);

		try
		{
			username = jwtTokenUtil.getUsernameFromToken(authToken);
		} catch (IllegalArgumentException e)
		{
			logger.error("an error occured during getting username from token", e);
		} catch (ExpiredJwtException e)
		{
			logger.warn("the token is expired and not valid anymore", e);
		}

		logger.info("checking authentication for user " + username);
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
		{

			// It is not compelling necessary to load the use details from the database. You
			// could also store the information
			// in the token and read it from it. It's up to you ;)
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

			// For simple validation it is completely sufficient to just check the token
			// integrity. You don't have to call
			// the database compellingly. Again it's up to you ;)
			if (jwtTokenUtil.validateToken(authToken, userDetails))
			{
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				//authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				logger.info("authenticated user " + username + ", setting security context");
				/* we need this for spring security authorization on resources*/
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(request, response);
	}
}
