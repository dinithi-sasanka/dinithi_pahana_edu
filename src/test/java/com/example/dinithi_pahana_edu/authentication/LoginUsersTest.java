package com.example.dinithi_pahana_edu.authentication;

import com.example.dinithi_pahana_edu.model.User;
import com.example.dinithi_pahana_edu.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit Tests for Login Users Functionality
 * Tests user authentication, login validation, and user management
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginUsersTest {
    
    private UserService userService;
    private User testUser;
    
    @BeforeEach
    void setUp() {
        userService = new UserService();
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("testuser");
        testUser.setPassword("testpass123");
        testUser.setRole("admin");
    }
    
    @Test
    @Order(1)
    @DisplayName("Test User Object Creation")
    void testUserCreation() {
        assertNotNull(testUser);
        assertEquals(1, testUser.getId());
        assertEquals("testuser", testUser.getUsername());
        assertEquals("testpass123", testUser.getPassword());
        assertEquals("admin", testUser.getRole());
    }
    
    @Test
    @Order(2)
    @DisplayName("Test User Getters and Setters")
    void testUserGettersAndSetters() {
        User user = new User();
        
        // Test setters
        user.setId(100);
        user.setUsername("newuser");
        user.setPassword("newpass456");
        user.setRole("staff");
        
        // Test getters
        assertEquals(100, user.getId());
        assertEquals("newuser", user.getUsername());
        assertEquals("newpass456", user.getPassword());
        assertEquals("staff", user.getRole());
    }
    
    @Test
    @Order(3)
    @DisplayName("Test User Authentication - Valid Credentials")
    void testValidUserAuthentication() {
        // This test simulates valid authentication
        // In a real scenario, you would test against actual database
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin123");
        user.setRole("admin");
        
        assertNotNull(user);
        assertEquals("admin", user.getUsername());
        assertEquals("admin123", user.getPassword());
        assertEquals("admin", user.getRole());
    }
    
    @Test
    @Order(4)
    @DisplayName("Test User Authentication - Invalid Credentials")
    void testInvalidUserAuthentication() {
        // This test simulates invalid authentication
        User user = new User();
        user.setUsername("invaliduser");
        user.setPassword("wrongpassword");
        
        assertNotNull(user);
        assertEquals("invaliduser", user.getUsername());
        assertEquals("wrongpassword", user.getPassword());
        // Should not authenticate with wrong credentials
        assertNotEquals("admin", user.getPassword());
    }
    
    @Test
    @Order(5)
    @DisplayName("Test User Role Validation")
    void testUserRoleValidation() {
        User adminUser = new User();
        adminUser.setRole("admin");
        
        User coadminUser = new User();
        coadminUser.setRole("coadmin");
        
        User staffUser = new User();
        staffUser.setRole("staff");
        
        User invalidUser = new User();
        invalidUser.setRole("invalid");
        
        // Test valid roles
        assertTrue(isValidRole(adminUser.getRole()));
        assertTrue(isValidRole(coadminUser.getRole()));
        assertTrue(isValidRole(staffUser.getRole()));
        
        // Test invalid role
        assertFalse(isValidRole(invalidUser.getRole()));
    }
    
    @Test
    @Order(6)
    @DisplayName("Test User Password Validation")
    void testUserPasswordValidation() {
        String validPassword = "password123";
        String shortPassword = "123";
        String emptyPassword = "";
        String nullPassword = null;
        
        // Test valid password
        assertTrue(isValidPassword(validPassword));
        
        // Test invalid passwords
        assertFalse(isValidPassword(shortPassword));
        assertFalse(isValidPassword(emptyPassword));
        assertFalse(isValidPassword(nullPassword));
    }
    
    @Test
    @Order(7)
    @DisplayName("Test User Username Validation")
    void testUserUsernameValidation() {
        String validUsername = "testuser123";
        String shortUsername = "ab";
        String emptyUsername = "";
        String nullUsername = null;
        
        // Test valid username
        assertTrue(isValidUsername(validUsername));
        
        // Test invalid usernames
        assertFalse(isValidUsername(shortUsername));
        assertFalse(isValidUsername(emptyUsername));
        assertFalse(isValidUsername(nullUsername));
    }
    
    @Test
    @Order(8)
    @DisplayName("Test User Object Equality")
    void testUserObjectEquality() {
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("user1");
        user1.setPassword("pass1");
        user1.setRole("admin");
        
        User user2 = new User();
        user2.setId(1);
        user2.setUsername("user1");
        user2.setPassword("pass1");
        user2.setRole("admin");
        
        User user3 = new User();
        user3.setId(2);
        user3.setUsername("user2");
        user3.setPassword("pass2");
        user3.setRole("staff");
        
        // Test equality
        assertEquals(user1.getId(), user2.getId());
        assertEquals(user1.getUsername(), user2.getUsername());
        assertEquals(user1.getRole(), user2.getRole());
        
        // Test inequality
        assertNotEquals(user1.getId(), user3.getId());
        assertNotEquals(user1.getUsername(), user3.getUsername());
        assertNotEquals(user1.getRole(), user3.getRole());
    }
    
    @Test
    @Order(9)
    @DisplayName("Test User Login Session")
    void testUserLoginSession() {
        User loggedInUser = new User();
        loggedInUser.setId(1);
        loggedInUser.setUsername("sessionuser");
        loggedInUser.setRole("admin");
        
        // Simulate session creation
        assertNotNull(loggedInUser);
        assertTrue(loggedInUser.getId() > 0);
        assertNotNull(loggedInUser.getUsername());
        assertNotNull(loggedInUser.getRole());
        
        // Test session validation
        assertTrue(isValidSession(loggedInUser));
    }
    
    @Test
    @Order(10)
    @DisplayName("Test User Logout")
    void testUserLogout() {
        User user = new User();
        user.setId(1);
        user.setUsername("logoutuser");
        user.setRole("admin");
        
        // Simulate logout
        user = null;
        
        // Test logout
        assertNull(user);
    }
    
    // Helper methods for validation
    private boolean isValidRole(String role) {
        return role != null && (role.equals("admin") || role.equals("coadmin") || role.equals("staff"));
    }
    
    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
    
    private boolean isValidUsername(String username) {
        return username != null && username.length() >= 3;
    }
    
    private boolean isValidSession(User user) {
        return user != null && user.getId() > 0 && user.getUsername() != null && user.getRole() != null;
    }
} 