package org.example.cuongvanhoa.controller;

import lombok.RequiredArgsConstructor;
import org.example.cuongvanhoa.entity.Blog;
import org.example.cuongvanhoa.service.BlogService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/blogs")
public class BlogController {
    private final BlogService blogService;

    // Danh sách tất cả bài viết
    @GetMapping("/")
    public String getHomePage(Model model) {
        // Lấy tất cả bài viết sắp xếp theo createdAt giảm dần
        List<Blog> blogList = blogService.getAllBlogs();
        model.addAttribute("blogList", blogList);
        return "blog/index";
    }

    // Danh sách bài viết của tôi
    @GetMapping("/own-blogs")
    public String getOwnPage(Model model) {
        // Lấy tất cả của user đang đăng nhập, sắp xếp theo createdAt giảm dần
        // Lấy user đang đăng nhập lấy trong session với key là "currentUser"
        // Lấy bài viết theo userId
        List<Blog> blogList = blogService.getAllBlogOfCurrentUser();
        model.addAttribute("blogList", blogList);
        return "blog/own-blog";
    }

    // Tạo bài viết
    @GetMapping("/create")
    public String getCreatePage(Model model) {
        return "/blog/create";
    }

    // Chi tiết bài viết
    @GetMapping("/{id}/detail")
    public String getDetailPage(@PathVariable Integer id, Model model) {
        // Lấy bài viết theo id
        Blog blog = blogService.getBlogById(id);
        model.addAttribute("blog", blog);
        return "/blog/detail";
    }
//    @PostMapping("/{id}/update")
//    public String updateBlog(@ModelAttribute  Blog blog) {
//        Blog blogs = blogService.getBlogById(blog.getId());
//        if (blogs != null) {
//            blogs.setTitle(blog.getTitle());
//            blogs.setContent(blog.getContent());
//            blogs.setDescription(blog.getDescription());
//            blogs.setStatus(blog.getStatus());
//            blogs.setThumbnail(blog.getThumbnail());
//            blogService.saveBlog(blogs);
//
//        }
//        return "redirect:/blogs/" + blogs.getId() + "/detail";
//
//
//
//    }
@PostMapping("/{id}/update")
public String updateBlog(@PathVariable Integer id,
                         @ModelAttribute Blog blog,
                         @RequestParam("thumbnailFile") MultipartFile thumbnailFile) {
    Blog existingBlog = blogService.getBlogById(id);
    if (existingBlog != null) {
        // Update the blog's attributes
        existingBlog.setTitle(blog.getTitle());
        existingBlog.setContent(blog.getContent());
        existingBlog.setDescription(blog.getDescription());

        // Update the status
        existingBlog.setStatus(blog.getStatus()); // Cập nhật trạng thái


        // Handle thumbnail file upload
        if (!thumbnailFile.isEmpty()) {
            String fileName = thumbnailFile.getOriginalFilename();
            // You should implement a service to save the file to a directory
            // For example: fileUploadService.saveFile(thumbnailFile);
            existingBlog.setThumbnail(fileName);
        }

        blogService.saveBlog(existingBlog);
    }

    return "redirect:/blogs/" + id + "/detail";
}


}
