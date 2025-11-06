package com.lms.dev.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.dev.entity.Course;
import com.lms.dev.entity.User;

import java.util.List;
import java.util.UUID;


public interface CourseRepository extends JpaRepository<Course, UUID> {
    
    List<Course> findByInstructorUser(User instructor);
}