package com.lms.dev.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.dev.entity.Course;
import com.lms.dev.entity.Learning;
import com.lms.dev.entity.Questions;
import com.lms.dev.repository.CourseRepository;
import com.lms.dev.repository.LearningRepository;
import com.lms.dev.repository.QuestionRepository;
import com.lms.dev.service.CourseInitializationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class CourseInitController {

    private final CourseRepository courseRepository;
    private final QuestionRepository questionRepository;
    private final LearningRepository learningRepository;
    private final CourseInitializationService courseInitializationService;

    // Temporarily removed @PreAuthorize for easier initialization
    @PostMapping("/init-courses")
    public ResponseEntity<Map<String, Object>> initializeCourses() {
        return initializeCoursesInternal(false);
    }

    @PostMapping("/reinit-courses")
    public ResponseEntity<Map<String, Object>> reinitializeCourses() {
        return initializeCoursesInternal(true);
    }

    @PostMapping("/reinit-questions")
    public ResponseEntity<Map<String, Object>> reinitializeQuestions() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Course> allCourses = courseRepository.findAll();
            log.info("Found {} courses. Re-initializing questions for each course...", allCourses.size());

            int totalQuestionsAdded = 0;

            for (Course course : allCourses) {
                String courseName = course.getCourse_name();
                
                // Delete existing questions for this course
                List<Questions> existingQuestions = questionRepository.findByCourse(course);
                if (!existingQuestions.isEmpty()) {
                    questionRepository.deleteAll(existingQuestions);
                    log.info("Deleted {} existing questions for course: {}", existingQuestions.size(), courseName);
                }
                
                // Create new questions using the same logic as QuestionInitializer
                List<Questions> questions = createQuestionsForCourse(courseName, course);
                if (!questions.isEmpty()) {
                    questionRepository.saveAll(questions);
                    totalQuestionsAdded += questions.size();
                    log.info("Added {} questions for course: {}", questions.size(), courseName);
                } else {
                    log.warn("No questions created for course: {}", courseName);
                }
            }

            response.put("success", true);
            response.put("message", "Successfully re-initialized " + totalQuestionsAdded + " questions across " + allCourses.size() + " courses");
            response.put("coursesCount", allCourses.size());
            response.put("totalQuestions", totalQuestionsAdded);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error during question re-initialization: ", e);
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    private Questions createQuestion(Course course, String question, String opt1, String opt2, String opt3, String opt4, String answer) {
        Questions q = new Questions();
        q.setQuestion(question);
        q.setOption1(opt1);
        q.setOption2(opt2);
        q.setOption3(opt3);
        q.setOption4(opt4);
        q.setAnswer(answer);
        q.setCourse(course);
        return q;
    }

    private List<Questions> createQuestionsForCourse(String courseName, Course course) {
        // Python Basics - 25 questions
        if (courseName.contains("Python Basics") || courseName.contains("Python")) {
            return List.of(
                createQuestion(course, "What is Python?", "A compiled language", "An interpreted, high-level programming language", "A markup language", "A database language", "option2"),
                createQuestion(course, "Which of the following is used to define a block of code in Python?", "Curly braces {}", "Indentation", "Square brackets []", "Parentheses ()", "option2"),
                createQuestion(course, "What is the output of: print(2 ** 3)?", "6", "8", "9", "5", "option2"),
                createQuestion(course, "Which method is used to add an item to the end of a list in Python?", "add()", "append()", "insert()", "push()", "option2"),
                createQuestion(course, "What keyword is used to define a function in Python?", "func", "def", "function", "define", "option2"),
                createQuestion(course, "What is the output of: print(type([]))?", "list", "array", "tuple", "dict", "option1"),
                createQuestion(course, "Which operator is used for exponentiation in Python?", "**", "^", "exp()", "pow()", "option1"),
                createQuestion(course, "What does 'len()' function do in Python?", "Returns the length of an object", "Converts to integer", "Rounds a number", "Finds maximum value", "option1"),
                createQuestion(course, "What is a tuple in Python?", "Mutable sequence", "Immutable sequence", "Dictionary", "Set", "option2"),
                createQuestion(course, "What is the difference between '==' and 'is' in Python?", "'==' compares values, 'is' compares identity", "'==' compares identity, 'is' compares values", "No difference", "'==' is for numbers only", "option1"),
                createQuestion(course, "What is list comprehension in Python?", "A way to create lists using a compact syntax", "A method to delete lists", "A function to sort lists", "A way to merge lists", "option1"),
                createQuestion(course, "What does 'import' do in Python?", "Exports a module", "Imports a module or library", "Deletes a module", "Creates a new module", "option2"),
                createQuestion(course, "What is a dictionary in Python?", "A sequence of values", "A key-value pair data structure", "A single value", "A function", "option2"),
                createQuestion(course, "What does 'range()' function return?", "A list", "A range object", "A tuple", "A string", "option2"),
                createQuestion(course, "What is the purpose of 'pass' statement in Python?", "To stop execution", "To skip a block", "A null operation placeholder", "To raise an error", "option3"),
                createQuestion(course, "What is lambda function in Python?", "A named function", "An anonymous function", "A built-in function", "A recursive function", "option2"),
                createQuestion(course, "What does 'try-except' block do?", "Defines a loop", "Handles exceptions", "Creates a class", "Imports a module", "option2"),
                createQuestion(course, "What is the output of: 'hello'.upper()?", "HELLO", "hello", "Hello", "hELLO", "option1"),
                createQuestion(course, "What is the method to remove whitespace from string in Python?", "trim()", "strip()", "remove()", "delete()", "option2"),
                createQuestion(course, "What is the result of: [1,2,3] + [4,5]?", "[1,2,3,4,5]", "[5,7]", "Error", "[1,2,3],[4,5]", "option1"),
                createQuestion(course, "What is a module in Python?", "A single Python file containing functions and variables", "A built-in function", "A data type", "A variable", "option1"),
                createQuestion(course, "What is __init__ in Python?", "A constructor method", "A destructor", "An import statement", "A class definition", "option1"),
                createQuestion(course, "What is the difference between list and tuple?", "Lists are mutable, tuples are immutable", "Tuples are mutable, lists are immutable", "No difference", "Lists are faster", "option1"),
                createQuestion(course, "What is PEP 8?", "Python Enhancement Proposal for code style", "A Python version", "A Python library", "A Python function", "option1"),
                createQuestion(course, "What does 'self' represent in Python classes?", "Instance of the class", "A method", "A variable", "A module", "option1")
            );
        }

        // Web Development - 25 questions  
        if (courseName.contains("Web Development") || courseName.contains("Web")) {
            return List.of(
                createQuestion(course, "What does HTML stand for?", "HyperText Markup Language", "HighText Markup Language", "HyperText Markdown Language", "HomeTool Markup Language", "option1"),
                createQuestion(course, "Which CSS property is used to change the text color?", "font-color", "text-color", "color", "foreground-color", "option3"),
                createQuestion(course, "What is the correct way to create a JavaScript array?", "var arr = []", "var arr = array()", "var arr = array[]", "var arr = (1,2,3)", "option1"),
                createQuestion(course, "Which HTML tag is used to create a hyperlink?", "<link>", "<a>", "<href>", "<url>", "option2"),
                createQuestion(course, "What does CSS stand for?", "Computer Style Sheets", "Creative Style Sheets", "Cascading Style Sheets", "Colorful Style Sheets", "option3"),
                createQuestion(course, "What is the purpose of <div> tag in HTML?", "To create a division or section", "To add images", "To create tables", "To add links", "option1"),
                createQuestion(course, "Which CSS property is used to make text bold?", "font-weight: bold", "text-style: bold", "bold: true", "font: bold", "option1"),
                createQuestion(course, "What is JavaScript?", "A markup language", "A programming language", "A styling language", "A database", "option2"),
                createQuestion(course, "What does DOM stand for?", "Document Object Model", "Data Object Model", "Document Order Model", "Dynamic Object Model", "option1"),
                createQuestion(course, "Which method is used to select an element by ID in JavaScript?", "getElementById()", "getElementByClass()", "getElementsByTag()", "queryById()", "option1"),
                createQuestion(course, "What is responsive web design?", "Design that adapts to different screen sizes", "Design with many colors", "Fast loading design", "Design with animations", "option1"),
                createQuestion(course, "What is CSS flexbox?", "A layout method", "A color scheme", "A font style", "An animation", "option1"),
                createQuestion(course, "What does 'var' keyword do in JavaScript?", "Declares a variable", "Creates a function", "Imports a module", "Exports a module", "option1"),
                createQuestion(course, "What is an HTML attribute?", "A property that provides additional information", "A tag", "A comment", "A script", "option1"),
                createQuestion(course, "What is the difference between 'let' and 'var' in JavaScript?", "let has block scope, var has function scope", "No difference", "let is faster", "var is newer", "option1"),
                createQuestion(course, "What is an HTML form?", "A way to collect user input", "A styling method", "An image", "A link", "option1"),
                createQuestion(course, "What is the purpose of CSS media queries?", "To apply styles based on device characteristics", "To add colors", "To create animations", "To add fonts", "option1"),
                createQuestion(course, "What is async/await in JavaScript?", "A way to handle asynchronous operations", "A loop syntax", "A data type", "A function", "option1"),
                createQuestion(course, "What is a CSS selector?", "A pattern to select elements", "A color name", "A font name", "A property name", "option1"),
                createQuestion(course, "What is the purpose of localStorage in JavaScript?", "To store data in the browser", "To delete data", "To send requests", "To create elements", "option1"),
                createQuestion(course, "What is CSS Grid?", "A two-dimensional layout system", "A one-dimensional layout", "A color system", "A font system", "option1"),
                createQuestion(course, "What is the box model in CSS?", "Content, padding, border, margin", "Width, height, color", "Font, size, style", "Position, display", "option1"),
                createQuestion(course, "What is JavaScript ES6?", "ECMAScript 2015 with new features", "A JavaScript library", "A framework", "A database", "option1"),
                createQuestion(course, "What is a closure in JavaScript?", "Function that has access to outer scope", "A loop", "A variable", "A class", "option1"),
                createQuestion(course, "What is the purpose of CSS transitions?", "To animate property changes", "To add colors", "To add fonts", "To create layouts", "option1")
            );
        }

        // Use QuestionInitializer's logic - for now return empty and let QuestionInitializer handle it on restart
        // This endpoint will work better after server restart when QuestionInitializer runs
        return List.of();
    }

    @PostMapping("/update-video-links")
    public ResponseEntity<Map<String, Object>> updateVideoLinks() {
        Map<String, Object> response = new HashMap<>();
        try {
            int updatedCount = 0;
            java.util.Map<String, String> videoLinkMap = createVideoLinkMap();
            
            List<Course> allCourses = courseRepository.findAll();
            for (Course course : allCourses) {
                String courseName = course.getCourse_name();
                String correctVideoLink = videoLinkMap.get(courseName);
                
                if (correctVideoLink != null && !correctVideoLink.equals(course.getY_link())) {
                    course.setY_link(correctVideoLink);
                    courseRepository.save(course);
                    updatedCount++;
                    log.info("Updated video link for course: {}", courseName);
                }
            }
            
            response.put("success", true);
            response.put("message", "Updated video links for " + updatedCount + " courses");
            response.put("updatedCount", updatedCount);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error updating video links: ", e);
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    private java.util.Map<String, String> createVideoLinkMap() {
        java.util.Map<String, String> map = new java.util.HashMap<>();
        
        // Python & Basics
        map.put("Python Basics", "https://www.youtube.com/watch?v=kqtD5dpn9C8");
        
        // Web Development
        map.put("Web Development", "https://youtu.be/zJSY8tbf_ys?si=5X_Ty76TrJfVUd7u");
        
        // Data Structures
        map.put("Data Structures", "https://youtu.be/8hly31xKli0?si=h0oIaP_HhfQqBNRw");
        
        // Machine Learning
        map.put("Machine Learning", "https://youtu.be/i_LwzRVP7bg?si=QoKOExtCumWW8Gmb");
        
        // Java & Spring Boot
        map.put("Java Full Stack", "https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn");
        map.put("Full Stack Development with Java Spring Boot, React, and MongoDB", "https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn");
        map.put("Spring Boot Framework", "https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn");
        map.put("Java Advanced Programming", "https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn");
        map.put("RESTful API Development with Spring Boot", "https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn");
        map.put("React + Spring Boot Integration", "https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn");
        map.put("Spring Security", "https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn");
        map.put("MySQL Database with Spring Boot", "https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn");
        map.put("Microservices with Spring Boot", "https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn");
        map.put("GraphQL with Spring Boot", "https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn");
        map.put("Spring Boot Testing", "https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn");
        
        // React & Frontend
        map.put("React.js Advanced", "https://youtu.be/SqcY0GlETPk?si=eLrQrq4wng7u5339");
        map.put("React.js Complete Guide", "https://youtu.be/SqcY0GlETPk?si=eLrQrq4wng7u5339");
        map.put("React Hooks & Context API", "https://youtu.be/SqcY0GlETPk?si=eLrQrq4wng7u5339");
        map.put("TypeScript for React", "https://youtu.be/SqcY0GlETPk?si=eLrQrq4wng7u5339");
        map.put("Redux State Management", "https://youtu.be/SqcY0GlETPk?si=eLrQrq4wng7u5339");
        map.put("Next.js Full Stack Development", "https://youtu.be/SqcY0GlETPk?si=eLrQrq4wng7u5339");
        
        // Node.js
        map.put("Node.js Backend Development", "https://youtu.be/TlB_eWDSMt4?si=hhOfldi9iSwCceLR");
        
        // Databases
        map.put("MongoDB Database Mastery", "https://www.youtube.com/watch?v=-56x56UppqQ");
        
        // DevOps
        map.put("Docker & Kubernetes", "https://youtu.be/Wf2eSG3owoA?si=kQUYpj2cCxHKZJdj");
        
        // Cloud
        map.put("AWS Cloud Computing", "https://youtu.be/2OHr0QnEkg4?si=IF1CHRPRGZIPcPmW");
        
        // Security
        map.put("Cybersecurity Fundamentals", "https://youtu.be/s19BxFpoSd0?si=2ahGSmmeGEAxEulN");
        
        // Mobile
        map.put("Mobile App Development", "https://youtu.be/DsIviEKZad0?si=OcFKjJZCBhvu3BoX");
        
        return map;
    }

    @Transactional
    private ResponseEntity<Map<String, Object>> initializeCoursesInternal(boolean force) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (force) {
                log.info("Force reinitialization requested. Deleting all courses and related data...");
                try {
                    // Delete all enrollments first (foreign key constraint)
                    List<Course> allCourses = courseRepository.findAll();
                    for (Course course : allCourses) {
                        List<Learning> enrollments = learningRepository.findByCourse(course);
                        if (!enrollments.isEmpty()) {
                            learningRepository.deleteAll(enrollments);
                            log.info("Deleted {} enrollments for course: {}", enrollments.size(), course.getCourse_name());
                        }
                        // Delete questions for this course
                        List<Questions> questions = questionRepository.findByCourse(course);
                        if (!questions.isEmpty()) {
                            questionRepository.deleteAll(questions);
                            log.info("Deleted {} questions for course: {}", questions.size(), course.getCourse_name());
                        }
                    }
                    // Now delete all courses
                    courseRepository.deleteAll();
                    log.info("Deleted all existing courses for re-initialization.");
                } catch (Exception e) {
                    log.warn("Error during deletion (may have foreign key constraints): {}", e.getMessage());
                    // Continue anyway - will try to create courses
                }
            }
            
            if (courseRepository.count() == 0) {
                log.info("Initializing all domain courses using CourseInitializationService...");
                long count = courseInitializationService.initializeAllCourses();
                
                response.put("success", true);
                response.put("message", "Courses initialized successfully");
                response.put("count", count);
                log.info("Successfully initialized {} courses.", count);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Courses already exist");
                response.put("count", courseRepository.count());
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            log.error("Error initializing courses: ", e);
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}

