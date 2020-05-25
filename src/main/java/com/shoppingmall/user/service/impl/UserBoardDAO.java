package com.shoppingmall.user.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoppingmall.user.vo.UserBoardReplyVO;
import com.shoppingmall.user.vo.UserBoardVO;

@Repository("userBoardDAO")
public class UserBoardDAO {
	
	@Autowired
    private SqlSession sqlSession;
	
	/*
	 * public void setSqlSession(SqlSession sqlSession){ this.sqlSession =
	 * sqlSession; }
	 */
	
	public List<UserBoardVO> getUserBoardContent(Map<String, Object> paramMap) {
        return sqlSession.selectList("SelectUserBoardContent", paramMap);
    }
	
    public int getUserBoardCnt(Map<String, Object> paramMap) {
        return sqlSession.selectOne("SelectUserBoardCnt", paramMap);
    }
    
    public UserBoardVO getUserBoardCotent(Map<String,Object> paramMap) {
    	return sqlSession.selectOne("SelectUserBoardGetContent",paramMap);
    }
    
    public int userBoardRegContent(Map<String, Object> paramMap) {
    	return sqlSession.insert("insertUserBoardContent",paramMap);
    }
    
    public int userBoardModifyContent(Map<String, Object> paramMap) {
        return sqlSession.update("updateUserBoardContent", paramMap);
    }
    
	public int userBoardContentDel(Map<String, Object> paramMap) {
		return sqlSession.delete("userBoardContentDel",paramMap);
	}

	public int userBoardPasswordCheck(Map<String, Object> paramMap) {
		return sqlSession.selectOne("userBoardPasswordCheck",paramMap);
	}
	
	
	public int userBoardRegReply(Map<String, Object> paramMap) {
		return sqlSession.insert("userBoardRegReply",paramMap);
	}

	public int boardReplyDel(Map<String, Object> paramMap) {
		
		if(paramMap.get("r_type").equals("main")) {
            //부모부터 하위 다 지움
            return sqlSession.delete("boardReplyDelAll", paramMap);
        }else {
            //자기 자신만 지움
            return sqlSession.delete("boardReplyDel", paramMap);
        }
	}
	
	public List<UserBoardReplyVO> getUserBoardReplyList(Map<String, Object> paramMap) {
		
        return sqlSession.selectList("getUserBoardReplyList",paramMap);
	}

	public boolean userBoardReplyPasswordCheck(Map<String, Object> paramMap) {
		int result = sqlSession.selectOne("userBoardReplyPasswordCheck",paramMap);
		
		if(result > 0) {
			return true;
		}else {
			return false;
		}
	}

	public boolean userBoardReplyUpdate(Map<String, Object> paramMap) {
		int result = sqlSession.update("userBoardReplyUpdate",paramMap);
		
		if(result > 0) {
			return true;
		}else {
			return false;
		}
	}
 
}
