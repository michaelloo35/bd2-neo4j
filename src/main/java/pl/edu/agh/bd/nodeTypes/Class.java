package pl.edu.agh.bd.nodeTypes;

import java.util.List;

public class Class {
    private final Course course;
    private final List<Student> students;
    private final Teacher teacher;

    public Class(Course course, List<Student> students, Teacher teacher) {
        this.course = course;
        this.students = students;
        this.teacher = teacher;
    }
}
