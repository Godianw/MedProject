package com.med.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.med.entity.DTRequestParam;
import com.med.entity.User;
import com.med.service.UserService;
import com.med.util.DataEncapUtil;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService userService;
	
	/**
	 * 进入用户信息管理界面
	 * @return
	 */
	@RequestMapping("/info.do")
	public String userInfo() {
		return "user";
	}
	
	/**
	 * 获取用户表数据源
	 * @param dtParams:封装了DataTables请求的内容
	 * @param request:servlet请求参数
	 * @return 封装了返回数据的map
	 */
	@RequestMapping("/data_source.do")
	@ResponseBody
	public Object userDataSource(
			@ModelAttribute DTRequestParam dtParams,
			HttpServletRequest request) {
		
		
	
		int recordsTotal = userService.findUsers(
				dtParams.getConditionSql()).size();
		int curIndex = dtParams.getCurPageStartIndex();
		List list = userService.findUsersByPaging(
				dtParams.getConditionSql(),
				(curIndex >= recordsTotal ? 0 : curIndex),
				dtParams.getLength());
		int pageTotal = DataEncapUtil.getPageTotal(
				recordsTotal, dtParams.getLength());
		
		// 生成返回表格的集合
		Map result = DataEncapUtil.createDTResponse(
				dtParams, recordsTotal, pageTotal, list);
		
		return result;
	}
	
	/**
	 * 保存用户
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/save.do")
	@ResponseBody
	public Object save(String userMap) {
		
		userService.saveUser(JSONObject.fromObject(userMap));
		Map resultMap = new HashMap<String, String>();
		resultMap.put("resultCode", "200");
		
		return resultMap;
	}
	
	/**
	 * 查找用户
	 * @param id
	 * @return
	 */
	@RequestMapping("/find.do")
	@ResponseBody
	public Object find(Integer id) {
		
		return userService.findUser(id);
	}
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/delete.do")
	@ResponseBody
	public Object delete(Integer id) {
		
		Map resultMap = new HashMap<String, String>();
		if (userService.delete(id))
			resultMap.put("resultCode", "200");
		
		return resultMap;
	}
	
	@RequestMapping("/find_name.do")
	@ResponseBody
	public Object findAllName() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resultCode", "200");
		resultMap.put("data", userService.findUserNameList());
		
		return resultMap;
	}
	
	@RequestMapping("/saveInfo.do")
	@ResponseBody
	public Object savePersionalInfo(User user, HttpSession session) {
		
		Map<String, String> resultMap = new HashMap<String, String>();
		User newUser = userService.savePersonalInfo(user);
		if (newUser != null) {
			resultMap.put("resultCode", "200");
			session.setAttribute("user", newUser);
		}
	
		return resultMap;
	}
}
