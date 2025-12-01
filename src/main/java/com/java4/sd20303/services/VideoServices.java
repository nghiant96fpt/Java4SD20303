package com.java4.sd20303.services;

import java.util.ArrayList;
import java.util.List;

import com.java4.sd20303.entities.Category;
import com.java4.sd20303.entities.Comment;
import com.java4.sd20303.entities.Favourite;
import com.java4.sd20303.entities.User;
import com.java4.sd20303.entities.Video;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class VideoServices {
	public static String addVideo(Video video, int userId, int catId) {

		Category category = CategoryServices.getInfoById(catId);
		if (category == null) {
			return "Lỗi";
		}

		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("dbConnect");
		EntityManager manager = managerFactory.createEntityManager();

		User user = UserServices.getUserInfoById(userId);

		try {
			if (!manager.getTransaction().isActive()) {
				manager.getTransaction().begin();
			}
			Video videoInsert = video;

			videoInsert.setCategory(category);
			videoInsert.setUser(user);

			manager.persist(videoInsert);

			manager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			manager.getTransaction().rollback();
			manager.close();
			return e.getMessage();
		}
		manager.close();
		return null;
	}

//	- Sửa
//    - Kiểm tra danh mục được chọn có tồn tại trong db không?
//    - Kiểm tra video có thuộc sở hữu của user hiện tại không?
//    - Có user_id và cat_id từ servlet => lấy được thông tin entity tương ứng 

	public static String updateVideo(Video video, int userId, int catId) {

		Category category = CategoryServices.getInfoById(catId);
		if (category == null) {
			return "Lỗi";
		}

		Video videoCheck = getInfoByIdAndUserId(video.getId(), userId);
		if (videoCheck == null) {
			return "Loi";
		}

		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("dbConnect");
		EntityManager manager = managerFactory.createEntityManager();

		User user = UserServices.getUserInfoById(userId);

		try {
			if (!manager.getTransaction().isActive()) {
				manager.getTransaction().begin();
			}
			Video videoInsert = video;

			videoInsert.setCategory(category);
			videoInsert.setUser(user);

			manager.merge(videoInsert);

			manager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			manager.getTransaction().rollback();
			manager.close();
			return e.getMessage();
		}

		manager.close();
		return null;
	}

//	Viết thêm 1 hàm kiểm tra user có đang sở hữu video không? 
//	INPUT: userId, videoId
//	OUTPUT: Video || null 

	public static Video getInfoByIdAndUserId(int videoId, int userId) {
		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("dbConnect");
		EntityManager manager = managerFactory.createEntityManager();

		try {
			String sql = "SELECT * FROM videos WHERE id=?1 AND user_id=?2";
			Query query = manager.createNativeQuery(sql, Video.class);
			query.setParameter(1, videoId);
			query.setParameter(2, userId);

			Video video = (Video) query.getSingleResult();

			manager.close();
			return video;

		} catch (Exception e) {
			// TODO: handle exception
		}

		manager.close();
		return null;
	}

	public static String deleteVideo(int videoId, int userId) {
		Video video = getInfoByIdAndUserId(videoId, userId);
		if (video == null) {
			return "Loi";
		}

		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("dbConnect");
		EntityManager manager = managerFactory.createEntityManager();

		try {
			if (!manager.getTransaction().isActive()) {
				manager.getTransaction().begin();
			}

			for (Favourite favourite : video.getFavourites()) {
				manager.remove(favourite);
			}

			removeComment(video.getComments(), manager);

			manager.remove(video);

			manager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			manager.getTransaction().rollback();
			manager.close();
			return e.getMessage();
		}

		manager.close();
		return null;
	}

	private static void removeComment(List<Comment> comments, EntityManager entityManager) {
		if (comments.size() == 0) {
			return;
		}

		for (Comment comment : comments) {
			removeComment(comment.getComments(), entityManager);
			entityManager.remove(comment);
		}
	}

	public static List<Video> getVideos(String title, int catId) {
		List<Video> videos = new ArrayList<Video>();

		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("dbConnect");
		EntityManager manager = managerFactory.createEntityManager();

		try {
			String sql = "SELECT * FROM videos " + "WHERE (:title IS NULL OR :title = '' OR title LIKE :search) "
					+ "AND (:catId = 0 OR cat_id = :catId)";
			Query query = manager.createNativeQuery(sql, Video.class);
			query.setParameter("title", title);
			query.setParameter("catId", catId);
			query.setParameter("search", "%" + title == null ? "" : title + "%");

			videos = query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return videos;
	}

	public static List<Video> getVideoListByUserId(int userId) {
		List<Video> videos = new ArrayList<Video>();

		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("dbConnect");
		EntityManager manager = managerFactory.createEntityManager();

		try {
			String sql = "SELECT * FROM videos WHERE user_id=?1";

			Query query = manager.createNativeQuery(sql, Video.class);
			query.setParameter(1, userId);

			videos = query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		manager.close();
		return videos;
	}
}
