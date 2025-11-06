# New Features Added

This document outlines all the new features that have been added to the Learning Management System.

## 1. Instructor Features

### Overview
Added full instructor role support with separate login and dedicated endpoints for course management.

### Backend Changes

#### Entity Updates
- **Course Entity**: Added `instructorUser` field to link courses to User entities (in addition to legacy `instructor` string field)
- **User Entity**: Already supports `INSTRUCTOR` role via `UserRole` enum

#### New Services
- **InstructorService**: Handles instructor-specific operations:
  - Get all courses created by an instructor
  - Create courses and automatically assign to instructor
  - Update/delete courses (only if instructor owns them)
  - View enrolled students for instructor's courses
  - Get enrollment details for specific courses

#### New Controllers
- **InstructorController** (`/api/instructor/**`):
  - `GET /api/instructor/courses` - Get instructor's courses
  - `POST /api/instructor/courses` - Create new course
  - `PUT /api/instructor/courses/{courseId}` - Update course
  - `DELETE /api/instructor/courses/{courseId}` - Delete course
  - `GET /api/instructor/students` - Get all students enrolled in instructor's courses
  - `GET /api/instructor/courses/{courseId}/students` - Get students for specific course
  - `GET /api/instructor/courses/{courseId}/enrollments` - Get enrollment details

#### Security Updates
- Updated `WebSecurityConfig` to allow `INSTRUCTOR` role:
  - Course CRUD operations: `hasAnyRole("ADMIN", "INSTRUCTOR")`
  - Instructor endpoints: `hasRole("INSTRUCTOR")`

### Repository Updates
- **CourseRepository**: Added `findByInstructorUser(User instructor)` method

### Frontend (TODO)
- Create instructor dashboard component
- Add instructor login page
- Add course management UI for instructors
- Add student viewing interface

---

## 2. File Storage Integration

### Overview
Added support for multiple cloud storage providers (AWS S3, Uploadcare, Firebase Storage) for storing course media files (PDF, video, audio).

### Implementation

#### Storage Service Interface
- **StorageService**: Common interface for all storage providers
  - `uploadFile(MultipartFile file, String folder)`: Upload file and return URL
  - `deleteFile(String fileUrl)`: Delete file by URL
  - `fileExists(String fileUrl)`: Check if file exists
  - `getProviderType()`: Get provider name

#### Storage Implementations
1. **AwsS3StorageService**: AWS S3 implementation
   - Requires: `AWS_ACCESS_KEY`, `AWS_SECRET_KEY`, `AWS_S3_BUCKET_NAME`
   - Config: `storage.provider=s3`

2. **UploadcareStorageService**: Uploadcare implementation
   - Requires: `UPLOADCARE_PUBLIC_KEY`, `UPLOADCARE_SECRET_KEY`
   - Config: `storage.provider=uploadcare`

3. **FirebaseStorageService**: Firebase Storage implementation
   - Requires: `FIREBASE_CREDENTIALS_PATH`, `FIREBASE_STORAGE_BUCKET`
   - Config: `storage.provider=firebase`

#### Configuration
- **AwsConfig**: Auto-configures S3Client when `storage.provider=s3`
- **FirebaseConfig**: Auto-configures Firebase Storage when `storage.provider=firebase`

#### File Upload Controller
- **FileUploadController** (`/api/files/**`):
  - `POST /api/files/upload` - Upload file (ADMIN/INSTRUCTOR only)
  - `DELETE /api/files/delete` - Delete file (ADMIN/INSTRUCTOR only)
  - `GET /api/files/exists` - Check file existence

### Configuration Properties
Add to `application.yml` or environment variables:
```yaml
storage:
  provider: ${STORAGE_PROVIDER:s3}  # Options: s3, uploadcare, firebase

# AWS S3
aws:
  s3:
    bucket-name: ${AWS_S3_BUCKET_NAME:}
    region: ${AWS_S3_REGION:us-east-1}
    access-key: ${AWS_ACCESS_KEY:}
    secret-key: ${AWS_SECRET_KEY:}

# Uploadcare
uploadcare:
  public-key: ${UPLOADCARE_PUBLIC_KEY:}
  secret-key: ${UPLOADCARE_SECRET_KEY:}

# Firebase
firebase:
  storage:
    bucket-name: ${FIREBASE_STORAGE_BUCKET:}
  credentials:
    path: ${FIREBASE_CREDENTIALS_PATH:}
```

---

## 3. Certificate Generation

### Overview
Automatic PDF certificate generation upon course completion with personalized user details.

### Implementation

#### Entity
- **Certificate**: Stores certificate metadata
  - Links to User and Course
  - Stores certificate URL (PDF location)
  - Unique certificate number
  - Issue timestamp

