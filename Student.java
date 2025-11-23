

public class Student extends Person {
    private int grade;
    private double feesPaid;
    private double feesTotal;
    private boolean isActive;
    
    public Student(int id, String name, String email, String phone, int grade, double feesTotal) {
        super(id, name, email, phone);
        validateGrade(grade);
        validateFees(feesTotal);
        this.grade = grade;
        this.feesTotal = feesTotal;
        this.feesPaid = 0;
        this.isActive = true;
    }
    
    private void validateGrade(int grade) {
        if (grade < 1 || grade > 12) {
            throw new IllegalArgumentException("Grade must be between 1 and 12");
        }
    }
    
    private void validateFees(double fees) {
        if (fees < 0) {
            throw new IllegalArgumentException("Fees cannot be negative");
        }
    }
    
    public void payFees(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }
        if (feesPaid + amount > feesTotal) {
            throw new IllegalArgumentException("Payment exceeds total fees");
        }
        if (!isActive) {
            throw new IllegalStateException("Cannot accept payment for inactive student");
        }
        this.feesPaid += amount;
    }
    
    public double getRemainingFees() {
        return feesTotal - feesPaid;
    }
    
    // Getters and setters
    public int getGrade() { return grade; }
    public void setGrade(int grade) { 
        validateGrade(grade);
        this.grade = grade; 
    }
    public double getFeesPaid() { return feesPaid; }
    public double getFeesTotal() { return feesTotal; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    
    @Override
    public String toString() {
        return String.format("Student[ID=%d, Name=%s, Grade=%d, FeesPaid=%.2f, Remaining=%.2f, Active=%s]", 
            id, name, grade, feesPaid, getRemainingFees(), isActive);
    }
}