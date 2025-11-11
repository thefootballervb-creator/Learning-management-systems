package com.lms.dev.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.dev.dto.ApiResponse;
import com.lms.dev.entity.Course;
import com.lms.dev.entity.Learning;
import com.lms.dev.entity.User;
import com.lms.dev.security.UserPrincipal;
import com.lms.dev.service.InstructorService;

@RestController
@RequestMapping("/api/instructor")
@PreAuthorize("hasRole('INSTRUCTOR')")
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    /**
     * Get all courses created by the logged-in instructor
     */
    @GetMapping("/courses")
    public ResponseEntity<ApiResponse<List<Course>>> getMyCourses(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UUID instructorId = userPrincipal.getId();
        
        List<Course> courses = instructorService.getCoursesByInstructor(instructorId);
        return ResponseEntity.ok(new ApiResponse<>("Courses retrieved successfully", courses));
    }

    /**
     * Create a new course (instructor will be set to the logged-in instructor)
     */
    @PostMapping("/courses")
    public ResponseEntity<ApiResponse<Course>> createCourse(
            @RequestBody Course course,
            Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UUID instructorId = userPrincipal.getId();
        
        Course createdCourse = instructorService.createCourseForInstructor(course, instructorId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Course created successfully", createdCourse));
    }

    /**
     * Update a course (only if the instructor owns it)
     */
    @PutMapping("/courses/{courseId}")
    public ResponseEntity<ApiResponse<Course>> updateCourse(
            @PathVariable UUID courseId,
            @RequestBody Course updatedCourse,
            Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UUID instructorId = userPrincipal.getId();
        
        Course course = instructorService.updateCourseForInstructor(courseId, updatedCourse, instructorId);
        if (course != null) {
            return ResponseEntity.ok(new ApiResponse<>("Course updated successfully", course));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse<>("You don't have permission to update this course", null));
    }

    /**
     * Delete a course (only if the instructor owns it)
     */
    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<ApiResponse<Void>> deleteCourse(
            @PathVariable UUID courseId,
            Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UUID instructorId = userPrincipal.getId();
        
        boolean deleted = instructorService.deleteCourseForInstructor(courseId, instructorId);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>("Course deleted successfully", null));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse<>("You don't have permission to delete this course", null));
    }

    /**
     * Get all students enrolled in instructor's courses
     */
    @GetMapping("/students")
    public ResponseEntity<ApiResponse<List<User>>> getMyStudents(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UUID instructorId = userPrincipal.getId();
        
        List<User> students = instructorService.getStudentsForInstructor(instructorId);
        return ResponseEntity.ok(new ApiResponse<>("Students retrieved successfully", students));
    }

    /**
     * Get students enrolled in a specific course
     */
    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<ApiResponse<List<User>>> getCourseStudents(
            @PathVariable UUID courseId,
            Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UUID instructorId = userPrincipal.getId();
        
        List<User> students = instructorService.getStudentsForCourse(courseId, instructorId);
        if (students != null) {
            return ResponseEntity.ok(new ApiResponse<>("Students retrieved successfully", students));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse<>("You don't have permission to view students for this course", null));
    }

    /**
     * Get enrollment details for a specific course
     */
    @GetMapping("/courses/{courseId}/enrollments")
    public ResponseEntity<ApiResponse<List<Learning>>> getCourseEnrollments(
            @PathVariable UUID courseId,
            Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UUID instructorId = userPrincipal.getId();
        
        List<Learning> enrollments = instructorService.getEnrollmentsForCourse(courseId, instructorId);
        if (enrollments != null) {
            return ResponseEntity.ok(new ApiResponse<>("Enrollments retrieved successfully", enrollments));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse<>("You don't have permission to view enrollments for this course", null));
    }
}

