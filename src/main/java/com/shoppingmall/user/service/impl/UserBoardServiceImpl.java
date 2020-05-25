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
	
	// �Խñ� ��� 
	@Override
	public List<UserBoardVO> getUserBoardContent(Map<String, Object> paramMap) {
		return userBoardDAO.getUserBoardContent(paramMap);
	}
	
	// �Խñ� ���� ī��Ʈ
	@Override
	public int getUserBoardCnt(Map<String, Object> paramMap) {
		return userBoardDAO.getUserBoardCnt(paramMap);
	}
	// �Խñ� �󼼺���
	@Override
	public UserBoardVO getUserBoardCotent(Map<String, Object> paramMap) {
		return userBoardDAO.getUserBoardCotent(paramMap);
	}
	// �Խñ� ���,����
	@Override
	public int userBoardRegContent(Map<String, Object> paramMap) {
		// ���̵𰡾����� �Է�
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
		
		// �θ�(����)���
		List<UserBoardReplyVO> boardReplyListParent = new ArrayList<UserBoardReplyVO>();
		// �ڽ�(����)���
		List<UserBoardReplyVO> boardReplyListChild = new ArrayList<UserBoardReplyVO>();
		// ����,���� ��� ����
		List<UserBoardReplyVO> newBoardReplyList = new ArrayList<UserBoardReplyVO>();
		
		// 1. �θ�(����),�ڽ�(����)��� �и�
		for(UserBoardReplyVO UserBoardReply : boardReplyList) {
			
			if(UserBoardReply.getDepth().equals("0")){
                boardReplyListParent.add(UserBoardReply);
            }else{
                boardReplyListChild.add(UserBoardReply);
            }
		}
		
		 //2.�θ�(����)��� �� ������.
        for(UserBoardReplyVO boardReplyParent: boardReplyListParent){
            //2-1. �θ�� ������ �ִ´�.
            newBoardReplyList.add(boardReplyParent);
            //3.�ڽ��� ������.
            for(UserBoardReplyVO boardReplyChild: boardReplyListChild){
                //3-1. �θ��� �ڽ��� �͵鸸 �ִ´�.
                if(boardReplyParent.getReply_id().equals(boardReplyChild.getParent_id())){
                    newBoardReplyList.add(boardReplyChild);
                }
 
            }
 
        }
        //������ list return
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
