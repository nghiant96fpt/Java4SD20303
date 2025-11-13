package com.java4.sd20303.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.java4.sd20303.beans.LoginBean;
import com.java4.sd20303.entities.User;
import com.java4.sd20303.services.UserServices;
import com.java4.sd20303.utils.Utils;
// abc 
// 819238912738917289
@WebServlet("/login")
public class LoginController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String userId = Utils.getCookie(Utils.COOKIE_KEY_USER_ID, req);

		req.getRequestDispatcher("/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			LoginBean bean = new LoginBean();

			BeanUtils.populate(bean, req.getParameterMap());

			req.setAttribute("bean", bean);

			if (bean.getErrors().isEmpty()) {
				User user = UserServices.login(bean.getUsernameOrEmail(), bean.getPassword());
				if (user != null) {
					System.out.println("Đăng nhập thành công");

//					Lưu user_id và role vào cookie với thời hạn: 3 ngày 7 giờ

					Utils.setCookie(Utils.COOKIE_KEY_USER_ID, String.valueOf(user.getId()), resp);
					Utils.setCookie(Utils.COOKIE_KEY_ROLE, String.valueOf(user.getRole()), resp);
				} else {
					System.out.println("Đăng nhập thất bại");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		req.getRequestDispatcher("/login.jsp").forward(req, resp);
	}
}
