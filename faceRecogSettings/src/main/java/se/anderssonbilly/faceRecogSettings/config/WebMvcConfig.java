package se.anderssonbilly.faceRecogSettings.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public abstract class WebMvcConfig implements WebMvcConfigurer  {
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/home").setViewName("index");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/register").setViewName("register");
		registry.addViewController("/settings").setViewName("settings");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
		registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
	}
}
