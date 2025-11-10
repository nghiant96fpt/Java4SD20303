package com.java4.sd20303.beans;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginBean {
	private String usernameOrEmail;
	private String password;

	public Map<String, String> getErrors() {
		Map<String, String> map = new HashMap<String, String>();

		if (usernameOrEmail.isBlank()) {
			map.put("errUsernameOrEmail", "Tên tài khoản hoặc email không rỗng");
		}

		if (password.isEmpty()) {
			map.put("errPassword", "Mật khẩu không bỏ trống");
		}

		return map;
	}
}
