package com.board.model.service;

import static com.common.JDBCTemplate.close;
import static com.common.JDBCTemplate.commit;
import static com.common.JDBCTemplate.getConnection;
import static com.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.board.model.dao.BoardDao;
import com.board.model.vo.Board;
import com.board.model.vo.BoardComment;

public class BoardService {
	
	private BoardDao dao=new BoardDao();
	
	public List<Board> selectBoardList(int cPage, int numPerpage){
		Connection conn=getConnection();
		List<Board> list=dao.selectBoardList(conn,cPage,numPerpage);
		close(conn);
		return list;
	}
	
	public int selectBoardCount() {
		Connection conn=getConnection();
		int result=dao.selectBoardCount(conn);
		close(conn);
		return result;
	}
	
	//UPDATE PRODUCT SET STOCK=STOCK+1 WHERE A112 
	public Board selectBoard(int boardNo,boolean readFlag) {
		Connection conn=getConnection();
		Board b=dao.selectBoard(conn,boardNo);
		if(!readFlag && b!=null) {
			//조회수 증가
			int result=dao.updateBoardReadCount(conn,boardNo);
			if(result>0) commit(conn);
			else rollback(conn);
		}
		close(conn);
		return b;
	}
	
	
	public int insertBoard(Board b) {
		Connection conn=getConnection();
		int result=dao.insertBoard(conn, b);
		if(result>0) {
			commit(conn);
			result=dao.selectSeqCur(conn);
		}
		else rollback(conn);
		close(conn);
		return result;
	}
	
	public int insertBoardComment(BoardComment bc) {
		Connection conn=getConnection();
		int result=dao.insertBoardComment(conn,bc);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	
	public List<BoardComment> selectBoardComment(int boardRef){
		Connection conn=getConnection();
		List<BoardComment> list=dao.selectBoardComment(conn,boardRef);
		close(conn);
		return list;
	}
	
}
