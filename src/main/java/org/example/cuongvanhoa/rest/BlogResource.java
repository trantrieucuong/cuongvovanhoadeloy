package org.example.cuongvanhoa.rest;

import lombok.RequiredArgsConstructor;
import org.example.cuongvanhoa.entity.Blog;
import org.example.cuongvanhoa.model.request.UpsertBlogRequest;
import org.example.cuongvanhoa.service.BlogService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/blogs")
@RequiredArgsConstructor
public class BlogResource {
    private final BlogService blogService;
    @GetMapping
    public ResponseEntity<List<Blog>> getAllBlogs() {
        List<Blog> blogs = blogService.getAllBlogs();
        return ResponseEntity.ok(blogs); // status code 200
    }

//     Tạo review - POST.
//     Client -> Request chứa thông tin -> Server
//     Server đọc dữ liệu từ Request -> Xử lý -> Trả về kết quả cho Client
    @PostMapping
    public ResponseEntity<?> createBlog(@RequestBody UpsertBlogRequest request) {
        Blog blog = blogService.createBlog(request);
        return ResponseEntity.ok(blog); // status code 200
    }
//    @PostMapping("/{id}/update")
//    public ResponseEntity<?> updateBlogDetail(@PathVariable Integer id, @RequestBody UpsertBlogRequest request) {
//        Blog updatedBlog = blogService.updateBlog(id, request);
//        return ResponseEntity.ok(updatedBlog); // Trả về bài viết đã cập nhật
//    }


    // Cập nhật review - PUT
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBlog(@RequestBody UpsertBlogRequest request, @PathVariable Integer id) {
        Blog blog = blogService.updateBlog(id, request);
        return ResponseEntity.ok(blog); // status code 200
    }

    // Xóa review - DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable Integer id) {
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build(); // status code 204
    }

    // Upload thumbnail - POST
    @PostMapping("/{id}/upload-thumbnail")
    public ResponseEntity<?> uploadThumbnail(@RequestParam("file") MultipartFile file, @PathVariable Integer id) {
        String filePath = blogService.uploadThumbnail(id, file);
        return ResponseEntity.ok(filePath); // status code 200
    }
}
