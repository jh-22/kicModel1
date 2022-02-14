<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- view 폴더 밑에 있는 파일은 header가 뜨니까 header 필요없는 파일은 single폴더에 넣기  -->
<form action="pictureImg.jsp" method="post" 
enctype="multipart/form-data"> 
<!-- 폼에서 파일 업로드 시 enctype="multipart/form-data">  필수 -->
	<input type="file" name="picture">
	<input type="submit" value="사진등록">
</form>
</body>
</html>