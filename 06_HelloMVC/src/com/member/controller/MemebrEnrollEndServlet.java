package com.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.AESCryptor;
import com.member.model.service.MemberService;
import com.member.model.vo.Member;

/**
 * Servlet implementation class MemebrEnrollEndServlet
 */
@WebServlet(name="enrollendservlet",urlPatterns = "/memberenrollend.do")
public class MemebrEnrollEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemebrEnrollEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//전송된 데이터가 한글일 경우 글자가 깨짐.....
//		request.setCharacterEncoding("utf-8");
		
		//회원가입로직 처리하기
		//1. 클라이언트가 가입을 위해 전송하는 데이터를 받아옴
		// request.getParameter, getParameterValues()
		String userId=request.getParameter("userId");
		String password=request.getParameter("password");
		String userName=request.getParameter("userName");
		String gender=request.getParameter("gender");
		int age=Integer.parseInt(request.getParameter("age"));
		String email=request.getParameter("email");
		String phone=request.getParameter("phone");
		try {
			email=AESCryptor.encrypt(email);//암호화처리
			phone=AESCryptor.encrypt(phone);
		}catch(Exception e) {
			e.printStackTrace();
		}
		String address=request.getParameter("address");
		String[] hobby=request.getParameterValues("hobby");
		
		//다중값을 보관하기 위해 -> vo객체를 생성해놓음
		
		Member m=new Member(userId, password,userName,gender,age,email,phone,
				address,String.join(",",hobby),null);
		System.out.println(m);
		
		//전달받은 데이터를 DB에 저장
		int result=new MemberService().insertMember(m);
		
		//결과를 기준으로 페이지를 선정
		//result>0 -> 회원가입성공
		//result==0 -> 회원가입실패!
		
		//msg.jsp로 알람메세지를 출력하고 메인으로 이동
		String loc="/";
		String msg=result>0?"회원가입성공":"회원가입실패";
		
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
	
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
