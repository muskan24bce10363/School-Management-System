

public class Teacher extends Person {
    private String subject;
    private double salary;
    private double salaryEarned;
    
    public Teacher(int id, String name, String email, String phone, String subject, double salary) {
        super(id, name, email, phone);
        validateSalary(salary);
        this.subject = subject;
        this.salary = salary;
        this.salaryEarned = 0;
    }
    
    private void validateSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
    }
    
    public void receiveSalary(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Salary amount must be positive");
        }
        this.salaryEarned += amount;
    }
    
    // Getters and setters
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { 
        validateSalary(salary);
        this.salary = salary; 
    }
    public double getSalaryEarned() { return salaryEarned; }
    
    @Override
    public String toString() {
        return String.format("Teacher[ID=%d, Name=%s, Subject=%s, Salary=%.2f, TotalEarned=%.2f]", 
            id, name, subject, salary, salaryEarned);
    }
}