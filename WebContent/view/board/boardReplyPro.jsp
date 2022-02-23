<%@page import="model.Board"%>
<%@page import="service.BoardDao"%>
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
request.setCharacterEncoding("utf-8"); //post방식 encoding

BoardDao bd = new BoardDao();
Board board = new Board();
 
board.setWriter(request.getParameter("writer")); 
board.setPass(request.getParameter("pass"));
board.setSubject(request.getParameter("subject")); 
board.setContent(request.getParameter("content")); 
board.setFile1(""); 

board.setIp(request.getLocalAddr()); 

String boardid= (String) session.getAttribute("boardid"); 
if (boardid == null) {
	boardid = "1";
}
board.setBoardid(boardid);

int num = Integer.parseInt(request.getParameter("num"));
int ref = Integer.parseInt(request.getParameter("ref"));
int reflevel = Integer.parseInt(request.getParameter("reflevel"));
int refstep = Integer.parseInt(request.getParameter("refstep"));

String msg ="답변 등록시 오류 발생";
String url ="boardReplyForm,jsp?num="+num;

bd.refStepAdd(ref, refstep);


//예를 들어 50번째 글에 답글을 달면
board.setNum(bd.nextNum()); 	//51
board.setRef(ref); 				//50
board.setReflevel(reflevel+1); 	//1  --> 들여쓰기 레벨? 가로 -> 같은 원글의 답글일 경우 level 같음
board.setRefstep(refstep+1);   	//1  --> 세로-> 같은 원글의 답글이어도 step 달라짐

if(bd.insertBoard(board)>0){
	msg ="답변 등록 완료";
	url = "list.jsp";
	
}

%>

<script>
alert('<%=msg%>')
location.href='<%=url%>'

</script>
</body>
</html>