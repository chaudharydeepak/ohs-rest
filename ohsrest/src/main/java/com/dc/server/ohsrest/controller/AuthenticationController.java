package com.dc.server.ohsrest.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dc.server.ohrest.jwtsecurityImpl.JwtAuthenticationResponse;
import com.dc.server.ohsrest.domain.Observation;
import com.dc.server.ohsrest.helper.EhsHelper;
import com.dc.server.ohsrest.jwtsecurity.JwtAuthenticationRequest;
import com.dc.server.ohsrest.jwtsecurity.JwtTokenUtil;
import com.dc.server.ohsrest.jwtsecurity.JwtUser;

@RestController
public class AuthenticationController
{

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	EhsHelper ehsHelper;

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			Device device) throws AuthenticationException, UnsupportedEncodingException
	{
		System.err.println("***** " + authenticationRequest.getUsername());
		System.err.println("***** " + authenticationRequest.getPassword());
		// Perform the security
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
						authenticationRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Reload password post-security so we can generate token
		final JwtUser userDetails = (JwtUser) userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails, device);

		// Return the token
		return ResponseEntity.ok(new JwtAuthenticationResponse(token));
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request)
	{
		String token = request.getHeader(tokenHeader);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

		if (jwtTokenUtil.canTokenBeRefreshed(token))
		{
			String refreshedToken = jwtTokenUtil.refreshToken(token);
			return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
		} else
		{
			return ResponseEntity.badRequest().body(null);
		}
	}

	@RequestMapping("/admin/loadAllObs")
	public List<Observation> loadAllObservations()
	{
		return ehsHelper.loadAllObservations();
	}

}