<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 해더파일불러오기 -->
<%@ include file="views/common/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<section id="content">
	<h1 align="center" style="margin:200px;">안녕하세요,mvc입니다.</h1>
	<%-- <img src="<%=request.getContextPath() %>/images/yoo.jpg"> --%>
	<button onclick="fn_ajaxTest();">정보 받기</button>
	<div id="result"></div>
	<script>
		const fn_ajaxTest=()=>{
			const xhr=new XMLHttpRequest();
			const userId = document.getElementById("userId").value;
			const password=document.getElementById("password").value;
			xhr.open("get", "<%=request.getContextPath()%>/memberInfo?userId="+userId+"&password="+password);
			xhr.onreadystatechange=()=>{
				if(xhr.readyState==4){
					if(xhr.status==200){
						console.log(xhr.responseText);
						document.getElementById("result").innerHTML+=xhr.responseText;
					}
				}
			}
			xhr.send();
		}
	</script>
	
	
	
	
</section>
<%@ include file="views/common/footer.jsp" %>






