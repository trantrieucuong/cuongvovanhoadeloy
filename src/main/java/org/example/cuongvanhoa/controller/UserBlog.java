package org.example.cuongvanhoa.controller;

import lombok.RequiredArgsConstructor;
import org.example.cuongvanhoa.entity.Blog;
import org.example.cuongvanhoa.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users/blogs")
public class UserBlog {
    private final BlogService blogService;

    // Danh sách tất cả bài viết
    @GetMapping("/")
    public String getHomePage(Model model) {
        // Lấy tất cả bài viết sắp xếp theo createdAt giảm dần
        List<Blog> blogList = blogService.getAllBlogsByStatus();
        model.addAttribute("blogList", blogList);
        return "blog/users/index";
    }

}
