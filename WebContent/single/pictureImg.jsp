<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 1) upload 폴더 만들기 (/upload) : /webcontent아래를 의미

C:\Users\shelk\eclipse-workspace-UTF\.metadata\.plugins\
org.eclipse.wst.server.core\tmp0\wtpwebapps\kicModel1\upload
 -->
 <% 
 String path = application.getRealPath("/")+"upload/";
 String fileName = null;
 
 //cos.jar
 MultipartRequest multi = new MultipartRequest(request, path, 10*1024*1024,"utf-8");
 
 fileName = multi.getFilesystemName("picture");
 /* pictureForm.jsp -> <input type="file" name="picture">에 있는 name 적어주기*/
 
 %>
 <script>
 const img = opener.document.querySelector("#pic");
 /*querySelector("#pic"); 대신  getElementById("pic"); 써도 됨 */
/*  memberInput-> <img src="" width="100" height="120" id="pic"> -> id : "pic" */

 img.src="<%=request.getContextPath()%>/upload/<%=fileName%>";
 opener.document.f.picture.value="<%=fileName%>";
 self.close();
 
 </script>
 </body>
</html>