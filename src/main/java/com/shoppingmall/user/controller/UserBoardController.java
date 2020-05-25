package com.shoppingmall.user.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shoppingmall.user.service.UserBoardService;

@Controller
public class UserBoardController {
	@Autowired
    UserBoardService UserBoardService;
	
	// �Խñ� ��� ��ȸ
	@RequestMapping(value ="/user/board/list")
	public String userBoardList(@RequestParam Map<String, Object> paramMap, Model model) {
		
		// ��ȸ �Ϸ��� ������
        int startPage = (paramMap.get("startPage")!=null?Integer.parseInt(paramMap.get("startPage").toString()):1);
        // ���������� ������ ����Ʈ ��
        int visiblePages = (paramMap.get("visiblePages")!=null?Integer.parseInt(paramMap.get("visiblePages").toString()):10);
        // ��ü �Ǽ��� �����´�.
        int totalCnt = UserBoardService.getUserBoardCnt(paramMap);
        
        //1.�ϴ� ������ �׺���̼ǿ��� ������ ����Ʈ ���� ���Ѵ�.
        BigDecimal decimal1 = new BigDecimal(totalCnt);
        BigDecimal decimal2 = new BigDecimal(visiblePages);
        BigDecimal totalPage = decimal1.divide(decimal2, 0, BigDecimal.ROUND_UP);
        
        // 2.�ִ� ������ ���ϱ� ���� ���
        int startLimitPage = 0;
        
        if(startPage==1){
            startLimitPage = 0;
        }else{
            startLimitPage = (startPage-1)*visiblePages;
        }
 
        paramMap.put("start", startLimitPage);
        paramMap.put("end", startLimitPage+visiblePages);
        
        //jsp ���� ������ ���� ����	
        model.addAttribute("startPage", startPage+"");//���� ������      
        model.addAttribute("totalCnt", totalCnt);//��ü �Խù���
        model.addAttribute("totalPage", totalPage);//������ �׺���̼ǿ� ������ ����Ʈ ��
        model.addAttribute("boardList", UserBoardService.getUserBoardContent(paramMap));//�˻�
        
        return "user/board/userBoardList";
        
	}
	
	//�Խñ� �� ����
    @RequestMapping(value = "/user/board/view")
    public String userBoardView(@RequestParam Map<String, Object> paramMap, Model model) {
    	
    	model.addAttribute("replyList", UserBoardService.getUserBoardReplyList(paramMap));
        model.addAttribute("boardView", UserBoardService.getUserBoardCotent(paramMap));
 
        return "user/board/userBoardView";
    }
    
    //�Խñ� ��� �� ����
    @RequestMapping(value = "/user/board/edit")
    public String userBoardEdit(HttpServletRequest request, @RequestParam Map<String, Object> paramMap, Model model) {
 
        //Referer �˻�
        String Referer = request.getHeader("referer");
 
        if(Referer!=null){//URL�� ���� ���� �Ұ�
            if(paramMap.get("id") != null){ //�Խñ� ����
                if(Referer.indexOf("/user/board/view")>-1){
 
                    //������ �����´�.
                    model.addAttribute("boardView", UserBoardService.getUserBoardCotent(paramMap));
                    return "user/board/userBoardEdit";
                }else{
                    return "redirect:/user/board/list";
                }
            }else{ //�Խñ� ���
                if(Referer.indexOf("/user/board/list")>-1){
                    return "user/board/userBoardEdit";
                }else{
                    return "redirect:/user/board/list";
                }
            }
        }else{
            return "redirect:/user/board/list";
        }
 
    }
    
    //AJAX ȣ�� (�Խñ� ���, ����)
    @RequestMapping(value="/user/board/contentSave", method=RequestMethod.POST)
    @ResponseBody
    public Object boardSave(@RequestParam Map<String, Object> paramMap) {
 
        //���ϰ�
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //�н����� ��ȣȭ
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String password = encoder.encodePassword(paramMap.get("password").toString(), null);
        paramMap.put("password", password);
 
        //�����Է�
        int result = UserBoardService.userBoardRegContent(paramMap);
 
        if(result>0){
            retVal.put("code", "OK");
            retVal.put("message", "��Ͽ� ���� �Ͽ����ϴ�.");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "��Ͽ� ���� �Ͽ����ϴ�.");
        }
 
