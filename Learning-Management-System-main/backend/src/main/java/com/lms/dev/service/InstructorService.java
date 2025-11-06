package com.lms.dev.service;

import com.lms.dev.entity.Course;
import com.lms.dev.entity.Learning;
import com.lms.dev.entity.User;
import com.lms.dev.repository.CourseRepository;
import com.lms.dev.repository.LearningRepository;
import com.lms.dev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstructorService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final LearningRepository learningRepository;

    /**
     * Get all courses created by a specific instructor
     */
    public List<Course> getCoursesByInstructor(UUID instructorId) {
        Optional<User> instructorOpt = userRepository.findById(instructorId);
        if (instructorOpt.isEmpty()) {
            return List.of();
        }
        User instructor = instructorOpt.get();
        return courseRepository.findByInstructorUser(instructor);
    }

    /**
     * Create a course and assign it to the instructor
     */
    @Transactional
    public Course createCourseForInstructor(Course course, UUID instructorId) {
        Optional<User> instructorOpt = userRepository.findById(instructorId);
        if (instructorOpt.isEmpty()) {
            throw new IllegalArgumentException("Instructor not found");
        }
        
        User instructor = instructorOpt.get();
        course.setInstructorUser(instructor);
        // Set instructor name from user's username for backward compatibility
        if (course.getInstructor() == null || course.getInstructor().isEmpty()) {
            course.setInstructor(instructor.getUsername());
        }
        
        return courseRepository.save(course);
    }

    /**
     * Update a course if the instructor owns it
     */
    @Transactional
    public Course updateCourseForInstructor(UUID courseId, Course updatedCourse, UUID instructorId) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isEmpty()) {
            return null;
        }
        
        Course existingCourse = courseOpt.get();
        
        // Check if the instructor owns this course
        if (existingCourse.getInstructorUser() == null || 
            !existingCourse.getInstructorUser().getId().equals(instructorId)) {
            return null;
        }
        
        // Update course fields
        existingCourse.setCourse_name(updatedCourse.getCourse_name());
        existingCourse.setDescription(updatedCourse.getDescription());
        existingCourse.setP_link(updatedCourse.getP_link());
        existingCourse.setPrice(updatedCourse.getPrice());
        existingCourse.setY_link(updatedCourse.getY_link());
        
        // Update instructor name if provided
        if (updatedCourse.getInstructor() != null && !updatedCourse.getInstructor().isEmpty()) {
            existingCourse.setInstructor(updatedCourse.getInstructor());
        }
        
        return courseRepository.save(existingCourse);
    }

    /**
     * Delete a course if the instructor owns it
     */
    @Transactional
    public boolean deleteCourseForInstructor(UUID courseId, UUID instructorId) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isEmpty()) {
            return false;
        }
        
        Course course = courseOpt.get();
        
        // Check if the instructor owns this course
        if (course.getInstructorUser() == null || 
            !course.getInstructorUser().getId().equals(instructorId)) {
            return false;
        }
        
        courseRepository.delete(course);
        return true;
    }

    /**
     * Get all unique students enrolled in any of the instructor's courses
     */
    public List<User> getStudentsForInstructor(UUID instructorId) {
        List<Course> instructorCourses = getCoursesByInstructor(instructorId);
        if (instructorCourses.isEmpty()) {
            return List.of();
        }
        
        Set<UUID> studentIds = instructorCourses.stream()
                .flatMap(course -> learningRepository.findByCourse(course).stream())
                .map(learning -> learning.getUser().getId())
                .collect(Collectors.toSet());
        
        return studentIds.stream()
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    /**
     * Get students enrolled in a specific course (only if instructor owns it)
     */
    public List<User> getStudentsForCourse(UUID courseId, UUID instructorId) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isEmpty()) {
            return null;
        }
        
        Course course = courseOpt.get();
        
        // Check if the instructor owns this course
        if (course.getInstructorUser() == null || 
            !course.getInstructorUser().getId().equals(instructorId)) {
            return null;
        }
        
        List<Learning> enrollments = learningRepository.findByCourse(course);
        
        return enrollments.stream()
                .map(Learning::getUser)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Get enrollment details for a specific course (only if instructor owns it)
     */
    public List<Learning> getEnrollmentsForCourse(UUID courseId, UUID instructorId) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isEmpty()) {
            return null;
        }
        
        Course course = courseOpt.get();
        
        // Check if the instructor owns this course
        if (course.getInstructorUser() == null || 
            !course.getInstructorUser().getId().equals(instructorId)) {
            return null;
        }
        
        return learningRepository.findByCourse(course);
    }
}

