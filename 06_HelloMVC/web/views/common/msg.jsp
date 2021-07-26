<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String msg=(String)request.getAttribute("msg");
	String script=(String)request.getAttribute("script");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>시스템메세지</title>
</head>
<body>
	<script>
		alert('<%=msg%>');
		//페이지를 닫아주기
		<%=script!=null?script:""%>
		//페이지전환하는 로직구성
		location.replace('<%=request.getContextPath()%><%=request.getAttribute("loc")%>');
	</script>
</body>
</html>