package com.lms.dev.controller;

import com.lms.dev.dto.ApiResponse;
import com.lms.dev.dto.JwtResponseDTO;
import com.lms.dev.dto.LoginRequestDTO;
import com.lms.dev.entity.User;
import com.lms.dev.enums.UserRole;
import com.lms.dev.security.UserPrincipal;
import com.lms.dev.security.util.JwtUtils;
import com.lms.dev.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserService authService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthController authController;

    private LoginRequestDTO loginRequest;
    private User testUser;
    private UserPrincipal userPrincipal;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setUsername("Test User");
        testUser.setRole(UserRole.USER);

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        userPrincipal = new UserPrincipal(
                testUser.getId(),
                testUser.getUsername(),
                testUser.getEmail(),
                testUser.getPassword(),
                Collections.singletonList(authority),
                true
        );
    }

    @Test
    void testLogin_Success() {
        // Given
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userPrincipal);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("test-jwt-token");

        // When
        ResponseEntity<ApiResponse<JwtResponseDTO>> response = authController.login(loginRequest);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());
        assertEquals("test-jwt-token", response.getBody().getData().getToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils, times(1)).generateJwtToken(authentication);
    }

    @Test
    void testRegister_Success() {
        // Given
        when(authService.createUser(any(User.class))).thenReturn(testUser);

        // When
        ResponseEntity<ApiResponse<User>> response = authController.register(testUser);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());
        assertEquals(testUser.getEmail(), response.getBody().getData().getEmail());
        verify(authService, times(1)).createUser(testUser);
    }

    @Test
    void testLogout() {
        // When
        ResponseEntity<ApiResponse<Void>> response = authController.logout();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}

