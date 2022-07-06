package com.SnHI.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    /**
     * 视图映射
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry相当于viewController的注册中心，标记跳转页面的位置
        //添加视图控制器,第一个参数urlPath是请求地址等同于requestMapping的地址。第二个参数viewName是视图名，也就是原来controller中return页面的名字
        registry.addViewController("/Login").setViewName("Login");
        registry.addViewController("/user/Login").setViewName("Login");
        registry.addViewController("/Register").setViewName("Register");
        registry.addViewController("/UserInterface").setViewName("UserInterface");
        registry.addViewController("/AdminInterface").setViewName("AdminInterface");
        registry.addViewController("/FindPassword").setViewName("FindPassword");
    }

}
