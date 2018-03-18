package com.med.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
@EnableWebMvc		// 启用注解驱动的SpringMVC
@ComponentScan("com.med")// 扫描指定包下的所有文件并注册bean到spring应用上下文中
//DispatcherServlet配置类
//常用来加载web组件
public class WebConfig extends WebMvcConfigurerAdapter {

	// 配置视图解析器
	@Bean
	public ViewResolver viewResolver(
			TemplateEngine templateEngine) {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine);
		viewResolver.setContentType("text/html; charset=utf-8");  
		return viewResolver;
	}

	@Bean
	public TemplateEngine templateEngine(
			ITemplateResolver templateResolver) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		// 注册安全方言
		//	templateEngine.addDialect(new SpringSecurityDialect());
		return templateEngine;
	}

	@Bean
	public ITemplateResolver templateResolver() {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();  
        // ServletContextTemplateResolver需要一个ServletContext作为构造参数，可通过<span style="font-family:Arial, Helvetica, sans-serif;">WebApplicationContext 的方法获得</span>  
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(  
                webApplicationContext.getServletContext());  
		resolver.setPrefix("/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode(TemplateMode.HTML);
		resolver.setCharacterEncoding("UTF-8");
		return resolver;
	}
	
	// 配置静态资源的处理
	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		// 要求DispatcherServlet把对静态资源的请求转发到Servlet容器中默认的Servlet上
		configurer.enable();
	}
}
