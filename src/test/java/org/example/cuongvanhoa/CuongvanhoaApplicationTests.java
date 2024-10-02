package org.example.cuongvanhoa;

import com.github.javafaker.Faker;

import com.github.slugify.Slugify;
import org.example.cuongvanhoa.entity.Blog;
import org.example.cuongvanhoa.entity.User;
import org.example.cuongvanhoa.model.enums.UserRole;
import org.example.cuongvanhoa.repository.BlogRepository;
import org.example.cuongvanhoa.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootTest
class CuongvanhoaApplicationTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bcryptPasswordEncoder;
    @Autowired
    private BlogRepository blogRepository;

    @Test
    void save_all_user() {
        Faker faker = new Faker(); // Faker data
        for (int i = 0; i < 20; i++) {
            String name = faker.name().fullName();
            User user = User.builder()
                    .name(name)
                    .email(faker.internet().emailAddress())
                    .password(bcryptPasswordEncoder.encode("123"))
                    .avatar("https://www.facebook.com/photo/?fbid=1232151827759039&set=a.110798953227671")
                    .role(i == 0 || i == 1 ? UserRole.ADMIN : UserRole.USER)
                    .build();

            userRepository.save(user); // Lưu vào database
        }
    }

    @Test
    void save_specific_and_random_users1() {
        Faker faker = new Faker(); // Faker data

        // Create the specific user
        User specificUser = User.builder()
                .name(faker.name().fullName())
                .email("vit27012005@gmail.com")
                .password(bcryptPasswordEncoder.encode("Cuong*09"))
                .avatar("https://www.facebook.com/photo/?fbid=1232151827759039&set=a.110798953227671") // Assuming you have a method to generate the link
                .role(UserRole.ADMIN) // Set role to 1 (assuming it's USER)
                .build();

        userRepository.save(specificUser); // Save the specific user

        // Create random users
//        for (int i = 0; i < 19; i++) { // Adjust the count if needed
//            String name = faker.name().fullName();
//            User randomUser = User.builder()
//                    .name(name)
//                    .email(faker.internet().emailAddress())
//                    .password(bCryptPasswordEncoder.encode("123"))
//                    .avatar(generateLinkImage(name))
//                    .role(i == 0 || i == 1 ? UserRole.ADMIN : UserRole.USER) // Assuming first two should be ADMIN
//                    .build();
//
//            userRepository.save(randomUser); // Save random users
//        }
    }


    @Test
    void contextLoads() {
    }
    @Test
    void save_all_blog() {
        List<User> userList = userRepository.findByRole(UserRole.ADMIN);

        Faker faker = new Faker(); // Faker data
        Random random = new Random();
        Slugify slugify = Slugify.builder().build(); // Generate slug

        for (int i = 0; i < 20; i++) {
            // Random 1 user
            User rdUser = userList.get(random.nextInt(userList.size()));

            boolean status = faker.bool().bool();
            Date createdAt = new Date();
            Date publishedAt = null;

            if (status) {
                publishedAt = createdAt;
            }

            // Tạo blog
            String title = faker.book().title();
            Blog blog = Blog.builder()
                    .title(title)
                    .slug("slugify.slugify(title)")
                    .description(faker.lorem().paragraph())
                    .content(faker.lorem().paragraph())
                    .thumbnail("IMG_1170.JPG")
                    .status(status)
                    .createdAt(createdAt)
                    .updatedAt(createdAt)
                    .publishedAt(publishedAt)
                    .user(rdUser)
                    .build();
            blogRepository.save(blog); // Lưu vào database
        }
    }
// get first character from string, and to uppercase
    private static String getCharacter(String str) {
        return str.substring(0, 1).toUpperCase();
    }

    // generate link author avatar follow struct : https://placehold.co/200x200?text=[...]
    public static String generateLinkImage(String name) {
        return "https://placehold.co/200x200?text=" + getCharacter(name);
    }
//    @Test
//    void save_all_blogv1() {
//        List<User> userList = userRepository.findByRole(UserRole.ADMIN);
//
//        if (userList == null || userList.isEmpty()) {
//            throw new IllegalArgumentException("The userList is empty. No users with role ADMIN found.");
//        }
//
//        Faker faker = new Faker(); // Faker data
//        Random random = new Random();
//        Slugify slugify = Slugify.builder().build(); // Generate slug
//
//        // Sử dụng ClassLoader để lấy tệp từ static/img
//        ClassLoader classLoader = getClass().getClassLoader();
//        String imgPath = "static/img";
//        URL imgDirectory = classLoader.getResource(imgPath);
//
//        if (imgDirectory == null) {
//            throw new IllegalArgumentException("The directory " + imgPath + " was not found.");
//        }
//
//        File imgDir = new File(imgDirectory.getFile());
//        File[] imgFiles = imgDir.listFiles((dir, name) -> name.endsWith(".jpg") || name.endsWith(".png"));
//
//        if (imgFiles == null || imgFiles.length == 0) {
//            throw new IllegalArgumentException("No image files found in the directory " + imgPath + ".");
//        }
//
//        for (int i = 0; i < 20; i++) {
//            // Random 1 user
//            User rdUser = userList.get(random.nextInt(userList.size()));
//
//            boolean status = faker.bool().bool();
//            Date createdAt = new Date();
//            Date publishedAt = null;
//
//            if (status) {
//                publishedAt = createdAt;
//            }
//
//            // Tạo blog
//            String title = faker.book().title();
//
//            // Lấy ngẫu nhiên 1 tệp ảnh làm thumbnail
//            File randomImg = imgFiles[random.nextInt(imgFiles.length)];
//            String thumbnail = "/img/" + randomImg.getName(); // Đường dẫn tương đối của ảnh
//
//            Blog blog = Blog.builder()
//                    .title(title)
//                    .slug(slugify.slugify(title))
//                    .description(faker.lorem().paragraph())
//                    .content(faker.lorem().paragraph())
//                    .thumbnail(thumbnail) // Sử dụng đường dẫn thumbnail
//                    .status(status)
//                    .createdAt(createdAt)
//                    .updatedAt(createdAt)
//                    .publishedAt(publishedAt)
//                    .user(rdUser)
//                    .build();
//            blogRepository.save(blog); // Lưu vào database
//        }
//    }



}
