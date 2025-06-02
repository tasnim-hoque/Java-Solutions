import java.util.ArrayList;
import java.util.Scanner;

class Course {
    private String courseCode;
    private String courseName;

    public Course(String courseCode, String courseName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    @Override
    public String toString() {
        return courseCode + " - " + courseName;
    }
}

class Student {
    private String id;
    private String name;
    private ArrayList<Course> courses;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.courses = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void enroll(Course course) {
        courses.add(course);
    }

    public void displayInfo() {
        System.out.println("Student ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Enrolled Courses:");
        for (Course course : courses) {
            System.out.println(" - " + course);
        }
    }
}

public class StudentRegistrationSystem {
    private static ArrayList<Student> students = new ArrayList<>();
    private static ArrayList<Course> courses = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Sample data
        courses.add(new Course("CSE101", "Introduction to Programming"));
        courses.add(new Course("MAT201", "Discrete Mathematics"));

        while (true) {
            System.out.println("\n--- Student Registration System ---");
            System.out.println("1. Register Student");
            System.out.println("2. Enroll in Course");
            System.out.println("3. View Student Info");
            System.out.println("4. Exit");
            System.out.print("Enter option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> registerStudent();
                case 2 -> enrollCourse();
                case 3 -> viewStudentInfo();
                case 4 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void registerStudent() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        students.add(new Student(id, name));
        System.out.println("Student registered successfully.");
    }

    private static void enrollCourse() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Available Courses:");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i));
        }

        System.out.print("Enter course number to enroll: ");
        int courseIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline

        if (courseIndex >= 0 && courseIndex < courses.size()) {
            student.enroll(courses.get(courseIndex));
            System.out.println("Enrollment successful.");
        } else {
            System.out.println("Invalid course number.");
        }
    }

    private static void viewStudentInfo() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        Student student = findStudent(id);

        if (student != null) {
            student.displayInfo();
        } else {
            System.out.println("Student not found.");
        }
    }

    private static Student findStudent(String id) {
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }
}
