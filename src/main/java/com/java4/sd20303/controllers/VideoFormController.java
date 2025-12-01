package com.java4.sd20303.controllers;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.java4.sd20303.beans.VideoFormBean;
import com.java4.sd20303.entities.Category;
import com.java4.sd20303.entities.Video;
import com.java4.sd20303.services.CategoryServices;
import com.java4.sd20303.services.VideoServices;
import com.java4.sd20303.utils.Utils;

@MultipartConfig
@WebServlet("/editer/video-form")
public class VideoFormController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//		Lấy danh sách danh mục từ DB
//		và hiển thị lên giao diện ở dropdown

		List<Category> categories = CategoryServices.getAll();
		req.setAttribute("categories", categories);

		req.getRequestDispatcher("/video-form.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");

		List<Category> categories = CategoryServices.getAll();
		req.setAttribute("categories", categories);

		try {
			VideoFormBean bean = new VideoFormBean();

			BeanUtils.populate(bean, req.getParameterMap());

			bean.setImage(req.getPart("image"));

			req.setAttribute("bean", bean);

			if (bean.getErrors().isEmpty()) {
				String type = bean.getImage().getContentType().split("/")[1];
				String name = String.valueOf(new Date().getTime()) + "." + type;
				String path = "/assets/images/" + name;
				String tomcatPath = req.getServletContext().getRealPath(path);
				bean.getImage().write(tomcatPath);

				Video video = new Video();
				video.setTitle(bean.getTitle());
				video.setDesc(bean.getDesc());
				video.setUrl(bean.getUrl());
				video.setPoster(name);
				video.setViewCount(0);
				video.setStatus(bean.getStatus());

				Calendar calendar = Calendar.getInstance();

				String date = String.format("%d-%d-%d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
						calendar.get(Calendar.DAY_OF_MONTH));

				video.setCreateAt(java.sql.Date.valueOf(date));

				String saveErr = VideoServices.addVideo(video,
						Integer.parseInt(Utils.getCookie(Utils.COOKIE_KEY_USER_ID, req)), bean.getCategory());

				if (saveErr != null) {
					System.out.println(saveErr);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		req.getRequestDispatcher("/video-form.jsp").forward(req, resp);
	}
}

// Danh sách => Xoá video => Danh sách 

// Chuyển qua xây dựng 2 chức năng (API)
// - Danh sách video
// - Xoá video 

// Danh sách
// - Gọi API danh sách video => js để hiển thị lên giao diện
// - Xoá gọi api xoá => gọi tiếp api danh sách 
//=> js để cập nhật giao diện 
