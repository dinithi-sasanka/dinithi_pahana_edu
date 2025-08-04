package com.example.dinithi_pahana_edu.userRole;

import com.example.dinithi_pahana_edu.model.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit Tests for User Management Operations
 * Tests add new user, view users, edit users, and delete users
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserManagementTest {
    
    private List<User> userList;
    private int nextUserId;
    
    @BeforeEach
    void setUp() {
        userList = new ArrayList<>();
        nextUserId = 1;
        
        // Create some existing users for testing
        User admin1 = new User();
        admin1.setId(1);
        admin1.setUsername("admin");
        admin1.setPassword("1234");
        admin1.setRole("admin");
        
        User coadmin1 = new User();
        coadmin1.setId(2);
        coadmin1.setUsername("coadmin");
        coadmin1.setPassword("1234");
        coadmin1.setRole("coadmin");
        
        User staff1 = new User();
        staff1.setId(3);
        staff1.setUsername("staff");
        staff1.setPassword("1234");
        staff1.setRole("staff");
        
        User admin2 = new User();
        admin2.setId(4);
        admin2.setUsername("admin2");
        admin2.setPassword("345");
        admin2.setRole("admin");
        
        User staff2 = new User();
        staff2.setId(5);
        staff2.setUsername("staff1");
        staff2.setPassword("456");
        staff2.setRole("staff");
        
        userList.add(admin1);
        userList.add(coadmin1);
        userList.add(staff1);
        userList.add(admin2);
        userList.add(staff2);
    }
    
    @Test
    @Order(1)
    @DisplayName("Test Add New User - Valid Admin")
    void testAddNewUserValidAdmin() {
        // Test adding a new admin user
        User newUser = new User();
        newUser.setUsername("newadmin");
        newUser.setPassword("admin123");
        newUser.setRole("admin");
        
        boolean added = addUser(newUser);
        assertTrue(added);
        assertEquals(6, userList.size());
        
        User addedUser = getUserByUsername("newadmin");
        assertNotNull(addedUser);
        assertEquals("newadmin", addedUser.getUsername());
        assertEquals("admin123", addedUser.getPassword());
        assertEquals("admin", addedUser.getRole());
    }
    
    @Test
    @Order(2)
    @DisplayName("Test Add New User - Valid Coadmin")
    void testAddNewUserValidCoadmin() {
        // Test adding a new coadmin user
        User newUser = new User();
        newUser.setUsername("newcoadmin");
        newUser.setPassword("coadmin123");
        newUser.setRole("coadmin");
        
        boolean added = addUser(newUser);
        assertTrue(added);
        assertEquals(6, userList.size());
        
        User addedUser = getUserByUsername("newcoadmin");
        assertNotNull(addedUser);
        assertEquals("newcoadmin", addedUser.getUsername());
        assertEquals("coadmin123", addedUser.getPassword());
        assertEquals("coadmin", addedUser.getRole());
    }
    
    @Test
    @Order(3)
    @DisplayName("Test Add New User - Valid Staff")
    void testAddNewUserValidStaff() {
        // Test adding a new staff user
        User newUser = new User();
        newUser.setUsername("newstaff");
        newUser.setPassword("staff123");
        newUser.setRole("staff");
        
        boolean added = addUser(newUser);
        assertTrue(added);
        assertEquals(6, userList.size());
        
        User addedUser = getUserByUsername("newstaff");
        assertNotNull(addedUser);
        assertEquals("newstaff", addedUser.getUsername());
        assertEquals("staff123", addedUser.getPassword());
        assertEquals("staff", addedUser.getRole());
    }
    
    @Test
    @Order(4)
    @DisplayName("Test Add New User - Duplicate Username")
    void testAddNewUserDuplicateUsername() {
        // Test adding user with existing username
        User newUser = new User();
        newUser.setUsername("admin"); // Already exists
        newUser.setPassword("newpassword");
        newUser.setRole("admin");
        
        boolean added = addUser(newUser);
        assertFalse(added);
        assertEquals(5, userList.size()); // Size should remain unchanged
    }
    
    @Test
    @Order(5)
    @DisplayName("Test Add New User - Invalid Role")
    void testAddNewUserInvalidRole() {
        // Test adding user with invalid role
        User newUser = new User();
        newUser.setUsername("invaliduser");
        newUser.setPassword("password123");
        newUser.setRole("invalid_role");
        
        boolean added = addUser(newUser);
        assertFalse(added);
        assertEquals(5, userList.size()); // Size should remain unchanged
    }
    
    @Test
    @Order(6)
    @DisplayName("Test Add New User - Empty Username")
    void testAddNewUserEmptyUsername() {
        // Test adding user with empty username
        User newUser = new User();
        newUser.setUsername("");
        newUser.setPassword("password123");
        newUser.setRole("admin");
        
        boolean added = addUser(newUser);
        assertFalse(added);
        assertEquals(5, userList.size()); // Size should remain unchanged
    }
    
    @Test
    @Order(7)
    @DisplayName("Test Add New User - Empty Password")
    void testAddNewUserEmptyPassword() {
        // Test adding user with empty password
        User newUser = new User();
        newUser.setUsername("emptypassuser");
        newUser.setPassword("");
        newUser.setRole("admin");
        
        boolean added = addUser(newUser);
        assertFalse(added);
        assertEquals(5, userList.size()); // Size should remain unchanged
    }
    
    @Test
    @Order(8)
    @DisplayName("Test View All Users")
    void testViewAllUsers() {
        // Test viewing all users
        List<User> allUsers = getAllUsers();
        assertNotNull(allUsers);
        assertEquals(5, allUsers.size());
        
        // Verify user details
        assertEquals("admin", allUsers.get(0).getUsername());
        assertEquals("admin", allUsers.get(0).getRole());
        
        assertEquals("coadmin", allUsers.get(1).getUsername());
        assertEquals("coadmin", allUsers.get(1).getRole());
        
        assertEquals("staff", allUsers.get(2).getUsername());
        assertEquals("staff", allUsers.get(2).getRole());
    }
    
    @Test
    @Order(9)
    @DisplayName("Test View User by ID")
    void testViewUserById() {
        // Test viewing user by ID
        User user = getUserById(1);
        assertNotNull(user);
        assertEquals("admin", user.getUsername());
        assertEquals("admin", user.getRole());
        
        User user2 = getUserById(3);
        assertNotNull(user2);
        assertEquals("staff", user2.getUsername());
        assertEquals("staff", user2.getRole());
    }
    
    @Test
    @Order(10)
    @DisplayName("Test View User by Username")
    void testViewUserByUsername() {
        // Test viewing user by username
        User user = getUserByUsername("admin");
        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("admin", user.getRole());
        
        User user2 = getUserByUsername("staff");
        assertNotNull(user2);
        assertEquals(3, user2.getId());
        assertEquals("staff", user2.getRole());
    }
    
    @Test
    @Order(11)
    @DisplayName("Test View Users by Role")
    void testViewUsersByRole() {
        // Test viewing users by role
        List<User> adminUsers = getUsersByRole("admin");
        assertNotNull(adminUsers);
        assertEquals(2, adminUsers.size());
        
        List<User> staffUsers = getUsersByRole("staff");
        assertNotNull(staffUsers);
        assertEquals(2, staffUsers.size());
        
        List<User> coadminUsers = getUsersByRole("coadmin");
        assertNotNull(coadminUsers);
        assertEquals(1, coadminUsers.size());
    }
    
    @Test
    @Order(12)
    @DisplayName("Test View User - Invalid ID")
    void testViewUserInvalidId() {
        // Test viewing user with invalid ID
        User user = getUserById(999);
        assertNull(user);
    }
    
    @Test
    @Order(13)
    @DisplayName("Test View User - Invalid Username")
    void testViewUserInvalidUsername() {
        // Test viewing user with invalid username
        User user = getUserByUsername("nonexistent");
        assertNull(user);
    }
    
    @Test
    @Order(14)
    @DisplayName("Test Edit User - Valid Data")
    void testEditUserValidData() {
        // Test editing user with valid data
        User userToEdit = getUserById(1); // admin user
        assertNotNull(userToEdit);
        
        // Edit user details
        userToEdit.setPassword("newpassword123");
        userToEdit.setRole("coadmin");
        
        boolean updated = updateUser(userToEdit);
        assertTrue(updated);
        
        // Verify changes
        User updatedUser = getUserById(1);
        assertEquals("newpassword123", updatedUser.getPassword());
        assertEquals("coadmin", updatedUser.getRole());
        assertEquals("admin", updatedUser.getUsername()); // Username should remain unchanged
    }
    
    @Test
    @Order(15)
    @DisplayName("Test Edit User - Change Role")
    void testEditUserChangeRole() {
        // Test changing user role
        User userToEdit = getUserById(3); // staff user
        assertNotNull(userToEdit);
        
        userToEdit.setRole("admin");
        boolean updated = updateUser(userToEdit);
        assertTrue(updated);
        
        User updatedUser = getUserById(3);
        assertEquals("admin", updatedUser.getRole());
    }
    
    @Test
    @Order(16)
    @DisplayName("Test Edit User - Invalid Role")
    void testEditUserInvalidRole() {
        // Test editing user with invalid role
        User userToEdit = getUserById(1);
        assertNotNull(userToEdit);
        
        String originalRole = userToEdit.getRole();
        
        // Create a new user object with invalid role
        User invalidUser = new User();
        invalidUser.setId(1);
        invalidUser.setUsername("admin");
        invalidUser.setPassword("1234");
        invalidUser.setRole("invalid_role");
        
        boolean updated = updateUser(invalidUser);
        assertFalse(updated);
        
        // Verify role was not changed
        User user = getUserById(1);
        assertEquals(originalRole, user.getRole());
    }
    
    @Test
    @Order(17)
    @DisplayName("Test Edit User - Empty Password")
    void testEditUserEmptyPassword() {
        // Test editing user with empty password
        User userToEdit = getUserById(1);
        assertNotNull(userToEdit);
        
        String originalPassword = userToEdit.getPassword();
        
        // Create a new user object with empty password
        User emptyPasswordUser = new User();
        emptyPasswordUser.setId(1);
        emptyPasswordUser.setUsername("admin");
        emptyPasswordUser.setPassword("");
        emptyPasswordUser.setRole("admin");
        
        boolean updated = updateUser(emptyPasswordUser);
        assertFalse(updated);
        
        // Verify password was not changed
        User user = getUserById(1);
        assertEquals(originalPassword, user.getPassword());
    }
    
    @Test
    @Order(18)
    @DisplayName("Test Edit User - Non-existent User")
    void testEditUserNonExistent() {
        // Test editing non-existent user
        User nonExistentUser = new User();
        nonExistentUser.setId(999);
        nonExistentUser.setUsername("nonexistent");
        nonExistentUser.setPassword("password");
        nonExistentUser.setRole("admin");
        
        boolean updated = updateUser(nonExistentUser);
        assertFalse(updated);
    }
    
    @Test
    @Order(19)
    @DisplayName("Test Delete User - Valid ID")
    void testDeleteUserValidId() {
        // Test deleting user with valid ID
        int initialSize = userList.size();
        boolean deleted = deleteUser(5); // staff1 user
        
        assertTrue(deleted);
        assertEquals(initialSize - 1, userList.size());
        
        // Verify user is removed
        User deletedUser = getUserById(5);
        assertNull(deletedUser);
        
        // Verify other users remain
        assertNotNull(getUserById(1));
        assertNotNull(getUserById(2));
        assertNotNull(getUserById(3));
        assertNotNull(getUserById(4));
    }
    
    @Test
    @Order(20)
    @DisplayName("Test Delete User - Invalid ID")
    void testDeleteUserInvalidId() {
        // Test deleting user with invalid ID
        int initialSize = userList.size();
        boolean deleted = deleteUser(999);
        
        assertFalse(deleted);
        assertEquals(initialSize, userList.size()); // Size should remain unchanged
    }
    
    @Test
    @Order(21)
    @DisplayName("Test Delete User - By Username")
    void testDeleteUserByUsername() {
        // Test deleting user by username
        int initialSize = userList.size();
        boolean deleted = deleteUserByUsername("staff1");
        
        assertTrue(deleted);
        assertEquals(initialSize - 1, userList.size());
        
        // Verify user is removed
        User deletedUser = getUserByUsername("staff1");
        assertNull(deletedUser);
    }
    
    @Test
    @Order(22)
    @DisplayName("Test Delete User - Non-existent Username")
    void testDeleteUserNonExistentUsername() {
        // Test deleting user with non-existent username
        int initialSize = userList.size();
        boolean deleted = deleteUserByUsername("nonexistent");
        
        assertFalse(deleted);
        assertEquals(initialSize, userList.size()); // Size should remain unchanged
    }
    
    @Test
    @Order(23)
    @DisplayName("Test User Validation")
    void testUserValidation() {
        // Test valid user
        User validUser = new User();
        validUser.setUsername("validuser");
        validUser.setPassword("password123");
        validUser.setRole("admin");
        assertTrue(isValidUser(validUser));
        
        // Test invalid user (empty username)
        User invalidUser1 = new User();
        invalidUser1.setUsername("");
        invalidUser1.setPassword("password123");
        invalidUser1.setRole("admin");
        assertFalse(isValidUser(invalidUser1));
        
        // Test invalid user (empty password)
        User invalidUser2 = new User();
        invalidUser2.setUsername("validuser");
        invalidUser2.setPassword("");
        invalidUser2.setRole("admin");
        assertFalse(isValidUser(invalidUser2));
        
        // Test invalid user (invalid role)
        User invalidUser3 = new User();
        invalidUser3.setUsername("validuser");
        invalidUser3.setPassword("password123");
        invalidUser3.setRole("invalid_role");
        assertFalse(isValidUser(invalidUser3));
    }
    
    @Test
    @Order(24)
    @DisplayName("Test User Count by Role")
    void testUserCountByRole() {
        // Test counting users by role
        int adminCount = getUserCountByRole("admin");
        assertEquals(2, adminCount);
        
        int staffCount = getUserCountByRole("staff");
        assertEquals(2, staffCount);
        
        int coadminCount = getUserCountByRole("coadmin");
        assertEquals(1, coadminCount);
        
        int invalidRoleCount = getUserCountByRole("invalid_role");
        assertEquals(0, invalidRoleCount);
    }
    
    // Helper methods
    private boolean addUser(User user) {
        if (!isValidUser(user)) {
            return false;
        }
        
        // Check for duplicate username
        if (getUserByUsername(user.getUsername()) != null) {
            return false;
        }
        
        user.setId(nextUserId++);
        userList.add(user);
        return true;
    }
    
    private List<User> getAllUsers() {
        return new ArrayList<>(userList);
    }
    
    private User getUserById(int id) {
        return userList.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    private User getUserByUsername(String username) {
        return userList.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
    
    private List<User> getUsersByRole(String role) {
        return userList.stream()
                .filter(u -> u.getRole().equals(role))
                .toList();
    }
    
    private boolean updateUser(User user) {
        if (!isValidUser(user)) {
            return false;
        }
        
        User existingUser = getUserById(user.getId());
        if (existingUser == null) {
            return false;
        }
        
        // Only update if the new data is valid
        if (isValidRole(user.getRole()) && 
            user.getPassword() != null && 
            !user.getPassword().trim().isEmpty()) {
            
            // Update user details
            existingUser.setPassword(user.getPassword());
            existingUser.setRole(user.getRole());
            return true;
        }
        
        return false;
    }
    
    private boolean deleteUser(int userId) {
        return userList.removeIf(u -> u.getId() == userId);
    }
    
    private boolean deleteUserByUsername(String username) {
        return userList.removeIf(u -> u.getUsername().equals(username));
    }
    
    private boolean isValidUser(User user) {
        return user != null &&
               user.getUsername() != null &&
               !user.getUsername().trim().isEmpty() &&
               user.getPassword() != null &&
               !user.getPassword().trim().isEmpty() &&
               isValidRole(user.getRole());
    }
    
    private boolean isValidRole(String role) {
        return "admin".equals(role) ||
               "coadmin".equals(role) ||
               "staff".equals(role);
    }
    
    private int getUserCountByRole(String role) {
        return (int) userList.stream()
                .filter(u -> u.getRole().equals(role))
                .count();
    }
} 