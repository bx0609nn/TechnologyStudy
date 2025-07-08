package com.bx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @author lili
 * @version 1.0
 * @date 2024/11/4 16:37
 * @description
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    //配置Swagger的docket的Bean
    @Bean
    public Docket docket(Environment environment){
        //设置要显示swagger的环境
        Profiles profiles = Profiles.of("dev", "test");
        //判断当前环境是否处于上面设置的环境中
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //如果处于就开启swagger，不处于就不会开启
                .enable(flag)
                .select()
                //配置扫描接口的方式
                // basePackage("com.bx.controller")：指定要扫描的包
                // any()：扫描全部
                // none()：不扫描
                // withClassAnnotation()：扫描类上的注解
                // withMethodAnnotation()：扫描方法上的注解
                .apis(RequestHandlerSelectors.basePackage("com.bx.controller"))
                //进一步过滤
//                .paths(PathSelectors.any())
                .build();
    }

    //配置API文档的分组，配置多个Docket实例，为每个实例设置不同的组即可
    @Bean
    public Docket docket1(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("A");
    }

    //配置API文档的分组，配置多个Docket实例，为每个实例设置不同的组即可
    @Bean
    public Docket docket2(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("B");
    }

    //配置Swagger信息
    public ApiInfo apiInfo(){
        Contact DEFAULT_CONTACT =   new Contact("", "", "");
        return new ApiInfo(
                "bx的SwaggerAPI",
                "bx的SwaggerAPI",
                "1.0",
                "urn:tos",
                DEFAULT_CONTACT,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
