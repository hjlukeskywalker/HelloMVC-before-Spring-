<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>    
<%
	List<Member> members=(List<Member>)request.getAttribute("members");
	
	String searchType=request.getParameter("searchType")==null?"":request.getParameter("searchType");
	String keyword=request.getParameter("searchKeyword")==null?"":request.getParameter("searchKeyword");
	out.print(searchType+" : "+keyword);
%>    
<%@ include file="/views/common/header.jsp"%>
	<style type="text/css">
    section#memberList-container {text-align:center;}
    
    section#memberList-container table#tbl-member {width:100%; border:1px solid gray; border-collapse:collapse;}
    section#memberList-container table#tbl-member th, table#tbl-member td {border:1px solid gray; padding:10px; }
    section#memberList-container>div#pageBar>*{margin-right:20px;}
    
    /* 검색창에 대한 스타일 */
    div#search-container {margin:0 0 10px 0; padding:3px; 
    background-color: rgba(0, 188, 212, 0.3);}
    div#search-userId{display:inline-block;}
    div#search-userName{display:none;}
    div#search-gender{display:none;}
    div#numPerpage-container{float:right;}
    form#numperPageFrm{display:inline;}
    
    </style>
    
    <section id="memberList-container">
        <h2>회원관리</h2>
        <div id="search-container">
        	검색타입 : 
        	<select id="searchType">
        		<option value="userId" <%=searchType.equals("userId")?"selected":"" %>>아이디</option>
        		<option value="userName" <%=searchType.equals("userName")?"selected":"" %>>회원이름</option>
        		<option value="gender" <%=searchType.equals("gender")?"selected":"" %>>성별</option>
        	</select>
        	<div id="search-userId">
        		<form action="<%=request.getContextPath()%>/admin/searchMember">
        			<input type="hidden" name="searchType" value="userId" >
        			<input type="text" name="searchKeyword" size="25" 
        			placeholder="검색할 아이디를 입력하세요" value='<%=searchType.equals("userId")?keyword:"" %>'>
        			<button type="submit">검색</button>
        		</form>
        	</div>
        	<div id="search-userName">
        		<form action="<%=request.getContextPath()%>/admin/searchMember">
        			<input type="hidden" name="searchType" value="userName">
        			<input type="text" name="searchKeyword" size="25" 
        			placeholder="검색할 이름을 입력하세요" value='<%=searchType.equals("userName")?keyword:"" %>'>
        			<button type="submit">검색</button>
        		</form>
        	</div>
        	<div id="search-gender">
        		<form action="<%=request.getContextPath()%>/admin/searchMember">
        			<input type="hidden" name="searchType" value="gender">
        			<label><input type="radio" name="searchKeyword" value="M" 
        			<%=searchType.equals("gender")&&keyword.equals("M")?"checked":"" %>>남</label>
        			<label><input type="radio" name="searchKeyword" value="F" 
        			<%=searchType.equals("gender")&&keyword.equals("F")?"checked":"" %>>여</label>
        			<button type="submit">검색</button>
        		</form>
        	</div>
        </div>
        <div id="numPerpage-container">
        	페이지당 회원수 : 
        	<form id="numPerFrm" action="">
        		<select name="numPerpage" id="numPerpage">
        			<option value="10" <%=request.getParameter("numPerpage")!=null&&request.getParameter("numPerpage").equals("10")?"selected":"" %>>10</option>
        			<option value="5" <%=request.getParameter("numPerpage")==null||request.getParameter("numPerpage").equals("5")?"selected":"" %>>5</option>
        			<option value="3" <%=request.getParameter("numPerpage")!=null&&request.getParameter("numPerpage").equals("3")?"selected":"" %>>3</option>
        		</select>
        	</form>
        </div>
        <table id="tbl-member">
            <thead>
                <tr>
                    <th>아이디</th>
				    <th>이름</th>
				    <th>성별</th>
				    <th>나이</th>
				    <th>이메일</th>
				    <th>전화번호</th>
				    <th>주소</th>
				    <th>취미</th>
				    <th>가입날짜</th>
                </tr>
            </thead>
            <tbody>
            	<%if(members.isEmpty()) {%>
            		<tr>
            			<td colspan="9" align="center">
            			검색결과가 없습니다.</td>
            		</tr>
            	<%}else{ %>
	            	<% for(Member m : members){ %>
		       	    	<tr>
		       	    		<td><%=m.getUserId() %></td>
		       	    		<td><%=m.getUserName() %></td>
		       	    		<td><%=m.getGender() %></td>
		       	    		<td><%=m.getAge() %></td>
		       	    		<td><%=m.getEmail() %></td>
		       	    		<td><%=m.getPhone() %></td>
		       	    		<td><%=m.getAddress() %></td>
		       	    		<td><%=m.getHobby() %></td>
		       	    		<td><%=m.getEnrollDate() %></td>
		       	    	</tr>
	       	    	<%}
            	}%>
            </tbody>
        </table>
        <div id="pageBar">
        	<%=request.getAttribute("pageBar") %>
        </div>
    </section>
    <script>
    	$("#searchType").change(e => {
    		const userId=$("#search-userId");
    		const userName=$("#search-userName");
    		const gender=$("#search-gender");
    		const value=$(e.target).val();//userId OR userName OR gender
    		
    		userId.css("display","none");
    		userName.css("display","none");
    		gender.css("display","none");
    		
    		
    		$("#search-"+value).css("display","inline-block");
    		
    	});
    	$(function(){   		
    		$("#searchType").change();
    	})
    	$("#numPerpage").change((e)=>{
    		
    		
    		$.ajax({
    			url:"<%=request.getContextPath()%>/admin/memberListAjax",
    			data:{
    				"cPage":<%=request.getParameter("cPage")%>,
    				"numPerpage":$(e.target).val()
    				},
    			success:data=>{
    				console.log(data);
    				$("#tbl-member").html(data);
    			}
    		});
    		
    		
    		
    		<%-- if(<%=keyword.equals("")%>){
    			//검색을 안했음
    			$("#numPerFrm").attr("action","<%=request.getContextPath()%>/admin/memberList");
    			$("#numPerFrm").append($("<input>").attr({
    				type:"hidden",name:"cPage",value:"<%=request.getParameter("cPage")%>"
    			}));
    			$("#numPerFrm").append($("<input>").attr({
    				type:"hidden",name:"numPepage",value:"<%=request.getParameter("numPerpage")%>"
    			}));
    		}else{ 
    			//검색을 했음
    			$("#numPerFrm").attr("action","<%=request.getContextPath()%>/admin/searchMember");
    			$("#numPerFrm").append($("<input>").attr({
    				type:"hidden",name:"cPage",value:"<%=request.getParameter("cPage")%>"
    			}));
    			$("#numPerFrm").append($("<input>").attr({
    				type:"hidden",name:"searchType",value:"<%=searchType%>"
    			}));
    			$("#numPerFrm").append($("<input>").attr({
    				type:"hidden",name:"searchKeyword",value:"<%=keyword%>"
    			}));
    		}
    		
    		
    		$("#numPerFrm").submit();//제출하기 --%>
    	});
    </script>
    
    
    
    
	

<%@ include file="/views/common/footer.jsp"%>













