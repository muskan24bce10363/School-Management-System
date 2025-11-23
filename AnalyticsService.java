
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalyticsService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final FinancialService financialService;
    
    public AnalyticsService() {
        this.studentRepository = StudentRepository.getInstance();
        this.teacherRepository = TeacherRepository.getInstance();
        this.financialService = new FinancialService();
    }
    
    public void generateComprehensiveReport() {
        System.out.println("\n=== SCHOOL ANALYTICS REPORT ===");
        generateStudentAnalytics();
        generateTeacherAnalytics();
        generateFinancialAnalytics();
    }
    
    private void generateStudentAnalytics() {
        List<Student> students = studentRepository.findAll();
        
        System.out.println("\n--- STUDENT ANALYTICS ---");
        System.out.println("Total Students: " + students.size());
        
        Map<Integer, Long> studentsByGrade = students.stream()
            .collect(Collectors.groupingBy(Student::getGrade, Collectors.counting()));
        
        System.out.println("Students by Grade:");
        studentsByGrade.forEach((grade, count) -> 
            System.out.printf("  Grade %d: %d students%n", grade, count));
        
        long activeStudents = students.stream().filter(Student::isActive).count();
        System.out.printf("Active Students: %d (%.1f%%)%n", 
            activeStudents, (activeStudents * 100.0 / students.size()));
        
        double avgFeesPaid = students.stream()
            .mapToDouble(Student::getFeesPaid)
            .average()
            .orElse(0.0);
        System.out.printf("Average Fees Paid per Student: Rs%.2f%n", avgFeesPaid);
    }
    
    private void generateTeacherAnalytics() {
        List<Teacher> teachers = teacherRepository.findAll();
        
        System.out.println("\n--- TEACHER ANALYTICS ---");
        System.out.println("Total Teachers: " + teachers.size());
        
        Map<String, Long> teachersBySubject = teachers.stream()
            .collect(Collectors.groupingBy(Teacher::getSubject, Collectors.counting()));
        
        System.out.println("Teachers by Subject:");
        teachersBySubject.forEach((subject, count) -> 
            System.out.printf("  %s: %d teachers%n", subject, count));
        
        double avgSalary = teachers.stream()
            .mapToDouble(Teacher::getSalary)
            .average()
            .orElse(0.0);
        System.out.printf("Average Salary: Rs%.2f%n", avgSalary);
        
        double totalSalaryEarned = teachers.stream()
            .mapToDouble(Teacher::getSalaryEarned)
            .sum();
        System.out.printf("Total Salary Paid: Rs%.2f%n", totalSalaryEarned);
    }
    
    private void generateFinancialAnalytics() {
        System.out.println("\n--- FINANCIAL ANALYTICS ---");
        System.out.printf("Total Revenue: Rs%.2f%n", financialService.getTotalMoneyEarned());
        System.out.printf("Total Expenditure: Rs%.2f%n", financialService.getTotalMoneySpent());
        System.out.printf("Net Profit/Loss: Rs%.2f%n", financialService.getNetBalance());
        
        double revenuePerStudent = studentRepository.findAll().isEmpty() ? 0 :
            financialService.getTotalMoneyEarned() / studentRepository.findAll().size();
        System.out.printf("Average Revenue per Student: Rs%.2f%n", revenuePerStudent);
        
        double expensePerTeacher = teacherRepository.findAll().isEmpty() ? 0 :
            financialService.getTotalMoneySpent() / teacherRepository.findAll().size();
        System.out.printf("Average Expense per Teacher: Rs%.2f%n", expensePerTeacher);
        
        // Financial health indicator
        double financialHealth = financialService.getNetBalance();
        String healthStatus = financialHealth >= 0 ? "HEALTHY" : "AT RISK";
        System.out.printf("Financial Health: %s (Balance: Rs%.2f)%n", healthStatus, financialHealth);
    }
}