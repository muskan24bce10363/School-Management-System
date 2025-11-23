
public abstract class Person {
    protected int id;
    protected String name;
    protected String email;
    protected String phone;
    
    public Person(int id, String name, String email, String phone) {
        validateId(id);
        validateName(name);
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    
    private void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
    }
    
    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }
    
    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { 
        validateName(name);
        this.name = name; 
    }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    @Override
    public String toString() {
        return String.format("Person[ID=%d, Name=%s, Email=%s, Phone=%s]", 
            id, name, email, phone);
    }
}