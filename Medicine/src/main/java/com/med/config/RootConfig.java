package com.med.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
@ComponentScan(basePackages={"com.med"}, 
	excludeFilters={
		@Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class)
	})
@Import({HibernateConfig.class, DataSourceConfig.class})
//常用来加载非web组件，如后端的中间层和数据层组件
public class RootConfig {

	/* json数据传递 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public RequestMappingHandlerAdapter handlerAdapter(
			MappingJackson2HttpMessageConverter messageConverter) {
		RequestMappingHandlerAdapter handlerAdapter = 
				new RequestMappingHandlerAdapter();
		
		List converters = new ArrayList<HttpMessageConverter>();
		converters.add(messageConverter);

		handlerAdapter.setMessageConverters(converters);
		
		return handlerAdapter;
	}
	
	@SuppressWarnings("unchecked")
	@Bean
	public MappingJackson2HttpMessageConverter messageConverter() {
		MappingJackson2HttpMessageConverter messageConverter =
				new MappingJackson2HttpMessageConverter();
		MediaType mediaType = new MediaType(MediaType.APPLICATION_JSON, 
				Charset.forName("UTF-8"));
		List mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(mediaType);
		
		messageConverter.setSupportedMediaTypes(mediaTypes);
		
		return messageConverter;
	}
}
