package com.lms.dev.service;

import com.lms.dev.entity.Certificate;
import com.lms.dev.entity.Course;
import com.lms.dev.entity.User;
import com.lms.dev.repository.CertificateRepository;
import com.lms.dev.repository.CourseRepository;
import com.lms.dev.repository.UserRepository;
import com.lms.dev.service.storage.StorageService;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CertificateService {

    private final CertificateRepository certificateRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final StorageService storageService;

    /**
     * Generate and issue a certificate for a user upon course completion
     */
    @Transactional
    public Certificate generateCertificate(UUID userId, UUID courseId) throws IOException {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        
        if (userOpt.isEmpty() || courseOpt.isEmpty()) {
            throw new IllegalArgumentException("User or Course not found");
        }
        
        User user = userOpt.get();
        Course course = courseOpt.get();
        
        // Check if certificate already exists
        Optional<Certificate> existingCert = certificateRepository.findByUserAndCourse(user, course);
        if (existingCert.isPresent()) {
            log.info("Certificate already exists for user {} and course {}", userId, courseId);
            return existingCert.get();
        }
        
        // Generate PDF certificate
        byte[] pdfBytes = generateCertificatePdf(user, course);
        
        // Upload PDF to storage
        String certificateUrl = uploadCertificateToStorage(pdfBytes, user, course);
        
        // Generate unique certificate number
        String certificateNumber = generateCertificateNumber(user, course);
        
        // Save certificate record
        Certificate certificate = new Certificate();
        certificate.setUser(user);
        certificate.setCourse(course);
        certificate.setCertificateUrl(certificateUrl);
        certificate.setCertificateNumber(certificateNumber);
        certificate.setIssuedAt(LocalDateTime.now());
        
        return certificateRepository.save(certificate);
    }

    /**
     * Generate PDF certificate using iText
     */
    private byte[] generateCertificatePdf(User user, Course course) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        
        try {
            // Add fonts
            PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont fontRegular = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            
            // Title
            Paragraph title = new Paragraph("CERTIFICATE OF COMPLETION")
                    .setFont(fontBold)
                    .setFontSize(24)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(30);
            document.add(title);
            
            // Subtitle
            Paragraph subtitle = new Paragraph("This is to certify that")
                    .setFont(fontRegular)
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(subtitle);
            
            // Student name
            Paragraph studentName = new Paragraph(user.getUsername())
                    .setFont(fontBold)
                    .setFontSize(28)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.BLUE)
                    .setMarginBottom(20);
            document.add(studentName);
            
            // Course details
            Paragraph courseText = new Paragraph()
                    .setFont(fontRegular)
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(10);
            courseText.add(new Text("has successfully completed the course\n"));
            courseText.add(new Text(course.getCourse_name()).setFont(fontBold));
            
            document.add(courseText);
            
            // Date
            String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
            Paragraph date = new Paragraph(dateStr)
                    .setFont(fontRegular)
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(40);
            document.add(date);
            
            // Certificate number
            String certNumber = generateCertificateNumber(user, course);
            Paragraph certNum = new Paragraph("Certificate No: " + certNumber)
                    .setFont(fontRegular)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(20);
            document.add(certNum);
            
        } finally {
            document.close();
        }
        
        return baos.toByteArray();
    }

    /**
     * Upload certificate PDF to storage
     */
    private String uploadCertificateToStorage(byte[] pdfBytes, User user, Course course) throws IOException {
        // Create a MultipartFile from byte array
        MultipartFile multipartFile = new MultipartFile() {
            @Override
            public String getName() {
                return "certificate";
            }
            
            @Override
            public String getOriginalFilename() {
                return "certificate_" + user.getId() + "_" + course.getCourse_id() + ".pdf";
            }
            
            @Override
            public String getContentType() {
                return "application/pdf";
            }
            
            @Override
            public boolean isEmpty() {
                return pdfBytes.length == 0;
            }
            
            @Override
            public long getSize() {
                return pdfBytes.length;
            }
            
            @Override
            public byte[] getBytes() throws IOException {
                return pdfBytes;
            }
            
            @Override
            public java.io.InputStream getInputStream() throws IOException {
                return new java.io.ByteArrayInputStream(pdfBytes);
            }
            
            @Override
            public void transferTo(java.io.File dest) throws IOException, IllegalStateException {
                java.nio.file.Files.write(dest.toPath(), pdfBytes);
            }
        };
        
        String folder = "certificates";
        return storageService.uploadFile(multipartFile, folder);
    }

    /**
     * Generate unique certificate number
     */
    private String generateCertificateNumber(User user, Course course) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String shortUserId = user.getId().toString().substring(0, 8);
        String shortCourseId = course.getCourse_id().toString().substring(0, 8);
        return "CERT-" + shortUserId + "-" + shortCourseId + "-" + timestamp.substring(timestamp.length() - 6);
    }

    /**
     * Get certificate for a user and course
     */
    public Optional<Certificate> getCertificate(UUID userId, UUID courseId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        
        if (userOpt.isEmpty() || courseOpt.isEmpty()) {
            return Optional.empty();
        }
        
        return certificateRepository.findByUserAndCourse(userOpt.get(), courseOpt.get());
    }

    /**
     * Get all certificates for a user
     */
    public java.util.List<Certificate> getUserCertificates(UUID userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        return certificateRepository.findByUser(userOpt.get());
    }
}

