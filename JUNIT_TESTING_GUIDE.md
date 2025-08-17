# JUnit Testing Guide for Pahana Edu Bookshop Management System

## 📚 **What is JUnit Testing?**

JUnit is a testing framework for Java that helps you verify your code works correctly by writing automated tests. Think of it as having a robot that checks if your functions do what they're supposed to do.

## 🎯 **Why Testing is Important?**

1. **Find Bugs Early**: Catch problems before they reach users
2. **Ensure Code Quality**: Make sure your code works as expected
3. **Documentation**: Tests show how your code should be used
4. **Confidence**: Know your changes don't break existing functionality
5. **Academic Requirement**: Your lecturer wants you to learn testing

## 📁 **Test Files Created (Organized by Package)**

I've created the following test files according to your requirements, organized in packages for easy management:

### **🔐 Authentication Package**
- **Location**: `src/test/java/com/example/dinithi_pahana_edu/authentication/`
- **File**: `LoginUsersTest.java`
- **Tests**: User authentication, login validation, user management
- **Coverage**: User object creation, getters/setters, authentication, role validation, password validation, username validation, session management

### **👥 Customer Package**
- **Location**: `src/test/java/com/example/dinithi_pahana_edu/customer/`
- **Files**: 
  - `CustomerManagementTest.java` - Add customers, view customers, delete customers
  - `CustomerEditTest.java` - Search customers, update customers
- **Coverage**: Customer object creation, data validation, duplicate detection, CRUD operations, search functionality, update validation

### **📦 Items Package**
- **Location**: `src/test/java/com/example/dinithi_pahana_edu/items/`
- **File**: `ItemManagementTest.java`
- **Coverage**: Add new items, view items, search items, delete items, edit items (admin/coadmin functions), view items and search items (staff functions)

### **🧮 Calculate Bills Package**
- **Location**: `src/test/java/com/example/dinithi_pahana_edu/calculateBills/`
- **Files**:
  - `BillCalculationTest.java` - Complete bill process: search customer, add items, calculate prices, add/delete rows, calculate totals, balance, save and print bill
  - `BillManagementTest.java` - Search bills, view all bills, update bills, delete bills, print bills
- **Coverage**: Bill calculation workflow, bill management operations, bill validation, bill statistics

### **👤 View Accounts Package**
- **Location**: `src/test/java/com/example/dinithi_pahana_edu/viewAccounts/`
- **Files**:
  - `CustomerAccountViewTest.java` - Search customer, view customer details, view bills history, view bill items, display summary statistics
  - `AccountReportingTest.java` - Bill status analysis, payment trends, account analytics, overpayment analysis
- **Coverage**: Customer account viewing, bill history analysis, payment tracking, account reporting, financial summaries

## 🚀 **How to Run Tests**

### **Method 1: Using Maven Command Line**

Open your terminal/command prompt in your project directory and run:

```bash
# Run all tests
mvn test

# Run tests from specific package
mvn test -Dtest="com.example.dinithi_pahana_edu.authentication.*"
mvn test -Dtest="com.example.dinithi_pahana_edu.customer.*"
mvn test -Dtest="com.example.dinithi_pahana_edu.items.*"
mvn test -Dtest="com.example.dinithi_pahana_edu.calculateBills.*"
mvn test -Dtest="com.example.dinithi_pahana_edu.viewAccounts.*"

# Run a specific test class
mvn test -Dtest=LoginUsersTest
mvn test -Dtest=CustomerManagementTest

# Run a specific test method
mvn test -Dtest=LoginUsersTest#testUserCreation
```

### **Method 2: Using IDE (Recommended)**

#### **In IntelliJ IDEA:**
1. Right-click on the `src/test/java` folder
2. Select "Run 'All Tests'"
3. Or right-click on any package and select "Run Tests in 'package_name'"
4. Or right-click on any test class and select "Run"

#### **In Eclipse:**
1. Right-click on the `src/test/java` folder
2. Select "Run As" → "JUnit Test"
3. Or right-click on any package and select "Run As" → "JUnit Test"

#### **In VS Code:**
1. Install the "Extension Pack for Java"
2. Open any test file
3. Click the "Run Test" button above each test method

