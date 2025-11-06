package com.lms.dev.config;

import com.lms.dev.entity.Course;
import com.lms.dev.entity.Questions;
import com.lms.dev.repository.CourseRepository;
import com.lms.dev.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class QuestionInitializer {

    private final CourseRepository courseRepository;
    private final QuestionRepository questionRepository;

    @Bean
    public CommandLineRunner initializeQuestions() {
        return args -> {
            try {
                List<Course> allCourses;
                try {
                    allCourses = courseRepository.findAll();
                } catch (Exception e) {
                    log.warn("Could not fetch courses for question initialization. This may be due to database schema issues. Error: {}", e.getMessage());
                    return; // Skip question initialization if we can't fetch courses
                }
                log.info("Found {} courses. Initializing questions for each course...", allCourses.size());

                int totalQuestionsAdded = 0;

                for (Course course : allCourses) {
                    String courseName = course.getCourse_name();
                    List<Questions> existingQuestions = questionRepository.findByCourse(course);
                    
                    // Always ensure courses have questions (delete existing and recreate if needed)
                    if (existingQuestions.size() < 25) {
                        // Delete existing questions if any
                        if (!existingQuestions.isEmpty()) {
                            questionRepository.deleteAll(existingQuestions);
                            log.info("Deleted {} existing questions for course: {}", existingQuestions.size(), courseName);
                        }
                        
                        // Create new questions
                        List<Questions> questions = createQuestionsForCourse(courseName, course);
                        if (!questions.isEmpty()) {
                            questionRepository.saveAll(questions);
                            totalQuestionsAdded += questions.size();
                            log.info("Added {} questions for course: {}", questions.size(), courseName);
                        } else {
                            log.warn("No questions created for course: {}", courseName);
                        }
                    } else {
                        log.info("Course '{}' already has {} questions (>= 25), skipping...", courseName, existingQuestions.size());
                    }
                }

                log.info("Question initialization complete! Total questions added: {}", totalQuestionsAdded);
            } catch (Exception e) {
                log.error("Error during question initialization: ", e);
            }
        };
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
        // Python Basics
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
                createQuestion(course, "What is the result of: [1,2,3] + [4,5]?", "[1,2,3,4,5]", "[5,7]", "Error", "[1,2,3],[4,5]", "option1")
            );
        }

        // Web Development
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
                createQuestion(course, "What is the purpose of localStorage in JavaScript?", "To store data in the browser", "To delete data", "To send requests", "To create elements", "option1")
            );
        }

        // Data Structures
        if (courseName.contains("Data Structures")) {
            return List.of(
                createQuestion(course, "What is the time complexity of accessing an element in an array by index?", "O(n)", "O(log n)", "O(1)", "O(n log n)", "option3"),
                createQuestion(course, "Which data structure follows LIFO (Last In First Out) principle?", "Queue", "Stack", "Array", "Linked List", "option2"),
                createQuestion(course, "What is the time complexity of binary search on a sorted array?", "O(n)", "O(log n)", "O(n log n)", "O(1)", "option2"),
                createQuestion(course, "Which of the following is a linear data structure?", "Tree", "Graph", "Array", "Heap", "option3"),
                createQuestion(course, "What is the maximum number of children a node can have in a binary tree?", "1", "2", "3", "Unlimited", "option2"),
                createQuestion(course, "Which data structure follows FIFO (First In First Out) principle?", "Stack", "Queue", "Tree", "Graph", "option2"),
                createQuestion(course, "What is a linked list?", "A collection of nodes connected by pointers", "An array", "A tree", "A graph", "option1"),
                createQuestion(course, "What is the time complexity of inserting at the beginning of a linked list?", "O(n)", "O(log n)", "O(1)", "O(n log n)", "option3"),
                createQuestion(course, "What is a hash table?", "A data structure that maps keys to values", "A tree structure", "A sorted array", "A linked list", "option1"),
                createQuestion(course, "What is the average time complexity of hash table lookup?", "O(n)", "O(log n)", "O(1)", "O(n log n)", "option3"),
                createQuestion(course, "What is a binary search tree?", "A tree where left child < parent < right child", "A tree with two roots", "A tree with unlimited children", "A tree with one node", "option1"),
                createQuestion(course, "What is the time complexity of searching in a balanced BST?", "O(n)", "O(log n)", "O(1)", "O(n log n)", "option2"),
                createQuestion(course, "What is an AVL tree?", "A self-balancing binary search tree", "A regular binary tree", "A linked list", "An array", "option1"),
                createQuestion(course, "What is depth-first search (DFS)?", "A traversal algorithm", "A sorting algorithm", "A searching algorithm", "A hashing algorithm", "option1"),
                createQuestion(course, "What is breadth-first search (BFS)?", "A traversal algorithm using a queue", "A sorting algorithm", "A hashing algorithm", "A compression algorithm", "option1"),
                createQuestion(course, "What is a heap?", "A complete binary tree with heap property", "A sorted array", "A linked list", "A hash table", "option1"),
                createQuestion(course, "What is the time complexity of heap insertion?", "O(n)", "O(log n)", "O(1)", "O(n log n)", "option2"),
                createQuestion(course, "What is dynamic programming?", "Solving problems by breaking into subproblems", "A data structure", "A sorting algorithm", "A searching algorithm", "option1"),
                createQuestion(course, "What is the difference between stack and queue?", "Stack is LIFO, Queue is FIFO", "No difference", "Stack is FIFO, Queue is LIFO", "Both are same", "option1"),
                createQuestion(course, "What is the time complexity of quicksort in average case?", "O(n)", "O(n log n)", "O(n²)", "O(log n)", "option2")
            );
        }

        // Machine Learning
        if (courseName.contains("Machine Learning")) {
            return List.of(
                createQuestion(course, "What is the main difference between supervised and unsupervised learning?", "Supervised uses labeled data, unsupervised uses unlabeled data", "Supervised is faster", "Unsupervised is more accurate", "No difference", "option1"),
                createQuestion(course, "Which algorithm is commonly used for classification?", "K-Means", "Linear Regression", "Decision Tree", "KNN", "option3"),
                createQuestion(course, "What is overfitting in machine learning?", "Model performs well on training but poorly on test data", "Model is too simple", "Model trains too slowly", "Model doesn't train at all", "option1"),
                createQuestion(course, "What is a neural network?", "A database structure", "A computing system inspired by biological neural networks", "A networking protocol", "A type of algorithm", "option2"),
                createQuestion(course, "What does 'epoch' mean in training a neural network?", "One complete pass through the training dataset", "A type of error", "A learning rate", "A network layer", "option1"),
                createQuestion(course, "What is gradient descent?", "An optimization algorithm", "A data structure", "A type of neural network", "A loss function", "option1"),
                createQuestion(course, "What is cross-validation?", "A technique to assess model performance", "A data structure", "An algorithm", "A neural network type", "option1"),
                createQuestion(course, "What is feature engineering?", "Process of selecting and transforming features", "Creating new algorithms", "Training models", "Deploying models", "option1"),
                createQuestion(course, "What is a confusion matrix?", "A table to evaluate classification performance", "A data structure", "An algorithm", "A neural network", "option1"),
                createQuestion(course, "What is regularization?", "A technique to prevent overfitting", "A sorting algorithm", "A data structure", "A type of neural network", "option1"),
                createQuestion(course, "What is deep learning?", "A subset of machine learning using neural networks", "A database", "A programming language", "A framework", "option1"),
                createQuestion(course, "What is backpropagation?", "Algorithm for training neural networks", "A data structure", "A sorting algorithm", "A search algorithm", "option1"),
                createQuestion(course, "What is a convolutional neural network (CNN)?", "A neural network for image processing", "A type of database", "A sorting algorithm", "A search algorithm", "option1"),
                createQuestion(course, "What is natural language processing (NLP)?", "Field of AI for understanding human language", "A database", "A programming language", "A framework", "option1"),
                createQuestion(course, "What is reinforcement learning?", "Learning through interaction and rewards", "Supervised learning", "Unsupervised learning", "A database", "option1"),
                createQuestion(course, "What is transfer learning?", "Using pre-trained models for new tasks", "Training from scratch", "A data structure", "An algorithm", "option1"),
                createQuestion(course, "What is precision in classification?", "True positives / (True positives + False positives)", "True positives / (True positives + False negatives)", "Accuracy", "Recall", "option1"),
                createQuestion(course, "What is recall in classification?", "True positives / (True positives + False negatives)", "True positives / (True positives + False positives)", "Accuracy", "Precision", "option1"),
                createQuestion(course, "What is a random forest?", "An ensemble learning method", "A single tree", "A data structure", "An algorithm", "option1"),
                createQuestion(course, "What is hyperparameter tuning?", "Adjusting parameters not learned during training", "Training the model", "Collecting data", "Deploying models", "option1")
            );
        }

        // Java Full Stack / Spring Boot
        if (courseName.contains("Java") || courseName.contains("Spring Boot") || courseName.contains("Spring")) {
            return List.of(
                createQuestion(course, "What is JVM in Java?", "Java Virtual Machine", "Java Variable Method", "Java Version Manager", "Java Vector Machine", "option1"),
                createQuestion(course, "Which keyword is used to inherit a class in Java?", "implements", "extends", "inherits", "super", "option2"),
                createQuestion(course, "What is the default value of a local variable in Java?", "0", "null", "Depends on data type", "No default value", "option4"),
                createQuestion(course, "Which annotation is used to mark a Spring Boot application entry point?", "@Component", "@SpringBootApplication", "@Service", "@Repository", "option2"),
                createQuestion(course, "What does REST stand for?", "Representational State Transfer", "Restful State Transfer", "Remote State Transfer", "Resource State Transfer", "option1"),
                createQuestion(course, "What is a package in Java?", "A collection of related classes", "A single class", "A method", "A variable", "option1"),
                createQuestion(course, "What is the difference between == and equals() in Java?", "== compares references, equals() compares values", "== compares values, equals() compares references", "No difference", "== is for numbers only", "option1"),
                createQuestion(course, "What is an interface in Java?", "A class that cannot be instantiated", "A contract that classes can implement", "A method", "A variable", "option2"),
                createQuestion(course, "What is polymorphism in Java?", "One name, many forms", "One form, many names", "Multiple inheritance", "Single inheritance", "option1"),
                createQuestion(course, "What is Spring Framework?", "A Java framework for building enterprise applications", "A database", "A programming language", "An IDE", "option1"),
                createQuestion(course, "What is dependency injection in Spring?", "A design pattern for loose coupling", "A database injection", "A security feature", "A testing method", "option1"),
                createQuestion(course, "What is @Autowired annotation in Spring?", "Automatically injects dependencies", "Creates a bean", "Deletes a bean", "Marks a class", "option1"),
                createQuestion(course, "What is Spring Data JPA?", "A framework for data access", "A database", "A programming language", "A testing tool", "option1"),
                createQuestion(course, "What is a Spring Bean?", "An object managed by Spring container", "A database bean", "A coffee bean", "A Java bean", "option1"),
                createQuestion(course, "What is the default scope of a Spring Bean?", "singleton", "prototype", "request", "session", "option1"),
                createQuestion(course, "What is @Controller annotation in Spring?", "Marks a class as a web controller", "Marks a service class", "Marks a repository", "Marks a component", "option1"),
                createQuestion(course, "What is @RequestMapping in Spring?", "Maps HTTP requests to handler methods", "Maps database tables", "Maps URLs", "Maps services", "option1"),
                createQuestion(course, "What is Spring Security?", "A framework for authentication and authorization", "A database", "A programming language", "A testing tool", "option1"),
                createQuestion(course, "What is Maven in Java?", "A build automation tool", "A framework", "A database", "An IDE", "option1"),
                createQuestion(course, "What is the purpose of pom.xml in Maven?", "Project Object Model - defines project configuration", "A Java class", "A database", "A service", "option1")
            );
        }

        // React.js
        if (courseName.contains("React")) {
            return List.of(
                createQuestion(course, "What is React?", "A database", "A JavaScript library for building user interfaces", "A programming language", "A framework for backend", "option2"),
                createQuestion(course, "What is a React Hook?", "A way to connect components", "Functions that let you use state and lifecycle features", "A type of component", "A styling method", "option2"),
                createQuestion(course, "Which hook is used to manage state in functional components?", "useEffect", "useState", "useContext", "useReducer", "option2"),
                createQuestion(course, "What does JSX stand for?", "JavaScript XML", "Java Syntax Extension", "JavaScript Extension", "Java XML", "option1"),
                createQuestion(course, "How do you pass data from parent to child component in React?", "Using props", "Using state", "Using refs", "Using hooks", "option1"),
                createQuestion(course, "What is useEffect hook used for?", "To perform side effects in functional components", "To manage state", "To create components", "To style components", "option1"),
                createQuestion(course, "What is a React component?", "A reusable piece of UI", "A database", "A function", "A variable", "option1"),
                createQuestion(course, "What is the Virtual DOM in React?", "A representation of the DOM in memory", "The actual DOM", "A database", "A framework", "option1"),
                createQuestion(course, "What is the purpose of keys in React lists?", "To help React identify which items changed", "To add styling", "To add events", "To add properties", "option1"),
                createQuestion(course, "What is React Router?", "A library for routing in React applications", "A database", "A framework", "A styling tool", "option1"),
                createQuestion(course, "What is the difference between props and state?", "Props are passed from parent, state is managed internally", "No difference", "Props are internal, state is external", "Both are same", "option1"),
                createQuestion(course, "What is a controlled component in React?", "Component whose value is controlled by React state", "A component with no state", "A database component", "A service component", "option1"),
                createQuestion(course, "What is React Context?", "A way to share data without prop drilling", "A database", "A component", "A hook", "option1"),
                createQuestion(course, "What is useReducer hook?", "An alternative to useState for complex state logic", "A database hook", "A component hook", "A styling hook", "option1"),
                createQuestion(course, "What is React.memo?", "A higher-order component for performance optimization", "A database", "A hook", "A component", "option1"),
                createQuestion(course, "What is the purpose of useCallback hook?", "To memoize functions", "To create callbacks", "To handle events", "To manage state", "option1"),
                createQuestion(course, "What is useMemo hook used for?", "To memoize expensive computations", "To manage memory", "To create components", "To handle events", "option1"),
                createQuestion(course, "What is a React Fragment?", "A way to group elements without adding extra nodes", "A database", "A component", "A hook", "option1"),
                createQuestion(course, "What is conditional rendering in React?", "Rendering different content based on conditions", "Rendering always", "Rendering once", "Rendering never", "option1"),
                createQuestion(course, "What is the difference between class and functional components?", "Functional components use hooks, class components use lifecycle methods", "No difference", "Class components are faster", "Functional components are slower", "option1")
            );
        }

        // Node.js
        if (courseName.contains("Node.js") || courseName.contains("Node")) {
            return List.of(
                createQuestion(course, "What is Node.js?", "A JavaScript framework", "A JavaScript runtime built on Chrome's V8 engine", "A database", "A programming language", "option2"),
                createQuestion(course, "Which module is used to create a web server in Node.js?", "http", "fs", "path", "crypto", "option1"),
                createQuestion(course, "What does NPM stand for?", "Node Package Manager", "Node Program Manager", "Node Process Manager", "Node Project Manager", "option1"),
                createQuestion(course, "What is the default package manager for Node.js?", "yarn", "npm", "pnpm", "bower", "option2"),
                createQuestion(course, "What is the purpose of package.json in Node.js?", "To define project metadata and dependencies", "To store user data", "To configure the server", "To define routes", "option1"),
                createQuestion(course, "What is Express.js?", "A web framework for Node.js", "A database", "A programming language", "An IDE", "option1"),
                createQuestion(course, "What is middleware in Express.js?", "Functions that execute during request-response cycle", "Routes", "Controllers", "Models", "option1"),
                createQuestion(course, "What is the Node.js event loop?", "Mechanism that handles asynchronous operations", "A database loop", "A for loop", "A while loop", "option1"),
                createQuestion(course, "What is npm install used for?", "To install package dependencies", "To uninstall packages", "To update packages", "To list packages", "option1"),
                createQuestion(course, "What is require() in Node.js?", "Function to import modules", "A function to export modules", "A database function", "A routing function", "option1"),
                createQuestion(course, "What is module.exports in Node.js?", "Way to export functionality from a module", "Way to import modules", "A database", "A framework", "option1"),
                createQuestion(course, "What is the fs module in Node.js?", "File system module for file operations", "A database module", "A networking module", "A security module", "option1"),
                createQuestion(course, "What is a callback in Node.js?", "A function passed as argument to another function", "A database", "A module", "A package", "option1"),
                createQuestion(course, "What is a Promise in JavaScript/Node.js?", "An object representing eventual completion of async operation", "A database", "A function", "A variable", "option1"),
                createQuestion(course, "What is async/await in Node.js?", "Syntactic sugar for Promises", "A database", "A module", "A package", "option1"),
                createQuestion(course, "What is RESTful API?", "API following REST principles", "A database", "A framework", "A programming language", "option1"),
                createQuestion(course, "What is JSON?", "JavaScript Object Notation - data format", "A database", "A programming language", "A framework", "option1"),
                createQuestion(course, "What is CORS?", "Cross-Origin Resource Sharing", "A database", "A framework", "A programming language", "option1"),
                createQuestion(course, "What is the purpose of body-parser in Express?", "To parse request bodies", "To parse responses", "To parse headers", "To parse URLs", "option1"),
                createQuestion(course, "What is a route handler in Express?", "Function that handles requests for specific routes", "A database handler", "A file handler", "An error handler", "option1")
            );
        }

        // Docker & Kubernetes
        if (courseName.contains("Docker") || courseName.contains("Kubernetes")) {
            return List.of(
                createQuestion(course, "What is Docker?", "A programming language", "A containerization platform", "A database", "A cloud service", "option2"),
                createQuestion(course, "What is a Docker image?", "A running container", "A template for creating containers", "A network configuration", "A volume", "option2"),
                createQuestion(course, "What is Kubernetes?", "A container runtime", "A container orchestration platform", "A cloud provider", "A database", "option2"),
                createQuestion(course, "What is a Kubernetes Pod?", "A container image", "The smallest deployable unit in Kubernetes", "A network policy", "A storage volume", "option2"),
                createQuestion(course, "What does 'docker run' command do?", "Builds an image", "Creates and starts a container from an image", "Stops a container", "Removes a container", "option2"),
                createQuestion(course, "What is a Dockerfile?", "A text file with instructions to build a Docker image", "A running container", "A Docker command", "A Docker volume", "option1"),
                createQuestion(course, "What is Docker Compose?", "Tool for defining and running multi-container applications", "A single container", "A Docker image", "A Docker volume", "option1"),
                createQuestion(course, "What is a Docker container?", "An instance of a Docker image", "A Docker image", "A Dockerfile", "A Docker volume", "option1"),
                createQuestion(course, "What is the purpose of Docker volumes?", "To persist data outside containers", "To create containers", "To delete containers", "To build images", "option1"),
                createQuestion(course, "What is Kubernetes deployment?", "A resource that manages Pod replicas", "A Pod", "A Node", "A Service", "option1"),
                createQuestion(course, "What is a Kubernetes Service?", "An abstraction that exposes Pods as network service", "A Pod", "A Deployment", "A Node", "option1"),
                createQuestion(course, "What is a Kubernetes Namespace?", "A way to divide cluster resources", "A Pod", "A Service", "A Node", "option1"),
                createQuestion(course, "What is docker build command used for?", "To build an image from a Dockerfile", "To run a container", "To stop a container", "To remove a container", "option1"),
                createQuestion(course, "What is container orchestration?", "Managing multiple containers across multiple machines", "Creating a container", "Deleting a container", "Building an image", "option1"),
                createQuestion(course, "What is a Kubernetes Node?", "A worker machine in Kubernetes", "A Pod", "A Service", "A Deployment", "option1"),
                createQuestion(course, "What is kubectl?", "Command-line tool for Kubernetes", "A container", "An image", "A volume", "option1"),
                createQuestion(course, "What is Docker Hub?", "A cloud-based registry for Docker images", "A container", "A Docker command", "A volume", "option1"),
                createQuestion(course, "What is containerization?", "Packaging applications with dependencies", "Creating containers", "Deleting containers", "Running containers", "option1"),
                createQuestion(course, "What is the difference between Docker and Virtual Machines?", "Docker uses OS-level virtualization, VMs use hardware virtualization", "No difference", "Docker is slower", "VMs are faster", "option1"),
                createQuestion(course, "What is Kubernetes ConfigMap?", "Stores configuration data", "Stores secret data", "Stores volume data", "Stores Pod data", "option1")
            );
        }

        // AWS Cloud Computing
        if (courseName.contains("AWS") || courseName.contains("Cloud")) {
            return List.of(
                createQuestion(course, "What does AWS stand for?", "Amazon Web Services", "Amazon Web Solutions", "Azure Web Services", "Application Web Services", "option1"),
                createQuestion(course, "What is Amazon EC2?", "A database service", "A virtual server in the cloud", "A storage service", "A networking service", "option2"),
                createQuestion(course, "What is Amazon S3?", "A compute service", "Object storage service", "A database", "A load balancer", "option2"),
                createQuestion(course, "What is AWS Lambda?", "A serverless compute service", "A database", "A storage service", "A networking service", "option1"),
                createQuestion(course, "Which AWS service is used for content delivery?", "EC2", "S3", "CloudFront", "Route 53", "option3"),
                createQuestion(course, "What is Amazon RDS?", "Relational Database Service", "Redis Database Service", "Resource Database Service", "Remote Database Service", "option1"),
                createQuestion(course, "What is AWS VPC?", "Virtual Private Cloud - isolated network", "Virtual Public Cloud", "Virtual Processing Cloud", "Virtual Package Cloud", "option1"),
                createQuestion(course, "What is an IAM role in AWS?", "Identity and Access Management role", "Internet Access Management", "Internal Access Management", "Identity Access Method", "option1"),
                createQuestion(course, "What is Auto Scaling in AWS?", "Automatically adjust capacity", "Manual scaling", "Fixed scaling", "No scaling", "option1"),
                createQuestion(course, "What is Amazon CloudWatch?", "Monitoring and logging service", "Storage service", "Compute service", "Database service", "option1"),
                createQuestion(course, "What is Elastic Load Balancer (ELB)?", "Distributes traffic across targets", "Stores data", "Processes data", "Monitors data", "option1"),
                createQuestion(course, "What is AWS Region?", "Geographic area with multiple Availability Zones", "A single data center", "A service", "A database", "option1"),
                createQuestion(course, "What is AWS Availability Zone?", "Isolated location within a region", "A region", "A service", "A database", "option1"),
                createQuestion(course, "What is S3 bucket?", "Container for storing objects", "A database", "A compute instance", "A network", "option1"),
                createQuestion(course, "What is EC2 instance type?", "Specification of CPU, memory, storage, networking", "A database type", "A storage type", "A network type", "option1"),
                createQuestion(course, "What is AWS CloudFormation?", "Infrastructure as Code tool", "A database", "A compute service", "A storage service", "option1"),
                createQuestion(course, "What is Route 53?", "DNS web service", "A database", "A compute service", "A storage service", "option1"),
                createQuestion(course, "What is Amazon EBS?", "Elastic Block Store for persistent storage", "A database", "A compute service", "A networking service", "option1"),
                createQuestion(course, "What is serverless computing?", "Cloud computing model where provider manages servers", "No servers needed", "Manual server management", "Physical servers only", "option1"),
                createQuestion(course, "What is the pay-as-you-go model in AWS?", "Pay only for what you use", "Pay upfront", "Pay monthly", "Pay yearly", "option1")
            );
        }

        // Cybersecurity
        if (courseName.contains("Cybersecurity") || courseName.contains("Security")) {
            return List.of(
                createQuestion(course, "What is a firewall?", "A security software that monitors network traffic", "A database", "A programming language", "A cloud service", "option1"),
                createQuestion(course, "What is SQL Injection?", "A database attack", "A code injection technique", "A network protocol", "A firewall rule", "option2"),
                createQuestion(course, "What is encryption?", "Converting plain text to unreadable format", "Deleting data", "Copying data", "Compressing data", "option1"),
                createQuestion(course, "What is a DDoS attack?", "Distributed Denial of Service attack", "Data Deletion attack", "Direct Database attack", "Device Destruction attack", "option1"),
                createQuestion(course, "What does HTTPS stand for?", "HyperText Transfer Protocol Secure", "HyperText Transfer Protocol", "HyperText Transfer Privacy", "HyperText Transfer Service", "option1"),
                createQuestion(course, "What is XSS (Cross-Site Scripting)?", "Injection of malicious scripts into web pages", "A database attack", "A network attack", "A physical attack", "option1"),
                createQuestion(course, "What is two-factor authentication (2FA)?", "Using two methods to verify identity", "One password", "No authentication", "Single method", "option1"),
                createQuestion(course, "What is a vulnerability?", "A weakness that can be exploited", "A strength", "A feature", "A bug fix", "option1"),
                createQuestion(course, "What is penetration testing?", "Authorized simulated attack to test security", "Unauthorized attack", "A database test", "A network test", "option1"),
                createQuestion(course, "What is SSL/TLS?", "Protocols for secure communication", "A database", "A programming language", "A framework", "option1"),
                createQuestion(course, "What is a phishing attack?", "Fraudulent attempt to obtain sensitive information", "A database attack", "A network attack", "A physical attack", "option1"),
                createQuestion(course, "What is malware?", "Malicious software", "Good software", "System software", "Application software", "option1"),
                createQuestion(course, "What is a VPN?", "Virtual Private Network for secure connection", "A database", "A programming language", "A framework", "option1"),
                createQuestion(course, "What is authentication?", "Verifying identity", "Verifying data", "Verifying network", "Verifying system", "option1"),
                createQuestion(course, "What is authorization?", "Granting access rights", "Verifying identity", "Encrypting data", "Decrypting data", "option1"),
                createQuestion(course, "What is a security patch?", "Update to fix security vulnerabilities", "A database patch", "A network patch", "A system patch", "option1"),
                createQuestion(course, "What is a zero-day vulnerability?", "Unknown vulnerability with no fix available", "A fixed vulnerability", "A known vulnerability", "A minor vulnerability", "option1"),
                createQuestion(course, "What is data encryption at rest?", "Encrypting stored data", "Encrypting transmitted data", "Encrypting processed data", "Encrypting deleted data", "option1"),
                createQuestion(course, "What is a security audit?", "Review of security policies and controls", "A database audit", "A network audit", "A system audit", "option1"),
                createQuestion(course, "What is a honeypot?", "Decoy system to attract attackers", "A security system", "A database", "A network", "option1")
            );
        }

        // Mobile App Development
        if (courseName.contains("Mobile") || courseName.contains("App Development")) {
            return List.of(
                createQuestion(course, "What is React Native?", "A database", "A framework for building native mobile apps using React", "A programming language", "A cloud service", "option2"),
                createQuestion(course, "What is Flutter?", "A UI toolkit for building mobile apps", "A database", "A programming language", "A server framework", "option1"),
                createQuestion(course, "What is an APK file?", "Android application package", "Apple package", "Application protocol", "Application program", "option1"),
                createQuestion(course, "What is responsive design in mobile development?", "Design that adapts to different screen sizes", "Fast loading design", "Colorful design", "Large text design", "option1"),
                createQuestion(course, "What is the main difference between native and hybrid mobile apps?", "Native uses platform-specific code, hybrid uses web technologies", "No difference", "Native is slower", "Hybrid uses native code", "option1"),
                createQuestion(course, "What is an iOS app bundle?", "Package containing iOS app and resources", "An Android package", "A database", "A framework", "option1"),
                createQuestion(course, "What is Material Design?", "Design language by Google", "Design language by Apple", "A database", "A framework", "option1"),
                createQuestion(course, "What is an API in mobile development?", "Application Programming Interface", "Application Package Interface", "Application Process Interface", "Application Program Interface", "option1"),
                createQuestion(course, "What is push notification?", "Message sent to mobile device from server", "A local notification", "A database", "A framework", "option1"),
                createQuestion(course, "What is offline-first architecture?", "App works without internet connection", "App requires internet", "App is online only", "App has no data", "option1"),
                createQuestion(course, "What is a mobile SDK?", "Software Development Kit for mobile platforms", "A database", "A framework", "A programming language", "option1"),
                createQuestion(course, "What is app state management?", "Managing application data and UI state", "Managing device state", "Managing server state", "Managing database state", "option1"),
                createQuestion(course, "What is deep linking in mobile apps?", "Linking directly to specific content in app", "Linking to web", "Linking to email", "Linking to SMS", "option1"),
                createQuestion(course, "What is app performance optimization?", "Improving app speed and responsiveness", "Adding features", "Removing features", "Changing design", "option1"),
                createQuestion(course, "What is a mobile app lifecycle?", "States an app goes through from launch to termination", "App installation", "App uninstallation", "App update", "option1"),
                createQuestion(course, "What is platform-specific code?", "Code written for specific platform (iOS/Android)", "Generic code", "Web code", "Server code", "option1"),
                createQuestion(course, "What is a mobile app store?", "Marketplace for distributing mobile apps", "A database", "A framework", "A programming language", "option1"),
                createQuestion(course, "What is app versioning?", "Numbering system for app releases", "App design", "App development", "App testing", "option1"),
                createQuestion(course, "What is mobile app testing?", "Process of verifying app functionality", "App design", "App development", "App deployment", "option1"),
                createQuestion(course, "What is progressive web app (PWA)?", "Web app that works like native app", "Native app", "Desktop app", "Server app", "option1")
            );
        }

        // MongoDB
        if (courseName.contains("MongoDB")) {
            return List.of(
                createQuestion(course, "What is MongoDB?", "A relational database", "A NoSQL document database", "A programming language", "A framework", "option2"),
                createQuestion(course, "What is a document in MongoDB?", "A file", "A record stored as BSON", "A table", "A column", "option2"),
                createQuestion(course, "What is a collection in MongoDB?", "A group of documents", "A table", "A database", "A query", "option1"),
                createQuestion(course, "Which command is used to create a database in MongoDB?", "CREATE DATABASE", "use databaseName", "NEW DATABASE", "ADD DATABASE", "option2"),
                createQuestion(course, "What does BSON stand for?", "Binary JSON", "Base JSON", "Basic JSON", "Big JSON", "option1"),
                createQuestion(course, "What is the difference between MongoDB and SQL databases?", "MongoDB is document-based, SQL is table-based", "No difference", "MongoDB is slower", "SQL is faster", "option1"),
                createQuestion(course, "What is a MongoDB query?", "Operation to retrieve documents", "A database", "A collection", "A document", "option1"),
                createQuestion(course, "What is indexing in MongoDB?", "Data structure to improve query performance", "A collection", "A document", "A database", "option1"),
                createQuestion(course, "What is aggregation in MongoDB?", "Process of transforming documents", "Creating documents", "Deleting documents", "Updating documents", "option1"),
                createQuestion(course, "What is sharding in MongoDB?", "Horizontal partitioning of data", "Vertical partitioning", "Creating backups", "Deleting data", "option1"),
                createQuestion(course, "What is a MongoDB replica set?", "Group of MongoDB instances with same data", "A single instance", "A collection", "A document", "option1"),
                createQuestion(course, "What is the _id field in MongoDB?", "Unique identifier for each document", "A regular field", "A collection name", "A database name", "option1"),
                createQuestion(course, "What is MongoDB Compass?", "GUI tool for MongoDB", "A database", "A programming language", "A framework", "option1"),
                createQuestion(course, "What is embedded document in MongoDB?", "Document within another document", "A standalone document", "A collection", "A database", "option1"),
                createQuestion(course, "What is reference in MongoDB?", "Storing reference to another document", "Storing the document itself", "Deleting documents", "Updating documents", "option1"),
                createQuestion(course, "What is updateOne() in MongoDB?", "Updates a single document", "Updates all documents", "Creates a document", "Deletes a document", "option1"),
                createQuestion(course, "What is findOne() in MongoDB?", "Returns one document matching query", "Returns all documents", "Creates a document", "Deletes a document", "option1"),
                createQuestion(course, "What is MongoDB Atlas?", "Cloud-hosted MongoDB service", "A local MongoDB", "A database", "A framework", "option1"),
                createQuestion(course, "What is MongoDB schema?", "Structure of documents", "A collection", "A database", "A query", "option1"),
                createQuestion(course, "What is MongoDB transaction?", "Atomic operation on multiple documents", "A single document operation", "A collection operation", "A database operation", "option1")
            );
        }

        // TypeScript
        if (courseName.contains("TypeScript")) {
            return List.of(
                createQuestion(course, "What is TypeScript?", "A database", "A typed superset of JavaScript", "A programming language", "A framework", "option2"),
                createQuestion(course, "What file extension does TypeScript use?", ".js", ".ts", ".tsx", ".java", "option2"),
                createQuestion(course, "What is the main advantage of TypeScript over JavaScript?", "Static typing", "Faster execution", "Smaller file size", "No compilation needed", "option1"),
                createQuestion(course, "How do you define a type in TypeScript?", "Using 'type' keyword", "Using 'var'", "Using 'let'", "Using 'const'", "option1"),
                createQuestion(course, "What does TypeScript compile to?", "Python", "Java", "JavaScript", "C++", "option3"),
                createQuestion(course, "What is an interface in TypeScript?", "Contract that defines structure", "A class", "A function", "A variable", "option1"),
                createQuestion(course, "What is a type assertion in TypeScript?", "Telling compiler the type of a value", "Creating a type", "Deleting a type", "Updating a type", "option1"),
                createQuestion(course, "What is a union type in TypeScript?", "Type that can be one of several types", "A single type", "Multiple types combined", "No type", "option1"),
                createQuestion(course, "What is an enum in TypeScript?", "A way to define named constants", "A class", "A function", "A variable", "option1"),
                createQuestion(course, "What is a generic in TypeScript?", "Reusable component that works with multiple types", "A specific type", "A class", "A function", "option1"),
                createQuestion(course, "What is readonly in TypeScript?", "Property that cannot be reassigned", "A property that can be reassigned", "A method", "A class", "option1"),
                createQuestion(course, "What is optional property in TypeScript?", "Property that may or may not exist", "Required property", "Read-only property", "Private property", "option1"),
                createQuestion(course, "What is a class in TypeScript?", "Blueprint for creating objects", "A type", "A function", "A variable", "option1"),
                createQuestion(course, "What is inheritance in TypeScript?", "Mechanism to create new class from existing class", "Creating a class", "Deleting a class", "Updating a class", "option1"),
                createQuestion(course, "What is decorator in TypeScript?", "Special kind of declaration", "A type", "A function", "A variable", "option1"),
                createQuestion(course, "What is namespace in TypeScript?", "Container for related code", "A type", "A function", "A variable", "option1"),
                createQuestion(course, "What is tsconfig.json?", "TypeScript configuration file", "A TypeScript file", "A JavaScript file", "A JSON file", "option1"),
                createQuestion(course, "What is type inference in TypeScript?", "Automatic type detection", "Manual type assignment", "Type deletion", "Type update", "option1"),
                createQuestion(course, "What is strict mode in TypeScript?", "Enables strict type checking", "Disables type checking", "Optional type checking", "No type checking", "option1"),
                createQuestion(course, "What is the difference between 'any' and 'unknown' in TypeScript?", "any disables type checking, unknown requires type checking", "No difference", "unknown disables checking", "any requires checking", "option1")
            );
        }

        // MySQL / Database
        if (courseName.contains("MySQL") || courseName.contains("Database")) {
            return List.of(
                createQuestion(course, "What is MySQL?", "A NoSQL database", "A relational database management system", "A programming language", "A framework", "option2"),
                createQuestion(course, "What is SQL?", "Structured Query Language", "Simple Query Language", "Sequential Query Language", "Standard Query Language", "option1"),
                createQuestion(course, "Which SQL command is used to retrieve data?", "INSERT", "UPDATE", "SELECT", "DELETE", "option3"),
                createQuestion(course, "What is a primary key?", "A foreign key", "A unique identifier for a record", "A column name", "A table name", "option2"),
                createQuestion(course, "What does JOIN do in SQL?", "Combines rows from two or more tables", "Deletes tables", "Creates tables", "Updates tables", "option1"),
                createQuestion(course, "What is a foreign key?", "Key that references primary key of another table", "A primary key", "A unique key", "An index", "option1"),
                createQuestion(course, "What is normalization in databases?", "Process of organizing data to reduce redundancy", "Adding redundancy", "Deleting data", "Updating data", "option1"),
                createQuestion(course, "What is ACID in databases?", "Properties of database transactions", "A database type", "A query type", "A table type", "option1"),
                createQuestion(course, "What is an index in a database?", "Data structure to improve query speed", "A table", "A column", "A row", "option1"),
                createQuestion(course, "What is a transaction in SQL?", "Sequence of operations as single unit", "A single operation", "A query", "A table", "option1"),
                createQuestion(course, "What is COMMIT in SQL?", "Saves transaction changes", "Rolls back changes", "Starts transaction", "Ends transaction", "option1"),
                createQuestion(course, "What is ROLLBACK in SQL?", "Undoes transaction changes", "Saves changes", "Starts transaction", "Ends transaction", "option1"),
                createQuestion(course, "What is GROUP BY in SQL?", "Groups rows with same values", "Selects rows", "Deletes rows", "Updates rows", "option1"),
                createQuestion(course, "What is HAVING clause in SQL?", "Filters groups after GROUP BY", "Filters rows before grouping", "Selects columns", "Orders rows", "option1"),
                createQuestion(course, "What is a view in SQL?", "Virtual table based on query result", "A physical table", "A database", "A column", "option1"),
                createQuestion(course, "What is a stored procedure?", "Precompiled SQL code", "A table", "A view", "A function", "option1"),
                createQuestion(course, "What is a trigger in SQL?", "Procedure that runs automatically on events", "A table", "A view", "A function", "option1"),
                createQuestion(course, "What is database normalization form?", "Level of data organization", "A table type", "A query type", "A data type", "option1"),
                createQuestion(course, "What is SQL injection?", "Code injection attack technique", "A database feature", "A query type", "A table type", "option1"),
                createQuestion(course, "What is a subquery in SQL?", "Query nested inside another query", "A main query", "A join query", "A union query", "option1")
            );
        }

        // Redux
        if (courseName.contains("Redux")) {
            return List.of(
                createQuestion(course, "What is Redux?", "A database", "A state management library for JavaScript applications", "A programming language", "A framework", "option2"),
                createQuestion(course, "What is the main purpose of Redux?", "To manage application state", "To style components", "To handle routing", "To manage packages", "option1"),
                createQuestion(course, "What are the three main concepts in Redux?", "Components, Props, State", "Store, Actions, Reducers", "Models, Views, Controllers", "Routes, Services, Controllers", "option2"),
                createQuestion(course, "What is an action in Redux?", "A function that modifies state", "An object that describes what happened", "A component", "A reducer", "option2"),
                createQuestion(course, "What is a reducer in Redux?", "A function that takes state and action, returns new state", "A component", "An action creator", "A middleware", "option1"),
                createQuestion(course, "What is the Redux store?", "Single source of truth for application state", "A component", "An action", "A reducer", "option1"),
                createQuestion(course, "What is dispatch in Redux?", "Method to send actions to store", "A component", "A reducer", "An action", "option1"),
                createQuestion(course, "What is an action creator?", "Function that creates actions", "A component", "A reducer", "A store", "option1"),
                createQuestion(course, "What is Redux middleware?", "Extension point between dispatching and reducer", "A component", "A reducer", "An action", "option1"),
                createQuestion(course, "What is Redux Thunk?", "Middleware for async actions", "A component", "A reducer", "A store", "option1"),
                createQuestion(course, "What is immutability in Redux?", "State cannot be modified directly", "State can be modified", "No state", "Mutable state", "option1"),
                createQuestion(course, "What is Redux Toolkit?", "Official toolset for Redux", "A component library", "A routing library", "A styling library", "option1"),
                createQuestion(course, "What is useSelector hook?", "Hook to read data from Redux store", "Hook to write data", "Hook to delete data", "Hook to update data", "option1"),
                createQuestion(course, "What is useDispatch hook?", "Hook to dispatch actions", "Hook to read state", "Hook to write state", "Hook to delete state", "option1"),
                createQuestion(course, "What is combineReducers in Redux?", "Combines multiple reducers into one", "Splits reducers", "Deletes reducers", "Updates reducers", "option1"),
                createQuestion(course, "What is Redux DevTools?", "Browser extension for debugging Redux", "A component", "A reducer", "An action", "option1"),
                createQuestion(course, "What is middleware chain in Redux?", "Sequence of middleware functions", "A single middleware", "No middleware", "All middleware", "option1"),
                createQuestion(course, "What is a selector in Redux?", "Function to extract data from state", "A component", "A reducer", "An action", "option1"),
                createQuestion(course, "What is Redux Saga?", "Library for managing side effects", "A component", "A reducer", "A store", "option1"),
                createQuestion(course, "What is the difference between Redux and Context API?", "Redux has single store, Context has multiple providers", "No difference", "Context is faster", "Redux is simpler", "option1")
            );
        }

        // GraphQL
        if (courseName.contains("GraphQL")) {
            return List.of(
                createQuestion(course, "What is GraphQL?", "A database", "A query language for APIs", "A programming language", "A framework", "option2"),
                createQuestion(course, "What is the main advantage of GraphQL over REST?", "Faster queries", "Fetch exactly the data you need", "No server needed", "Simpler syntax", "option2"),
                createQuestion(course, "What are the three main operations in GraphQL?", "GET, POST, PUT", "Query, Mutation, Subscription", "Read, Write, Delete", "Create, Update, Delete", "option2"),
                createQuestion(course, "What is a schema in GraphQL?", "A database structure", "A definition of the data structure and operations", "A component", "A route", "option2"),
                createQuestion(course, "What does GraphQL use to describe data?", "XML", "JSON", "Type system", "YAML", "option3"),
                createQuestion(course, "What is a resolver in GraphQL?", "Function that resolves a field", "A query", "A mutation", "A subscription", "option1"),
                createQuestion(course, "What is a query in GraphQL?", "Read operation to fetch data", "Write operation", "Delete operation", "Update operation", "option1"),
                createQuestion(course, "What is a mutation in GraphQL?", "Write operation to modify data", "Read operation", "Delete operation", "Update operation", "option1"),
                createQuestion(course, "What is a subscription in GraphQL?", "Real-time data operation", "Query operation", "Mutation operation", "Delete operation", "option1"),
                createQuestion(course, "What is GraphQL schema definition language (SDL)?", "Syntax for defining GraphQL schema", "A query language", "A mutation language", "A subscription language", "option1"),
                createQuestion(course, "What is introspection in GraphQL?", "Querying schema structure", "Querying data", "Updating data", "Deleting data", "option1"),
                createQuestion(course, "What is a GraphQL fragment?", "Reusable piece of query", "A mutation", "A subscription", "A resolver", "option1"),
                createQuestion(course, "What is GraphQL variable?", "Dynamic value in query", "A constant", "A type", "A schema", "option1"),
                createQuestion(course, "What is GraphQL alias?", "Rename result field", "A type", "A query", "A mutation", "option1"),
                createQuestion(course, "What is GraphQL directive?", "Modify query execution", "A type", "A query", "A mutation", "option1"),
                createQuestion(course, "What is Apollo Client?", "GraphQL client library", "A database", "A framework", "A programming language", "option1"),
                createQuestion(course, "What is GraphQL Playground?", "Interactive GraphQL IDE", "A database", "A framework", "A programming language", "option1"),
                createQuestion(course, "What is N+1 problem in GraphQL?", "Multiple queries for related data", "Single query", "No queries", "All queries", "option1"),
                createQuestion(course, "What is DataLoader in GraphQL?", "Solution for batching and caching", "A query", "A mutation", "A subscription", "option1"),
                createQuestion(course, "What is GraphQL federation?", "Composing multiple GraphQL services", "A single service", "No service", "All services", "option1")
            );
        }

        // Next.js
        if (courseName.contains("Next.js") || courseName.contains("Next")) {
            return List.of(
                createQuestion(course, "What is Next.js?", "A database", "A React framework for production", "A programming language", "A state management tool", "option2"),
                createQuestion(course, "What is Server-Side Rendering (SSR) in Next.js?", "Rendering pages on the client", "Rendering pages on the server", "Rendering components", "Rendering styles", "option2"),
                createQuestion(course, "What folder is used for API routes in Next.js?", "api/", "routes/", "pages/api/", "server/", "option3"),
                createQuestion(course, "What is Static Site Generation (SSG) in Next.js?", "Generating pages at build time", "Generating pages at runtime", "Generating components", "Generating styles", "option1"),
                createQuestion(course, "Which command is used to create a new Next.js app?", "create-next-app", "npm init next", "next create", "new next-app", "option1"),
                createQuestion(course, "What is getServerSideProps in Next.js?", "Server-side function to fetch data", "Client-side function", "Static function", "No function", "option1"),
                createQuestion(course, "What is getStaticProps in Next.js?", "Static generation function", "Server-side function", "Client-side function", "Dynamic function", "option1"),
                createQuestion(course, "What is Incremental Static Regeneration (ISR)?", "Updating static pages after build", "Building static pages", "Deleting static pages", "Updating dynamic pages", "option1"),
                createQuestion(course, "What is next/link used for?", "Client-side navigation", "Server-side navigation", "External navigation", "No navigation", "option1"),
                createQuestion(course, "What is next/image used for?", "Optimized image component", "Regular image", "Background image", "Icon image", "option1"),
                createQuestion(course, "What is Next.js router?", "File-based routing system", "Component-based routing", "Manual routing", "No routing", "option1"),
                createQuestion(course, "What is dynamic routing in Next.js?", "Routes with parameters", "Static routes", "Fixed routes", "No routes", "option1"),
                createQuestion(course, "What is middleware in Next.js?", "Code that runs before request completes", "Code after request", "Code during request", "No code", "option1"),
                createQuestion(course, "What is Next.js API route?", "Serverless function endpoint", "Client function", "Static function", "Dynamic function", "option1"),
                createQuestion(course, "What is _app.js in Next.js?", "Custom App component", "A page", "A component", "A layout", "option1"),
                createQuestion(course, "What is _document.js in Next.js?", "Custom Document component", "A page", "A component", "A layout", "option1"),
                createQuestion(course, "What is next.config.js?", "Next.js configuration file", "A page", "A component", "A layout", "option1"),
                createQuestion(course, "What is environment variables in Next.js?", "Configuration stored in .env file", "A page", "A component", "A layout", "option1"),
                createQuestion(course, "What is Next.js head component?", "Modifies HTML head section", "Modifies body", "Modifies footer", "Modifies content", "option1"),
                createQuestion(course, "What is code splitting in Next.js?", "Automatic code splitting for optimization", "Manual splitting", "No splitting", "Full splitting", "option1")
            );
        }

        // Microservices
        if (courseName.contains("Microservices")) {
            return List.of(
                createQuestion(course, "What are microservices?", "Small databases", "Small, independent services that work together", "Small components", "Small functions", "option2"),
                createQuestion(course, "What is the main advantage of microservices architecture?", "Faster development", "Independent deployment and scaling", "Smaller codebase", "Less complexity", "option2"),
                createQuestion(course, "What is API Gateway in microservices?", "A database gateway", "A single entry point for client requests", "A service mesh", "A load balancer", "option2"),
                createQuestion(course, "What is service discovery in microservices?", "Finding lost services", "Mechanism for services to find and communicate with each other", "Discovering APIs", "Finding databases", "option2"),
                createQuestion(course, "What is circuit breaker pattern used for?", "Breaking circuits", "Preventing cascade failures in distributed systems", "Breaking services", "Breaking databases", "option2"),
                createQuestion(course, "What is a service mesh?", "Infrastructure layer for service-to-service communication", "A single service", "A database", "A framework", "option1"),
                createQuestion(course, "What is container orchestration?", "Managing containerized microservices", "Creating containers", "Deleting containers", "Building containers", "option1"),
                createQuestion(course, "What is distributed tracing?", "Tracking requests across multiple services", "Tracking single service", "Tracking database", "Tracking network", "option1"),
                createQuestion(course, "What is eventual consistency?", "System eventually becomes consistent", "Immediate consistency", "No consistency", "Partial consistency", "option1"),
                createQuestion(course, "What is saga pattern in microservices?", "Managing distributed transactions", "Managing single transaction", "Managing database", "Managing network", "option1"),
                createQuestion(course, "What is API versioning in microservices?", "Managing different versions of APIs", "Single API version", "No versioning", "All versions", "option1"),
                createQuestion(course, "What is database per service pattern?", "Each service has its own database", "Shared database", "No database", "All databases", "option1"),
                createQuestion(course, "What is event-driven architecture?", "Services communicate through events", "Services communicate directly", "Services don't communicate", "Services communicate through database", "option1"),
                createQuestion(course, "What is service registry?", "Database of service instances", "A single service", "A database", "A framework", "option1"),
                createQuestion(course, "What is load balancing in microservices?", "Distributing traffic across instances", "Concentrating traffic", "No traffic", "All traffic", "option1"),
                createQuestion(course, "What is health check in microservices?", "Monitoring service availability", "Checking database", "Checking network", "Checking disk", "option1"),
                createQuestion(course, "What is blue-green deployment?", "Deployment strategy with two environments", "Single environment", "No environment", "All environments", "option1"),
                createQuestion(course, "What is canary deployment?", "Gradual rollout to subset of users", "Full rollout", "No rollout", "Partial rollout", "option1"),
                createQuestion(course, "What is service monitoring?", "Observing service behavior and performance", "Observing database", "Observing network", "Observing disk", "option1"),
                createQuestion(course, "What is distributed logging?", "Collecting logs from multiple services", "Collecting from single service", "No logging", "All logging", "option1")
            );
        }

        // Testing
        if (courseName.contains("Testing") || courseName.contains("Test")) {
            return List.of(
                createQuestion(course, "What is unit testing?", "Testing the entire application", "Testing individual components or functions", "Testing the database", "Testing the UI", "option2"),
                createQuestion(course, "What is integration testing?", "Testing individual units", "Testing how different parts work together", "Testing the database", "Testing the UI", "option2"),
                createQuestion(course, "What is Test-Driven Development (TDD)?", "Writing tests after code", "Writing tests before writing code", "Writing code without tests", "Writing tests manually", "option2"),
                createQuestion(course, "What is a mock in testing?", "A real object", "A fake object that simulates behavior", "A test case", "A test framework", "option2"),
                createQuestion(course, "What is code coverage?", "Amount of code tested", "Percentage of code executed by tests", "Number of tests", "Test execution time", "option2"),
                createQuestion(course, "What is a test stub?", "Simplified implementation for testing", "Full implementation", "No implementation", "Partial implementation", "option1"),
                createQuestion(course, "What is end-to-end (E2E) testing?", "Testing entire application flow", "Testing single unit", "Testing database", "Testing network", "option1"),
                createQuestion(course, "What is regression testing?", "Testing to ensure new changes don't break existing features", "Testing new features", "Testing database", "Testing network", "option1"),
                createQuestion(course, "What is a test fixture?", "Test data and setup", "A test case", "A test framework", "A test runner", "option1"),
                createQuestion(course, "What is assertion in testing?", "Statement that verifies expected behavior", "A test case", "A test framework", "A test runner", "option1"),
                createQuestion(course, "What is test isolation?", "Tests should not depend on each other", "Tests depend on each other", "No tests", "All tests", "option1"),
                createQuestion(course, "What is a test double?", "Replacement for real dependency in testing", "Real dependency", "No dependency", "Partial dependency", "option1"),
                createQuestion(course, "What is behavior-driven development (BDD)?", "Writing tests in natural language", "Writing tests in code", "Writing no tests", "Writing manual tests", "option1"),
                createQuestion(course, "What is performance testing?", "Testing system under load", "Testing functionality", "Testing database", "Testing network", "option1"),
                createQuestion(course, "What is smoke testing?", "Basic test to verify system works", "Comprehensive testing", "No testing", "Partial testing", "option1"),
                createQuestion(course, "What is test automation?", "Running tests automatically", "Running tests manually", "No tests", "All tests", "option1"),
                createQuestion(course, "What is continuous integration?", "Automatically running tests on code changes", "Running tests manually", "No tests", "All tests", "option1"),
                createQuestion(course, "What is a test suite?", "Collection of test cases", "Single test", "No tests", "All tests", "option1"),
                createQuestion(course, "What is acceptance testing?", "Testing if system meets requirements", "Testing functionality", "Testing database", "Testing network", "option1"),
                createQuestion(course, "What is white-box testing?", "Testing with knowledge of internal structure", "Testing without knowledge", "No testing", "All testing", "option1")
            );
        }

        // Default questions for courses that don't match
        return List.of(
            createQuestion(course, "What is the main topic covered in this course?", "Topic A", "Topic B", "Topic C", "Topic D", "option1"),
            createQuestion(course, "Which concept is fundamental to understanding this course?", "Concept A", "Concept B", "Concept C", "Concept D", "option2"),
            createQuestion(course, "What is the primary learning objective of this course?", "Objective A", "Objective B", "Objective C", "Objective D", "option3")
        );
    }
}
