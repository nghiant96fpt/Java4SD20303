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
	private String status;

//	enum 

//	Có hàm setStatus nhận dữ liệu đầu vào int 
//	Dựa vào gía trị int set giá trị STring lại cho status

	public void setStatus(int status) {
		switch (status) {
		case 1:
			this.status = "Chờ duyệt";
		case 2:
			this.status = "Ẩn";
		case 3:
			this.status = "Từ chối";
		case 4:
			this.status = "Đã duyệt";
		default:
			this.status = "Chờ duyệt";
		}
	}
}

//1 = Chờ duyệt => Cho duyệt
//2 = Ẩn => HIDE
//3 = Từ chối
//4 = Duyệt

//- Id
//- Tên
//- Hình ảnh
//- URL
//- Tên người đăng (users.name)
//- Tên danh mục (categories.name)
//- Trạng thái: