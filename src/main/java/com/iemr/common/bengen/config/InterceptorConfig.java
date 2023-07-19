package com.iemr.common.bengen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.iemr.common.bengen.utils.http.HTTPRequestInterceptor;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter
{

	@Autowired
	HTTPRequestInterceptor requestInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(requestInterceptor);
	}
}