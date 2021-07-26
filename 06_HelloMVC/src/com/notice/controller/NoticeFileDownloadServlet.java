package com.notice.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NoticeFileDownloadServlet
 */
@WebServlet("/notice/fileDownload")
public class NoticeFileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeFileDownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//파일 다운로드 서비스
		//1. 클라이언트가 보낸 파일명 가져오기
		//2. 파일명이랑 일치하는 파일 서버의 저장경로에서 가져오기 -> InputStream이용
		//3. 클라이언트에게 보낼 파일명 인코딩처리하기 -> 한글파일명이 깨지지않게
		//4. 응답 MIME타입설정하기 / header설정
		//5. 클라이언트와 연결되 스트림으로 파일 전송
		//6. 스트림닫기
		
		//파일명가져오기
		String fileName=request.getParameter("fname");
		
		//저장경로가져오기
		String path=getServletContext().getRealPath("/upload/notice/");
		String filePath=path+fileName;
		
		//파일경로에서 파일을 가져옴
		FileInputStream fis=new FileInputStream(filePath);
		BufferedInputStream bis=new BufferedInputStream(fis);
		
		//파일명에 대한 인코딩처리
		String fileRename="";
		String header=request.getHeader("user-agent");
		boolean isMSIE=header.indexOf("MSIE")!=-1||header.indexOf("Trident")!=-1;
		if(isMSIE) {
			fileRename=URLEncoder.encode(fileName,"utf-8").replaceAll("\\+", "%20");
		}else {
			fileRename=new String(fileName.getBytes("utf-8"),"ISO_8859_1");
		}
		
		//응답메세지작성
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment;filename="+fileRename);
		
		//클라이언트에게 보낼 스트림 열기
		ServletOutputStream sos=response.getOutputStream();
		BufferedOutputStream bos=new BufferedOutputStream(sos);
		
		//파일전송하기!
		int read=-1;//아무의미 없는 값
		while((read=bis.read())!=-1) {
			bos.write(read);
		}
		bis.close();
		bos.close();
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
