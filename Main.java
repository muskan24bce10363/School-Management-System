
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final UserManagementService userService = new UserManagementService();
    private static final FinancialService financialService = new FinancialService();
    private static final AnalyticsService analyticsService = new AnalyticsService();
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        initializeSampleData();
        displayWelcomeMessage();
        
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1 -> manageStudents();
                case 2 -> manageTeachers();
                case 3 -> manageFinance();
                case 4 -> generateReports();
                case 5 -> running = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        
        System.out.println("Thank you for using School Management System!");
        scanner.close();
    }
    
    private static void initializeSampleData() {
        try {
            // Sample teachers
            Teacher teacher1 = new Teacher(1, "Lizzy", "lizzy@school.com", "1234567890", "Mathematics", 50000);
            Teacher teacher2 = new Teacher(2, "Mellisa", "mellisa@school.com", "1234567891", "Science", 70000);
            Teacher teacher3 = new Teacher(3, "Vanderhorn", "vanderhorn@school.com", "1234567892", "English", 60000);
            
            userService.addTeacher(teacher1);
            userService.addTeacher(teacher2);
            userService.addTeacher(teacher3);
            
            // Sample students
            Student student1 = new Student(1, "Tamasha", "tamasha@school.com", "1234567893", 4, 30000);
            Student student2 = new Student(2, "Rakshith", "rakshith@school.com", "1234567894", 12, 30000);
            Student student3 = new Student(3, "Rabbi", "rabbi@school.com", "1234567895", 5, 30000);
            
            userService.addStudent(student1);
            userService.addStudent(student2);
            userService.addStudent(student3);
            
            // Process some sample payments
            financialService.processStudentPayment(1, 5000);
            financialService.processStudentPayment(2, 6000);
            financialService.processStudentPayment(3, 4000);
            
            // Process some sample salaries
            financialService.processTeacherSalary(1);
            financialService.processTeacherSalary(3);
            
            logger.info("Sample data initialized successfully");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to initialize sample data", e);
        }
    }
    
    private static void displayWelcomeMessage() {
        System.out.println("====================================");
        System.out.println("   SCHOOL MANAGEMENT SYSTEM");
        System.out.println("====================================");
    }
    
    private static void displayMainMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Student Management");
        System.out.println("2. Teacher Management");
        System.out.println("3. Financial Management");
        System.out.println("4. Reports & Analytics");
        System.out.println("5. Exit");
    }
    
    private static void manageStudents() {
        boolean inStudentMenu = true;
        while (inStudentMenu) {
            System.out.println("\n--- STUDENT MANAGEMENT ---");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Find Student by ID");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. View Students by Grade");
            System.out.println("7. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewAllStudents();
                case 3 -> findStudentById();
                case 4 -> updateStudent();
                case 5 -> deleteStudent();
                case 6 -> viewStudentsByGrade();
                case 7 -> inStudentMenu = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }
    
    private static void addStudent() {
        try {
            System.out.println("\n--- ADD NEW STUDENT ---");
            int id = getIntInput("Enter student ID: ");
            scanner.nextLine(); // consume newline
            String name = getStringInput("Enter student name: ");
            String email = getStringInput("Enter email: ");
            String phone = getStringInput("Enter phone: ");
            int grade = getIntInput("Enter grade (1-12): ");
            double feesTotal = getDoubleInput("Enter total fees: ");
            
            Student student = new Student(id, name, email, phone, grade, feesTotal);
            userService.addStudent(student);
            System.out.println("Student added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }
    
    private static void viewAllStudents() {
        System.out.println("\n--- ALL STUDENTS ---");
        userService.getAllStudents().forEach(System.out::println);
    }
    
    private static void findStudentById() {
        try {
            int id = getIntInput("Enter student ID to search: ");
            Optional<Student> student = userService.getStudent(id);
            if (student.isPresent()) {
                System.out.println("Student found: " + student.get());
            } else {
                System.out.println("Student not found with ID: " + id);
            }
        } catch (Exception e) {
            System.out.println("Error finding student: " + e.getMessage());
        }
    }
    
    private static void updateStudent() {
        try {
            int id = getIntInput("Enter student ID to update: ");
            Optional<Student> studentOpt = userService.getStudent(id);
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                scanner.nextLine(); // consume newline
                
                System.out.println("Current student: " + student);
                System.out.println("Leave blank to keep current value:");
                
                String name = getStringInput("Enter new name (" + student.getName() + "): ");
                if (!name.isEmpty()) student.setName(name);
                
                String email = getStringInput("Enter new email (" + student.getEmail() + "): ");
                if (!email.isEmpty()) student.setEmail(email);
                
                String phone = getStringInput("Enter new phone (" + student.getPhone() + "): ");
                if (!phone.isEmpty()) student.setPhone(phone);
                
                String gradeStr = getStringInput("Enter new grade (" + student.getGrade() + "): ");
                if (!gradeStr.isEmpty()) student.setGrade(Integer.parseInt(gradeStr));
                
                userService.updateStudent(student);
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Student not found with ID: " + id);
            }
        } catch (Exception e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }
    
    private static void deleteStudent() {
        try {
            int id = getIntInput("Enter student ID to delete: ");
            userService.deleteStudent(id);
            System.out.println("Student deleted successfully!");
        } catch (Exception e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }
    
    private static void viewStudentsByGrade() {
        try {
            int grade = getIntInput("Enter grade to view students: ");
            userService.getStudentsByGrade(grade).forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error viewing students by grade: " + e.getMessage());
        }
    }
    
    private static void manageTeachers() {
        boolean inTeacherMenu = true;
        while (inTeacherMenu) {
            System.out.println("\n--- TEACHER MANAGEMENT ---");
            System.out.println("1. Add Teacher");
            System.out.println("2. View All Teachers");
            System.out.println("3. Find Teacher by ID");
            System.out.println("4. Update Teacher");
            System.out.println("5. Delete Teacher");
            System.out.println("6. View Teachers by Subject");
            System.out.println("7. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1 -> addTeacher();
                case 2 -> viewAllTeachers();
                case 3 -> findTeacherById();
                case 4 -> updateTeacher();
                case 5 -> deleteTeacher();
                case 6 -> viewTeachersBySubject();
                case 7 -> inTeacherMenu = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }
    
    private static void addTeacher() {
        try {
            System.out.println("\n--- ADD NEW TEACHER ---");
            int id = getIntInput("Enter teacher ID: ");
            scanner.nextLine(); // consume newline
            String name = getStringInput("Enter teacher name: ");
            String email = getStringInput("Enter email: ");
            String phone = getStringInput("Enter phone: ");
            String subject = getStringInput("Enter subject: ");
            double salary = getDoubleInput("Enter salary: ");
            
            Teacher teacher = new Teacher(id, name, email, phone, subject, salary);
            userService.addTeacher(teacher);
            System.out.println("Teacher added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding teacher: " + e.getMessage());
        }
    }
    
    private static void viewAllTeachers() {
        System.out.println("\n--- ALL TEACHERS ---");
        userService.getAllTeachers().forEach(System.out::println);
    }
    
    private static void findTeacherById() {
        try {
            int id = getIntInput("Enter teacher ID to search: ");
            Optional<Teacher> teacher = userService.getTeacher(id);
            if (teacher.isPresent()) {
                System.out.println("Teacher found: " + teacher.get());
            } else {
                System.out.println("Teacher not found with ID: " + id);
            }
        } catch (Exception e) {
            System.out.println("Error finding teacher: " + e.getMessage());
        }
    }
    
    private static void updateTeacher() {
        try {
            int id = getIntInput("Enter teacher ID to update: ");
            Optional<Teacher> teacherOpt = userService.getTeacher(id);
            if (teacherOpt.isPresent()) {
                Teacher teacher = teacherOpt.get();
                scanner.nextLine(); // consume newline
                
                System.out.println("Current teacher: " + teacher);
                System.out.println("Leave blank to keep current value:");
                
                String name = getStringInput("Enter new name (" + teacher.getName() + "): ");
                if (!name.isEmpty()) teacher.setName(name);
                
                String email = getStringInput("Enter new email (" + teacher.getEmail() + "): ");
                if (!email.isEmpty()) teacher.setEmail(email);
                
                String phone = getStringInput("Enter new phone (" + teacher.getPhone() + "): ");
                if (!phone.isEmpty()) teacher.setPhone(phone);
                
                String subject = getStringInput("Enter new subject (" + teacher.getSubject() + "): ");
                if (!subject.isEmpty()) teacher.setSubject(subject);
                
                String salaryStr = getStringInput("Enter new salary (" + teacher.getSalary() + "): ");
                if (!salaryStr.isEmpty()) teacher.setSalary(Double.parseDouble(salaryStr));
                
                userService.updateTeacher(teacher);
                System.out.println("Teacher updated successfully!");
            } else {
                System.out.println("Teacher not found with ID: " + id);
            }
        } catch (Exception e) {
            System.out.println("Error updating teacher: " + e.getMessage());
        }
    }
    
    private static void deleteTeacher() {
        try {
            int id = getIntInput("Enter teacher ID to delete: ");
            userService.deleteTeacher(id);
            System.out.println("Teacher deleted successfully!");
        } catch (Exception e) {
            System.out.println("Error deleting teacher: " + e.getMessage());
        }
    }
    
    private static void viewTeachersBySubject() {
        try {
            scanner.nextLine(); // consume newline
            String subject = getStringInput("Enter subject to view teachers: ");
            userService.getTeachersBySubject(subject).forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error viewing teachers by subject: " + e.getMessage());
        }
    }
    
    private static void manageFinance() {
        boolean inFinanceMenu = true;
        while (inFinanceMenu) {
            System.out.println("\n--- FINANCIAL MANAGEMENT ---");
            System.out.println("1. Process Student Payment");
            System.out.println("2. Process Teacher Salary");
            System.out.println("3. View Financial Report");
            System.out.println("4. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1 -> processStudentPayment();
                case 2 -> processTeacherSalary();
                case 3 -> financialService.generateFinancialReport();
                case 4 -> inFinanceMenu = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }
    
    private static void processStudentPayment() {
        try {
            int studentId = getIntInput("Enter student ID: ");
            double amount = getDoubleInput("Enter payment amount: ");
            financialService.processStudentPayment(studentId, amount);
        } catch (Exception e) {
            System.out.println("Error processing payment: " + e.getMessage());
        }
    }
    
    private static void processTeacherSalary() {
        try {
            int teacherId = getIntInput("Enter teacher ID: ");
            financialService.processTeacherSalary(teacherId);
        } catch (Exception e) {
            System.out.println("Error processing salary: " + e.getMessage());
        }
    }
    
    private static void generateReports() {
        boolean inReportMenu = true;
        while (inReportMenu) {
            System.out.println("\n--- REPORTS & ANALYTICS ---");
            System.out.println("1. Comprehensive Analytics Report");
            System.out.println("2. Financial Report");
            System.out.println("3. Student Statistics");
            System.out.println("4. Teacher Statistics");
            System.out.println("5. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1 -> analyticsService.generateComprehensiveReport();
                case 2 -> financialService.generateFinancialReport();
                case 3 -> {
                    System.out.println("\n--- STUDENT STATISTICS ---");
                    System.out.println("Total Students: " + userService.getTotalStudentCount());
                    System.out.println("Active Students: " + userService.getActiveStudents().size());
                }
                case 4 -> {
                    System.out.println("\n--- TEACHER STATISTICS ---");
                    System.out.println("Total Teachers: " + userService.getTotalTeacherCount());
                }
                case 5 -> inReportMenu = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }
    
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = scanner.nextInt();
                scanner.nextLine(); // consume newline
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // clear invalid input
            }
        }
    }
    
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double input = scanner.nextDouble();
                scanner.nextLine(); // consume newline
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // clear invalid input
            }
        }
    }
}