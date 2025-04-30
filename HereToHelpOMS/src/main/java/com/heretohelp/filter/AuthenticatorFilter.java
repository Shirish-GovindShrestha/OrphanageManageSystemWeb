	package com.heretohelp.filter;

import java.io.IOException;

import com.heretohelp.util.CookieUtil;
import com.heretohelp.util.SessionUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(asyncSupported = true, urlPatterns = { "/*" })
public class AuthenticatorFilter implements Filter {
	private static final String LOGIN = "/login";
	private static final String REGISTER = "/register";
	private static final String HOME = "/home";
	private static final String ABOUT = "/about";
	private static final String CONTACT = "/contact";
	private static final String DASHBOARD = "/dashboard";
	private static final String ROOT = "/";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		Filter.super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpServletResponse httpResp = (HttpServletResponse) resp;
		String session = (String) SessionUtil.getAttribute(httpReq, "username");
		Cookie roleCookie = CookieUtil.getCookie(httpReq, "role");
		String role = roleCookie != null ? roleCookie.getValue() : null;
		String currentUrl = httpReq.getRequestURI();

		if (currentUrl.endsWith(".css") || currentUrl.endsWith(".svg") ||currentUrl.endsWith(HOME) || currentUrl.endsWith(ROOT) ||currentUrl.endsWith(ABOUT)||  currentUrl.endsWith(CONTACT) ||  currentUrl.endsWith(".jpg")||currentUrl.endsWith(".png")){
			chain.doFilter(req, resp);
			return;
		}
		
	    if (currentUrl.endsWith(DASHBOARD) && !"admin".equals(role)) {
	        httpResp.sendRedirect(httpReq.getContextPath() + ROOT);
	        return;
	    }

		if (session == null || role == null) {
			if (currentUrl.endsWith(LOGIN) || currentUrl.endsWith(REGISTER)) {
				chain.doFilter(req, resp);
			} else {
				httpResp.sendRedirect(httpReq.getContextPath() + LOGIN);
			}
		} else {
			if (currentUrl.endsWith(LOGIN) || currentUrl.endsWith(REGISTER)) {
				httpResp.sendRedirect(httpReq.getContextPath() + HOME);
			} else {
				chain.doFilter(req, resp);
			}
		}

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		Filter.super.destroy();
	}

}
