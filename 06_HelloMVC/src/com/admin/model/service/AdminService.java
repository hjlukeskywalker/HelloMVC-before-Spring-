package com.admin.model.service;

import static com.common.JDBCTemplate.close;
import static com.common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.List;

import com.admin.model.dao.AdminDao;
import com.member.model.vo.Member;

public class AdminService {

	private AdminDao dao=new AdminDao();
	
	public List<Member> memberList(int cPage, int numPerpage){
		Connection conn=getConnection();
		List<Member> list=dao.memberList(conn,cPage,numPerpage);
		close(conn);
		return list;
	}
	
	public int selectMemberCount() {
		Connection conn=getConnection();
		int result=dao.selectMemberCount(conn);
		close(conn);
		return result;
	}
	
	
	public List<Member> searchMember(String searchType, String keyword, int cPage, int numPerpage){
		Connection conn=getConnection();
		List<Member> list=dao.searchMember(conn,searchType,keyword,cPage,numPerpage);
		close(conn);
		return list;
	}
	
	public int searchMemberCount(String searchType, String keyword) {
		Connection conn=getConnection();
		int result=dao.searchMemberCount(conn,searchType,keyword);
		close(conn);
		return result;
	}
}









