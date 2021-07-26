<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.member.model.vo.Member" %>    
<%
	Member result=(Member)request.getAttribute("result");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 중복확인</title>
<style>
	div#checkId-container{text-align:center;padding-top:50px;}
	span#duplicated{color:red;font-weight:bolder;}
</style>
</head>
<body>
	<div id="checkId-container">
		<%if(result==null){ %>
			[<span><%=request.getParameter("userId") %></span>]는 사용가능합니다.	
			<br><br>
			<button type="button" onclick="fn_close();">닫기</button>
		<%}else{ %>
			[<span id="duplicated"><%=request.getParameter("userId") %></span>]는 사용중입니다.
			<br><br>
			<!-- 아이디 재입력창 구성 -->
			<form action="<%=request.getContextPath() %>/checkDuplicateId" method="post">
				<input type="text" name="userId" id="userId">
				<input type="submit" value="중복검사" >
			</form>
		<%} %>
	</div>
	<script>
		const fn_close=()=>{
			//1. 현재값을 부모창의 userId_에 대입
			const userId='<%=request.getParameter("userId")%>';
			opener.memberEnrollFrm.userId.value=userId;
			opener.memberEnrollFrm.password.focus();
			//윈도우창 닫기
			close();
		}
	</script>
	
	
	
</body>
</html>