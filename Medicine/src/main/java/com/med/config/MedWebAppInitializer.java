package com.med.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/* 
 * 在Servlet3.0以上的环境中，容器会在类路径中查找实现
 * javax.servlet.ServletContainerInitializer接口的类，如果发现则用它来配置 
 * Servlet容器，Spring提供了这个接口的实现名为
 * SpringServletContainerInitializer,这个类反过来又会查找实现了 
 * WebApplicationInitializer的类并把配置任务交给它们来完成,
 * AbstractAnnotationConfigDispatcherServletInitializer的祖先类已 
 * 对该接口进行了实现 
 */ 
//该类拓展了AbstractAnnotationConfigDispatcherServletInitializer
//容器会自动发现该类，并配置DispatcherServlet和Spring应用上下文
public class MedWebAppInitializer 
	extends AbstractAnnotationConfigDispatcherServletInitializer {
	
/*	@Override
	public void onStartup(ServletContext servletContext) 
			throws ServletException {
		// TODO 自动生成的方法存根
		/** 
         * 添加编码过滤 
         */ 
	/*	FilterRegistration.Dynamic characterEncodingFilter = 
    			servletContext.addFilter("encodingFilter",  
                new CharacterEncodingFilter());  
        characterEncodingFilter.setInitParameter("encoding", "UTF-8");  
        characterEncodingFilter.setInitParameter("forceEncoding", "true");  
        characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");  
		
		/**
         * log4j2
         */
        // 添加log4j监听器
    /*    servletContext.setInitParameter("log4jConfigLocation", 
        		"classpath:log4j2.properties");
        servletContext.addListener(Log4jServletContextListener.class);
        
        // 添加log4j过滤器
        FilterRegistration.Dynamic log4jFilter = 
        		servletContext.addFilter("log4jServletFilter", 
        				new Log4jServletFilter());
        log4jFilter.addMappingForUrlPatterns(null, false, "/*");
	}*/
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// 使用RootConfig.class配置类来配置ContextLoaderListener创建的应用上下文的bean
		return new Class<?>[] { RootConfig.class };
	}

	// 指定DispatcherServlet配置类
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// 使用定义在WebConfig.class配置类中的bean加载serlvet应用上下文
		return new Class<?>[] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		
		return new String[] { "*.do" };
	}
	
	/* 
     * 注册过滤器，映射路径与DispatcherServlet一致，
     * 路径不一致的过滤器需要注册到另外的WebApplicationInitializer中 
     */ 
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = 
				new CharacterEncodingFilter();  
        characterEncodingFilter.setEncoding("UTF-8");  
        characterEncodingFilter.setForceEncoding(true);  
        
        return new Filter[] { characterEncodingFilter };  
	}

}
