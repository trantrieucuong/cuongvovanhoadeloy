package org.example.cuongvanhoa.repository;

import org.example.cuongvanhoa.entity.Blog;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {
    List<Blog> findByUser_Id(Integer id, Sort sort);
    @Query("SELECT b FROM Blog b WHERE b.status = true")
    List<Blog> findAllPublishedBlogs();


}
