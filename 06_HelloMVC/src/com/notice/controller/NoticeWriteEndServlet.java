package com.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.notice.model.service.NoticeService;
import com.notice.model.vo.Notice;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class NoticeWriteEndServlet
 */
@WebServlet("/notice/noticeWriteEnd")
public class NoticeWriteEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeWriteEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//파일업로드 처리하기
		//파일 업로드처리시에는 cos.jar 라이브러리에서 제공하는 클래스를 이용함.
		//1.클라이언트가 보낸 데이터가 multipart형식인지 확인하기
		if(!ServletFileUpload.isMultipartContent(request)) {
			//잘못된 요청이기때문에 
			request.setAttribute("msg", "공지사항 작성오류 [form:enctype] 관리자에게 문의하세요 :(");
			request.setAttribute("loc", "/notice/noticeList");
			request.getRequestDispatcher("/views/common/msg.jsp")
			.forward(request,response);
			return;
		}
		
		//2.파일 업로드처리를 위한 필요한 값을 설정하기
		//1) 파일업로드 위치 -> 절대경로로 가져와야함.
		String path=request.getServletContext().getRealPath("/upload/notice/");
		System.out.println("경로 : "+path);
		//2) 저장할 파일에 대한 최대크기 설정
		int maxSize=1024*1024*10;//10MB
		//3) 문자열 인코딩
		String encode="utf-8";
		//4) 업로드된 파일에 대한 이름 재정의(rename)
		// 개발자가 직접 작성할 수도 있고, 기본으로 제공하는 클래스가 있음(DefaultFileRenamePolicy)
		
		//파일업로드하기
		//MultipartRequest클래스를 생성 -> request로 전송된 데이터가 지정한 경로에 저장
		//MultipartRequest클래스생성자는 5개의 매개변수를 가지고 있음
		//1. HttpServletRequest, 2. 파일경로,3. 파일최대크기, 4. 인코딩, 5.rename규칙
		MultipartRequest mr=new MultipartRequest(request,path,maxSize,
				encode,new DefaultFileRenamePolicy());
		
		
		Notice n=new Notice();
		n.setNoticeTitle(mr.getParameter("noticeTitle"));
		n.setNoticeWriter(mr.getParameter("noticeWrite"));
		n.setNoticeContent(mr.getParameter("content"));
		//파일명을 DB에 저장해야함. -> rename된 파일을 가져오기		
		//n.setFilePath(mr.getParameter("up_file"));
		n.setFilePath(mr.getFilesystemName("up_file"));
		System.out.println(mr.getFilesystemName("up_file2"));
		int result=new NoticeService().insertNotice(n);
		
		//등록성공하면 등록성공 메세지출력 후 리스트화면으로 이동
		//등록실패하면 등록실패 메세지출력 후 등록화면으로 이동
		String msg="";
		String loc="";
		if(result>0) {
			msg="공지사항등록 성공";
			loc="/notice/noticeList";
		}else {
			msg="공지사항등록 실패";
			loc="/notice/noticeForm";
		}
		request.setAttribute("msg",msg);
		request.setAttribute("loc",loc);
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
