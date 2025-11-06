package com.lms.dev.service;

import com.lms.dev.entity.Course;
import com.lms.dev.entity.User;
import com.lms.dev.enums.UserRole;
import com.lms.dev.repository.CourseRepository;
import com.lms.dev.repository.LearningRepository;
import com.lms.dev.repository.UserRepository;
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
class InstructorServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LearningRepository learningRepository;

    @InjectMocks
    private InstructorService instructorService;

    private User instructor;
    private Course testCourse;
    private UUID instructorId;
    private UUID courseId;

    @BeforeEach
    void setUp() {
        instructorId = UUID.randomUUID();
        courseId = UUID.randomUUID();

        instructor = new User();
        instructor.setId(instructorId);
        instructor.setEmail("instructor@example.com");
        instructor.setUsername("Instructor");
        instructor.setRole(UserRole.INSTRUCTOR);

        testCourse = new Course();
        testCourse.setCourse_id(courseId);
        testCourse.setCourse_name("Test Course");
        testCourse.setInstructorUser(instructor);
    }

    @Test
    void testGetCoursesByInstructor_Success() {
        // Given
        List<Course> courses = Arrays.asList(testCourse);
        when(userRepository.findById(instructorId)).thenReturn(Optional.of(instructor));
        when(courseRepository.findByInstructorUser(instructor)).thenReturn(courses);

        // When
        List<Course> result = instructorService.getCoursesByInstructor(instructorId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testCourse.getCourse_name(), result.get(0).getCourse_name());
        verify(userRepository, times(1)).findById(instructorId);
        verify(courseRepository, times(1)).findByInstructorUser(instructor);
    }

    @Test
    void testGetCoursesByInstructor_NotFound() {
        // Given
        when(userRepository.findById(instructorId)).thenReturn(Optional.empty());

        // When
        List<Course> result = instructorService.getCoursesByInstructor(instructorId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findById(instructorId);
        verify(courseRepository, never()).findByInstructorUser(any());
    }

    @Test
    void testCreateCourseForInstructor_Success() {
        // Given
        Course newCourse = new Course();
        newCourse.setCourse_name("New Course");
        when(userRepository.findById(instructorId)).thenReturn(Optional.of(instructor));
        when(courseRepository.save(any(Course.class))).thenReturn(testCourse);

        // When
        Course result = instructorService.createCourseForInstructor(newCourse, instructorId);

        // Then
        assertNotNull(result);
        verify(userRepository, times(1)).findById(instructorId);
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testCreateCourseForInstructor_InstructorNotFound() {
        // Given
        Course newCourse = new Course();
        when(userRepository.findById(instructorId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            instructorService.createCourseForInstructor(newCourse, instructorId);
        });
        verify(courseRepository, never()).save(any());
    }

    @Test
    void testUpdateCourseForInstructor_Success() {
        // Given
        Course updatedCourse = new Course();
        updatedCourse.setCourse_name("Updated Course");
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(testCourse));
        when(courseRepository.save(any(Course.class))).thenReturn(testCourse);

        // When
        Course result = instructorService.updateCourseForInstructor(courseId, updatedCourse, instructorId);

        // Then
        assertNotNull(result);
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testUpdateCourseForInstructor_NotOwner() {
        // Given
        UUID otherInstructorId = UUID.randomUUID();
        Course updatedCourse = new Course();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(testCourse));

        // When
        Course result = instructorService.updateCourseForInstructor(courseId, updatedCourse, otherInstructorId);

        // Then
        assertNull(result);
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, never()).save(any());
    }

    @Test
    void testDeleteCourseForInstructor_Success() {
        // Given
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(testCourse));
        doNothing().when(courseRepository).delete(any(Course.class));

        // When
        boolean result = instructorService.deleteCourseForInstructor(courseId, instructorId);

        // Then
        assertTrue(result);
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(1)).delete(testCourse);
    }

    @Test
    void testDeleteCourseForInstructor_NotOwner() {
        // Given
        UUID otherInstructorId = UUID.randomUUID();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(testCourse));

        // When
        boolean result = instructorService.deleteCourseForInstructor(courseId, otherInstructorId);

        // Then
        assertFalse(result);
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, never()).delete(any());
    }
}

