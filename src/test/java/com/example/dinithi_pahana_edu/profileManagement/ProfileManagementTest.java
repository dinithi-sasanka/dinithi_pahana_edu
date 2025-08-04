package com.example.dinithi_pahana_edu.profileManagement;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * JUnit tests for Profile Management functionality
 * Tests: view user details, edit profile, save profile changes
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProfileManagementTest {
    
    // Mock data for testing
    private static List<User> users;
    private static User currentUser;
    
    @BeforeAll
    static void setUp() {
        users = new ArrayList<>();
        
        // Create test users with different roles
        User admin = new User();
        admin.setId(1);
        admin.setUsername("admin");
        admin.setPassword("1234");
        admin.setRole("admin");
        admin.setEmail("admin@example.com");
        admin.setFullName("Administrator User");
        admin.setPhone("1234567890");
        admin.setAddress("123 Admin Street");
        
        User coadmin = new User();
        coadmin.setId(2);
        coadmin.setUsername("coadmin");
        coadmin.setPassword("1234");
        coadmin.setRole("coadmin");
        coadmin.setEmail("coadmin@example.com");
        coadmin.setFullName("Co-Administrator User");
        coadmin.setPhone("0987654321");
        coadmin.setAddress("456 CoAdmin Avenue");
        
        User staff = new User();
        staff.setId(3);
        staff.setUsername("staff");
        staff.setPassword("1234");
        staff.setRole("staff");
        staff.setEmail("staff@example.com");
        staff.setFullName("Staff Member");
        staff.setPhone("5555555555");
        staff.setAddress("789 Staff Road");
        
        users.add(admin);
        users.add(coadmin);
        users.add(staff);
        
        // Set default current user
        currentUser = admin;
    }
    
    // Helper method to simulate getting user by ID
    private User getUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    // Helper method to simulate getting user by username
    private User getUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
    
    // Helper method to simulate updating user profile
    private boolean updateUserProfile(User updatedUser) {
        User existingUser = getUserById(updatedUser.getId());
        if (existingUser == null) {
            return false;
        }
        
        // Validate the updated user data
        if (!isValidProfileData(updatedUser)) {
            return false;
        }
        
        // Update the user profile
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setAddress(updatedUser.getAddress());
        
        return true;
    }
    
    // Helper method to validate profile data
    private boolean isValidProfileData(User user) {
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return false;
        }
        if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
            return false;
        }
        if (user.getPhone() == null || user.getPhone().trim().isEmpty()) {
            return false;
        }
        if (user.getAddress() == null || user.getAddress().trim().isEmpty()) {
            return false;
        }
        
        // Basic email validation
        if (!user.getEmail().contains("@")) {
            return false;
        }
        
        // Basic phone validation (should contain only digits)
        if (!user.getPhone().matches("\\d+")) {
            return false;
        }
        
        return true;
    }
    
    // Helper method to simulate changing password
    private boolean changePassword(int userId, String currentPassword, String newPassword) {
        User user = getUserById(userId);
        if (user == null) {
            return false;
        }
        
        // Verify current password
        if (!user.getPassword().equals(currentPassword)) {
            return false;
        }
        
        // Validate new password
        if (newPassword == null || newPassword.trim().isEmpty() || newPassword.length() < 4) {
            return false;
        }
        
        // Update password
        user.setPassword(newPassword);
        return true;
    }
    
    // Helper method to get current user profile
    private User getCurrentUserProfile() {
        return currentUser;
    }
    
    // Helper method to set current user
    private void setCurrentUser(String username) {
        currentUser = getUserByUsername(username);
    }
    
    // ========== VIEW USER DETAILS TESTS ==========
    
    @Test
    @Order(1)
    @DisplayName("Test View Profile - Admin User")
    void testViewProfileAdmin() {
        setCurrentUser("admin");
        User profile = getCurrentUserProfile();
        
        assertNotNull(profile);
        assertEquals("admin", profile.getUsername());
        assertEquals("admin", profile.getRole());
        assertEquals("admin@example.com", profile.getEmail());
        assertEquals("Administrator User", profile.getFullName());
        assertEquals("1234567890", profile.getPhone());
        assertEquals("123 Admin Street", profile.getAddress());
    }
    
    @Test
    @Order(2)
    @DisplayName("Test View Profile - CoAdmin User")
    void testViewProfileCoAdmin() {
        setCurrentUser("coadmin");
        User profile = getCurrentUserProfile();
        
        assertNotNull(profile);
        assertEquals("coadmin", profile.getUsername());
        assertEquals("coadmin", profile.getRole());
        assertEquals("coadmin@example.com", profile.getEmail());
        assertEquals("Co-Administrator User", profile.getFullName());
        assertEquals("0987654321", profile.getPhone());
        assertEquals("456 CoAdmin Avenue", profile.getAddress());
    }
    
    @Test
    @Order(3)
    @DisplayName("Test View Profile - Staff User")
    void testViewProfileStaff() {
        setCurrentUser("staff");
        User profile = getCurrentUserProfile();
        
        assertNotNull(profile);
        assertEquals("staff", profile.getUsername());
        assertEquals("staff", profile.getRole());
        assertEquals("staff@example.com", profile.getEmail());
        assertEquals("Staff Member", profile.getFullName());
        assertEquals("5555555555", profile.getPhone());
        assertEquals("789 Staff Road", profile.getAddress());
    }
    
    @Test
    @Order(4)
    @DisplayName("Test View Profile - Non-existent User")
    void testViewProfileNonExistent() {
        setCurrentUser("nonexistent");
        User profile = getCurrentUserProfile();
        
        assertNull(profile);
    }
    
    @Test
    @Order(5)
    @DisplayName("Test View Profile - All User Details Present")
    void testViewProfileAllDetailsPresent() {
        setCurrentUser("admin");
        User profile = getCurrentUserProfile();
        
        assertNotNull(profile);
        assertNotNull(profile.getId());
        assertNotNull(profile.getUsername());
        assertNotNull(profile.getRole());
        assertNotNull(profile.getEmail());
        assertNotNull(profile.getFullName());
        assertNotNull(profile.getPhone());
        assertNotNull(profile.getAddress());
        assertNotNull(profile.getPassword());
    }
    
    // ========== EDIT PROFILE TESTS ==========
    
    @Test
    @Order(6)
    @DisplayName("Test Edit Profile - Valid Data")
    void testEditProfileValidData() {
        User originalUser = getUserById(1);
        assertNotNull(originalUser);
        
        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setUsername("admin");
        updatedUser.setPassword("1234");
        updatedUser.setRole("admin");
        updatedUser.setEmail("newadmin@example.com");
        updatedUser.setFullName("Updated Administrator");
        updatedUser.setPhone("1111111111");
        updatedUser.setAddress("999 New Admin Street");
        
        boolean updated = updateUserProfile(updatedUser);
        assertTrue(updated);
        
        User user = getUserById(1);
        assertEquals("newadmin@example.com", user.getEmail());
        assertEquals("Updated Administrator", user.getFullName());
        assertEquals("1111111111", user.getPhone());
        assertEquals("999 New Admin Street", user.getAddress());
        
        // Verify other fields remain unchanged
        assertEquals("admin", user.getUsername());
        assertEquals("admin", user.getRole());
        assertEquals("1234", user.getPassword());
    }
    
    @Test
    @Order(7)
    @DisplayName("Test Edit Profile - Invalid Email")
    void testEditProfileInvalidEmail() {
        User originalUser = getUserById(2);
        assertNotNull(originalUser);
        String originalEmail = originalUser.getEmail();
        
        User updatedUser = new User();
        updatedUser.setId(2);
        updatedUser.setUsername("coadmin");
        updatedUser.setPassword("1234");
        updatedUser.setRole("coadmin");
        updatedUser.setEmail("invalid-email"); // Invalid email
        updatedUser.setFullName("Co-Administrator User");
        updatedUser.setPhone("0987654321");
        updatedUser.setAddress("456 CoAdmin Avenue");
        
        boolean updated = updateUserProfile(updatedUser);
        assertFalse(updated);
        
        // Verify email was not changed
        User user = getUserById(2);
        assertEquals(originalEmail, user.getEmail());
    }
    
    @Test
    @Order(8)
    @DisplayName("Test Edit Profile - Empty Full Name")
    void testEditProfileEmptyFullName() {
        User originalUser = getUserById(3);
        assertNotNull(originalUser);
        String originalFullName = originalUser.getFullName();
        
        User updatedUser = new User();
        updatedUser.setId(3);
        updatedUser.setUsername("staff");
        updatedUser.setPassword("1234");
        updatedUser.setRole("staff");
        updatedUser.setEmail("staff@example.com");
        updatedUser.setFullName(""); // Empty full name
        updatedUser.setPhone("5555555555");
        updatedUser.setAddress("789 Staff Road");
        
        boolean updated = updateUserProfile(updatedUser);
        assertFalse(updated);
        
        // Verify full name was not changed
        User user = getUserById(3);
        assertEquals(originalFullName, user.getFullName());
    }
    
    @Test
    @Order(9)
    @DisplayName("Test Edit Profile - Invalid Phone Number")
    void testEditProfileInvalidPhone() {
        User originalUser = getUserById(1);
        assertNotNull(originalUser);
        String originalPhone = originalUser.getPhone();
        
        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setUsername("admin");
        updatedUser.setPassword("1234");
        updatedUser.setRole("admin");
        updatedUser.setEmail("admin@example.com");
        updatedUser.setFullName("Administrator User");
        updatedUser.setPhone("abc123"); // Invalid phone (contains letters)
        updatedUser.setAddress("123 Admin Street");
        
        boolean updated = updateUserProfile(updatedUser);
        assertFalse(updated);
        
        // Verify phone was not changed
        User user = getUserById(1);
        assertEquals(originalPhone, user.getPhone());
    }
    
    @Test
    @Order(10)
    @DisplayName("Test Edit Profile - Non-existent User")
    void testEditProfileNonExistentUser() {
        User updatedUser = new User();
        updatedUser.setId(999); // Non-existent ID
        updatedUser.setUsername("nonexistent");
        updatedUser.setPassword("1234");
        updatedUser.setRole("admin");
        updatedUser.setEmail("test@example.com");
        updatedUser.setFullName("Test User");
        updatedUser.setPhone("1234567890");
        updatedUser.setAddress("Test Address");
        
        boolean updated = updateUserProfile(updatedUser);
        assertFalse(updated);
    }
    
    // ========== SAVE PROFILE TESTS ==========
    
    @Test
    @Order(11)
    @DisplayName("Test Save Profile - Successful Save")
    void testSaveProfileSuccessful() {
        User originalUser = getUserById(2);
        assertNotNull(originalUser);
        
        User updatedUser = new User();
        updatedUser.setId(2);
        updatedUser.setUsername("coadmin");
        updatedUser.setPassword("1234");
        updatedUser.setRole("coadmin");
        updatedUser.setEmail("saved@example.com");
        updatedUser.setFullName("Saved CoAdmin");
        updatedUser.setPhone("2222222222");
        updatedUser.setAddress("777 Saved Street");
        
        boolean saved = updateUserProfile(updatedUser);
        assertTrue(saved);
        
        // Verify the profile was actually saved
        User savedUser = getUserById(2);
        assertEquals("saved@example.com", savedUser.getEmail());
        assertEquals("Saved CoAdmin", savedUser.getFullName());
        assertEquals("2222222222", savedUser.getPhone());
        assertEquals("777 Saved Street", savedUser.getAddress());
    }
    
    @Test
    @Order(12)
    @DisplayName("Test Save Profile - Partial Update")
    void testSaveProfilePartialUpdate() {
        User originalUser = getUserById(3);
        assertNotNull(originalUser);
        String originalEmail = originalUser.getEmail();
        String originalPhone = originalUser.getPhone();
        
        User updatedUser = new User();
        updatedUser.setId(3);
        updatedUser.setUsername("staff");
        updatedUser.setPassword("1234");
        updatedUser.setRole("staff");
        updatedUser.setEmail("partial@example.com");
        updatedUser.setFullName("Partially Updated Staff");
        updatedUser.setPhone("3333333333");
        updatedUser.setAddress("888 Partial Road");
        
        boolean saved = updateUserProfile(updatedUser);
        assertTrue(saved);
        
        // Verify only specified fields were updated
        User savedUser = getUserById(3);
        assertEquals("partial@example.com", savedUser.getEmail());
        assertEquals("Partially Updated Staff", savedUser.getFullName());
        assertEquals("3333333333", savedUser.getPhone());
        assertEquals("888 Partial Road", savedUser.getAddress());
        
        // Verify other fields remain unchanged
        assertEquals("staff", savedUser.getUsername());
        assertEquals("staff", savedUser.getRole());
        assertEquals("1234", savedUser.getPassword());
    }
    
    // ========== PASSWORD CHANGE TESTS ==========
    
    @Test
    @Order(13)
    @DisplayName("Test Change Password - Valid Change")
    void testChangePasswordValid() {
        User user = getUserById(1);
        assertNotNull(user);
        String originalPassword = user.getPassword();
        
        boolean changed = changePassword(1, "1234", "newpassword123");
        assertTrue(changed);
        
        User updatedUser = getUserById(1);
        assertEquals("newpassword123", updatedUser.getPassword());
        assertNotEquals(originalPassword, updatedUser.getPassword());
    }
    
    @Test
    @Order(14)
    @DisplayName("Test Change Password - Wrong Current Password")
    void testChangePasswordWrongCurrent() {
        User user = getUserById(2);
        assertNotNull(user);
        String originalPassword = user.getPassword();
        
        boolean changed = changePassword(2, "wrongpassword", "newpassword123");
        assertFalse(changed);
        
        // Verify password was not changed
        User updatedUser = getUserById(2);
        assertEquals(originalPassword, updatedUser.getPassword());
    }
    
    @Test
    @Order(15)
    @DisplayName("Test Change Password - Empty New Password")
    void testChangePasswordEmptyNew() {
        User user = getUserById(3);
        assertNotNull(user);
        String originalPassword = user.getPassword();
        
        boolean changed = changePassword(3, "1234", "");
        assertFalse(changed);
        
        // Verify password was not changed
        User updatedUser = getUserById(3);
        assertEquals(originalPassword, updatedUser.getPassword());
    }
    
    @Test
    @Order(16)
    @DisplayName("Test Change Password - Short New Password")
    void testChangePasswordShortNew() {
        User user = getUserById(1);
        assertNotNull(user);
        String originalPassword = user.getPassword();
        
        boolean changed = changePassword(1, "newpassword123", "123");
        assertFalse(changed);
        
        // Verify password was not changed
        User updatedUser = getUserById(1);
        assertEquals(originalPassword, updatedUser.getPassword());
    }
    
    @Test
    @Order(17)
    @DisplayName("Test Change Password - Non-existent User")
    void testChangePasswordNonExistentUser() {
        boolean changed = changePassword(999, "1234", "newpassword123");
        assertFalse(changed);
    }
    
    // ========== PROFILE VALIDATION TESTS ==========
    
    @Test
    @Order(18)
    @DisplayName("Test Profile Validation - Valid Profile")
    void testProfileValidationValid() {
        User validUser = new User();
        validUser.setEmail("valid@example.com");
        validUser.setFullName("Valid User");
        validUser.setPhone("1234567890");
        validUser.setAddress("Valid Address");
        
        boolean isValid = isValidProfileData(validUser);
        assertTrue(isValid);
    }
    
    @Test
    @Order(19)
    @DisplayName("Test Profile Validation - Invalid Email")
    void testProfileValidationInvalidEmail() {
        User invalidUser = new User();
        invalidUser.setEmail("invalid-email");
        invalidUser.setFullName("Valid User");
        invalidUser.setPhone("1234567890");
        invalidUser.setAddress("Valid Address");
        
        boolean isValid = isValidProfileData(invalidUser);
        assertFalse(isValid);
    }
    
    @Test
    @Order(20)
    @DisplayName("Test Profile Validation - Empty Fields")
    void testProfileValidationEmptyFields() {
        User invalidUser = new User();
        invalidUser.setEmail("");
        invalidUser.setFullName("");
        invalidUser.setPhone("");
        invalidUser.setAddress("");
        
        boolean isValid = isValidProfileData(invalidUser);
        assertFalse(isValid);
    }
    
    @Test
    @Order(21)
    @DisplayName("Test Profile Validation - Null Fields")
    void testProfileValidationNullFields() {
        User invalidUser = new User();
        invalidUser.setEmail(null);
        invalidUser.setFullName(null);
        invalidUser.setPhone(null);
        invalidUser.setAddress(null);
        
        boolean isValid = isValidProfileData(invalidUser);
        assertFalse(isValid);
    }
    
    // ========== USER ROLE SPECIFIC TESTS ==========
    
    @Test
    @Order(22)
    @DisplayName("Test Admin Profile Management")
    void testAdminProfileManagement() {
        setCurrentUser("admin");
        User adminProfile = getCurrentUserProfile();
        
        assertNotNull(adminProfile);
        assertEquals("admin", adminProfile.getRole());
        
        // Test admin can edit their own profile
        User updatedAdmin = new User();
        updatedAdmin.setId(1);
        updatedAdmin.setUsername("admin");
        updatedAdmin.setPassword("1234");
        updatedAdmin.setRole("admin");
        updatedAdmin.setEmail("admin_updated@example.com");
        updatedAdmin.setFullName("Updated Admin");
        updatedAdmin.setPhone("4444444444");
        updatedAdmin.setAddress("Admin Updated Address");
        
        boolean updated = updateUserProfile(updatedAdmin);
        assertTrue(updated);
    }
    
    @Test
    @Order(23)
    @DisplayName("Test CoAdmin Profile Management")
    void testCoAdminProfileManagement() {
        setCurrentUser("coadmin");
        User coadminProfile = getCurrentUserProfile();
        
        assertNotNull(coadminProfile);
        assertEquals("coadmin", coadminProfile.getRole());
        
        // Test coadmin can edit their own profile
        User updatedCoAdmin = new User();
        updatedCoAdmin.setId(2);
        updatedCoAdmin.setUsername("coadmin");
        updatedCoAdmin.setPassword("1234");
        updatedCoAdmin.setRole("coadmin");
        updatedCoAdmin.setEmail("coadmin_updated@example.com");
        updatedCoAdmin.setFullName("Updated CoAdmin");
        updatedCoAdmin.setPhone("5555555555");
        updatedCoAdmin.setAddress("CoAdmin Updated Address");
        
        boolean updated = updateUserProfile(updatedCoAdmin);
        assertTrue(updated);
    }
    
    @Test
    @Order(24)
    @DisplayName("Test Staff Profile Management")
    void testStaffProfileManagement() {
        setCurrentUser("staff");
        User staffProfile = getCurrentUserProfile();
        
        assertNotNull(staffProfile);
        assertEquals("staff", staffProfile.getRole());
        
        // Test staff can edit their own profile
        User updatedStaff = new User();
        updatedStaff.setId(3);
        updatedStaff.setUsername("staff");
        updatedStaff.setPassword("1234");
        updatedStaff.setRole("staff");
        updatedStaff.setEmail("staff_updated@example.com");
        updatedStaff.setFullName("Updated Staff");
        updatedStaff.setPhone("6666666666");
        updatedStaff.setAddress("Staff Updated Address");
        
        boolean updated = updateUserProfile(updatedStaff);
        assertTrue(updated);
    }
    
    // ========== PROFILE ANALYTICS TESTS ==========
    
    @Test
    @Order(25)
    @DisplayName("Test Profile Update Statistics")
    void testProfileUpdateStatistics() {
        // Count total users
        long totalUsers = users.size();
        assertEquals(3, totalUsers);
        
        // Count users by role
        long adminCount = users.stream()
                .filter(user -> "admin".equals(user.getRole()))
                .count();
        assertEquals(1, adminCount);
        
        long coadminCount = users.stream()
                .filter(user -> "coadmin".equals(user.getRole()))
                .count();
        assertEquals(1, coadminCount);
        
        long staffCount = users.stream()
                .filter(user -> "staff".equals(user.getRole()))
                .count();
        assertEquals(1, staffCount);
        
        // Verify all users have complete profile data
        long completeProfiles = users.stream()
                .filter(user -> isValidProfileData(user))
                .count();
        assertEquals(3, completeProfiles);
    }
}

// Mock User class for testing
class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private String email;
    private String fullName;
    private String phone;
    private String address;
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
} 