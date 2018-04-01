package com.med.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.logging.log4j.web.Log4jServletContextListener;
import org.apache.logging.log4j.web.Log4jServletFilter;
import org.springframework.web.WebApplicationInitializer;

import com.med.entity.Privilege;
import com.med.web.LoginFilter;
import com.med.web.PrivilegeFilter;

public class MyFilterInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// TODO 自动生成的方法存根
		System.out.println("Enter MyFilterInitialzer");
		
		// 添加log4j监听器
		servletContext.setInitParameter("log4jConfigLocation", 
				"classpath:log4j2.xml");
		servletContext.addListener(Log4jServletContextListener.class);

		// 添加log4j过滤器
		FilterRegistration.Dynamic log4jServletFilter = 
				servletContext.addFilter("Log4jServletFilter", 
						Log4jServletFilter.class);
		log4jServletFilter.addMappingForUrlPatterns(null, false, "/*");
		
		// 添加登录过滤器
		FilterRegistration.Dynamic loginFilter = 
				servletContext.addFilter("LoginFilter", 
						LoginFilter.class);
		loginFilter.addMappingForUrlPatterns(null, false, "*.do");
		
		// 权限过滤器
		FilterRegistration.Dynamic privilegeFilter = 
				servletContext.addFilter("PrivilegeFilter", 
						PrivilegeFilter.class);
		privilegeFilter.addMappingForUrlPatterns(null, false, "*.do");
	}
}
