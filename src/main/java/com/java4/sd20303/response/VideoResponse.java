package com.java4.sd20303.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VideoResponse {
	private int id;
	private String name;
	private String image;
	private String url;
	private String authName;
	private String catName;
	private int status;
}

//- Id
//- Tên
//- Hình ảnh
//- URL
//- Tên người đăng (users.name)
//- Tên danh mục (categories.name)
//- Trạng thái: