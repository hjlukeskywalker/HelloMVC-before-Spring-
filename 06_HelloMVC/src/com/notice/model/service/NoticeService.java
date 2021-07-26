package com.notice.model.service;

import static com.common.JDBCTemplate.close;
import static com.common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.List;

import com.notice.model.dao.NoticeDao;
import com.notice.model.vo.Notice;
import static com.common.JDBCTemplate.commit;
import static com.common.JDBCTemplate.rollback;

public class NoticeService {
	
	private NoticeDao dao=new NoticeDao();
	
	public List<Notice> selectNoticeList(int cPage, int numPerpage){
		Connection conn=getConnection();
		List<Notice> list=dao.selectNoticeList(conn,cPage,numPerpage);
		close(conn);
		return list;
	}
	
	public int selectNoticeCount() {
		Connection conn=getConnection();
		int result=dao.selectNoticeCount(conn);
		close(conn);
		return result;
	}
	
	
	public Notice selectNotice(int no) {
		Connection conn=getConnection();
		Notice n=dao.selectNotice(conn, no);
		close(conn);
		return n;
	}
	
	public int insertNotice(Notice n) {
		Connection conn=getConnection();
		int result=dao.insertNotice(conn,n);
		
		if(result>0) commit(conn);
		else rollback(conn);
		
		close(conn);
		return result;
	}
	
	
	public int noticeUpdate(Notice n) {
		Connection conn=getConnection();
		int result=dao.noticeUpdate(conn,n);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	
}







