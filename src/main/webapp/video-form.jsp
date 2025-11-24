<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
</head>
</head>
<body>
	<div class="container">
		<div class="col-6 offset-3">
			<form method="POST"
				action="${pageContext.request.contextPath}"
				enctype="multipart/form-data">
				<div class="mb-3">
				  <label for="exampleFormControlInput1" class="form-label">Tiêu đề</label>
				  <input name="title" type="text" class="form-control">
				</div>
				
				<div class="mb-3">
				  <label for="exampleFormControlInput1" class="form-label">Mô tả</label>
				  <textarea name="desc" class="form-control" rows="3">${bean.desc}</textarea>
				</div>
				
				<div class="mb-3">
				  <label for="exampleFormControlInput1" class="form-label">Hình ảnh</label>
				  <input name="image" type="file" class="form-control" accept="image/*">
				</div>
				
				<div class="mb-3">
				  <label for="exampleFormControlInput1" class="form-label">Url</label>
				  <input name="url" type="text" class="form-control">
				</div>
				
				<div class="mb-3">
				  <label for="exampleFormControlInput1" class="form-label">Danh mục video</label>
				  <select name="category" class="form-select" aria-label="Default select example">
					  <option value="0">----Chọn danh mục------</option>
				</select>
				</div>
				
				<div class="mb-3">
				  <label for="exampleFormControlInput1" class="form-label">Trạng thái video</label>
				  <div class="form-check">
					  <input name="status" value="1" class="form-check-input" type="radio" id="radioDefault1">
					  <label class="form-check-label" for="radioDefault1">
					   	Hiển thị
					  </label>
					</div>
					<div class="form-check">
					  <input name="status" value="2" class="form-check-input" type="radio" id="radioDefault2">
					  <label class="form-check-label" for="radioDefault2">
					   	Ẩn
					  </label>
					</div>
				</div>
				
				<button type="submit" class="btn btn-primary">Thêm video</button>
			</form>
		</div>
	</div>
	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
</body>
</html>