package com.med.web;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.med.entity.Role;
import com.med.entity.User;
import com.med.service.RoleService;
import com.med.util.DataEncapUtil;


/**
 * 权限过滤器
 * @author Runtime
 * @time 2018/3/27
 * @version v1.0
 */
public class PrivilegeFilter implements Filter {

	RoleService roleService;
	
	@Override
	public void destroy() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain filterChain) throws IOException, ServletException {
		// TODO 自动生成的方法存根
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = request.getSession();
		
		// 获得用户请求的url
		String path = request.getRequestURI();
		
		String[] doNotFilterUrl = {"/index/", "login.do", 
				"logout.do", "index.do", "data_source.do", 
				"find_quantity.do", "find_name.do", 
				"get_privtree_data.do", "personalInfo.do",
				"saveInfo.do", "downloadTemplate.do"};
		
		String[] doFilterUrl = {"/inventory/", "/inventoryCount/",
				"/inventory_history/", "/medicine/", "/role/",
				"/sales/", "/salesCount/", "/staff/", "/supplier/",
				"/user/"};
		
		boolean notFilter = false;
		/* 除去不过滤的url */
		for (String url : doNotFilterUrl) {
			if (path.indexOf(url) > -1) {
				notFilter = true;
				break;
			}
		}
		
		if (!notFilter) {
			notFilter = true;
			for (String url : doFilterUrl) {
				if (path.indexOf(url) > -1) {
					notFilter = false;
					break;
				}
			}
		}
		
		if (notFilter || session.getAttribute("user") == null) {
			filterChain.doFilter(request, response);
			return ;
		} else {
			User user = (User) session.getAttribute("user");
			Set<Role> roles = user.getRoles();
			if (!roles.isEmpty()) {
				Iterator<Role> iterator = roles.iterator();
				while (iterator.hasNext()) {
					Role role = (Role) iterator.next();
					List<Map<String, Object>> list 
						= roleService.findRolePrivileges(role.getId());
					for (Map<String, Object> privilege : list) {
						if (privilege.get("url") == null) {
							continue;
						}
						
						String url = "/med/" + 
								privilege.get("url").toString();
						// 找到该权限
						if (path.equals(url)) {
							filterChain.doFilter(request, response);
							return ;
						}
					}
				}
				
				/* 找不到相关权限则重定向页面 */
				// 判断是否为ajax请求
				String redirectUrl = "/med/err403.do";
				if (DataEncapUtil.isAjaxRequest(request, response,
						redirectUrl)) {
					filterChain.doFilter(request, response);
					return ;
				}
				
				response.sendRedirect(redirectUrl);
			}
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 手动注入bean
		ServletContext servletContext = filterConfig.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		roleService = (RoleService) ctx.getBean("roleService");
	}
	
	
}