#### Service
- **CertificateService**: Handles certificate generation
  - `generateCertificate(UUID userId, UUID courseId)`: Generate and save certificate
  - `getCertificate(UUID userId, UUID courseId)`: Get certificate
  - `getUserCertificates(UUID userId)`: Get all user certificates
  - Uses iTextPDF to generate PDF certificates
  - Uploads PDF to configured storage service

#### Controller
- **CertificateController** (`/api/certificates/**`):
  - `POST /api/certificates/generate` - Generate certificate (ADMIN/INSTRUCTOR/USER)
  - `GET /api/certificates` - Get certificate by user and course
  - `GET /api/certificates/user/{userId}` - Get all user certificates

#### Certificate PDF Features
- Professional certificate design
- Student name (prominent)
- Course name
- Issue date
- Unique certificate number
- Stored in cloud storage

---

## 4. Unit Testing

### Overview
Added comprehensive JUnit 5 and Mockito unit tests for controllers and services.

### Test Files Created

#### Controller Tests
1. **CourseControllerTest**
   - Tests all CRUD operations
   - Tests success and error scenarios
   - Tests 404 responses

2. **AuthControllerTest**
   - Tests login, register, logout
   - Tests JWT token generation
   - Tests authentication flow

#### Service Tests
1. **CourseServiceTest**
   - Tests all service methods
   - Tests null/empty input handling
   - Tests repository interactions

2. **InstructorServiceTest**
   - Tests instructor-specific operations
   - Tests ownership validation
   - Tests permission checks

### Running Tests
```bash
cd backend
./mvnw test
```

---

## 5. Dependencies Added

### Storage
- `software.amazon.awssdk:s3` (v2.20.26) - AWS S3 SDK
- `com.uploadcare:uploadcare` (v3.0.0) - Uploadcare SDK
- `com.google.firebase:firebase-admin` (v9.2.0) - Firebase Admin SDK

### Certificate Generation
- `com.itextpdf:kernel` (v8.0.1) - iTextPDF core
- `com.itextpdf:layout` (v8.0.1) - iTextPDF layout

### Testing
- `org.mockito:mockito-core` - Mockito (already included in spring-boot-starter-test)
- `org.mockito:mockito-junit-jupiter` - Mockito JUnit 5 integration

---

## 6. Configuration Updates

### application.yml
- Added storage provider configuration
- Added AWS S3 configuration
- Added Uploadcare configuration
- Added Firebase Storage configuration

### Security Configuration
- Updated `WebSecurityConfig` to support INSTRUCTOR role
- Added instructor-specific endpoints

---

## 7. Next Steps (Frontend)

### Instructor Dashboard
- Create instructor login page
- Create instructor dashboard with:
  - Course management (create, edit, delete)
  - Student list view
  - Enrollment statistics
  - Course analytics

### File Upload UI
- Add file upload component for course media
- Support for PDF, video, audio files
- Show uploaded file URLs
- File preview/download

### Certificate Display
- Certificate download/view page
- User's certificate list
- Certificate verification

---

## 8. Environment Variables Required

### For Storage (choose one):

**AWS S3:**
```
STORAGE_PROVIDER=s3
AWS_S3_BUCKET_NAME=your-bucket-name
AWS_S3_REGION=us-east-1
AWS_ACCESS_KEY=your-access-key
AWS_SECRET_KEY=your-secret-key
```

**Uploadcare:**
```
STORAGE_PROVIDER=uploadcare
UPLOADCARE_PUBLIC_KEY=your-public-key
UPLOADCARE_SECRET_KEY=your-secret-key
```

**Firebase Storage:**
```
STORAGE_PROVIDER=firebase
FIREBASE_STORAGE_BUCKET=your-bucket-name
FIREBASE_CREDENTIALS_PATH=/path/to/service-account.json
```

---

## 9. API Documentation

All endpoints are documented via Swagger/OpenAPI:
- Access at: `http://localhost:8080/swagger-ui.html`
- JSON docs at: `http://localhost:8080/v3/api-docs`

---

## 10. Database Schema Changes

### New Tables
- `certificates` table (auto-created by JPA)

### Updated Tables
- `courses` table: Added `instructor_id` column (foreign key to `users`)

**Note:** Run the application and JPA will automatically update the schema (`ddl-auto: update`).

---

## Summary

All requested features have been implemented:
- ✅ Instructor features with separate login
- ✅ File storage integration (AWS S3, Uploadcare, Firebase)
- ✅ Automatic certificate generation
- ✅ Comprehensive unit tests (JUnit 5 + Mockito)

The frontend instructor dashboard is the remaining task to complete the full feature set.

