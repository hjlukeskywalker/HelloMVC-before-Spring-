package com.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.model.service.BoardService;
import com.board.model.vo.Board;
import com.board.model.vo.BoardComment;

/**
 * Servlet implementation class BoardViewServlet
 */
@WebServlet("/board/boardView")
public class BoardViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int boardNo=Integer.parseInt(request.getParameter("no"));
		
		//Cookie에 저장하는 값
		//boadRead를 키로 읽은 게시글의 번호를 저장
		Cookie[] cookies=request.getCookies();
		String boardRead="";
		boolean readFlag=false;//안읽음
		if(cookies!=null) {
			for(Cookie c: cookies) {
				String name=c.getName();//key값 가져오기
				String value=c.getValue();//value값 가져오기
				if(name.equals("boardRead")) {
					boardRead=value;
					if(value.contains("|"+boardNo+"|")) {
						readFlag=true;
						break;
					}
				}
			}
		}
		
		if(!readFlag) {
			//안읽었다면 기존 boardRead값에 추가
			Cookie c=new Cookie("boardRead",boardRead+"|"+boardNo+"|");
			c.setMaxAge(60*60*24);
			response.addCookie(c);
		}
		
		Board b=new BoardService().selectBoard(boardNo,readFlag);
		
		//댓글도 가져오자
		List<BoardComment> comments=new BoardService().selectBoardComment(boardNo);
		
		request.setAttribute("comments",comments);
		
		request.setAttribute("board", b);
		
		request.getRequestDispatcher("/views/board/boardView.jsp")
		.forward(request,response);
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
