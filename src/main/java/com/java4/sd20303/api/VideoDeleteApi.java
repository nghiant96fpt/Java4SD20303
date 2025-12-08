package com.java4.sd20303.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java4.sd20303.response.VideoDeleteResponse;
import com.java4.sd20303.services.VideoServices;
import com.java4.sd20303.utils.Utils;

@WebServlet("/api/video-delete")
public class VideoDeleteApi extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json");
		String videoId = req.getParameter("videoId");
		VideoDeleteResponse videoDeleteResponse = new VideoDeleteResponse();
		Gson gson = new GsonBuilder().serializeNulls().create();

		if (videoId == null) {
			videoDeleteResponse.setMessage("Video Id không rỗng");
			videoDeleteResponse.setStatus(false);

			resp.getWriter().print(gson.toJson(videoDeleteResponse));
			return;
		}

		try {
			int id = Integer.parseInt(videoId);
			if (id <= 0) {
				videoDeleteResponse.setMessage("Video Id phải > 0");
				videoDeleteResponse.setStatus(false);

				resp.getWriter().print(gson.toJson(videoDeleteResponse));
				return;
			}
		} catch (Exception e) {
			videoDeleteResponse.setMessage("Video Id phải là số");
			videoDeleteResponse.setStatus(false);

			resp.getWriter().print(gson.toJson(videoDeleteResponse));
			return;
		}

//		kiểm tra id có phải là số không?
//		kiểm tra id có lớn hơn bằng 0 hay không?

		String userId = Utils.getCookie(Utils.COOKIE_KEY_USER_ID, req);

		if (userId == null) {
			videoDeleteResponse.setMessage("User Id không rỗng");
			videoDeleteResponse.setStatus(false);

			resp.getWriter().print(gson.toJson(videoDeleteResponse));
			return;
		}

		try {
			int id = Integer.parseInt(userId);
			if (id <= 0) {
				videoDeleteResponse.setMessage("User Id phải > 0");
				videoDeleteResponse.setStatus(false);

				resp.getWriter().print(gson.toJson(videoDeleteResponse));
				return;
			}
		} catch (Exception e) {
			videoDeleteResponse.setMessage("User Id phải là số");
			videoDeleteResponse.setStatus(false);

			resp.getWriter().print(gson.toJson(videoDeleteResponse));
			return;
		}

		String errDelete = VideoServices.deleteVideo(Integer.parseInt(videoId), Integer.parseInt(userId));

		if (errDelete != null) {
			videoDeleteResponse.setMessage(errDelete);
			videoDeleteResponse.setStatus(false);

			resp.getWriter().print(gson.toJson(videoDeleteResponse));
			return;
		}

		videoDeleteResponse.setMessage("Xoá thành công");
		videoDeleteResponse.setStatus(true);

		resp.getWriter().print(gson.toJson(videoDeleteResponse));
	}
}
