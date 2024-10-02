package org.example.cuongvanhoa.rest;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.example.cuongvanhoa.model.request.LoginRequest;
import org.example.cuongvanhoa.model.request.RegisterRequest;
import org.example.cuongvanhoa.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthResource {
    private final AuthService authService;
    private final HttpSession session; // Thêm HttpSession vào đây


//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//        authService.login(request);
//
//        // Kiểm tra thông tin người dùng từ session
//        if (session.getAttribute("currentUser") != null) {
//            // Nếu người dùng đã đăng nhập thành công, trả về phản hồi thành công
//            return ResponseEntity.ok("Đăng nhập thành công!"); // status code 200
//        } else {
//            // Nếu có vấn đề, bạn có thể trả về một lỗi hoặc thông báo khác
//            return ResponseEntity.status(401).body("Đăng nhập thất bại!"); // status code 401
//        }
//    }
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    try {
        // Thực hiện xác thực
        authService.login(request);

        // Nếu xác thực thành công, thêm người dùng vào session
        session.setAttribute("currentUser", request.getEmail());

        return ResponseEntity.ok("Đăng nhập thành công!"); // status code 200
    } catch (Exception e) {
        // Nếu có vấn đề, bạn có thể trả về một lỗi hoặc thông báo khác
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Đăng nhập thất bại!"); // status code 401
    }
}


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("Đăng ký thành công!");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        authService.logout();
        return ResponseEntity.ok("Đăng xuất thành công!");
    }
}
