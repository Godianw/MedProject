package com.med.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.med.entity.DTRequestParam;
import com.med.entity.Role;
import com.med.service.RoleService;
import com.med.util.DataEncapUtil;

@Controller
@RequestMapping("role")
public class RoleController {

	@Autowired
	RoleService roleService;
	
	/**
	 * 进入角色信息管理页面
	 * @return 视图名
	 */
	@RequestMapping("/info.do")
	public String roleInfo() {
		return "role";
	}
	
	/**
	 * 获取角色表数据
	 * @param dtParams:封装了DataTables请求的内容
	 * @param request:servlet请求参数
	 * @return 封装了返回数据的map
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/data_source.do")
	@ResponseBody
	public Object roleDataSource(
			@ModelAttribute DTRequestParam dtParams,
			HttpServletRequest request) {
		
		int recordsTotal = roleService.findRoles(
				dtParams.getConditionSql()).size();
		int curIndex = dtParams.getCurPageStartIndex();
		List list = roleService.findRolesByPaging(
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
	 * 保存角色信息
	 * @param role
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/save.do")
	@ResponseBody
	public Object save(Role role) {
		
		roleService.saveRole(role);
		Map resultMap = new HashMap<String, String>();
		resultMap.put("resultCode", "200");
		
		return resultMap;
	}
	
	/**
	 * 更新角色权限信息
	 * @param id
	 * @param privList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping("/save_priv.do")
	@ResponseBody
	public Object savePriv(Integer id, String privIdSets) {
		Map resultMap = null;
		
		if (roleService.saveRolePriv(id, privIdSets.split(","))) {
			resultMap = new HashMap<String, String>();
			resultMap.put("resultCode", "200");
		}
		
		return resultMap;
	}
	
	/**
	 * 查找角色信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/find.do")
	@ResponseBody
	public Object find(Integer id) {
		
		return roleService.findRole(id);
	}
	
	/**
	 * 查找角色权限信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/find_priv.do")
	@ResponseBody
	public Object findPriv(Integer id) {
		
		return roleService.findRolePrivileges(id);
	}
	
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/delete.do")
	@ResponseBody
	public Object delete(Integer id) {
		Map resultMap = null;
		
		if (roleService.deleteRole(id)) {
			resultMap = new HashMap<String, String>();
			resultMap.put("resultCode", "200");
		}

		return resultMap;
	}
	
	/**
	 * 查找角色名称
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/find_name.do")
	@ResponseBody
	public Object findName() {
		
		Map resultMap = new HashMap<String, String>();
		resultMap.put("resultCode", "200");
		resultMap.put("data", roleService.findRoleNameList());
		return resultMap;
	}
	
	@RequestMapping("/get_privtree_data.do")
	@ResponseBody
	public Object getPrivTreeData() {
		return roleService.getPrivTreeData();
	}
}