## 📝 **Understanding Test Structure**

### **Basic Test Method:**
```java
@Test
void testUserCreation() {
    // Arrange - Set up test data
    User user = new User();
    
    // Act - Perform the action you want to test
    user.setUsername("testuser");
    
    // Assert - Check if the result is what you expect
    assertEquals("testuser", user.getUsername(), "Username should match");
}
```

### **Test Annotations:**
- `@Test` - Marks a method as a test
- `@BeforeEach` - Runs before each test method
- `@DisplayName` - Gives a friendly name to the test
- `@Order` - Specifies the order of test execution

### **Common Assertions:**
```java
assertEquals(expected, actual, "message");     // Check if values are equal
assertTrue(condition, "message");               // Check if condition is true
assertFalse(condition, "message");              // Check if condition is false
assertNull(object, "message");                  // Check if object is null
assertNotNull(object, "message");               // Check if object is not null
assertThrows(Exception.class, () -> { code }); // Check if exception is thrown
```

## 🔍 **What Each Test File Tests**

### **🔐 LoginUsersTest.java (Authentication Package)**
- ✅ User object creation and validation
- ✅ User authentication (valid/invalid credentials)
- ✅ Role validation (admin, coadmin, staff)
- ✅ Password validation (length, format)
- ✅ Username validation (length, format)
- ✅ Session management (login/logout)
- ✅ User object equality and comparison

### **👥 CustomerManagementTest.java (Customer Package)**
- ✅ Customer object creation and validation
- ✅ Add customer with valid data
- ✅ Add customer with invalid data (validation)
- ✅ Duplicate account number detection
- ✅ View all customers
- ✅ View customer by ID
- ✅ View customer by account number
- ✅ Delete customer by ID
- ✅ Delete customer by account number
- ✅ Data validation (account number, name, telephone, email)

### **👥 CustomerEditTest.java (Customer Package)**
- ✅ Search customer by account number
- ✅ Search customer by name (exact match)
- ✅ Search customer by telephone
- ✅ Search customer by email
- ✅ Partial name search (case insensitive)
- ✅ Update customer with valid data
- ✅ Update customer with invalid data
- ✅ Duplicate account number prevention during update
- ✅ Update all customer fields
- ✅ Handle null and empty values

### **📦 ItemManagementTest.java (Items Package)**
- ✅ Item object creation and validation
- ✅ Add new items with valid data
- ✅ Add items with invalid data (validation)
- ✅ View all items
- ✅ View item by ID
- ✅ Search items by name
- ✅ Search items by category
- ✅ Delete items by ID
- ✅ Edit items with valid data
- ✅ Edit items with invalid data
- ✅ Stock management validation
- ✅ Price validation
- ✅ Category validation
- ✅ Data integrity checks
- ✅ Staff access limitations (view/search only)

### **🧮 BillCalculationTest.java (Calculate Bills Package)**
- ✅ Search customer by account number, name, telephone, email
- ✅ Add items to bill (single and multiple items)
- ✅ Calculate item prices and bill totals
- ✅ Calculate discounts and final totals
- ✅ Calculate balance (total - paid amount)
- ✅ Add and delete rows from bill
- ✅ Save bill with validation
- ✅ Print bill with itemized details
- ✅ Bill validation (customer, bill number, amounts)
- ✅ Complete bill workflow testing

### **🧮 BillManagementTest.java (Calculate Bills Package)**
- ✅ Search bills by bill number, customer ID, date range, amount range
- ✅ View all bills and bill details
- ✅ Update bills with valid and invalid data
- ✅ Delete bills by ID
- ✅ Print individual bills and all bills
- ✅ Calculate bill statistics (revenue, paid amounts, balances)
- ✅ Bill validation and data integrity
- ✅ Payment status tracking

### **👤 CustomerAccountViewTest.java (View Accounts Package)**
- ✅ Search customer by account number, name, telephone, email
- ✅ View customer details (ID, account number, name, address, phone, email)
- ✅ View all bills for a customer
- ✅ View bill items for specific bills
- ✅ Calculate total bills, total amount, total paid, total balance
- ✅ Get bill status (Paid, Pending, Unpaid)
- ✅ Generate customer summary reports
- ✅ Validate customer account data
- ✅ Handle customers with no bills

