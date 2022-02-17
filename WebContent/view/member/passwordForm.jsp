<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
function inputChk(f){
	
	/* Form : 프론트 -> Pro.jsp 로넘어가면 백앤드,서버연결 
	기존 비밀번호는 서버연결해서 불러와야해 */
	if(f.pass.value == ''){
		alert("비밀번호를 입력하세요")
		f.pass.focus()
		/* 커서 이동 */
		return false;
	}
	if(f.newpass.value == ''){
		alert("새로운 비밀번호를 입력하세요")
		f.pass.focus()
		return false;
	}if(f.pass.value == f.newpass.value){
		alert("새로운 비밀번호가 기존 비밀번호와 동일합니다")
		f.pass.focus()
		return false;
	}
	
	if(f.newpass.value != f.newpass2.value){
		alert("새로운 비밀번호가 다릅니다.")
		f.newpass2.value=""
		f.newpass2.focus()
		return false;
	}
	
	return true;
	
}
</script>
</head>
<body>
<%
String login = (String) session.getAttribute("memberId");
if (login == null || login.equals("")){
%>
<script>
alert("로그인이 필요합니다.")
location.href = "<%=request.getContextPath()%>/view/member/loginForm.jsp"


</script>
<% } else {%>

<hr>
	<div class="container">
		<h2   id="center">비밀번호 변경</h2>
		<form action="<%=request.getContextPath()%>/view/member/passwordPro.jsp" 
		method="post" name="f" onsubmit="return inputChk(this)">
		<!-- onsubmit : true 면 submit 진행, false면 submit안함 -->
		<div class="form-group">
			<label for="usr">ID:</label> 
			<input type="text" class="form-control" name="id" readonly="readonly" value="<%=login%>"> 
			
			<label for="pwd">현재 비밀번호:</label>
			<input type="password" class="form-control" name="pass">
			<label for="pwd">변경 비밀번호:</label>
			<input type="password" class="form-control" name="newpass">
			<label for="pwd">변경 비밀번호 확인:</label>
			<input type="password" class="form-control" name="newpass2">
			
		</div>
		<div id="center" style="padding: 3px;">
			<button type="submit" class="btn btn-dark">비밀번호 변경</button>
		</div>
		</form>
	</div>






<%} %>
</body>
</html>