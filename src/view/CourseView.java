
package view;

import model.Course;
import service.CourseService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class CourseView {
    private final CourseService courseService = new CourseService();
    private final Scanner scanner = new Scanner(System.in);

    public void displayMenu() {
        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Add new Course");
            System.out.println("2. List Courses");
            System.out.println("3. Find Course BY ID");
            System.out.println("4. Find Course BY Title");
            System.out.println("5. Remove Course By ID");
            System.out.println("0/99. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        courseService.addCourse(scanner);
                        break;
                    case 2:
                        listCourses(courseService.listCourses());
                        break;
                    case 3:
                        listCourses(courseService.findCourseById(scanner));
                        break;
                    case 4:
                        listCourses(courseService.findCourseByTitle(scanner));
                        break;
                    case 5:
                        boolean removed = courseService.removeCourseById(scanner);
                        System.out.println(removed ? "Course removed successfully." : "No course found with the given ID.");
                        break;
                    case 0:
                    case 99:
                        System.out.println("Exiting the program.");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private void listCourses(List<Course> courses) {
        Collections.sort(courses, Comparator.comparing(Course::getId));
        System.out.println("+------+----------------------+-----------------------------+-----------------------------------+------------------------------+");
        System.out.println("| ID   | Title                | Instructors                 | Requirements                      | Start Date                   |");
        System.out.println("+------+----------------------+-----------------------------+-----------------------------------+------------------------------+");

        courses.forEach(course -> {
            System.out.println(course.toString());
        });

        System.out.println("+------+----------------------+-----------------------------+-----------------------------------+------------------------------+");
    }
}



