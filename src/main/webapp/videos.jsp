<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
</head>
</head>
<body>
	<div class="container">
		<a class="btn btn-primary mt-3 mb-3" href="${pageContext.request.contextPath}/editer/video-form">Thêm video</a>
		<table class="table">
		  <thead>
		    <tr>
		      <th scope="col">ID</th>
		      <th scope="col">Tiêu đề</th>
		      <th scope="col">Ảnh</th>
		      <th scope="col">URL</th>
		      <th scope="col">Tác giả</th>
		      <th scope="col">Danh mục</th>
		      <th scope="col">Trạng thái</th>
		      <th scope="col">Hành động</th>
		    </tr>
		  </thead>
		  <tbody id="bodyTable" data-context-path="${pageContext.request.contextPath}">
		  	
		  </tbody>
		</table>
	</div>
	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
	<script>
		/* Viết 1 hàm gọi danh sách video bằng axios sau đó log ra dữ liệu đã gọi được */
		/* http://localhost:8080/Java4SD20303/api/videos */
		
		/* axios.get() */
		/* axios.post() */
		
		/* Chuẩn ES6: arrow function */
		/* const getData = async () => {
			try{
				const bodyTable = document.getElementById("bodyTable");
				
				const contextPath = bodyTable.getAttribute("data-context-path");
				
				const data = await axios.get(contextPath + "/api/videos");
				console.log(data.data);
				
				const data1 = await axios.get(contextPath + "/api/videos");
				console.log(data1.data);
				
				const data2 = await axios.get(contextPath + "/api/videos");
				console.log(data2.data);
				
				
				
				let html = "";
				
				data.data.forEach((item)=>{
					html += "<tr>" +
					      "<th>" + item.id + "</th>" +
					      "<td>" + item.name + "</th>" +
					      "<td>" + item.image + "</th>" +
					      "<td>" + item.url + "</th>" +
					      "<td>" + item.authName + "</th>" +
					      "<td>" + item.catName + "</th>" +
					      "<td>" + item.status + "</th>" +
					      "<td>Hành động</th>" +
					    "</tr>";
				});
				
				bodyTable.innerHTML = html;
				
			}catch(err){
				console.log(err);
			}
		} */
		
		const getData = () => {
			const bodyTable = document.getElementById("bodyTable");
			
			const contextPath = bodyTable.getAttribute("data-context-path");
			
			axios.get(contextPath + "/api/videos")
			.then(res=>{
				console.log(res.data);
				let html = "";
				
				res.data.forEach((item)=>{
					html += "<tr>" +
					      "<th>" + item.id + "</th>" +
					      "<td>" + item.name + "</th>" +
					      "<td>" + item.image + "</th>" +
					      "<td>" + item.url + "</th>" +
					      "<td>" + item.authName + "</th>" +
					      "<td>" + item.catName + "</th>" +
					      "<td>" + item.status + "</th>" +
					      "<td>" +
					      	"<button onclick='deleteVideo(" + item.id + ");' class='btn btn-danger'>Xoá video</button>" +
					      "</th>" +
					    "</tr>";
				});
				
				bodyTable.innerHTML = html;
			})
			.catch(err=>{
				console.log(err);
			});
			
		}
		
		async function deleteVideo(id){
			if(confirm("Ban co muon xoa video khong?")){
				try{
					const bodyTable = document.getElementById("bodyTable");
					const contextPath = bodyTable.getAttribute("data-context-path");
					let config = {
					  method: 'post',
					  url: contextPath + '/api/video-delete',
					  headers: { 
					    'Content-Type': 'application/x-www-form-urlencoded'
					  },
					  data : {
						  "videoId": id
					  }
					};

					const response = await axios.request(config);
					console.log(JSON.stringify(response.data));
					  
					alert(response.data.message);
					  
					if(response.data.status){
						getData();
					}
				}catch(e){
					console.log(e)
				}
			}
		}
		
		getData();
		
		/* async function getData(){
			
		} */
		
		/* function *getData(){
			yield == await
		} */
	</script>
</body>
</html>