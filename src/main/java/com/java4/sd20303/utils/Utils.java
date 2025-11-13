package com.java4.sd20303.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Utils {
	public static final String COOKIE_KEY_USER_ID = "user_id";
	public static final String COOKIE_KEY_ROLE = "role";

	public static String getCookie(String key, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(key)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	public static void clearCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return;
		}
		for (Cookie cookie : cookies) {
			cookie.setMaxAge(-1);
			response.addCookie(cookie);
		}
	}

	public static void setCookie(String key, String value, HttpServletResponse response) {
		int maxAge = 60 * 60 * 24 * (3 + 7 / 24);

		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(maxAge);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}
