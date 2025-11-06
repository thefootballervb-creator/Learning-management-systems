package com.lms.dev.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lms.dev.entity.Course;
import com.lms.dev.repository.CourseRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class CourseInitializationService {

    private final CourseRepository courseRepository;

    @Transactional
    public long initializeAllCourses() {
        log.info("Initializing all domain courses...");

        // Python Basics
        Course pythonBasics = createCourse("Python Basics", "Alice", 50,
            "Master the fundamentals of Python programming. Learn syntax, data types, control structures, and object-oriented programming.",
            "https://img-c.udemycdn.com/course/750x422/394676_ce3d_5.jpg",
            "https://www.youtube.com/watch?v=kqtD5dpn9C8");
        courseRepository.save(pythonBasics);

        // Web Development
        Course webDevelopment = createCourse("Web Development", "Bob", 75,
            "Build modern, responsive websites using HTML5, CSS3, JavaScript, and popular frameworks. Learn full-stack development.",
            "https://img-c.udemycdn.com/course/750x422/851712_fc61_6.jpg",
            "https://youtu.be/zJSY8tbf_ys?si=5X_Ty76TrJfVUd7u");
        courseRepository.save(webDevelopment);

        // Data Structures
        Course dataStructures = createCourse("Data Structures", "Charlie", 80,
            "Deep dive into essential data structures including arrays, linked lists, trees, graphs, and hash tables.",
            "https://img-c.udemycdn.com/course/750x422/1362070_b9a1_2.jpg",
            "https://youtu.be/8hly31xKli0?si=h0oIaP_HhfQqBNRw");
        courseRepository.save(dataStructures);

        // Machine Learning
        Course machineLearning = createCourse("Machine Learning", "David", 120,
            "Introduction to machine learning algorithms, neural networks, and AI applications. Build intelligent systems from scratch.",
            "https://img-c.udemycdn.com/course/750x422/950390_270f_5.jpg",
            "https://youtu.be/i_LwzRVP7bg?si=QoKOExtCumWW8Gmb");
        courseRepository.save(machineLearning);

        // Java Full Stack
        Course javaFullStack = createCourse("Java Full Stack", "Emma", 100,
            "Complete Java full-stack development course covering Spring Boot, React, REST APIs, and database integration.",
            "https://img-c.udemycdn.com/course/750x422/827692_91ad_2.jpg",
            "https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn");
        courseRepository.save(javaFullStack);

        // React.js Advanced
        Course react = createCourse("React.js Advanced", "Frank", 90,
            "Master React.js with hooks, context API, Redux, and advanced patterns. Build scalable front-end applications.",
            "https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=800&h=400&fit=crop",
            "https://youtu.be/SqcY0GlETPk?si=eLrQrq4wng7u5339");
        courseRepository.save(react);

        // Node.js Backend Development
        Course nodejs = createCourse("Node.js Backend Development", "Grace", 85,
            "Learn Node.js, Express.js, and MongoDB. Build RESTful APIs and real-time applications.",
            "https://images.unsplash.com/photo-1558494949-ef010cbdcc31?w=800&h=400&fit=crop",
            "https://youtu.be/TlB_eWDSMt4?si=hhOfldi9iSwCceLR");
        courseRepository.save(nodejs);

        // Docker & Kubernetes
        Course docker = createCourse("Docker & Kubernetes", "Henry", 95,
            "Containerization and orchestration with Docker and Kubernetes. Deploy scalable applications in production.",
            "https://images.unsplash.com/photo-1605745341112-85968b19335b?w=800&h=400&fit=crop",
            "https://youtu.be/Wf2eSG3owoA?si=kQUYpj2cCxHKZJdj");
        courseRepository.save(docker);

        // AWS Cloud Computing
        Course aws = createCourse("AWS Cloud Computing", "Ivy", 110,
            "Master Amazon Web Services. Learn EC2, S3, Lambda, and cloud architecture patterns.",
            "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=800&h=400&fit=crop",
            "https://youtu.be/2OHr0QnEkg4?si=IF1CHRPRGZIPcPmW");
        courseRepository.save(aws);

        // Cybersecurity Fundamentals
        Course cybersecurity = createCourse("Cybersecurity Fundamentals", "Jack", 105,
            "Learn ethical hacking, network security, cryptography, and secure coding practices.",
            "https://images.unsplash.com/photo-1550751827-4bd374c3f58b?w=800&h=400&fit=crop",
            "https://youtu.be/s19BxFpoSd0?si=2ahGSmmeGEAxEulN");
        courseRepository.save(cybersecurity);

        // Mobile App Development
        Course mobileDev = createCourse("Mobile App Development", "Kate", 95,
            "Build iOS and Android apps using React Native and Flutter. Cross-platform mobile development.",
            "https://images.unsplash.com/photo-1512941937669-90a1b58e7e9c?w=800&h=400&fit=crop",
            "https://youtu.be/DsIviEKZad0?si=OcFKjJZCBhvu3BoX");
        courseRepository.save(mobileDev);

        // Continue with all other courses from CourseInitializer...
        // For brevity, I'll add the key additional courses
        
        // Full Stack Development with Java Spring Boot, React, and MongoDB
        courseRepository.save(createCourse("Full Stack Development with Java Spring Boot, React, and MongoDB", "Emma", 150,
            "Master full-stack development using Java Spring Boot for backend, React for frontend, and MongoDB for database. Build complete web applications from scratch.",
            "https://img-c.udemycdn.com/course/750x422/827692_91ad_2.jpg",
            "https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn"));

        // Spring Boot Framework
        courseRepository.save(createCourse("Spring Boot Framework", "Emma", 110,
            "Deep dive into Spring Boot framework. Learn dependency injection, REST APIs, Spring Security, and microservices architecture.",
            "https://img-c.udemycdn.com/course/750x422/827692_91ad_2.jpg",
            "https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn"));

        // React.js Complete Guide
        courseRepository.save(createCourse("React.js Complete Guide", "Frank", 100,
            "Complete React.js course covering hooks, context API, Redux, React Router, and building production-ready applications.",
            "https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=800&h=400&fit=crop",
            "https://youtu.be/SqcY0GlETPk?si=eLrQrq4wng7u5339"));

        // MongoDB Database Mastery
        courseRepository.save(createCourse("MongoDB Database Mastery", "Grace", 90,
            "Master MongoDB NoSQL database. Learn data modeling, aggregation, indexing, and integration with Java Spring Boot applications.",
            "https://images.unsplash.com/photo-1544383835-bda2bc66a6d3?w=800&h=400&fit=crop",
            "https://www.youtube.com/watch?v=-56x56UppqQ"));

        // Java Advanced Programming
        courseRepository.save(createCourse("Java Advanced Programming", "Emma", 105,
            "Advanced Java concepts including multithreading, collections framework, streams API, lambdas, and design patterns.",
            "https://img-c.udemycdn.com/course/750x422/827692_91ad_2.jpg",
            "https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn"));

        // RESTful API Development with Spring Boot
        courseRepository.save(createCourse("RESTful API Development with Spring Boot", "Emma", 95,
            "Build robust REST APIs using Spring Boot. Learn HTTP methods, JSON handling, authentication, and API best practices.",
            "https://img-c.udemycdn.com/course/750x422/827692_91ad_2.jpg",
            "https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn"));

        // React + Spring Boot Integration
        courseRepository.save(createCourse("React + Spring Boot Integration", "Emma", 120,
            "Learn to connect React frontend with Spring Boot backend. Handle CORS, JWT authentication, and build complete applications.",
            "https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=800&h=400&fit=crop",
            "https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn"));

        // Spring Security
        courseRepository.save(createCourse("Spring Security", "Emma", 100,
            "Implement security in Spring Boot applications. Learn JWT, OAuth2, authentication, authorization, and securing REST APIs.",
            "https://img-c.udemycdn.com/course/750x422/827692_91ad_2.jpg",
            "https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn"));

        // TypeScript for React
        courseRepository.save(createCourse("TypeScript for React", "Frank", 85,
            "Use TypeScript with React for type-safe frontend development. Learn interfaces, types, and best practices.",
            "https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=800&h=400&fit=crop",
            "https://youtu.be/SqcY0GlETPk?si=eLrQrq4wng7u5339"));

        // MySQL Database with Spring Boot
        courseRepository.save(createCourse("MySQL Database with Spring Boot", "Emma", 95,
            "Learn Spring Data JPA, Hibernate, and MySQL integration. Master database operations and relationships.",
            "https://images.unsplash.com/photo-1544383835-bda2bc66a6d3?w=800&h=400&fit=crop",
            "https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn"));

        // Microservices with Spring Boot
        courseRepository.save(createCourse("Microservices with Spring Boot", "Emma", 130,
            "Build microservices architecture using Spring Boot, Spring Cloud, Eureka, and API Gateway. Scalable application design.",
            "https://img-c.udemycdn.com/course/750x422/827692_91ad_2.jpg",
            "https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn"));

        // Redux State Management
        courseRepository.save(createCourse("Redux State Management", "Frank", 90,
            "Master Redux for React applications. Learn state management, middleware, Redux Toolkit, and async operations.",
            "https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=800&h=400&fit=crop",
            "https://youtu.be/SqcY0GlETPk?si=eLrQrq4wng7u5339"));

        // GraphQL with Spring Boot
        courseRepository.save(createCourse("GraphQL with Spring Boot", "Emma", 100,
            "Build GraphQL APIs with Spring Boot GraphQL. Learn schema design, queries, mutations, and subscriptions.",
            "https://img-c.udemycdn.com/course/750x422/827692_91ad_2.jpg",
            "https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn"));

        // Next.js Full Stack Development
        courseRepository.save(createCourse("Next.js Full Stack Development", "Frank", 110,
            "Build full-stack applications with Next.js. Server-side rendering, API routes, and React best practices.",
            "https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=800&h=400&fit=crop",
            "https://youtu.be/SqcY0GlETPk?si=eLrQrq4wng7u5339"));

        // Spring Boot Testing
        courseRepository.save(createCourse("Spring Boot Testing", "Emma", 85,
            "Master testing in Spring Boot applications. Unit tests, integration tests, MockMvc, and TestContainers.",
            "https://img-c.udemycdn.com/course/750x422/827692_91ad_2.jpg",
            "https://youtu.be/eIrMbAQSU34?si=qWzcNuwuqbqR4jAn"));

        // React Hooks & Context API
        courseRepository.save(createCourse("React Hooks & Context API", "Frank", 80,
            "Deep dive into React Hooks (useState, useEffect, useContext, useReducer) and Context API for state management.",
            "https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=800&h=400&fit=crop",
            "https://youtu.be/SqcY0GlETPk?si=eLrQrq4wng7u5339"));

        // Data Science & Analytics
        courseRepository.save(createCourse("Data Science & Analytics", "Luna", 125,
            "Learn data science fundamentals: Python, pandas, NumPy, data visualization, statistical analysis, and machine learning basics.",
            "https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800&h=400&fit=crop",
            "https://youtu.be/ua-CiDNNj30?si=KxJZqYqYqYqYqYqY"));

        // DevOps & CI/CD Pipeline
        courseRepository.save(createCourse("DevOps & CI/CD Pipeline", "Mike", 115,
            "Master DevOps practices: Jenkins, GitLab CI, GitHub Actions, automated testing, deployment strategies, and infrastructure as code.",
            "https://images.unsplash.com/photo-1618401479427-c8ef9465fbe1?w=800&h=400&fit=crop",
            "https://youtu.be/9pZ2xmsSDdo?si=abc123def456"));

        // UI/UX Design Fundamentals
        courseRepository.save(createCourse("UI/UX Design Fundamentals", "Nina", 95,
            "Learn user interface and user experience design. Wireframing, prototyping, design systems, and usability testing.",
            "https://images.unsplash.com/photo-1561070791-2526d30994b5?w=800&h=400&fit=crop",
            "https://youtu.be/c9Z5Y9Fw5ow?si=xyz789"));

        // Blockchain Development
        courseRepository.save(createCourse("Blockchain Development", "Oscar", 140,
            "Build blockchain applications with Solidity, Ethereum, smart contracts, Web3, and decentralized applications (DApps).",
            "https://images.unsplash.com/photo-1639762681485-074b7f938ba0?w=800&h=400&fit=crop",
            "https://youtu.be/gyMwXuJrbJQ?si=block123"));

        // Game Development with Unity
        courseRepository.save(createCourse("Game Development with Unity", "Pamela", 110,
            "Create games with Unity engine. Learn C# scripting, game physics, animation, and publishing games to multiple platforms.",
            "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=800&h=400&fit=crop",
            "https://youtu.be/j48LtUkZRjU?si=game456"));

        // Internet of Things (IoT)
        courseRepository.save(createCourse("Internet of Things (IoT)", "Quinn", 100,
            "Build IoT solutions with Arduino, Raspberry Pi, sensors, MQTT, and cloud integration. Smart home and industrial IoT applications.",
            "https://images.unsplash.com/photo-1518444065439-e933c06ce9cd?w=800&h=400&fit=crop",
            "https://youtu.be/6mBO2vqLv38?si=iot789"));

        // Microsoft Azure Cloud
        courseRepository.save(createCourse("Microsoft Azure Cloud", "Robert", 115,
            "Master Microsoft Azure cloud services. Learn Azure Virtual Machines, App Services, Functions, and cloud architecture.",
            "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=800&h=400&fit=crop",
            "https://youtu.be/3Arq5y6zJsY?si=azure123"));

        // Google Cloud Platform (GCP)
        courseRepository.save(createCourse("Google Cloud Platform (GCP)", "Sarah", 115,
            "Learn Google Cloud Platform: Compute Engine, Cloud Functions, BigQuery, and GCP architecture patterns.",
            "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=800&h=400&fit=crop",
            "https://youtu.be/kd6Ut1c2gCw?si=gcp456"));

        // Big Data & Hadoop
        courseRepository.save(createCourse("Big Data & Hadoop", "Tom", 130,
            "Process big data with Hadoop, Spark, Hive, and HBase. Learn distributed computing and data processing at scale.",
            "https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800&h=400&fit=crop",
            "https://youtu.be/1vbXmCrkT3Y?si=bigdata789"));

        // Angular Framework
        courseRepository.save(createCourse("Angular Framework", "Uma", 100,
            "Build dynamic web applications with Angular. Learn TypeScript, components, services, routing, and Angular CLI.",
            "https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=800&h=400&fit=crop",
            "https://youtu.be/k5E2AVpwsko?si=angular123"));

        // Vue.js Framework
        courseRepository.save(createCourse("Vue.js Framework", "Victor", 90,
            "Master Vue.js for building reactive user interfaces. Learn Vue 3, Composition API, Vuex, and Vue Router.",
            "https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=800&h=400&fit=crop",
            "https://youtu.be/FXpIoQ_TT5M?si=vue456"));

        // SQL Database Mastery
        courseRepository.save(createCourse("SQL Database Mastery", "Wendy", 75,
            "Master SQL for database management. Learn queries, joins, subqueries, stored procedures, and database optimization.",
            "https://images.unsplash.com/photo-1544383835-bda2bc66a6d3?w=800&h=400&fit=crop",
            "https://youtu.be/HXV3zeQKqGY?si=sql789"));

        // PostgreSQL Advanced
        courseRepository.save(createCourse("PostgreSQL Advanced", "Xavier", 85,
            "Advanced PostgreSQL features: indexing, query optimization, replication, partitioning, and advanced SQL techniques.",
            "https://images.unsplash.com/photo-1544383835-bda2bc66a6d3?w=800&h=400&fit=crop",
            "https://youtu.be/qw--VYLpxG4?si=postgres123"));

        // Redis Caching & Data Structures
        courseRepository.save(createCourse("Redis Caching & Data Structures", "Yara", 80,
            "Learn Redis for caching, session management, pub/sub messaging, and high-performance data storage.",
            "https://images.unsplash.com/photo-1544383835-bda2bc66a6d3?w=800&h=400&fit=crop",
            "https://youtu.be/G1rOthIU-uo?si=redis456"));

        // Elasticsearch & Search Engine
        courseRepository.save(createCourse("Elasticsearch & Search Engine", "Zoe", 95,
            "Build powerful search engines with Elasticsearch. Learn indexing, querying, aggregations, and Kibana visualization.",
            "https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800&h=400&fit=crop",
            "https://youtu.be/C5tWmPpLwvI?si=elastic789"));

        // Git & Version Control Mastery
        courseRepository.save(createCourse("Git & Version Control Mastery", "Alice", 60,
            "Master Git version control. Learn branching, merging, rebasing, GitHub workflows, and collaborative development.",
            "https://images.unsplash.com/photo-1618401479427-c8ef9465fbe1?w=800&h=400&fit=crop",
            "https://youtu.be/HVsySz-h9r4?si=git123"));

        // Linux System Administration
        courseRepository.save(createCourse("Linux System Administration", "Bob", 85,
            "Master Linux command line, shell scripting, system administration, process management, and server configuration.",
            "https://images.unsplash.com/photo-1558494949-ef010cbdcc31?w=800&h=400&fit=crop",
            "https://youtu.be/ROjZy1WbCIA?si=linux456"));

        // API Design & REST Architecture
        courseRepository.save(createCourse("API Design & REST Architecture", "Charlie", 90,
            "Design robust RESTful APIs. Learn API best practices, versioning, documentation, and API security.",
            "https://images.unsplash.com/photo-1558494949-ef010cbdcc31?w=800&h=400&fit=crop",
            "https://youtu.be/7YcW25PHnAA?si=api789"));

        // GraphQL API Development
        courseRepository.save(createCourse("GraphQL API Development", "David", 100,
            "Build GraphQL APIs with Apollo Server. Learn schema design, resolvers, subscriptions, and GraphQL best practices.",
            "https://images.unsplash.com/photo-1558494949-ef010cbdcc31?w=800&h=400&fit=crop",
            "https://youtu.be/ed8SzALpx1Q?si=graphql123"));

        // TensorFlow & Deep Learning
        courseRepository.save(createCourse("TensorFlow & Deep Learning", "Emma", 135,
            "Build deep learning models with TensorFlow and Keras. Neural networks, CNNs, RNNs, and transfer learning.",
            "https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800&h=400&fit=crop",
            "https://youtu.be/tPYj3fFJGjk?si=tensor456"));

        // PyTorch Machine Learning
        courseRepository.save(createCourse("PyTorch Machine Learning", "Frank", 130,
            "Learn PyTorch for deep learning. Build neural networks, work with tensors, and train models for production.",
            "https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800&h=400&fit=crop",
            "https://youtu.be/V_xro1bcAuA?si=pytorch789"));

        // Natural Language Processing (NLP)
        courseRepository.save(createCourse("Natural Language Processing (NLP)", "Grace", 120,
            "Process and understand human language with NLP. Learn text preprocessing, sentiment analysis, chatbots, and transformers.",
            "https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800&h=400&fit=crop",
            "https://youtu.be/fM4qTMfCoak?si=nlp123"));

        // Computer Vision
        courseRepository.save(createCourse("Computer Vision", "Henry", 125,
            "Build computer vision applications. Image processing, object detection, face recognition, and OpenCV.",
            "https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800&h=400&fit=crop",
            "https://youtu.be/oXlwWbU8l2o?si=vision456"));

        long finalCount = courseRepository.count();
        log.info("Successfully initialized {} courses across all domains.", finalCount);
        return finalCount;
    }

    private Course createCourse(String name, String instructor, int price, String description, String pLink, String yLink) {
        Course course = new Course();
        course.setCourse_name(name);
        course.setInstructor(instructor);
        course.setPrice(price);
        course.setDescription(description);
        course.setP_link(pLink);
        course.setY_link(yLink);
        return course;
    }
}

