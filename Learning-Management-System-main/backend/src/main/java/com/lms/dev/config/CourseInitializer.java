package com.lms.dev.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lms.dev.entity.Course;
import com.lms.dev.repository.CourseRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Configuration
@Slf4j
@RequiredArgsConstructor
public class CourseInitializer {

    private final CourseRepository courseRepository;

    @Bean
    public CommandLineRunner initializeCourses() {
        return args -> {
            try {
                long existingCount = courseRepository.count();
                log.info("Current course count: {}", existingCount);
                
                // Check if we need to add courses
                boolean shouldInitialize = existingCount == 0;
                
                if (shouldInitialize) {
                    log.info("Initializing all domain courses...");

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

                // Additional domains
                
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
                
                // Data Science & Analytics
                Course dataScience = new Course();
                dataScience.setCourse_name("Data Science & Analytics");
                dataScience.setInstructor("Luna");
                dataScience.setPrice(125);
                dataScience.setDescription("Learn data science fundamentals: Python, pandas, NumPy, data visualization, statistical analysis, and machine learning basics.");
                dataScience.setP_link("https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800&h=400&fit=crop");
                dataScience.setY_link("https://youtu.be/ua-CiDNNj30?si=KxJZqYqYqYqYqYqY");
                courseRepository.save(dataScience);

                // DevOps & CI/CD
                Course devops = new Course();
                devops.setCourse_name("DevOps & CI/CD Pipeline");
                devops.setInstructor("Mike");
                devops.setPrice(115);
                devops.setDescription("Master DevOps practices: Jenkins, GitLab CI, GitHub Actions, automated testing, deployment strategies, and infrastructure as code.");
                devops.setP_link("https://images.unsplash.com/photo-1618401479427-c8ef9465fbe1?w=800&h=400&fit=crop");
                devops.setY_link("https://youtu.be/9pZ2xmsSDdo?si=abc123def456");
                courseRepository.save(devops);

                // UI/UX Design
                Course uiux = new Course();
                uiux.setCourse_name("UI/UX Design Fundamentals");
                uiux.setInstructor("Nina");
                uiux.setPrice(95);
                uiux.setDescription("Learn user interface and user experience design. Wireframing, prototyping, design systems, and usability testing.");
                uiux.setP_link("https://images.unsplash.com/photo-1561070791-2526d30994b5?w=800&h=400&fit=crop");
                uiux.setY_link("https://youtu.be/c9Z5Y9Fw5ow?si=xyz789");
                courseRepository.save(uiux);

                // Blockchain Development
                Course blockchain = new Course();
                blockchain.setCourse_name("Blockchain Development");
                blockchain.setInstructor("Oscar");
                blockchain.setPrice(140);
                blockchain.setDescription("Build blockchain applications with Solidity, Ethereum, smart contracts, Web3, and decentralized applications (DApps).");
                blockchain.setP_link("https://images.unsplash.com/photo-1639762681485-074b7f938ba0?w=800&h=400&fit=crop");
                blockchain.setY_link("https://youtu.be/gyMwXuJrbJQ?si=block123");
                courseRepository.save(blockchain);

                // Game Development
                Course gameDev = new Course();
                gameDev.setCourse_name("Game Development with Unity");
                gameDev.setInstructor("Pamela");
                gameDev.setPrice(110);
                gameDev.setDescription("Create games with Unity engine. Learn C# scripting, game physics, animation, and publishing games to multiple platforms.");
                gameDev.setP_link("https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=800&h=400&fit=crop");
                gameDev.setY_link("https://youtu.be/j48LtUkZRjU?si=game456");
                courseRepository.save(gameDev);

                // Internet of Things (IoT)
                Course iot = new Course();
                iot.setCourse_name("Internet of Things (IoT)");
                iot.setInstructor("Quinn");
                iot.setPrice(100);
                iot.setDescription("Build IoT solutions with Arduino, Raspberry Pi, sensors, MQTT, and cloud integration. Smart home and industrial IoT applications.");
                iot.setP_link("https://images.unsplash.com/photo-1518444065439-e933c06ce9cd?w=800&h=400&fit=crop");
                iot.setY_link("https://youtu.be/6mBO2vqLv38?si=iot789");
                courseRepository.save(iot);

                // Azure Cloud Platform
                Course azure = new Course();
                azure.setCourse_name("Microsoft Azure Cloud");
                azure.setInstructor("Robert");
                azure.setPrice(115);
                azure.setDescription("Master Microsoft Azure cloud services. Learn Azure Virtual Machines, App Services, Functions, and cloud architecture.");
                azure.setP_link("https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=800&h=400&fit=crop");
                azure.setY_link("https://youtu.be/3Arq5y6zJsY?si=azure123");
                courseRepository.save(azure);

                // Google Cloud Platform (GCP)
                Course gcp = new Course();
                gcp.setCourse_name("Google Cloud Platform (GCP)");
                gcp.setInstructor("Sarah");
                gcp.setPrice(115);
                gcp.setDescription("Learn Google Cloud Platform: Compute Engine, Cloud Functions, BigQuery, and GCP architecture patterns.");
                gcp.setP_link("https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=800&h=400&fit=crop");
                gcp.setY_link("https://youtu.be/kd6Ut1c2gCw?si=gcp456");
                courseRepository.save(gcp);

                // Big Data & Hadoop
                Course bigData = new Course();
                bigData.setCourse_name("Big Data & Hadoop");
                bigData.setInstructor("Tom");
                bigData.setPrice(130);
                bigData.setDescription("Process big data with Hadoop, Spark, Hive, and HBase. Learn distributed computing and data processing at scale.");
                bigData.setP_link("https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800&h=400&fit=crop");
                bigData.setY_link("https://youtu.be/1vbXmCrkT3Y?si=bigdata789");
                courseRepository.save(bigData);

                // Angular Framework
                Course angular = new Course();
                angular.setCourse_name("Angular Framework");
                angular.setInstructor("Uma");
                angular.setPrice(100);
                angular.setDescription("Build dynamic web applications with Angular. Learn TypeScript, components, services, routing, and Angular CLI.");
                angular.setP_link("https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=800&h=400&fit=crop");
                angular.setY_link("https://youtu.be/k5E2AVpwsko?si=angular123");
                courseRepository.save(angular);

                // Vue.js Framework
                Course vuejs = new Course();
                vuejs.setCourse_name("Vue.js Framework");
                vuejs.setInstructor("Victor");
                vuejs.setPrice(90);
                vuejs.setDescription("Master Vue.js for building reactive user interfaces. Learn Vue 3, Composition API, Vuex, and Vue Router.");
                vuejs.setP_link("https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=800&h=400&fit=crop");
                vuejs.setY_link("https://youtu.be/FXpIoQ_TT5M?si=vue456");
                courseRepository.save(vuejs);

                // SQL Database Mastery
                Course sql = new Course();
                sql.setCourse_name("SQL Database Mastery");
                sql.setInstructor("Wendy");
                sql.setPrice(75);
                sql.setDescription("Master SQL for database management. Learn queries, joins, subqueries, stored procedures, and database optimization.");
                sql.setP_link("https://images.unsplash.com/photo-1544383835-bda2bc66a6d3?w=800&h=400&fit=crop");
                sql.setY_link("https://youtu.be/HXV3zeQKqGY?si=sql789");
                courseRepository.save(sql);

                // PostgreSQL Advanced
                Course postgresql = new Course();
                postgresql.setCourse_name("PostgreSQL Advanced");
                postgresql.setInstructor("Xavier");
                postgresql.setPrice(85);
                postgresql.setDescription("Advanced PostgreSQL features: indexing, query optimization, replication, partitioning, and advanced SQL techniques.");
                postgresql.setP_link("https://images.unsplash.com/photo-1544383835-bda2bc66a6d3?w=800&h=400&fit=crop");
                postgresql.setY_link("https://youtu.be/qw--VYLpxG4?si=postgres123");
                courseRepository.save(postgresql);

                // Redis Caching
                Course redis = new Course();
                redis.setCourse_name("Redis Caching & Data Structures");
                redis.setInstructor("Yara");
                redis.setPrice(80);
                redis.setDescription("Learn Redis for caching, session management, pub/sub messaging, and high-performance data storage.");
                redis.setP_link("https://images.unsplash.com/photo-1544383835-bda2bc66a6d3?w=800&h=400&fit=crop");
                redis.setY_link("https://youtu.be/G1rOthIU-uo?si=redis456");
                courseRepository.save(redis);

                // Elasticsearch & Search
                Course elasticsearch = new Course();
                elasticsearch.setCourse_name("Elasticsearch & Search Engine");
                elasticsearch.setInstructor("Zoe");
                elasticsearch.setPrice(95);
                elasticsearch.setDescription("Build powerful search engines with Elasticsearch. Learn indexing, querying, aggregations, and Kibana visualization.");
                elasticsearch.setP_link("https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800&h=400&fit=crop");
                elasticsearch.setY_link("https://youtu.be/C5tWmPpLwvI?si=elastic789");
                courseRepository.save(elasticsearch);

                // Git & Version Control
                Course git = new Course();
                git.setCourse_name("Git & Version Control Mastery");
                git.setInstructor("Alice");
                git.setPrice(60);
                git.setDescription("Master Git version control. Learn branching, merging, rebasing, GitHub workflows, and collaborative development.");
                git.setP_link("https://images.unsplash.com/photo-1618401479427-c8ef9465fbe1?w=800&h=400&fit=crop");
                git.setY_link("https://youtu.be/HVsySz-h9r4?si=git123");
                courseRepository.save(git);

                // Linux System Administration
                Course linux = new Course();
                linux.setCourse_name("Linux System Administration");
                linux.setInstructor("Bob");
                linux.setPrice(85);
                linux.setDescription("Master Linux command line, shell scripting, system administration, process management, and server configuration.");
                linux.setP_link("https://images.unsplash.com/photo-1558494949-ef010cbdcc31?w=800&h=400&fit=crop");
                linux.setY_link("https://youtu.be/ROjZy1WbCIA?si=linux456");
                courseRepository.save(linux);

                // API Design & REST Architecture
                Course apiDesign = new Course();
                apiDesign.setCourse_name("API Design & REST Architecture");
                apiDesign.setInstructor("Charlie");
                apiDesign.setPrice(90);
                apiDesign.setDescription("Design robust RESTful APIs. Learn API best practices, versioning, documentation, and API security.");
                apiDesign.setP_link("https://images.unsplash.com/photo-1558494949-ef010cbdcc31?w=800&h=400&fit=crop");
                apiDesign.setY_link("https://youtu.be/7YcW25PHnAA?si=api789");
                courseRepository.save(apiDesign);

                // GraphQL API Development
                Course graphqlApi = new Course();
                graphqlApi.setCourse_name("GraphQL API Development");
                graphqlApi.setInstructor("David");
                graphqlApi.setPrice(100);
                graphqlApi.setDescription("Build GraphQL APIs with Apollo Server. Learn schema design, resolvers, subscriptions, and GraphQL best practices.");
                graphqlApi.setP_link("https://images.unsplash.com/photo-1558494949-ef010cbdcc31?w=800&h=400&fit=crop");
                graphqlApi.setY_link("https://youtu.be/ed8SzALpx1Q?si=graphql123");
                courseRepository.save(graphqlApi);

                // TensorFlow & Deep Learning
                Course tensorflow = new Course();
                tensorflow.setCourse_name("TensorFlow & Deep Learning");
                tensorflow.setInstructor("Emma");
                tensorflow.setPrice(135);
                tensorflow.setDescription("Build deep learning models with TensorFlow and Keras. Neural networks, CNNs, RNNs, and transfer learning.");
                tensorflow.setP_link("https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800&h=400&fit=crop");
                tensorflow.setY_link("https://youtu.be/tPYj3fFJGjk?si=tensor456");
                courseRepository.save(tensorflow);

                // PyTorch Machine Learning
                Course pytorch = new Course();
                pytorch.setCourse_name("PyTorch Machine Learning");
                pytorch.setInstructor("Frank");
                pytorch.setPrice(130);
                pytorch.setDescription("Learn PyTorch for deep learning. Build neural networks, work with tensors, and train models for production.");
                pytorch.setP_link("https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800&h=400&fit=crop");
                pytorch.setY_link("https://youtu.be/V_xro1bcAuA?si=pytorch789");
                courseRepository.save(pytorch);

                // Natural Language Processing (NLP)
                Course nlp = new Course();
                nlp.setCourse_name("Natural Language Processing (NLP)");
                nlp.setInstructor("Grace");
                nlp.setPrice(120);
                nlp.setDescription("Process and understand human language with NLP. Learn text preprocessing, sentiment analysis, chatbots, and transformers.");
                nlp.setP_link("https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800&h=400&fit=crop");
                nlp.setY_link("https://youtu.be/fM4qTMfCoak?si=nlp123");
                courseRepository.save(nlp);

                // Computer Vision
                Course computerVision = new Course();
                computerVision.setCourse_name("Computer Vision");
                computerVision.setInstructor("Henry");
                computerVision.setPrice(125);
                computerVision.setDescription("Build computer vision applications. Image processing, object detection, face recognition, and OpenCV.");
                computerVision.setP_link("https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800&h=400&fit=crop");
                computerVision.setY_link("https://youtu.be/oXlwWbU8l2o?si=vision456");
                courseRepository.save(computerVision);

                    long finalCount = courseRepository.count();
                    log.info("Successfully initialized {} courses across all domains.", finalCount);
                } else {
                    log.info("Courses already exist ({} courses found). To reinitialize all courses, delete existing courses first or use /api/admin/reinit-courses endpoint.", existingCount);
                }
            } catch (Exception e) {
                log.error("Error during course initialization: ", e);
            }
        };
    }
}

