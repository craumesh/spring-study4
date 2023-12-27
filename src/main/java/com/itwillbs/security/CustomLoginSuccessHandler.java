package com.itwillbs.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 
 * 로그인 성공시 권한별로 페이지 이동
 *
 */

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {	
	private static final Logger logger = LoggerFactory.getLogger(CustomLoginSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication auth) throws IOException, ServletException {
		logger.debug("CustomLoginSuccessHandler-onAuthenticationSuccess() 호출");
		
		List<String> roleNames = new ArrayList<String>();
		
//		auth.getAuthorities().forEach(new Consumer<GrantedAuthority>() {
//			@Override
//			public void accept(GrantedAuthority authority) {
//				roleNames.add(authority.getAuthority());
//			}
//		});
		auth.getAuthorities().forEach(authority -> roleNames.add(authority.getAuthority()));
		logger.debug("roleNames : "+roleNames);
		
		if(roleNames.contains("ROLE_ADMIN")) {
			logger.debug("관리자 로그인 성공");
			response.sendRedirect("/sample/admin");
			return;
		}
		
		if(roleNames.contains("ROLE_MEMBER")) {
			logger.debug("멤버 로그인 성공");
			response.sendRedirect("/sample/member");
			return;
		}
		response.sendRedirect("/sample/all");	
	}

}
