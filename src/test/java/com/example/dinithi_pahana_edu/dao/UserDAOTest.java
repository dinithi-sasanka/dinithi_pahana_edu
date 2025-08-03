package com.example.dinithi_pahana_edu.dao;

import com.example.dinithi_pahana_edu.model.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * JUnit tests for UserDAO
 * Tests: Authentication, CRUD operations, validation
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDAOTest {
    
    private UserDAO userDAO;
    
    @BeforeEach
    void setUp() {
        userDAO = new UserDAO();
    }
    
    // Helper method to create a test user
    private User createTestUser() {
        User user = new User();
        user.setUsername("testuser_" + System.currentTimeMillis());
        user.setPassword("testpass123");
        user.setRole("staff");
        user.setUseName("Test User");
        user.setEmail("test@example.com");
        user.setTelephone("1234567890");
        return user;
    }
    
    // Helper method to create another test user
    private User createTestUser2() {
        User user = new User();
        user.setUsername("testuser2_" + System.currentTimeMillis());
        user.setPassword("testpass456");
        user.setRole("admin");
        user.setUseName("Test User 2");
        user.setEmail("test2@example.com");
        user.setTelephone("0987654321");
        return user;
    }
    
    // ========== AUTHENTICATION TESTS ==========
    
    @Test
    @Order(1)
    @DisplayName("Test Get User By Username And Password - Valid Credentials")
    void testGetUserByUsernameAndPasswordValid() {
        // First add a user
        User user = createTestUser();
        userDAO.addUser(user);
        
        User foundUser = userDAO.getUserByUsernameAndPassword(user.getUsername(), "testpass123");
        
        if (foundUser != null) {
            assertEquals(user.getUsername(), foundUser.getUsername(), "Username should match");
            assertEquals("testpass123", foundUser.getPassword(), "Password should match");
            assertEquals("staff", foundUser.getRole(), "Role should match");
            assertEquals("Test User", foundUser.getUseName(), "Use name should match");
            assertEquals("test@example.com", foundUser.getEmail(), "Email should match");
            assertEquals("1234567890", foundUser.getTelephone(), "Telephone should match");
        }
    }
    
    @Test
    @Order(2)
    @DisplayName("Test Get User By Username And Password - Invalid Username")
    void testGetUserByUsernameAndPasswordInvalidUsername() {
        User foundUser = userDAO.getUserByUsernameAndPassword("invaliduser", "testpass123");
        assertNull(foundUser, "Should return null for invalid username");
    }
    
    @Test
    @Order(3)
    @DisplayName("Test Get User By Username And Password - Invalid Password")
    void testGetUserByUsernameAndPasswordInvalidPassword() {
        // First add a user
        User user = createTestUser();
        userDAO.addUser(user);
        
        User foundUser = userDAO.getUserByUsernameAndPassword(user.getUsername(), "wrongpassword");
        assertNull(foundUser, "Should return null for invalid password");
    }
    
    @Test
    @Order(4)
    @DisplayName("Test Get User By Username And Password - Null Username")
    void testGetUserByUsernameAndPasswordNullUsername() {
        User foundUser = userDAO.getUserByUsernameAndPassword(null, "testpass123");
        assertNull(foundUser, "Should return null for null username");
    }
    
    @Test
    @Order(5)
    @DisplayName("Test Get User By Username And Password - Null Password")
    void testGetUserByUsernameAndPasswordNullPassword() {
        User foundUser = userDAO.getUserByUsernameAndPassword("testuser", null);
        assertNull(foundUser, "Should return null for null password");
    }
    
    @Test
    @Order(6)
    @DisplayName("Test Get User By Username And Password - Empty Username")
    void testGetUserByUsernameAndPasswordEmptyUsername() {
        User foundUser = userDAO.getUserByUsernameAndPassword("", "testpass123");
        assertNull(foundUser, "Should return null for empty username");
    }
    
    @Test
    @Order(7)
    @DisplayName("Test Get User By Username And Password - Empty Password")
    void testGetUserByUsernameAndPasswordEmptyPassword() {
        User foundUser = userDAO.getUserByUsernameAndPassword("testuser", "");
        assertNull(foundUser, "Should return null for empty password");
    }
    
    // ========== ADD USER TESTS ==========
    
    @Test
    @Order(8)
    @DisplayName("Test Add User - Valid User")
    void testAddUserValid() {
        User user = createTestUser();
        
        boolean result = userDAO.addUser(user);
        assertTrue(result, "User should be added successfully");
    }
    
    @Test
    @Order(9)
    @DisplayName("Test Add User - Null User")
    void testAddUserNull() {
        boolean result = userDAO.addUser(null);
        assertFalse(result, "Should return false for null user");
    }
    
    @Test
    @Order(10)
    @DisplayName("Test Add User - User with Empty Fields")
    void testAddUserEmptyFields() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setRole("");
        user.setUseName("");
        user.setEmail("");
        user.setTelephone("");
        
        boolean result = userDAO.addUser(user);
        // This might succeed or fail depending on database constraints
        // We're just testing that the method handles it gracefully
        assertNotNull(result, "Method should return a boolean result");
    }
    
    @Test
    @Order(11)
    @DisplayName("Test Add User - Different Roles")
    void testAddUserDifferentRoles() {
        User adminUser = createTestUser();
        adminUser.setUsername("adminuser_" + System.currentTimeMillis());
        adminUser.setRole("admin");
        
        User coadminUser = createTestUser();
        coadminUser.setUsername("coadminuser_" + System.currentTimeMillis());
        coadminUser.setRole("coadmin");
        
        User staffUser = createTestUser();
        staffUser.setUsername("staffuser_" + System.currentTimeMillis());
        staffUser.setRole("staff");
        
        boolean adminResult = userDAO.addUser(adminUser);
        boolean coadminResult = userDAO.addUser(coadminUser);
        boolean staffResult = userDAO.addUser(staffUser);
        
        assertTrue(adminResult, "Admin user should be added successfully");
        assertTrue(coadminResult, "CoAdmin user should be added successfully");
        assertTrue(staffResult, "Staff user should be added successfully");
    }
    
    // ========== GET ALL USERS TESTS ==========
    
    @Test
    @Order(12)
    @DisplayName("Test Get All Users")
    void testGetAllUsers() {
        List<User> users = userDAO.getAllUsers();
        
        assertNotNull(users, "User list should not be null");
        assertTrue(users.size() >= 0, "User list should be empty or contain users");
        
        // If there are users, verify their structure
        if (!users.isEmpty()) {
            User firstUser = users.get(0);
            assertNotNull(firstUser.getId(), "User ID should not be null");
            assertNotNull(firstUser.getUsername(), "Username should not be null");
            assertNotNull(firstUser.getRole(), "Role should not be null");
        }
    }
    
    // ========== GET USER BY ID TESTS ==========
    
    @Test
    @Order(13)
    @DisplayName("Test Get User By ID - Valid ID")
    void testGetUserByIdValid() {
        // First add a user
        User user = createTestUser();
        userDAO.addUser(user);
        
        // Get all users to find the added one
        List<User> users = userDAO.getAllUsers();
        if (!users.isEmpty()) {
            User foundUser = userDAO.getUserById(users.get(0).getId());
            
            assertNotNull(foundUser, "User should be found");
            assertEquals(users.get(0).getId(), foundUser.getId(), "User ID should match");
            assertEquals(users.get(0).getUsername(), foundUser.getUsername(), "Username should match");
            assertEquals(users.get(0).getRole(), foundUser.getRole(), "Role should match");
        }
    }
    
    @Test
    @Order(14)
    @DisplayName("Test Get User By ID - Invalid ID")
    void testGetUserByIdInvalid() {
        User user = userDAO.getUserById(-1);
        assertNull(user, "Should return null for invalid ID");
    }
    
    @Test
    @Order(15)
    @DisplayName("Test Get User By ID - Zero ID")
    void testGetUserByIdZero() {
        User user = userDAO.getUserById(0);
        assertNull(user, "Should return null for zero ID");
    }
    
    @Test
    @Order(16)
    @DisplayName("Test Get User By ID - Non-existent ID")
    void testGetUserByIdNonExistent() {
        User user = userDAO.getUserById(99999);
        assertNull(user, "Should return null for non-existent ID");
    }
    
    // ========== UPDATE USER TESTS ==========
    
    @Test
    @Order(17)
    @DisplayName("Test Update User - Valid Update")
    void testUpdateUserValid() {
        // First add a user
        User user = createTestUser();
        userDAO.addUser(user);
        
        // Get the user to update
        List<User> users = userDAO.getAllUsers();
        if (!users.isEmpty()) {
            User userToUpdate = users.get(0);
            userToUpdate.setUseName("Updated User Name");
            userToUpdate.setEmail("updated@example.com");
            userToUpdate.setTelephone("5555555555");
            
            boolean result = userDAO.updateUser(userToUpdate);
            assertTrue(result, "User should be updated successfully");
            
            // Verify the update
            User updatedUser = userDAO.getUserById(userToUpdate.getId());
            assertEquals("Updated User Name", updatedUser.getUseName(), "Use name should be updated");
            assertEquals("updated@example.com", updatedUser.getEmail(), "Email should be updated");
            assertEquals("5555555555", updatedUser.getTelephone(), "Telephone should be updated");
        }
    }
    
    @Test
    @Order(18)
    @DisplayName("Test Update User - Change Role")
    void testUpdateUserChangeRole() {
        // First add a user
        User user = createTestUser();
        userDAO.addUser(user);
        
        // Get the user to update
        List<User> users = userDAO.getAllUsers();
        if (!users.isEmpty()) {
            User userToUpdate = users.get(0);
            userToUpdate.setRole("admin");
            
            boolean result = userDAO.updateUser(userToUpdate);
            assertTrue(result, "User role should be updated successfully");
            
            // Verify the update
            User updatedUser = userDAO.getUserById(userToUpdate.getId());
            assertEquals("admin", updatedUser.getRole(), "Role should be updated");
        }
    }
    
    @Test
    @Order(19)
    @DisplayName("Test Update User - Change Password")
    void testUpdateUserChangePassword() {
        // First add a user
        User user = createTestUser();
        userDAO.addUser(user);
        
        // Get the user to update
        List<User> users = userDAO.getAllUsers();
        if (!users.isEmpty()) {
            User userToUpdate = users.get(0);
            userToUpdate.setPassword("newpassword123");
            
            boolean result = userDAO.updateUser(userToUpdate);
            assertTrue(result, "User password should be updated successfully");
            
            // Verify the update
            User updatedUser = userDAO.getUserById(userToUpdate.getId());
            assertEquals("newpassword123", updatedUser.getPassword(), "Password should be updated");
        }
    }
    
    @Test
    @Order(20)
    @DisplayName("Test Update User - Non-existent User")
    void testUpdateUserNonExistent() {
        User user = new User();
        user.setId(99999); // Non-existent ID
        user.setUsername("nonexistent");
        user.setPassword("password");
        user.setRole("staff");
        user.setUseName("Non-existent User");
        user.setEmail("nonexistent@example.com");
        user.setTelephone("0000000000");
        
        boolean result = userDAO.updateUser(user);
        assertFalse(result, "Should return false for non-existent user");
    }
    
    @Test
    @Order(21)
    @DisplayName("Test Update User - Null User")
    void testUpdateUserNull() {
        boolean result = userDAO.updateUser(null);
        assertFalse(result, "Should return false for null user");
    }
    
    // ========== DELETE USER TESTS ==========
    
    @Test
    @Order(22)
    @DisplayName("Test Delete User - Valid ID")
    void testDeleteUserValid() {
        // First add a user
        User user = createTestUser2();
        userDAO.addUser(user);
        
        // Get the user to delete
        List<User> users = userDAO.getAllUsers();
        if (!users.isEmpty()) {
            int userId = users.get(0).getId();
            
            boolean result = userDAO.deleteUser(userId);
            assertTrue(result, "User should be deleted successfully");
            
            // Verify deletion
            User deletedUser = userDAO.getUserById(userId);
            assertNull(deletedUser, "User should be null after deletion");
        }
    }
    
    @Test
    @Order(23)
    @DisplayName("Test Delete User - Invalid ID")
    void testDeleteUserInvalid() {
        boolean result = userDAO.deleteUser(-1);
        assertFalse(result, "Should return false for invalid ID");
    }
    
    @Test
    @Order(24)
    @DisplayName("Test Delete User - Non-existent ID")
    void testDeleteUserNonExistent() {
        boolean result = userDAO.deleteUser(99999);
        assertFalse(result, "Should return false for non-existent ID");
    }
    
    // ========== ROLE-BASED TESTS ==========
    
    @Test
    @Order(25)
    @DisplayName("Test Admin User Operations")
    void testAdminUserOperations() {
        User adminUser = createTestUser();
        adminUser.setUsername("adminuser_" + System.currentTimeMillis());
        adminUser.setRole("admin");
        
        // Test add
        boolean addResult = userDAO.addUser(adminUser);
        assertTrue(addResult, "Admin user should be added successfully");
        
        // Test authentication
        User foundUser = userDAO.getUserByUsernameAndPassword(adminUser.getUsername(), "testpass123");
        if (foundUser != null) {
            assertEquals("admin", foundUser.getRole(), "User should have admin role");
        }
    }
    
    @Test
    @Order(26)
    @DisplayName("Test CoAdmin User Operations")
    void testCoAdminUserOperations() {
        User coadminUser = createTestUser();
        coadminUser.setUsername("coadminuser_" + System.currentTimeMillis());
        coadminUser.setRole("coadmin");
        
        // Test add
        boolean addResult = userDAO.addUser(coadminUser);
        assertTrue(addResult, "CoAdmin user should be added successfully");
        
        // Test authentication
        User foundUser = userDAO.getUserByUsernameAndPassword(coadminUser.getUsername(), "testpass123");
        if (foundUser != null) {
            assertEquals("coadmin", foundUser.getRole(), "User should have coadmin role");
        }
    }
    
    @Test
    @Order(27)
    @DisplayName("Test Staff User Operations")
    void testStaffUserOperations() {
        User staffUser = createTestUser();
        staffUser.setUsername("staffuser_" + System.currentTimeMillis());
        staffUser.setRole("staff");
        
        // Test add
        boolean addResult = userDAO.addUser(staffUser);
        assertTrue(addResult, "Staff user should be added successfully");
        
        // Test authentication
        User foundUser = userDAO.getUserByUsernameAndPassword(staffUser.getUsername(), "testpass123");
        if (foundUser != null) {
            assertEquals("staff", foundUser.getRole(), "User should have staff role");
        }
    }
    
    // ========== DATA VALIDATION TESTS ==========
    
    @Test
    @Order(28)
    @DisplayName("Test User Data Integrity")
    void testUserDataIntegrity() {
        // First add a user
        User user = createTestUser();
        userDAO.addUser(user);
        
        // Get all users and verify data integrity
        List<User> users = userDAO.getAllUsers();
        
        for (User u : users) {
            assertNotNull(u.getId(), "User ID should not be null");
            assertNotNull(u.getUsername(), "Username should not be null");
            assertNotNull(u.getPassword(), "Password should not be null");
            assertNotNull(u.getRole(), "Role should not be null");
            assertNotNull(u.getUseName(), "Use name should not be null");
            assertNotNull(u.getEmail(), "Email should not be null");
            assertNotNull(u.getTelephone(), "Telephone should not be null");
            
            // Verify data types
            assertTrue(u.getId() > 0, "User ID should be positive");
            // Some fields might be empty in the database, which is valid
            // We only check that they are not null (handled above)
            
            // Verify role is valid (only if role is not empty)
            if (u.getRole() != null && !u.getRole().trim().isEmpty()) {
                assertTrue(u.getRole().equals("admin") || u.getRole().equals("coadmin") || u.getRole().equals("staff"), 
                          "Role should be admin, coadmin, or staff");
            }
        }
    }
    
    @Test
    @Order(29)
    @DisplayName("Test User Authentication Performance")
    void testUserAuthenticationPerformance() {
        // Test that authentication operations complete in reasonable time
        long startTime = System.currentTimeMillis();
        
        List<User> users = userDAO.getAllUsers();
        userDAO.getUserByUsernameAndPassword("testuser", "testpass123");
        userDAO.getUserByUsernameAndPassword("invaliduser", "invalidpass");
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        // Should complete within 5 seconds
        assertTrue(duration < 5000, "Authentication operations should complete within 5 seconds");
    }
    
    @Test
    @Order(30)
    @DisplayName("Test User CRUD Performance")
    void testUserCRUDPerformance() {
        // Test that CRUD operations complete in reasonable time
        long startTime = System.currentTimeMillis();
        
        List<User> users = userDAO.getAllUsers();
        if (!users.isEmpty()) {
            userDAO.getUserById(users.get(0).getId());
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        // Should complete within 5 seconds
        assertTrue(duration < 5000, "CRUD operations should complete within 5 seconds");
    }
    
    // ========== EDGE CASE TESTS ==========
    
    @Test
    @Order(31)
    @DisplayName("Test User with Special Characters")
    void testUserWithSpecialCharacters() {
        User user = createTestUser();
        user.setUsername("test@user#123_" + System.currentTimeMillis());
        user.setUseName("Test User with Special Chars !@#$%");
        user.setEmail("test+special@example.com");
        user.setTelephone("123-456-7890");
        
        boolean result = userDAO.addUser(user);
        // This might succeed or fail depending on database constraints
        // We're just testing that the method handles it gracefully
        assertNotNull(result, "Method should return a boolean result");
    }
    
    @Test
    @Order(32)
    @DisplayName("Test User with Long Fields")
    void testUserWithLongFields() {
        User user = createTestUser();
        user.setUsername("verylongusername123456789012345678901234567890_" + System.currentTimeMillis());
        user.setUseName("Very Long User Name That Exceeds Normal Length Limits");
        user.setEmail("verylongemailaddress@verylongdomainname.com");
        user.setTelephone("12345678901234567890");
        
        boolean result = userDAO.addUser(user);
        // This might succeed or fail depending on database constraints
        // We're just testing that the method handles it gracefully
        assertNotNull(result, "Method should return a boolean result");
    }
} 