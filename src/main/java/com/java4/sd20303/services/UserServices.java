package com.java4.sd20303.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.java4.sd20303.entities.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class UserServices {
	public static Map<String, String> register(User user) {
		Map<String, String> map = new HashMap<String, String>();
		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("dbConnect");
		EntityManager manager = managerFactory.createEntityManager();

		try {

			String sql = "SELECT * FROM users WHERE username=?1 OR email=?2 OR phone=?3";

			Query query = manager.createNativeQuery(sql, User.class);
			query.setParameter(1, user.getUsername());
			query.setParameter(2, user.getEmail());
			query.setParameter(3, user.getPhone());

			List<User> users = query.getResultList();
			for (User item : users) {
				if (item.getUsername().equals(user.getUsername())) {
					map.put("errUsername", "Tên đăng nhập đã tồn tại");
				}

				if (item.getEmail().equals(user.getEmail())) {
					map.put("errEmail", "Email đã tồn tại");
				}

				if (item.getPhone().equals(user.getPhone())) {
					map.put("errPhone", "Số điện thoại đã tồn tại");
				}
			}

			if (map.isEmpty()) {
//				Không có lỗi thực hiện insert user vào db

				if (!manager.getTransaction().isActive()) {
					manager.getTransaction().begin();
				}

				manager.persist(user);

				manager.getTransaction().commit();
			}

		} catch (Exception e) {
			e.printStackTrace();
			manager.getTransaction().rollback();
		}
		manager.close();
		return map;
	}

//	Đăng nhập 
//	Đăng nhập bằng (username hoặc email) và password

	public static User login(String usernameOrEmail, String password) {
		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("dbConnect");
		EntityManager manager = managerFactory.createEntityManager();

		try {
			String sql = "SELECT * FROM users WHERE username=?1 OR email=?2";
			Query query = manager.createNativeQuery(sql, User.class);
			query.setParameter(1, usernameOrEmail);
			query.setParameter(2, usernameOrEmail);

			User user = (User) query.getSingleResult();

			if (password.equals(user.getPassword())) {
				manager.close();
				return user;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		manager.close();
		return null;
	}

	public static User getUserInfoById(int id) {
		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("dbConnect");
		EntityManager manager = managerFactory.createEntityManager();
		try {
			User user = manager.find(User.class, id);

//			user.getFavourites().get(0).getVideo().getTitle();
//			user.getVideos().get(0).getComments().size();

			manager.close();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}

		manager.close();
		return null;
	}
}
