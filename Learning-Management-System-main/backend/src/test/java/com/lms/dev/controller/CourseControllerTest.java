package com.lms.dev.controller;

import com.lms.dev.entity.Course;
import com.lms.dev.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    private Course testCourse;
    private UUID courseId;

    @BeforeEach
    void setUp() {
        courseId = UUID.randomUUID();
        testCourse = new Course();
        testCourse.setCourse_id(courseId);
        testCourse.setCourse_name("Test Course");
        testCourse.setDescription("Test Description");
        testCourse.setPrice(100);
        testCourse.setInstructor("Test Instructor");
    }

    @Test
    void testGetAllCourses() {
        // Given
        List<Course> courses = Arrays.asList(testCourse);
        when(courseService.getAllCourses()).thenReturn(courses);

        // When
        List<Course> result = courseController.getAllCourses();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testCourse.getCourse_name(), result.get(0).getCourse_name());
        verify(courseService, times(1)).getAllCourses();
    }

    @Test
    void testGetCourseById_Success() {
        // Given
        when(courseService.getCourseById(courseId)).thenReturn(Optional.of(testCourse));

        // When
        ResponseEntity<Course> response = courseController.getCourseById(courseId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testCourse.getCourse_name(), response.getBody().getCourse_name());
        verify(courseService, times(1)).getCourseById(courseId);
    }

    @Test
    void testGetCourseById_NotFound() {
        // Given
        when(courseService.getCourseById(courseId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<Course> response = courseController.getCourseById(courseId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(courseService, times(1)).getCourseById(courseId);
    }

    @Test
    void testCreateCourse() {
        // Given
        when(courseService.createCourse(any(Course.class))).thenReturn(testCourse);

        // When
        Course result = courseController.createCourse(testCourse);

        // Then
        assertNotNull(result);
        assertEquals(testCourse.getCourse_name(), result.getCourse_name());
        verify(courseService, times(1)).createCourse(testCourse);
    }

    @Test
    void testUpdateCourse_Success() {
        // Given
        Course updatedCourse = new Course();
        updatedCourse.setCourse_name("Updated Course");
        when(courseService.updateCourse(courseId, updatedCourse)).thenReturn(Optional.of(updatedCourse));

        // When
        ResponseEntity<Course> response = courseController.updateCourse(courseId, updatedCourse);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Course", response.getBody().getCourse_name());
        verify(courseService, times(1)).updateCourse(courseId, updatedCourse);
    }

    @Test
    void testUpdateCourse_NotFound() {
        // Given
        Course updatedCourse = new Course();
        when(courseService.updateCourse(courseId, updatedCourse)).thenReturn(Optional.empty());

        // When
        ResponseEntity<Course> response = courseController.updateCourse(courseId, updatedCourse);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(courseService, times(1)).updateCourse(courseId, updatedCourse);
    }

    @Test
    void testDeleteCourse() {
        // Given
        doNothing().when(courseService).deleteCourse(courseId);

        // When
        courseController.deleteCourse(courseId);

        // Then
        verify(courseService, times(1)).deleteCourse(courseId);
    }
}