        return retVal;
    }
    
    //AJAX ȣ�� (�Խñ� ����)
    @RequestMapping(value="/user/board/delete", method=RequestMethod.POST)
    @ResponseBody
    public Object userBoardContentDel(@RequestParam Map<String, Object> paramMap) {
 
        //���ϰ�
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //�н����� ��ȣȭ
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String password = encoder.encodePassword(paramMap.get("password").toString(), null);
        paramMap.put("password", password);
 
        //�����Է�
        int result = UserBoardService.userBoardContentDel(paramMap);
 
        if(result>0){
            retVal.put("code", "OK");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "�н����带 Ȯ�����ּ���.");
        }
 
        return retVal;
 
    }
 
    //AJAX ȣ�� (�Խñ� �н����� Ȯ��)
    @RequestMapping(value="/user/board/check", method=RequestMethod.POST)
    @ResponseBody
    public Object userBoardPasswordCheck(@RequestParam Map<String, Object> paramMap) {
 
        //���ϰ�
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //�н����� ��ȣȭ
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String password = encoder.encodePassword(paramMap.get("password").toString(), null);
        paramMap.put("password", password);
 
        //�����Է�
        int result = UserBoardService.userBoardPasswordCheck(paramMap);
 
        if(result>0){
            retVal.put("code", "OK");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "�н����带 Ȯ�����ּ���.");
        }
 
        return retVal;
 
    }
    
    //AJAX ȣ�� (��� ���)
    @RequestMapping(value="/user/board/reply/save", method=RequestMethod.POST)
    @ResponseBody
    public Object boardReplySave(@RequestParam Map<String, Object> paramMap) {
 
        //���ϰ�
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //�н����� ��ȣȭ
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String password = encoder.encodePassword(paramMap.get("reply_password").toString(), null);
        paramMap.put("reply_password", password);
 
        //�����Է�
        int result = UserBoardService.userBoardRegReply(paramMap);
 
        if(result>0){
            retVal.put("code", "OK");
            retVal.put("reply_id", paramMap.get("reply_id"));
            retVal.put("parent_id", paramMap.get("parent_id"));
            retVal.put("message", "��Ͽ� ���� �Ͽ����ϴ�.");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "��Ͽ� ���� �Ͽ����ϴ�.");
        }
 
        return retVal;
 
    }
 
    //AJAX ȣ�� (��� ����)
    @RequestMapping(value="/user/board/reply/del", method=RequestMethod.POST)
    @ResponseBody
    public Object boardReplyDel(@RequestParam Map<String, Object> paramMap) {
 
        //���ϰ�
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //�н����� ��ȣȭ
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String password = encoder.encodePassword(paramMap.get("reply_password").toString(), null);
        paramMap.put("reply_password", password);
 
        //�����Է�
        int result = UserBoardService.boardReplyDel(paramMap);
 
        if(result>0){
            retVal.put("code", "OK");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "������ �����߽��ϴ�. �н����带 Ȯ�����ּ���.");
        }
 
        return retVal;
 
    }
 
    //AJAX ȣ�� (��� �н����� Ȯ��)
    @RequestMapping(value="/user/board/reply/check", method=RequestMethod.POST)
    @ResponseBody
    public Object boardReplyCheck(@RequestParam Map<String, Object> paramMap) {
 
        //���ϰ�
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //�н����� ��ȣȭ
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String password = encoder.encodePassword(paramMap.get("reply_password").toString(), null);
        paramMap.put("reply_password", password);
 
        //�����Է�
        boolean check = UserBoardService.userBoardReplyPasswordCheck(paramMap);
 
        if(check){
            retVal.put("code", "OK");
            retVal.put("reply_id", paramMap.get("reply_id"));
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "�н����带 Ȯ���� �ּ���.");
        }
 
        return retVal;
 
    }
 
    //AJAX ȣ�� (��� ����)
    @RequestMapping(value="/user/board/reply/update", method=RequestMethod.POST)
    @ResponseBody
    public Object boardReplyUpdate(@RequestParam Map<String, Object> paramMap) {
 
        //���ϰ�
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //�н����� ��ȣȭ
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String password = encoder.encodePassword(paramMap.get("reply_password").toString(), null);
        paramMap.put("reply_password", password);
 
        System.out.println(paramMap);
 
        //�����Է�
        boolean check = UserBoardService.userBoardReplyUpdate(paramMap);
 
        if(check){
            retVal.put("code", "OK");
            retVal.put("reply_id", paramMap.get("reply_id"));
            retVal.put("message", "������ ���� �Ͽ����ϴ�.");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "������ ���� �Ͽ����ϴ�.");
        }
 
        return retVal;
 
    }
}
