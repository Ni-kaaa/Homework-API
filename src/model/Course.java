git
package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Course {
    private String id;
    private String title;
    private String[] instructorNames;
    private String[] requirements;
    private Date startDate;

    public Course(String id, String title, String[] instructorNames, String[] requirements, Date startDate) {
        this.id = id;
        this.title = title;
        this.instructorNames = Objects.requireNonNullElse(instructorNames, new String[0]);
        this.requirements = Objects.requireNonNullElse(requirements, new String[0]);
        this.startDate = startDate;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String[] getInstructorNames() {
        return instructorNames;
    }

    public String[] getRequirements() {
        return requirements;
    }

    public Date getStartDate() {
        return startDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        String instructors = String.join(",", instructorNames);
        String requirementsStr = String.join(",", requirements);
        String startDateStr = (startDate == null) ? "" : dateFormat.format(startDate);

        return String.format("| %-4s | %-20s | %-27s | %-33s | %-27s |",
            id, title, instructors, requirementsStr, startDateStr);
    }
}





