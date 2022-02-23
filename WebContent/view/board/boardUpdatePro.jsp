<%@page import="service.BoardDao"%>
<%@page import="model.Board"%>
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
<%
//~pro : 서비스작업하는 페이지
String path = application.getRealPath("/")+"/boardupload/";
int size = 10*1024*1024;
MultipartRequest multi = new MultipartRequest(request, path, size, "UTF-8");
Board board = new Board(); 
board.setNum(Integer.parseInt(multi.getParameter("num")));
board.setWriter(multi.getParameter("writer")); 
board.setPass(multi.getParameter("pass")); 
board.setSubject(multi.getParameter("subject")); 
board.setContent(multi.getParameter("content")); 
board.setFile1(multi.getFilesystemName("file1")); 

if(board.getFile1()==null || board.getFile1().equals("")) {
	//null이거나 "" 일경우 : 수정사항이 없을 경우 -> 기존 파일 그대로 저장
	board.setFile1(multi.getParameter("file2"));
}
	

BoardDao bd = new BoardDao();
Board dbboard = bd.boardOne(board.getNum());

String msg = "비밀번호가 틀렸습니다";
String url = "boardUpdateForm.jsp?num="+board.getNum();

if(board.getPass().equals(dbboard.getPass())){  //비밀번호 확인
	if(bd.boardUpdate(board)>0){				// 데이터 수정
		msg="수정 완료";
		url="boardInfo.jsp?num="+board.getNum();
	} else{
		msg="수정 실패";
	}
} 

%>
<script>
alert('<%=msg%>')
location.href='<%=url%>'


</script>
</body>
</html>