### **👤 AccountReportingTest.java (View Accounts Package)**
- ✅ Analyze bill status distribution (paid, pending, unpaid)
- ✅ Calculate payment trends and rates
- ✅ Generate account summary reports
- ✅ Calculate overpayment analysis
- ✅ Validate account data integrity
- ✅ Payment behavior analysis

## 📊 **Package Organization Benefits**

### **Easy to Find:**
- **Authentication tests** → `authentication/` package
- **Customer tests** → `customer/` package
- **Future tests** → Can be organized in similar packages (e.g., `item/`, `bill/`, etc.)

### **Easy to Run:**
```bash
# Run only authentication tests
mvn test -Dtest="com.example.dinithi_pahana_edu.authentication.*"

# Run only customer tests
mvn test -Dtest="com.example.dinithi_pahana_edu.customer.*"

# Run only items tests
mvn test -Dtest="com.example.dinithi_pahana_edu.items.*"

# Run only bill calculation tests
mvn test -Dtest="com.example.dinithi_pahana_edu.calculateBills.*"

# Run only account viewing tests
mvn test -Dtest="com.example.dinithi_pahana_edu.viewAccounts.*"
```

### **Easy to Maintain:**
- Related tests are grouped together
- Clear separation of concerns
- Easy to add new test categories
- **Authentication tests** → `authentication/` package
- **Customer tests** → `customer/` package
- **Item tests** → `items/` package
- **Bill calculation tests** → `calculateBills/` package
- **Account viewing tests** → `viewAccounts/` package

## ⚠️ **Important Notes**

### **Database Tests:**
- Tests that use `UserService`, `CustomerService`, etc. require a database connection
- Make sure your MySQL database is running
- These tests might fail if the database is not available

### **Test Results:**
- **Green** ✅ = Test passed
- **Red** ❌ = Test failed
- **Yellow** ⚠️ = Test skipped

### **Common Issues:**
1. **Database Connection**: Make sure MySQL is running
2. **Dependencies**: Make sure all Maven dependencies are downloaded
3. **Java Version**: Make sure you're using Java 17 or higher

## 📊 **Test Coverage**

The tests cover:
- **Model Classes**: 100% of getter/setter methods
- **Service Classes**: Basic CRUD operations
- **Edge Cases**: Null values, empty strings, invalid inputs
- **Business Logic**: Data validation and format checking
- **Authentication**: User login, role validation, session management
- **Customer Management**: CRUD operations, search, validation
- **Item Management**: Inventory operations, stock tracking
- **Bill Processing**: Calculation, management, validation
- **Account Viewing**: Customer account analysis, reporting

## 🎓 **For Your Lecturer**

This testing setup demonstrates:
1. **Unit Testing**: Testing individual components
2. **Test Organization**: Proper package structure with logical grouping
3. **Test Naming**: Clear, descriptive test names
4. **Assertions**: Various types of assertions
5. **Test Documentation**: Comments explaining test purpose
6. **Edge Case Testing**: Handling null, empty, and invalid inputs
7. **Database Integration**: Testing with real database operations
8. **Package Organization**: Logical grouping of related tests

## 🚀 **Next Steps**

1. **Run the tests** using one of the methods above
2. **Read the test code** to understand what each test does
3. **Modify tests** to test your specific requirements
4. **Add more tests** for other classes in your project
5. **Practice** by writing tests for new features

## 💡 **Tips for Writing Good Tests**

1. **Test One Thing**: Each test should test one specific behavior
2. **Use Descriptive Names**: Test method names should explain what they test
3. **Arrange-Act-Assert**: Structure your tests in this order
4. **Test Edge Cases**: Don't just test the happy path
5. **Keep Tests Simple**: Tests should be easy to understand
6. **Test Both Success and Failure**: Test what happens when things go wrong

## 🆘 **Need Help?**

If you encounter issues:
1. Check that your database is running
2. Make sure all Maven dependencies are downloaded
3. Verify your Java version is compatible
4. Check the test output for specific error messages

---

**Good luck with your testing! 🎉** 