package com.java4.sd20303.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java4.sd20303.entities.User;
import com.java4.sd20303.services.UserServices;
import com.java4.sd20303.utils.Utils;

@WebFilter(urlPatterns = { "/admin/*", "/user/*", "/editer/*" })
public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

//		User đã đăng nhập (có đủ userid và role ở Cookie)
//		Nếu path /user/* và role != 1 => Sai vai trò => Login
//		Nếu path /editer/* và role != 2 => Sai vai trò => Login
//		Nếu path /admin/* và role != 3 => Sai vai trò => Login

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String userId = Utils.getCookie(Utils.COOKIE_KEY_USER_ID, req);
		String role = Utils.getCookie(Utils.COOKIE_KEY_ROLE, req);

		if (userId == null || role == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		String path = req.getRequestURI();

		User user = UserServices.getUserInfoById(Integer.parseInt(userId));

		if (!user.isStatus()) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		if (role.equals(String.valueOf(user.getRole()))) {
			Utils.setCookie(Utils.COOKIE_KEY_USER_ID, String.valueOf(user.getId()), resp);
			Utils.setCookie(Utils.COOKIE_KEY_ROLE, String.valueOf(user.getRole()), resp);

			role = String.valueOf(user.getRole());
		}

		if (path.contains("/user/") && !role.equals("1") && !role.equals("2")) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		if (path.contains("/editer/") && !role.equals("2")) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		if (path.contains("/admin/") && !role.equals("3")) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		chain.doFilter(request, response);

	}

}