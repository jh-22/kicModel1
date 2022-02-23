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

String path = application.getRealPath("/")+"/boardupload/";
/* getRealPath : 웹서버의 로케이션 , webcontent */

int size = 10*1024*1024;
MultipartRequest multi = new MultipartRequest(request, path, size, "UTF-8");

Board board = new Board(); //이렇게 사용하려면 디폴트생성자가 있어야해
board.setWriter(multi.getParameter("writer")); //이전에는 request.getParameter 사용했지만 writeForm이 multi로 넘어오기때문에 multi.getParameter 사용
board.setPass(multi.getParameter("pass")); 
board.setSubject(multi.getParameter("subject")); 
board.setContent(multi.getParameter("content")); 
board.setFile1(multi.getFilesystemName("file1")); 

board.setIp(request.getLocalAddr()); //writeForm에서 받아오는 내용아니니까 request사용(multi 아님)

String boardid= (String) session.getAttribute("boardid"); //session : object -> String 형변환? ?
if (boardid == null) {
	boardid = "1";
}
board.setBoardid(boardid);

BoardDao bd = new BoardDao();

//새글 작성시
board.setNum(bd.nextNum()); //db에서 다음 번호 읽음
board.setRef(board.getNum());  

//insert : DML -> int로 잡기
int num = bd.insertBoard(board); // ref랑 num 설정하고 insert 하기

String msg = "게시물 등록 실패";
String url = "writeForm.jsp";


if(num==1){
	msg ="게시물 등록 성공";
	url ="list.jsp?pageNum=1";
	
	
}

%>
<script>
alert('<%=msg%>')
location.href='<%=url%>'

<%-- 

 url = "writeForm.jsp";  //model1은 같은 폴더에 작성 : 상대경로써도 상관없음
		<%=request.getContextPath()%>/view/board/writeForm.jsp // model2에서는 다른폴더에 작성 : 절대경로 써줘야함

 --%>
</script>
</body>
</html>