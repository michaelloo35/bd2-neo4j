package pl.edu.agh.bd.nodeTypes;

import java.util.List;

public class Group {
    private final String id;
    private final Course course;
    private final List<Student> students;
    private final Teacher teacher;

    public Group(String id, Course course, List<Student> students, Teacher teacher) {
        this.id = id;
        this.course = course;
        this.students = students;
        this.teacher = teacher;
    }
}
