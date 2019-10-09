package com.head.h1;

import com.sun.deploy.ui.AppInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
public class FunApplication {

	public static void main(String[] args) {
		SpringApplication.run(FunApplication.class, args);
		System.out.println("hello");
	}
	@Bean
    public Docket swaggerConfiguration()
	{
       return new Docket(DocumentationType.SWAGGER_2)
			   .select()
			   .paths(PathSelectors.any())
			   .apis(RequestHandlerSelectors.basePackage("com.head.h1"))
			   .build()
			   .apiInfo(apiDetails());
	}

	   private ApiInfo apiDetails()
	{
		 return new ApiInfo(
				"Employee API",
				"Sample API for project",
				"2.0",
				"Free to use",
				 new springfox.documentation.service.Contact("Techi","https://youtube.com","https://kungfupanda792@gmail.com"),
				"API License",
				 "https://www.empdetail.com",
				 Collections.emptyList());

	}
}
