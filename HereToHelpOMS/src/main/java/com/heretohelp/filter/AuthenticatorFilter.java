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
	private static final String ORPHAN = "/orphans";
	private static final String ORPHANPROFILE = "/orphan-profile";

	/**
	 * Checks if the given URL is a public path that does not require
	 * authentication.
	 *
	 * @param currentUrl the URL string to check
	 * @return true if the URL matches any public path, false otherwise
	 */
	private boolean isPublicPath(String currentUrl) {
		String[] publicPaths = { LOGIN, REGISTER, ORPHAN, ABOUT, ROOT, CONTACT, HOME, ORPHANPROFILE };
		for (String path : publicPaths) {
			if (currentUrl.endsWith(path)) {
				return true;
			}
		}
		return false;
	}

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

		Cookie Cookie = CookieUtil.getCookie(httpReq, "username");
		String session = Cookie != null ? Cookie.getValue() : null;

		String role = (String) SessionUtil.getAttribute(httpReq, "role");

		String currentUrl = httpReq.getRequestURI();
		// Only set if not already available
		if (session != null && role != null) {
			req.setAttribute("currentUserRole", role);
		}

		if (currentUrl.endsWith(".css") || currentUrl.endsWith(".svg") || currentUrl.endsWith(".jpg")
				|| currentUrl.endsWith(".png")) {
			chain.doFilter(req, resp);
			return;
		}

		if (currentUrl.endsWith(DASHBOARD) && !"admin".equals(role)) {
			httpResp.sendRedirect(httpReq.getContextPath() + ROOT);
			return;
		}

		if (session == null || role == null) {
			if (isPublicPath(currentUrl)) {
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
