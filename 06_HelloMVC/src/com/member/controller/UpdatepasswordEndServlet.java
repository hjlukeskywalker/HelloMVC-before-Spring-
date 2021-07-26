package com.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.service.MemberService;
import com.member.model.vo.Member;

/**
 * Servlet implementation class UpdatepasswordEndServlet
 */
@WebServlet(name="updatePasswordEnd",urlPatterns = "/updatePasswordEnd")
public class UpdatepasswordEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatepasswordEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//전송된 회원아이디의 패스워드를 변경하는 매소드 
		String userId=request.getParameter("userId");
		String curPw=request.getParameter("password");
		String newPw=request.getParameter("password_new");
		
		//1. 아이디 있는지여부, 현재패스워드가 일치한지여부
		Member m=new MemberService().login(userId,curPw);
		String msg="";
		String loc="";
		String script="";
		if(m!=null) {
			//비밀번호 수정로직 실행
			int result=new MemberService().updatePassword(userId,newPw);
			msg=result>0?"비밀번호수정완료!":"비밀번호수정실패";
			loc="/";			
			script="window.close();";
		}else {
			//다시 돌아감... 비밀번호 수정패이지로
			msg="현재 비밀번호가 일치하지않아 비밀번호를 수정할 수 없습니다.";
			loc="/updatePassword?userId="+userId;
		}
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.setAttribute("script", script);
		request.getRequestDispatcher("/views/common/msg.jsp")
		.forward(request, response);
		
	
	
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
