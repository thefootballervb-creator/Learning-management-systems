package com.lms.dev.service;

import com.lms.dev.entity.Course;
import com.lms.dev.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

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
    }

    @Test
    void testGetAllCourses() {
        // Given
        List<Course> courses = Arrays.asList(testCourse);
        when(courseRepository.findAll()).thenReturn(courses);

        // When
        List<Course> result = courseService.getAllCourses();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testCourse.getCourse_name(), result.get(0).getCourse_name());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void testGetCourseById_Success() {
        // Given
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(testCourse));

        // When
        Optional<Course> result = courseService.getCourseById(courseId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(testCourse.getCourse_name(), result.get().getCourse_name());
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void testGetCourseById_NotFound() {
        // Given
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // When
        Optional<Course> result = courseService.getCourseById(courseId);

        // Then
        assertFalse(result.isPresent());
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void testGetCourseById_NullId() {
        // When
        Optional<Course> result = courseService.getCourseById(null);

        // Then
        assertFalse(result.isPresent());
        verify(courseRepository, never()).findById(any());
    }

    @Test
    void testCreateCourse_Success() {
        // Given
        when(courseRepository.save(any(Course.class))).thenReturn(testCourse);

        // When
        Course result = courseService.createCourse(testCourse);

        // Then
        assertNotNull(result);
        assertEquals(testCourse.getCourse_name(), result.getCourse_name());
        verify(courseRepository, times(1)).save(testCourse);
    }

    @Test
    void testCreateCourse_NullCourse() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            courseService.createCourse(null);
        });
        verify(courseRepository, never()).save(any());
    }

    @Test
    void testUpdateCourse_Success() {
        // Given
        Course updatedCourse = new Course();
        updatedCourse.setCourse_name("Updated Course");
        updatedCourse.setDescription("Updated Description");
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(testCourse));
        when(courseRepository.save(any(Course.class))).thenReturn(testCourse);

        // When
        Optional<Course> result = courseService.updateCourse(courseId, updatedCourse);

        // Then
        assertTrue(result.isPresent());
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testUpdateCourse_NotFound() {
        // Given
        Course updatedCourse = new Course();
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // When
        Optional<Course> result = courseService.updateCourse(courseId, updatedCourse);

        // Then
        assertFalse(result.isPresent());
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, never()).save(any());
    }

    @Test
    void testDeleteCourse_Success() {
        // Given
        doNothing().when(courseRepository).deleteById(courseId);

        // When
        courseService.deleteCourse(courseId);

        // Then
        verify(courseRepository, times(1)).deleteById(courseId);
    }

    @Test
    void testDeleteCourse_NullId() {
        // When
        courseService.deleteCourse(null);

        // Then
        verify(courseRepository, never()).deleteById(any());
    }
}

