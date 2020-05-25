package com.shoppingmall.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shoppingmall.user.service.UserBoardService;
import com.shoppingmall.user.vo.UserBoardReplyVO;
import com.shoppingmall.user.vo.UserBoardVO;

@Service("UserBoardService")
public class UserBoardServiceImpl implements UserBoardService {
	
	@Resource(name="userBoardDAO")
    private UserBoardDAO userBoardDAO;
	
	// 게시글 목록 
	@Override
	public List<UserBoardVO> getUserBoardContent(Map<String, Object> paramMap) {
		return userBoardDAO.getUserBoardContent(paramMap);
	}
	
	// 게시글 갯수 카운트
	@Override
	public int getUserBoardCnt(Map<String, Object> paramMap) {
		return userBoardDAO.getUserBoardCnt(paramMap);
	}
	// 게시글 상세보기
	@Override
	public UserBoardVO getUserBoardCotent(Map<String, Object> paramMap) {
		return userBoardDAO.getUserBoardCotent(paramMap);
	}
	// 게시글 등록,수정
	@Override
	public int userBoardRegContent(Map<String, Object> paramMap) {
		// 아이디가없으면 입력
		if(paramMap.get("id") == null) {
			return userBoardDAO.userBoardRegContent(paramMap);
		}else {
			 return userBoardDAO.userBoardModifyContent(paramMap);
		}
		
	}

	@Override
	public int userBoardContentDel(Map<String, Object> paramMap) {
		return userBoardDAO.userBoardContentDel(paramMap);
	}

	@Override
	public int userBoardPasswordCheck(Map<String, Object> paramMap) {
		return userBoardDAO.userBoardPasswordCheck(paramMap);
	}

	@Override
	public int userBoardRegReply(Map<String, Object> paramMap) {
		return userBoardDAO.userBoardRegReply(paramMap);
	}

	@Override
	public int boardReplyDel(Map<String, Object> paramMap) {
		return userBoardDAO.boardReplyDel(paramMap);
	}

	@Override
	public List<UserBoardReplyVO> getUserBoardReplyList(Map<String, Object> paramMap) {
		List<UserBoardReplyVO> boardReplyList = userBoardDAO.getUserBoardReplyList(paramMap);
		
		// 부모(원본)댓글
		List<UserBoardReplyVO> boardReplyListParent = new ArrayList<UserBoardReplyVO>();
		// 자식(하위)댓글
		List<UserBoardReplyVO> boardReplyListChild = new ArrayList<UserBoardReplyVO>();
		// 원본,하위 댓글 통합
		List<UserBoardReplyVO> newBoardReplyList = new ArrayList<UserBoardReplyVO>();
		
		// 1. 부모(원본),자식(하위)댓글 분리
		for(UserBoardReplyVO UserBoardReply : boardReplyList) {
			
			if(UserBoardReply.getDepth().equals("0")){
                boardReplyListParent.add(UserBoardReply);
            }else{
                boardReplyListChild.add(UserBoardReply);
            }
		}
		
		 //2.부모(원본)댓글 을 돌린다.
        for(UserBoardReplyVO boardReplyParent: boardReplyListParent){
            //2-1. 부모는 무조건 넣는다.
            newBoardReplyList.add(boardReplyParent);
            //3.자식을 돌린다.
            for(UserBoardReplyVO boardReplyChild: boardReplyListChild){
                //3-1. 부모의 자식인 것들만 넣는다.
                if(boardReplyParent.getReply_id().equals(boardReplyChild.getParent_id())){
                    newBoardReplyList.add(boardReplyChild);
                }
 
            }
 
        }
        //정리한 list return
        return newBoardReplyList;
	}

	@Override
	public boolean userBoardReplyPasswordCheck(Map<String, Object> paramMap) {
		return userBoardDAO.userBoardReplyPasswordCheck(paramMap);
	}

	@Override
	public boolean userBoardReplyUpdate(Map<String, Object> paramMap) {
		return userBoardDAO.userBoardReplyUpdate(paramMap);
	}
	
	

}
