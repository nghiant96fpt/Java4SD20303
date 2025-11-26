package com.java4.sd20303.beans;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Part;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Tạo các biến để nhận giá trị từ form
//Thực hiện kiểm tra các giá trị từ form nếu có 
//- Tiêu đề không được bỏ rỗng isBlank 
//- Mô tả phải có tối thiểu 10 từ
//- URL phải đúng định dạng link embed từ youtube 
//- Hình ảnh bắt buộc phải có và không được lớn hơn 20MB
//- Danh mục bắt buộc chọn
//- Trạng thái bắt buộc chọn

//- **Chuẩn hoá dữ liệu tiêu đề và mô tả để xử lý đồng nhất dữ liệu
//khi insert vào db
//"     Tiêu      đề  lỚp    sd20302      "
//=> "Tiêu đề lỚp sd20302"

//Có thể xử lý ở Getter hoặc Setter đều được (Chỉ thực hiện 1 trong 2 cách)

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VideoFormBean {
	private String title;
	private String desc;
	private String url;
	private Part image;
	private int category;
	private int status;

//	Nếu url này không tồn tại thì hiển thị video mặc định
//	public String getUrl() {
//		return url == null ? "" : url;
//	}

//	getUrl != null ? getUrl : "abc"

	public Map<String, String> getErrors() {
		Map<String, String> map = new HashMap<String, String>();

		return map;
	}

	public void setTitle(String title) {
		this.title = String.join(" ", title.trim().split("\\s+"));
	}

	public void setDesc(String desc) {
		this.desc = String.join(" ", desc.trim().split("\\s+"));
	}

	public String getTitle() {
		return String.join(" ", this.title.trim().split("\\s+"));
	}

	public String getDesc() {
		return String.join(" ", this.desc.trim().split("\\s+"));
	}
}
