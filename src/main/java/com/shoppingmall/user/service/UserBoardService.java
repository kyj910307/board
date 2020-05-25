package com.shoppingmall.user.service;

import java.util.List;
import java.util.Map;

import com.shoppingmall.user.vo.UserBoardReplyVO;
import com.shoppingmall.user.vo.UserBoardVO;

public interface UserBoardService {
	
	// �Խñ� ��� 
	List<UserBoardVO> getUserBoardContent(Map<String,Object> paramMap);
	
	// �Խñ� ���� ī��Ʈ
	int getUserBoardCnt(Map<String,Object> paramMap);
	
	// �Խñ� �󼼺���
	UserBoardVO getUserBoardCotent(Map<String,Object> paramMap);
	
	// �Խñ� ���,����
	int userBoardRegContent(Map<String,Object> paramMap); 
	
	// �Խñ� ����
	int userBoardContentDel(Map<String,Object> paramMap);
	
	// �Խñ� ��й�ȣ Ȯ��
	int userBoardPasswordCheck(Map<String,Object> paramMap);
	
	// ��� ���
	int userBoardRegReply(Map<String,Object>paramMap);

	// ��ۻ���
	int boardReplyDel(Map<String,Object>paramMap);
	
	// ��� ���
	List<UserBoardReplyVO>getUserBoardReplyList(Map<String,Object>paramMap);
	
	// ��� ��й�ȣ Ȯ��
	boolean userBoardReplyPasswordCheck(Map<String,Object>paramMap);
	
	// ��� ����
	boolean userBoardReplyUpdate(Map<String,Object>paramMap);
}
