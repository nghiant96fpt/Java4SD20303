package com.java4.sd20303.services;

import java.util.ArrayList;
import java.util.List;

import com.java4.sd20303.entities.Category;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class CategoryServices {
	public static List<Category> getAll() {
		List<Category> categories = new ArrayList<Category>();

		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("dbConnect");
		EntityManager manager = managerFactory.createEntityManager();

		try {
			String sql = "SELECT * FROM categories";

			Query query = manager.createNativeQuery(sql, Category.class);

			categories = query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		manager.close();
		return categories;
	}

	public static Category getInfoById(int id) {
		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("dbConnect");
		EntityManager manager = managerFactory.createEntityManager();

		try {

			Category category = manager.find(Category.class, id);

			manager.close();
			return category;

		} catch (Exception e) {
			e.printStackTrace();
		}

		manager.close();
		return null;
	}

	public static String addCategory(Category category) {

		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("dbConnect");
		EntityManager manager = managerFactory.createEntityManager();

		try {

//			"    Laptop     Dell    " => "Laptop     Dell"

			String name = category.getName().trim();

//			"Laptop     Dell" => ["Laptop", "Dell"] => slipt

			String[] nameArr = name.split("//s+");

//			["Laptop", "Dell"] => "Laptop Dell" => "laptop dell"

			String nameFinal = String.join(" ", nameArr).toLowerCase();

			String sql = "SELECT * FROM categories WHERE LOWER(name)=?1";

			Query query = manager.createNativeQuery(sql, Category.class);
			query.setParameter(1, nameFinal);

			Category categoryCheck = (Category) query.getSingleResult();

			if (categoryCheck != null) {
				manager.close();
				return "Trùng tên";
			}

			if (!manager.getTransaction().isActive()) {
				manager.getTransaction().begin();
			}

//			insert name = "   tHế    Giới độNg     VậT    "
//			insert name ="Thế Giới Động Vật"

//			nameFinal => "thế giới động vật"
//			
			String[] nameFinalFinal = nameFinal.split(" ");
			for (int index = 0; index < nameFinalFinal.length; index++) {
				String firstStr = nameFinalFinal[index].substring(0, 1).toUpperCase();
				nameFinalFinal[index] = String.format("%s%s", firstStr, nameFinalFinal[index].substring(1));
			}

			category.setName(String.join(" ", nameFinalFinal));

			manager.persist(category);

			manager.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();

			manager.getTransaction().rollback();
		}

		manager.close();
		return null;
	}

//	1, name 1 
//	TH1: => name 2 (Có lỗi)
//	TH2: => name 1 (Có lỗi) => Không có lỗi?
//	2, name 2

	public static String updateCategory(Category category) {

		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("dbConnect");
		EntityManager manager = managerFactory.createEntityManager();

		try {

//			"    Laptop     Dell    " => "Laptop     Dell"

			String name = category.getName().trim();

//			"Laptop     Dell" => ["Laptop", "Dell"] => slipt

			String[] nameArr = name.split("//s+");

//			["Laptop", "Dell"] => "Laptop Dell" => "laptop dell"

			String nameFinal = String.join(" ", nameArr).toLowerCase();

			String sql = "SELECT * FROM categories WHERE LOWER(name)=?1 AND id!=?2";

			Query query = manager.createNativeQuery(sql, Category.class);
			query.setParameter(1, nameFinal);
			query.setParameter(2, category.getId());

			Category categoryCheck = (Category) query.getSingleResult();

			if (categoryCheck != null) {
				manager.close();
				return "Trùng tên";
			}

			if (!manager.getTransaction().isActive()) {
				manager.getTransaction().begin();
			}

			String[] nameFinalFinal = nameFinal.split(" ");
			for (int index = 0; index < nameFinalFinal.length; index++) {
				String firstStr = nameFinalFinal[index].substring(0, 1).toUpperCase();
				nameFinalFinal[index] = String.format("%s%s", firstStr, nameFinalFinal[index].substring(1));
			}

			category.setName(String.join(" ", nameFinalFinal));

			manager.merge(category);

			manager.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();

			manager.getTransaction().rollback();
		}

		manager.close();
		return null;
	}
}
