package org.example.cuongvanhoa.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final AuthenticationInterceptor authenticationInterceptor;
    private final RoleBasedAuthInterceptor roleBasedAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Không cần đăng nhập để vào trang /users/blogs/
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/blogs/**") // Áp dụng xác thực cho các đường dẫn bắt đầu bằng /blogs
                .excludePathPatterns("/users/blogs/**"); // Loại trừ /users/blogs/ để không yêu cầu đăng nhập

        // Chỉ cho phép role = true truy cập vào /blogs
        registry.addInterceptor(roleBasedAuthInterceptor)
                .addPathPatterns("/blogs/**") // Chỉ áp dụng cho trang admin /blogs/**
                .excludePathPatterns("/users/blogs/**"); // Loại trừ /users/blogs/
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image_uploads/**")
                .addResourceLocations("file:image_uploads/");
    }
}
