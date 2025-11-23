
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TeacherRepository implements Repository<Teacher> {
    private final Map<Integer, Teacher> teachers;
    private static TeacherRepository instance;
    
    private TeacherRepository() {
        this.teachers = new ConcurrentHashMap<>();
    }
    
    public static synchronized TeacherRepository getInstance() {
        if (instance == null) {
            instance = new TeacherRepository();
        }
        return instance;
    }
    
    @Override
    public void add(Teacher teacher) {
        if (teachers.containsKey(teacher.getId())) {
            throw new IllegalArgumentException("Teacher with ID " + teacher.getId() + " already exists");
        }
        teachers.put(teacher.getId(), teacher);
    }
    
    @Override
    public void update(Teacher teacher) {
        if (!teachers.containsKey(teacher.getId())) {
            throw new IllegalArgumentException("Teacher with ID " + teacher.getId() + " not found");
        }
        teachers.put(teacher.getId(), teacher);
    }
    
    @Override
    public void delete(int id) {
        if (!teachers.containsKey(id)) {
            throw new IllegalArgumentException("Teacher with ID " + id + " not found");
        }
        teachers.remove(id);
    }
    
    @Override
    public Optional<Teacher> findById(int id) {
        return Optional.ofNullable(teachers.get(id));
    }
    
    @Override
    public List<Teacher> findAll() {
        return new ArrayList<>(teachers.values());
    }
    
    @Override
    public boolean exists(int id) {
        return teachers.containsKey(id);
    }
    
    public List<Teacher> findBySubject(String subject) {
        return teachers.values().stream()
            .filter(teacher -> teacher.getSubject().equalsIgnoreCase(subject))
            .collect(Collectors.toList());
    }
    
    public double getTotalSalaryExpenditure() {
        return teachers.values().stream()
            .mapToDouble(Teacher::getSalaryEarned)
            .sum();
    }
}