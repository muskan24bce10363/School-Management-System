
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class UserManagementService {
    private static final Logger logger = Logger.getLogger(UserManagementService.class.getName());
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    
    public UserManagementService() {
        this.studentRepository = StudentRepository.getInstance();
        this.teacherRepository = TeacherRepository.getInstance();
    }
    
    // Student CRUD operations
    public void addStudent(Student student) {
        validateStudent(student);
        studentRepository.add(student);
        logger.info("Student added: " + student.getName());
    }
    
    public void updateStudent(Student student) {
        validateStudent(student);
        studentRepository.update(student);
        logger.info("Student updated: " + student.getName());
    }
    
    public void deleteStudent(int id) {
        studentRepository.delete(id);
        logger.info("Student deleted: ID " + id);
    }
    
    public Optional<Student> getStudent(int id) {
        return studentRepository.findById(id);
    }
    
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    public List<Student> getStudentsByGrade(int grade) {
        return studentRepository.findByGrade(grade);
    }
    
    public List<Student> getActiveStudents() {
        return studentRepository.findActiveStudents();
    }
    
    // Teacher CRUD operations
    public void addTeacher(Teacher teacher) {
        validateTeacher(teacher);
        teacherRepository.add(teacher);
        logger.info("Teacher added: " + teacher.getName());
    }
    
    public void updateTeacher(Teacher teacher) {
        validateTeacher(teacher);
        teacherRepository.update(teacher);
        logger.info("Teacher updated: " + teacher.getName());
    }
    
    public void deleteTeacher(int id) {
        teacherRepository.delete(id);
        logger.info("Teacher deleted: ID " + id);
    }
    
    public Optional<Teacher> getTeacher(int id) {
        return teacherRepository.findById(id);
    }
    
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }
    
    public List<Teacher> getTeachersBySubject(String subject) {
        return teacherRepository.findBySubject(subject);
    }
    
    private void validateStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
    }
    
    private void validateTeacher(Teacher teacher) {
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher cannot be null");
        }
    }
    
    public int getTotalStudentCount() {
        return studentRepository.findAll().size();
    }
    
    public int getTotalTeacherCount() {
        return teacherRepository.findAll().size();
    }
}