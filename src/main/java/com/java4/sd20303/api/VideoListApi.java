package com.java4.sd20303.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java4.sd20303.entities.Video;
import com.java4.sd20303.response.VideoResponse;
import com.java4.sd20303.services.VideoServices;
import com.java4.sd20303.utils.Utils;

@WebServlet("/api/videos")
public class VideoListApi extends HttpServlet {

//	PUT || PATCH
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPut(req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doDelete(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json");

//		lấy danh sách video theo user id từ db 
		String userId = Utils.getCookie(Utils.COOKIE_KEY_USER_ID, req);
		List<Video> videos = VideoServices.getVideoListByUserId(Integer.parseInt(userId));

//		Không được print entity ******

		List<VideoResponse> videoResponses = new ArrayList<VideoResponse>();
//		Chuyển dữ liệu từ danh sách video ở DB sang danh sách video response

//		localhost:8080/api/videos

		for (Video video : videos) {
			VideoResponse videoResponse = new VideoResponse();
			videoResponse.setId(video.getId());
			videoResponse.setName(video.getTitle());
			videoResponse.setImage(video.getPoster());
			videoResponse.setUrl(video.getUrl());
			videoResponse.setAuthName(video.getUser().getName());
			videoResponse.setCatName(video.getCategory().getName());
			videoResponse.setStatus(video.getStatus());

			videoResponses.add(videoResponse);
		}

//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		Gson gson = new GsonBuilder().serializeNulls().create();
		resp.getWriter().print(gson.toJson(videoResponses));
	}
}

// Bean Class => Xử lý form
// Entity Class => Xử lý DB
// Response/DTO Class => trả dữ liệu về cho người dùng  
