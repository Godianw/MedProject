package com.med.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.med.util.DataEncapUtil;
import com.mysql.cj.api.Session;

/**
 * 登录过滤器
 * @author Runtime
 * @time 2018/3/20
 * @version v1.0
 */
public class LoginFilter implements Filter {

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
		
		String[] doNotFilterUrl = {"index.do", "login.do"};
		
		boolean notFilter = false;
		// 除去不过滤的url
		for (String url : doNotFilterUrl) {
			if (path.indexOf(url) > -1) {
				notFilter = true;
				break;
			}
		}
		
		// 如果是登录页面则不过滤
		if (notFilter || session.getAttribute("user") != null) {
			filterChain.doFilter(request, response);
			return ;
		} else {
			String redirectUrl = "/med/index.do";
			// 判断是否为ajax请求
			if (DataEncapUtil.isAjaxRequest(request, response,
					redirectUrl)) {
				filterChain.doFilter(request, response);
				return ;
			}
			
			response.sendRedirect(redirectUrl);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO 自动生成的方法存根
		
	}
}
