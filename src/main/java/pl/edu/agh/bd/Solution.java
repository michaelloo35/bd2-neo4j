package pl.edu.agh.bd;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

public class Solution {

    private final GraphDatabase graphDatabase = GraphDatabase.createDatabase();

    public void databaseStatistics() {
        System.out.println(graphDatabase.runCypher("CALL db.labels()"));
        System.out.println(graphDatabase.runCypher("CALL db.relationshipTypes()"));
    }

    public void runAllTests() {
//        System.out.println(findActorByName("Emma Watson"));
//        System.out.println(findMovieByTitleLike("Star Wars"));
//        System.out.println(findRatedMoviesForUser("maheshksp"));
//        System.out.println(findCommonMoviesForActors("Emma Watson", "Daniel Radcliffe"));
//        System.out.println(findMovieRecommendationForUser("emileifrem"));
    }

    public void viewSchema() {
        System.out.println(graphDatabase.runCypher("MATCH (n) RETURN (n)"));
    }

    public void clearNodes() {
        System.out.println(graphDatabase.runCypher("MATCH (n)\n" +
                "DETACH DELETE n"));
    }

    public void deleteAllData() {
        System.out.println(graphDatabase.runCypher("MATCH (n)\n" +
                "OPTIONAL MATCH (n)-[r]-()\n" +
                "DELETE n,r"));
    }

    public void fillDatabase() {
        GraphDatabaseService gdb = graphDatabase.getGraphDatabaseService();

        try (Transaction tx = gdb.beginTx()) {

            // STUDENTS
            Node mateusz = createStudent(gdb, "Mateusz", "Kowalski", 21);
            Node emilia = createStudent(gdb, "Emilia", "Kowalski", 20);
            Node marcin = createStudent(gdb, "Marcin", "Kmicic", 20);

            // TEACHERS
            Node andrzej = createTeacher(gdb, "Andrzej", "Włoch", 35, "Math");

            // COURSES

            Node math = createCourse(gdb, "Math", 2);

            tx.success();
        }
    }

    private Node createCourse(GraphDatabaseService gdb, String name, int lengthInSemesters) {
        Node course = gdb.createNode();
        course.setProperty("name", name);
        course.setProperty("lengthInSemesters", lengthInSemesters);
        course.addLabel(() -> "Course");
        return course;
    }

    private Node createTeacher(GraphDatabaseService gdb, String name, String surname, int age, String regionOfInterest) {
        Node teacher = gdb.createNode();
        teacher.setProperty("name", name);
        teacher.setProperty("surname", surname);
        teacher.setProperty("age", age);
        teacher.setProperty("regionOfInterest", regionOfInterest);
        teacher.addLabel(() -> "Teacher");
        return teacher;
    }

    private Node createStudent(GraphDatabaseService gdb, String name, String surname, int age) {
        Node student = gdb.createNode();
        student.setProperty("name", name);
        student.setProperty("surname", surname);
        student.setProperty("age", age);
        student.addLabel(() -> "Student");
        return student;
    }

}
