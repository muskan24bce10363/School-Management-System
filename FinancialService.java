
import java.util.logging.Logger;

public class FinancialService {
    private static final Logger logger = Logger.getLogger(FinancialService.class.getName());
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private double totalMoneyEarned;
    private double totalMoneySpent;
    
    public FinancialService() {
        this.studentRepository = StudentRepository.getInstance();
        this.teacherRepository = TeacherRepository.getInstance();
        this.totalMoneyEarned = 0;
        this.totalMoneySpent = 0;
    }
    
    public synchronized void processStudentPayment(int studentId, double amount) {
        try {
            Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));
            
            if (!student.isActive()) {
                throw new IllegalStateException("Cannot process payment for inactive student");
            }
            
            student.payFees(amount);
            totalMoneyEarned += amount;
            studentRepository.update(student);
            
            logger.info(String.format("Payment processed: Student ID %d, Amount %.2f", studentId, amount));
            System.out.printf("Payment of Rs%.2f processed successfully for %s%n", amount, student.getName());
        } catch (Exception e) {
            logger.severe("Payment processing failed: " + e.getMessage());
            throw e;
        }
    }
    
    public synchronized void processTeacherSalary(int teacherId) {
        try {
            Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with ID: " + teacherId));
            
            if (getNetBalance() < teacher.getSalary()) {
                throw new IllegalStateException("Insufficient funds to pay salary. Available: " + getNetBalance());
            }
            
            teacher.receiveSalary(teacher.getSalary());
            totalMoneySpent += teacher.getSalary();
            teacherRepository.update(teacher);
            
            logger.info(String.format("Salary paid: Teacher ID %d, Amount %.2f", teacherId, teacher.getSalary()));
            System.out.printf("Salary of Rs%.2f paid successfully to %s%n", teacher.getSalary(), teacher.getName());
        } catch (Exception e) {
            logger.severe("Salary processing failed: " + e.getMessage());
            throw e;
        }
    }
    
    public double getTotalMoneyEarned() { return totalMoneyEarned; }
    public double getTotalMoneySpent() { return totalMoneySpent; }
    public double getNetBalance() { return totalMoneyEarned - totalMoneySpent; }
    
    public void generateFinancialReport() {
        System.out.println("\n=== FINANCIAL REPORT ===");
        System.out.printf("Total Money Earned: Rs%.2f%n", totalMoneyEarned);
        System.out.printf("Total Money Spent: Rs%.2f%n", totalMoneySpent);
        System.out.printf("Net Balance: Rs%.2f%n", getNetBalance());
        System.out.printf("Total Salary Expenditure: Rs%.2f%n", teacherRepository.getTotalSalaryExpenditure());
        System.out.printf("Total Fees Collected: Rs%.2f%n", studentRepository.getTotalFeesCollected());
        System.out.printf("Total Fees Expected: Rs%.2f%n", studentRepository.getTotalFeesExpected());
        
        double collectionRate = studentRepository.getTotalFeesExpected() > 0 ? 
            (studentRepository.getTotalFeesCollected() / studentRepository.getTotalFeesExpected()) * 100 : 0;
        System.out.printf("Fee Collection Rate: %.1f%%%n", collectionRate);
    }
}