package com.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.admin.model.service.AdminService;
import com.common.AESCryptor;
import com.member.model.vo.Member;

/**
 * Servlet implementation class MemberListServlet
 */
@WebServlet("/admin/memberList")
public class MemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//클릭시 전체 회원에 대한 정보를 조회해서 가져옴
		
		//관리자가 아닌 사용자가 접근했을때 예외처리하기
		HttpSession session=request.getSession(false);
		Member loginMember=(Member)session.getAttribute("loginMember");
		if(session==null || loginMember==null||!loginMember.getUserId().equals("admin")) {
			// 잘못된 경로로 접근하셨습니다. 이페이지 사용권한이 없습니다.
			request.setAttribute("msg", "잘못되 경로로 접근하셨습니다. 이페이지 사용권한이 없습니다!");
			request.setAttribute("loc", "/");
			RequestDispatcher rd=request.getRequestDispatcher("/views/common/msg.jsp");
			
			rd.forward(request, response);
		}else {
			//데이터를 가져올때 원하는 구역(page) 가져오기!
			//1. 사용자가 원하는 page -> 현재페이지
			int cPage;
			try {
				cPage=Integer.parseInt(request.getParameter("cPage"));
			}catch(NumberFormatException e) {
				cPage=1;
			}
			
//			if(request.getParameter("cPage")!=null) {
//				cPage=Integer.parseInt(request.getParameter("cPage"));
//			}else {
//				cPage=1;
//			}
			
			
			
			//2. 페이지당 데이터수 -> 사용자가 설정
			int numPerpage;
			try {
				numPerpage=Integer.parseInt(request.getParameter("numPerpage"));
			}catch(NumberFormatException e) {
				numPerpage=5;
			}
			
			List<Member> list=new AdminService().memberList(cPage,numPerpage);
		
			for(Member m : list) {
				try {
					m.setPhone(AESCryptor.decrypt(m.getPhone()));
					m.setEmail(AESCryptor.decrypt(m.getEmail()));
				}catch(Exception e) {
					
				}
			}
			
			//사용자가 원하는 페이지를 호출할 수 있게 pageBar를 구성해보자!
			//1. 전체페이지에 대한 수
			//전체자료에서 페이지당 수 나누면 됨. 5.5 -> 무조건 올림 처리함.
			int totalData=new AdminService().selectMemberCount();
			int totalPage=(int)Math.ceil((double)totalData/numPerpage);
			System.out.println(totalData);
			System.out.println(totalPage);
			
			//pageBar구성하기 -> html방식으로
			int pageBarSize=4;//pageBar에 출력된 페이지숫자 갯수
			//pageNo는 pageBar에 출력되는 페이지숫자의 시작값
			//cPage 2 pageNo 1
			//cPage 3 pageNo 1
			//cPage 4 pageNo 1
			//cPage 5 pageNo 5
			//cPage 6 pageNo 5
			//cPage 7 pageNo 5
			//cPage 9 pageNo 9
			int pageNo=((cPage-1)/pageBarSize)*pageBarSize+1;
			//pageNo 1 pageEnd 4
			//pageNo 5 pageEnd 8
			//pageNo 9 pageEnd 12
			int pageEnd=pageNo+pageBarSize-1;
			
			//사용자가 클릭할수있는 페이지바를 구성해보자
			String pageBar="";
			if(pageNo==1) {
				pageBar+="<span>[이전]</span>";
			}else {
				pageBar+="<a href='"+request.getContextPath()
				+"/admin/memberList?cPage="+(pageNo-1)
				+"&numPerpage="+numPerpage+"'>[이전]</a>";
			}
			//while(pageNo<=pageEnd&&pageNo<=totalPage) {
			while(!(pageNo>pageEnd||pageNo>totalPage)) {
				if(cPage==pageNo) {
					pageBar+="<span style='background-color:magenta;'>"+pageNo+"</span>";
				}else {
					pageBar+="<a href='"+request.getContextPath()
					+"/admin/memberList?cPage="+pageNo
					+"&numPerpage="+numPerpage+"'>"+pageNo+"</a>";
				}
				pageNo++;
			}
			
			if(pageNo>totalPage) {
				pageBar+="<span>[다음]</span>";
			}else {
				pageBar+="<a href='"+request.getContextPath()
				+"/admin/memberList?cPage="+pageNo
				+"&numPerpage="+numPerpage+"'>[다음]</a>";
			}
			
			request.setAttribute("pageBar",pageBar);		
			
			
			//가져온값을 jsp페이지에 전달
			request.setAttribute("members", list);
			
			request.getRequestDispatcher("/views/admin/memberList.jsp")
			.forward(request, response);
		}
	
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
