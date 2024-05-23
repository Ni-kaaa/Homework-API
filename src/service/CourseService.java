
package service;

import model.Course;
import repository.CourseRepository;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CourseService {
    private final CourseRepository courseRepository = new CourseRepository();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final String filePath = "courses.txt";

    public CourseService() {
        readCoursesFromFile(filePath);
    }

    public void addCourse(Scanner scanner) {
        System.out.print("Enter course ID: ");
        String id = scanner.nextLine();

        if (courseRepository.findCourseById(id) != null) {
            System.out.println("A course with the same ID already exists. Please enter a unique ID.");
            return;
        }

        System.out.print("Enter course title: ");
        String title = scanner.nextLine();
        System.out.print("Enter instructor names (comma separated): ");
        String[] instructorNames = parseInput(scanner.nextLine());
        System.out.print("Enter course requirements (comma separated): ");
        String[] requirements = parseInput(scanner.nextLine());

        Date startDate = new Date();

        Course newCourse = new Course(id, title, instructorNames, requirements, startDate);
        courseRepository.addCourse(newCourse);
        System.out.println("Course added successfully.");

        writeCoursesToFile(filePath);
    }

    private String[] parseInput(String input) {
        return input.isBlank() ? new String[0] : input.split(",");
    }

    public List<Course> listCourses() {
        return courseRepository.listCourses();
    }

    public List<Course> findCourseById(Scanner scanner) {
        System.out.print("Enter course ID to find: ");
        String id = scanner.nextLine();
        Course course = courseRepository.findCourseById(id);
        return (course != null) ? Collections.singletonList(course) : Collections.emptyList();
    }

    public List<Course> findCourseByTitle(Scanner scanner) {
        System.out.print("Enter course title to find: ");
        String title = scanner.nextLine();
        return courseRepository.findCourseByTitle(title);
    }

    public boolean removeCourseById(Scanner scanner) {
        System.out.print("Enter course ID to remove: ");
        String id = scanner.nextLine();
        boolean removed = courseRepository.removeCourseById(id);
        if (removed) {
            writeCoursesToFile(filePath);
        }
        return removed;
    }

    private void readCoursesFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Course course = parseCourse(line);
                if (course != null) {
                    courseRepository.addCourse(course);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading data from file: " + e.getMessage());
        }
    }

    private Course parseCourse(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length != 6) return null;
            String id = parts[1].trim();
            String title = parts[2].trim();
            String[] instructorNames = parts[3].trim().split(",");
            String[] requirements = parts[4].trim().split(",");
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            Date startDate = dateFormat.parse(parts[5].trim());
            return new Course(id, title, instructorNames, requirements, startDate);
        } catch (ParseException e) {
            System.out.println("An error occurred while parsing course data: " + e.getMessage());
            return null;
        }
    }

    public void writeCoursesToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            List<Course> courses = courseRepository.listCourses();
            for (Course course : courses) {
                writer.write(course.toString());
                writer.newLine();
            }
            System.out.println("Data written to file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing data to file: " + e.getMessage());
        }
    }
}




