package com.shoppingmall.user.service;

import java.util.List;
import java.util.Map;

import com.shoppingmall.user.vo.UserBoardReplyVO;
import com.shoppingmall.user.vo.UserBoardVO;

public interface UserBoardService {
	
	// 게시글 목록 
	List<UserBoardVO> getUserBoardContent(Map<String,Object> paramMap);
	
	// 게시글 갯수 카운트
	int getUserBoardCnt(Map<String,Object> paramMap);
	
	// 게시글 상세보기
	UserBoardVO getUserBoardCotent(Map<String,Object> paramMap);
	
	// 게시글 등록,수정
	int userBoardRegContent(Map<String,Object> paramMap); 
	
	// 게시글 삭제
	int userBoardContentDel(Map<String,Object> paramMap);
	
	// 게시글 비밀번호 확인
	int userBoardPasswordCheck(Map<String,Object> paramMap);
	
	// 댓글 등록
	int userBoardRegReply(Map<String,Object>paramMap);

	// 댓글삭제
	int boardReplyDel(Map<String,Object>paramMap);
	
	// 댓글 목록
	List<UserBoardReplyVO>getUserBoardReplyList(Map<String,Object>paramMap);
	
	// 댓글 비밀번호 확인
	boolean userBoardReplyPasswordCheck(Map<String,Object>paramMap);
	
	// 댓글 수정
	boolean userBoardReplyUpdate(Map<String,Object>paramMap);
}
