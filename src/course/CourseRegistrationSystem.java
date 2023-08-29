
package course;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    String courseCode;
    String title;
    String description;
    int capacity;
    String schedule;

    Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
    }
}

class Student {
    int studentID;
    String name;
    List<Course> registeredCourses;

    Student(int studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        registeredCourses = new ArrayList<>();
    }
}

public class CourseRegistrationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Course> courses = new ArrayList<>();
        List<Student> students = new ArrayList<>();

        while (true) {
            System.out.println("\n1. Course Listing");
            System.out.println("2. Student Registration");
            System.out.println("3. Course Removal");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayCourses(courses);
                    break;

                case 2:
                    registerStudent(scanner, courses, students);
                    break;

                case 3:
                    removeCourse(scanner, students);
                    break;

                case 4:
                    System.out.println("Exiting the program.");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }

    public static void displayCourses(List<Course> courses) {
        System.out.println("\nAvailable Courses:");
        for (Course course : courses) {
            System.out.println(course.courseCode + ": " + course.title + " (" + course.schedule + ")");
            System.out.println("Description: " + course.description);
            System.out.println("Capacity: " + course.capacity + " slots\n");
        }
    }

    public static void registerStudent(Scanner scanner, List<Course> courses, List<Student> students) {
        System.out.print("\nEnter student ID: ");
        int studentID = scanner.nextInt();
        System.out.print("Enter student name: ");
        scanner.nextLine(); // Consume newline
        String studentName = scanner.nextLine();

        Student newStudent = new Student(studentID, studentName);

        displayCourses(courses);

        while (true) {
            System.out.print("Enter course code to register (or type 'exit' to finish): ");
            String courseCode = scanner.next();
            if (courseCode.equalsIgnoreCase("exit")) {
                break;
            }

            Course selectedCourse = null;
            for (Course course : courses) {
                if (course.courseCode.equalsIgnoreCase(courseCode)) {
                    selectedCourse = course;
                    break;
                }
            }

            if (selectedCourse != null && selectedCourse.capacity > 0) {
                newStudent.registeredCourses.add(selectedCourse);
                selectedCourse.capacity--;
                System.out.println("Course registration successful.");
            } else {
                System.out.println("Course registration failed. Course not found or no available slots.");
            }
        }

        students.add(newStudent);
    }

    public static void removeCourse(Scanner scanner, List<Student> students) {
        System.out.print("\nEnter student ID for course removal: ");
        int studentID = scanner.nextInt();

        Student studentToRemove = null;
        for (Student student : students) {
            if (student.studentID == studentID) {
                studentToRemove = student;
                break;
            }
        }

        if (studentToRemove != null) {
            System.out.println("\nRegistered Courses:");
            List<Course> registeredCourses = studentToRemove.registeredCourses;
            if (registeredCourses.isEmpty()) {
                System.out.println("No registered courses for this student.");
                return;
            }
            for (Course course : registeredCourses) {
                System.out.println(course.courseCode + ": " + course.title + " (" + course.schedule + ")");
            }

            System.out.print("Enter course code to remove (or type 'exit' to finish): ");
            String courseCode = scanner.next();
            Course courseToRemove = null;

            for (Course course : registeredCourses) {
                if (course.courseCode.equalsIgnoreCase(courseCode)) {
                    courseToRemove = course;
                    break;
                }
            }

            if (courseToRemove != null) {
                registeredCourses.remove(courseToRemove);
                courseToRemove.capacity++;
                System.out.println("Course removal successful.");
            } else {
                System.out.println("Course removal failed. Course not found in student's registered courses.");
            }
        } else {
            System.out.println("Student not found.");
        }
    }
}

