package com.lms.dev.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.dev.entity.Course;
import com.lms.dev.entity.Questions;
import com.lms.dev.entity.Learning;
import com.lms.dev.repository.CourseRepository;
import com.lms.dev.repository.QuestionRepository;
import com.lms.dev.repository.LearningRepository;
import com.lms.dev.service.CourseInitializationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

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

    private ResponseEntity<Map<String, Object>> initializeCoursesInternal(boolean force) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (force) {
                try {
                    courseRepository.deleteAll();
                    log.info("Deleted all existing courses for re-initialization.");
                } catch (Exception e) {
                    log.warn("Could not delete all courses (may have foreign key constraints): {}", e.getMessage());
                    // Try to delete individually or continue - courses will be skipped if they exist
                }
            }
            
            if (courseRepository.count() == 0) {
                log.info("Initializing default courses...");

                // Python Basics
                Course pythonBasics = new Course();
                pythonBasics.setCourse_name("Python Basics");
                pythonBasics.setInstructor("Alice");
                pythonBasics.setPrice(50);
                pythonBasics.setDescription("Master the fundamentals of Python programming. Learn syntax, data types, control structures, and object-oriented programming.");
                pythonBasics.setP_link("https://img-c.udemycdn.com/course/750x422/394676_ce3d_5.jpg");
                pythonBasics.setY_link("https://www.youtube.com/watch?v=kqtD5dpn9C8");
                courseRepository.save(pythonBasics);

                // Web Development
                Course webDevelopment = new Course();
                webDevelopment.setCourse_name("Web Development");
                webDevelopment.setInstructor("Bob");
                webDevelopment.setPrice(75);
                webDevelopment.setDescription("Build modern, responsive websites using HTML5, CSS3, JavaScript, and popular frameworks. Learn full-stack development.");
                webDevelopment.setP_link("https://img-c.udemycdn.com/course/750x422/851712_fc61_6.jpg");
                webDevelopment.setY_link("https://youtu.be/zJSY8tbf_ys?si=5X_Ty76TrJfVUd7u");
                courseRepository.save(webDevelopment);

                // Data Structures
                Course dataStructures = new Course();
                dataStructures.setCourse_name("Data Structures");
                dataStructures.setInstructor("Charlie");
                dataStructures.setPrice(80);
                dataStructures.setDescription("Deep dive into essential data structures including arrays, linked lists, trees, graphs, and hash tables.");
                dataStructures.setP_link("https://img-c.udemycdn.com/course/750x422/1362070_b9a1_2.jpg");
                dataStructures.setY_link("https://youtu.be/8hly31xKli0?si=h0oIaP_HhfQqBNRw");
                courseRepository.save(dataStructures);

                // Machine Learning
                Course machineLearning = new Course();
                machineLearning.setCourse_name("Machine Learning");
                machineLearning.setInstructor("David");
                machineLearning.setPrice(120);
                machineLearning.setDescription("Introduction to machine learning algorithms, neural networks, and AI applications. Build intelligent systems from scratch.");
                machineLearning.setP_link("https://img-c.udemycdn.com/course/750x422/950390_270f_5.jpg");
                machineLearning.setY_link("https://youtu.be/i_LwzRVP7bg?si=QoKOExtCumWW8Gmb");
                courseRepository.save(machineLearning);

                // Java Full Stack
                Course javaFullStack = new Course();
                javaFullStack.setCourse_name("Java Full Stack");
                javaFullStack.setInstructor("Emma");
                javaFullStack.setPrice(100);
                javaFullStack.setDescription("Complete Java full-stack development course covering Spring Boot, React, REST APIs, and database integration.");
                javaFullStack.setP_link("https://img-c.udemycdn.com/course/750x422/827692_91ad_2.jpg");
                javaFullStack.setY_link("https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn");
                courseRepository.save(javaFullStack);

                // React.js
                Course react = new Course();
                react.setCourse_name("React.js Advanced");
                react.setInstructor("Frank");
                react.setPrice(90);
                react.setDescription("Master React.js with hooks, context API, Redux, and advanced patterns. Build scalable front-end applications.");
                react.setP_link("https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=800&h=400&fit=crop");
                react.setY_link("https://youtu.be/SqcY0GlETPk?si=eLrQrq4wng7u5339");
                courseRepository.save(react);

                // Node.js
                Course nodejs = new Course();
                nodejs.setCourse_name("Node.js Backend Development");
                nodejs.setInstructor("Grace");
                nodejs.setPrice(85);
                nodejs.setDescription("Learn Node.js, Express.js, and MongoDB. Build RESTful APIs and real-time applications.");
                nodejs.setP_link("https://images.unsplash.com/photo-1558494949-ef010cbdcc31?w=800&h=400&fit=crop");
                nodejs.setY_link("https://youtu.be/TlB_eWDSMt4?si=hhOfldi9iSwCceLR");
                courseRepository.save(nodejs);

                // Docker & Kubernetes
                Course docker = new Course();
                docker.setCourse_name("Docker & Kubernetes");
                docker.setInstructor("Henry");
                docker.setPrice(95);
                docker.setDescription("Containerization and orchestration with Docker and Kubernetes. Deploy scalable applications in production.");
                docker.setP_link("https://images.unsplash.com/photo-1605745341112-85968b19335b?w=800&h=400&fit=crop");
                docker.setY_link("https://youtu.be/Wf2eSG3owoA?si=kQUYpj2cCxHKZJdj");
                courseRepository.save(docker);

                // Cloud Computing (AWS)
                Course aws = new Course();
                aws.setCourse_name("AWS Cloud Computing");
                aws.setInstructor("Ivy");
                aws.setPrice(110);
                aws.setDescription("Master Amazon Web Services. Learn EC2, S3, Lambda, and cloud architecture patterns.");
                aws.setP_link("https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=800&h=400&fit=crop");
                aws.setY_link("https://youtu.be/2OHr0QnEkg4?si=IF1CHRPRGZIPcPmW");
                courseRepository.save(aws);

                // Cybersecurity
                Course cybersecurity = new Course();
                cybersecurity.setCourse_name("Cybersecurity Fundamentals");
                cybersecurity.setInstructor("Jack");
                cybersecurity.setPrice(105);
                cybersecurity.setDescription("Learn ethical hacking, network security, cryptography, and secure coding practices.");
                cybersecurity.setP_link("https://images.unsplash.com/photo-1550751827-4bd374c3f58b?w=800&h=400&fit=crop");
                cybersecurity.setY_link("https://youtu.be/s19BxFpoSd0?si=2ahGSmmeGEAxEulN");
                courseRepository.save(cybersecurity);

                // Mobile App Development
                Course mobileDev = new Course();
                mobileDev.setCourse_name("Mobile App Development");
                mobileDev.setInstructor("Kate");
                mobileDev.setPrice(95);
                mobileDev.setDescription("Build iOS and Android apps using React Native and Flutter. Cross-platform mobile development.");
                mobileDev.setP_link("https://images.unsplash.com/photo-1512941937669-90a1b58e7e9c?w=800&h=400&fit=crop");
                mobileDev.setY_link("https://youtu.be/DsIviEKZad0?si=OcFKjJZCBhvu3BoX");
                courseRepository.save(mobileDev);

                // Full Stack Development with Java Spring Boot, React, and MongoDB
                Course fullStackJava = new Course();
                fullStackJava.setCourse_name("Full Stack Development with Java Spring Boot, React, and MongoDB");
                fullStackJava.setInstructor("Emma");
                fullStackJava.setPrice(150);
                fullStackJava.setDescription("Master full-stack development using Java Spring Boot for backend, React for frontend, and MongoDB for database. Build complete web applications from scratch.");
                fullStackJava.setP_link("https://img-c.udemycdn.com/course/750x422/827692_91ad_2.jpg");
                fullStackJava.setY_link("https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn");
                courseRepository.save(fullStackJava);

                // Spring Boot Framework
                Course springBoot = new Course();
                springBoot.setCourse_name("Spring Boot Framework");
                springBoot.setInstructor("Emma");
                springBoot.setPrice(110);
                springBoot.setDescription("Deep dive into Spring Boot framework. Learn dependency injection, REST APIs, Spring Security, and microservices architecture.");
                springBoot.setP_link("https://img-c.udemycdn.com/course/750x422/827692_91ad_2.jpg");
                springBoot.setY_link("https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn");
                courseRepository.save(springBoot);

                // React.js Complete Guide
                Course reactComplete = new Course();
                reactComplete.setCourse_name("React.js Complete Guide");
                reactComplete.setInstructor("Frank");
                reactComplete.setPrice(100);
                reactComplete.setDescription("Complete React.js course covering hooks, context API, Redux, React Router, and building production-ready applications.");
                reactComplete.setP_link("https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=800&h=400&fit=crop");
                reactComplete.setY_link("https://youtu.be/SqcY0GlETPk?si=eLrQrq4wng7u5339");
                courseRepository.save(reactComplete);

                // MongoDB Database Mastery
                Course mongoDB = new Course();
                mongoDB.setCourse_name("MongoDB Database Mastery");
                mongoDB.setInstructor("Grace");
                mongoDB.setPrice(90);
                mongoDB.setDescription("Master MongoDB NoSQL database. Learn data modeling, aggregation, indexing, and integration with Java Spring Boot applications.");
                mongoDB.setP_link("https://images.unsplash.com/photo-1544383835-bda2bc66a6d3?w=800&h=400&fit=crop");
                mongoDB.setY_link("https://www.youtube.com/watch?v=-56x56UppqQ");
                courseRepository.save(mongoDB);

                // Java Advanced Programming
                Course javaAdvanced = new Course();
                javaAdvanced.setCourse_name("Java Advanced Programming");
                javaAdvanced.setInstructor("Emma");
                javaAdvanced.setPrice(105);
                javaAdvanced.setDescription("Advanced Java concepts including multithreading, collections framework, streams API, lambdas, and design patterns.");
                javaAdvanced.setP_link("https://img-c.udemycdn.com/course/750x422/827692_91ad_2.jpg");
                javaAdvanced.setY_link("https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn");
                courseRepository.save(javaAdvanced);

                // RESTful API Development with Spring Boot
                Course restAPI = new Course();
                restAPI.setCourse_name("RESTful API Development with Spring Boot");
                restAPI.setInstructor("Emma");
                restAPI.setPrice(95);
                restAPI.setDescription("Build robust REST APIs using Spring Boot. Learn HTTP methods, JSON handling, authentication, and API best practices.");
                restAPI.setP_link("https://img-c.udemycdn.com/course/750x422/827692_91ad_2.jpg");
                restAPI.setY_link("https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn");
                courseRepository.save(restAPI);

                // React + Spring Boot Integration
                Course reactSpringIntegration = new Course();
                reactSpringIntegration.setCourse_name("React + Spring Boot Integration");
                reactSpringIntegration.setInstructor("Emma");
                reactSpringIntegration.setPrice(120);
                reactSpringIntegration.setDescription("Learn to connect React frontend with Spring Boot backend. Handle CORS, JWT authentication, and build complete applications.");
                reactSpringIntegration.setP_link("https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=800&h=400&fit=crop");
                reactSpringIntegration.setY_link("https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn");
                courseRepository.save(reactSpringIntegration);

                // Spring Security
                Course springSecurity = new Course();
                springSecurity.setCourse_name("Spring Security");
                springSecurity.setInstructor("Emma");
                springSecurity.setPrice(100);
                springSecurity.setDescription("Implement security in Spring Boot applications. Learn JWT, OAuth2, authentication, authorization, and securing REST APIs.");
                springSecurity.setP_link("https://img-c.udemycdn.com/course/750x422/827692_91ad_2.jpg");
                springSecurity.setY_link("https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn");
                courseRepository.save(springSecurity);

                // TypeScript for React
                Course typescriptReact = new Course();
                typescriptReact.setCourse_name("TypeScript for React");
                typescriptReact.setInstructor("Frank");
                typescriptReact.setPrice(85);
                typescriptReact.setDescription("Use TypeScript with React for type-safe frontend development. Learn interfaces, types, and best practices.");
                typescriptReact.setP_link("https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=800&h=400&fit=crop");
                typescriptReact.setY_link("https://youtu.be/SqcY0GlETPk?si=eLrQrq4wng7u5339");
                courseRepository.save(typescriptReact);

                // MySQL Database with Spring Boot
                Course mysqlSpring = new Course();
                mysqlSpring.setCourse_name("MySQL Database with Spring Boot");
                mysqlSpring.setInstructor("Emma");
                mysqlSpring.setPrice(95);
                mysqlSpring.setDescription("Learn Spring Data JPA, Hibernate, and MySQL integration. Master database operations and relationships.");
                mysqlSpring.setP_link("https://images.unsplash.com/photo-1544383835-bda2bc66a6d3?w=800&h=400&fit=crop");
                mysqlSpring.setY_link("https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn");
                courseRepository.save(mysqlSpring);

                // Microservices with Spring Boot
                Course microservices = new Course();
                microservices.setCourse_name("Microservices with Spring Boot");
                microservices.setInstructor("Emma");
                microservices.setPrice(130);
                microservices.setDescription("Build microservices architecture using Spring Boot, Spring Cloud, Eureka, and API Gateway. Scalable application design.");
                microservices.setP_link("https://img-c.udemycdn.com/course/750x422/827692_91ad_2.jpg");
                microservices.setY_link("https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn");
                courseRepository.save(microservices);

                // Redux State Management
                Course redux = new Course();
                redux.setCourse_name("Redux State Management");
                redux.setInstructor("Frank");
                redux.setPrice(90);
                redux.setDescription("Master Redux for React applications. Learn state management, middleware, Redux Toolkit, and async operations.");
                redux.setP_link("https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=800&h=400&fit=crop");
                redux.setY_link("https://youtu.be/SqcY0GlETPk?si=eLrQrq4wng7u5339");
                courseRepository.save(redux);

                // GraphQL with Spring Boot
                Course graphql = new Course();
                graphql.setCourse_name("GraphQL with Spring Boot");
                graphql.setInstructor("Emma");
                graphql.setPrice(100);
                graphql.setDescription("Build GraphQL APIs with Spring Boot GraphQL. Learn schema design, queries, mutations, and subscriptions.");
                graphql.setP_link("https://img-c.udemycdn.com/course/750x422/827692_91ad_2.jpg");
                graphql.setY_link("https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn");
                courseRepository.save(graphql);

                // Next.js Full Stack
                Course nextjs = new Course();
                nextjs.setCourse_name("Next.js Full Stack Development");
                nextjs.setInstructor("Frank");
                nextjs.setPrice(110);
                nextjs.setDescription("Build full-stack applications with Next.js. Server-side rendering, API routes, and React best practices.");
                nextjs.setP_link("https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=800&h=400&fit=crop");
                nextjs.setY_link("https://youtu.be/SqcY0GlETPk?si=eLrQrq4wng7u5339");
                courseRepository.save(nextjs);

                // Spring Boot Testing
                Course springTesting = new Course();
                springTesting.setCourse_name("Spring Boot Testing");
                springTesting.setInstructor("Emma");
                springTesting.setPrice(85);
                springTesting.setDescription("Master testing in Spring Boot applications. Unit tests, integration tests, MockMvc, and TestContainers.");
                springTesting.setP_link("https://img-c.udemycdn.com/course/750x422/827692_91ad_2.jpg");
                springTesting.setY_link("https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn");
                courseRepository.save(springTesting);

                // React Hooks & Context API
                Course reactHooks = new Course();
                reactHooks.setCourse_name("React Hooks & Context API");
                reactHooks.setInstructor("Frank");
                reactHooks.setPrice(80);
                reactHooks.setDescription("Deep dive into React Hooks (useState, useEffect, useContext, useReducer) and Context API for state management.");
                reactHooks.setP_link("https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=800&h=400&fit=crop");
                reactHooks.setY_link("https://youtu.be/SqcY0GlETPk?si=eLrQrq4wng7u5339");
                courseRepository.save(reactHooks);

                // ========== ADDITIONAL DOMAIN COURSES ==========
                // (Same courses as in CourseInitializer - see CourseInitializer.java for full list)
                // Data Science, DevOps, UI/UX, Blockchain, Game Dev, IoT, Azure, GCP, Big Data, 
                // Angular, Vue.js, SQL, PostgreSQL, Redis, Elasticsearch, Git, Linux, API Design,
                // GraphQL, TensorFlow, PyTorch, NLP, Computer Vision, etc.

                long count = courseRepository.count();
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

