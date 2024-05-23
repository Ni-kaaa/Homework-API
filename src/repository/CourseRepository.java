
package repository;

import model.Course;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {
    private final List<Course> courses = new ArrayList<>();

    public void addCourse(Course course) {
        courses.add(course);
    }

    public List<Course> listCourses() {
        return courses;
    }

    public Course findCourseById(String id) {
        return courses.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Course> findCourseByTitle(String title) {
        List<Course> foundCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.getTitle().equalsIgnoreCase(title)) {
                foundCourses.add(course);
            }
        }
        return foundCourses;
    }

    public boolean removeCourseById(String id) {
        Course courseToRemove = findCourseById(id);
        if (courseToRemove != null) {
            return courses.remove(courseToRemove);
        }
        return false;
    }
}


