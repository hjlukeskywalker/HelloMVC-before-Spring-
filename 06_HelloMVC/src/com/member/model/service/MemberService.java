package com.member.model.service;

import static com.common.JDBCTemplate.getConnection;
import static com.common.JDBCTemplate.commit;
import static com.common.JDBCTemplate.rollback;

import java.sql.Connection;

import com.member.model.dao.MemberDao;
import com.member.model.vo.Member;
import static com.common.JDBCTemplate.close;
public class MemberService {
	
	private MemberDao dao=new MemberDao();
	
	
	public Member login(String userId, String pw) {
		Connection conn=getConnection();
		Member m=dao.login(conn,userId,pw);
		close(conn);
		return m;
	}
	
	public int insertMember(Member m)
	{
		Connection conn=getConnection();
		int result=dao.insertMember(conn,m);
		
		if(result>0) commit(conn);
		else rollback(conn);
		
		close(conn);		
		
		return result;
	}
	
	
	
	public Member checkDuplicateId(String userId) {
		Connection conn=getConnection();
		Member m=dao.checkDuplicateId(conn,userId);
		close(conn);
		return m;
	}
	
	public int updateMember(Member m) {
		Connection conn=getConnection();
		int result=dao.updateMember(conn,m);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	public int deleteMember(String userId) {
		Connection conn=getConnection();
		int result=dao.deleteMember(conn,userId);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	public int updatePassword(String userId, String newPw) {
		Connection conn=getConnection();
		int result=dao.updatePassword(conn,userId,newPw);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
}






