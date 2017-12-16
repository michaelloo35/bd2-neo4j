package pl.edu.agh.bd;

import org.neo4j.graphdb.*;

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

    public void viewNodes() {
        System.out.println(graphDatabase.runCypher("MATCH (n) RETURN (n)"));
    }

    public void viewSchema() {
        System.out.println(graphDatabase.runCypher("MATCH (n)\n" +
                "OPTIONAL MATCH (n)-[r]->(x)\n" +
                "WITH DISTINCT {l1: labels(n), r: type(r), l2: labels(x)}\n" +
                "AS `first degree connection`\n" +
                "RETURN `first degree connection`;"));
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

            // *******NODES********
            // STUDENTS
            Node mateusz = createStudent(gdb, "Mateusz", "Kowalski", 21);
            Node marta = createStudent(gdb, "Marta", "Kowalski", 20);
            Node marcin = createStudent(gdb, "Marcin", "Kmicic", 20);
            Node anna = createStudent(gdb, "Anna", "Barosz", 23);
            Node monika = createStudent(gdb, "Monika", "Warciniec", 21);

            // COURSES
            Node math = createCourse(gdb, "Math", 2);
            Node programming = createCourse(gdb, "Programming", 1);
            Node englishLiterature = createCourse(gdb, "EnglishLiterature", 4);
            Node physics = createCourse(gdb, "Physics", 2);
            Node biology = createCourse(gdb, "Biology", 1);


            // TEACHERS
            Node andrzej = createTeacher(gdb, "Andrzej", "Włoch", 35, "Math");
            Node marek = createTeacher(gdb, "Marek", "Janus", 45, "Programming");
            Node janusz = createTeacher(gdb, "Janusz", "Nowak", 32, "Math");
            Node konstanty = createTeacher(gdb, "Konstanty", "Wiśniewski", 56, "Physics");
            Node kamil = createTeacher(gdb, "Kamil", "Grabowski", 32, "Biology");


            // GROUPS
            Node a5 = createGroup(gdb, "A5");
            Node b3 = createGroup(gdb, "B3");
            Node a1 = createGroup(gdb, "A1");
            Node a2 = createGroup(gdb, "A2");
            Node c5 = createGroup(gdb, "C5");
            Node c6 = createGroup(gdb, "C6");
            Node c7 = createGroup(gdb, "C7");
            Node c8 = createGroup(gdb, "C8");
            Node c9 = createGroup(gdb, "C9");
            Node c10 = createGroup(gdb, "C10");


            // *******RELATIONS********

            createLikesRelationShip(marta, mateusz);
            createLikesRelationShip(mateusz, marta);
            createLikesRelationShip(marta, marcin);
            createLikesRelationShip(anna, monika);
            createLikesRelationShip(monika, anna);
            createLikesRelationShip(monika, kamil);
            createLikesRelationShip(anna, kamil);
            createLikesRelationShip(mateusz, kamil);
            createLikesRelationShip(janusz, konstanty);
            createLikesRelationShip(konstanty, janusz);

            createAttendsRelationship(mateusz, a5);
            createAttendsRelationship(marcin, a5);
            createAttendsRelationship(monika, a5);
            createAttendsRelationship(anna, a5);
            createAttendsRelationship(marta, a5);
            createAttendsRelationship(mateusz, a2);
            createAttendsRelationship(anna, c5);
            createAttendsRelationship(monika, c5);
            createAttendsRelationship(mateusz, b3);
            createAttendsRelationship(mateusz, a1);

            createLeadsRelation(andrzej, a5);
            createLeadsRelation(kamil, b3);
            createLeadsRelation(marek, c5);
            createLeadsRelation(marek, c6);
            createLeadsRelation(marek, c7);
            createLeadsRelation(marek, c8);
            createLeadsRelation(marek, c9);
            createLeadsRelation(marek, c10);
            createLeadsRelation(janusz, a2);
            createLeadsRelation(konstanty, a1);

            createCourseOfGroupRelationship(math, a5);
            createCourseOfGroupRelationship(programming, a1);
            createCourseOfGroupRelationship(programming, b3);
            createCourseOfGroupRelationship(programming, a2);
            createCourseOfGroupRelationship(programming, c5);
            createCourseOfGroupRelationship(programming, c6);
            createCourseOfGroupRelationship(programming, c7);
            createCourseOfGroupRelationship(programming, c8);
            createCourseOfGroupRelationship(programming, c9);
            createCourseOfGroupRelationship(programming, c10);


            tx.success();
        }
    }

    private void createLikesRelationShip(Node person1, Node person2) {
        person2.createRelationshipTo(person1, RelationshipType.withName("LIKES"));
    }

    private Relationship createAttendsRelationship(Node student, Node group) {
        return student.createRelationshipTo(group, RelationshipType.withName("ATTENDS"));
    }

    private Relationship createLeadsRelation(Node teacher, Node group) {
        return teacher.createRelationshipTo(group, RelationshipType.withName("LEADS"));
    }

    private Relationship createCourseOfGroupRelationship(Node course, Node group) {
        return group.createRelationshipTo(course, RelationshipType.withName("COURSE_OF_GROUP"));
    }

    private Node createGroup(GraphDatabaseService gdb, String groupId) {
        Node group = gdb.createNode();
        group.setProperty("id", groupId);
        group.addLabel(() -> "Group");
        return group;
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
        teacher.addLabel(() -> "Person");
        return teacher;
    }

    private Node createStudent(GraphDatabaseService gdb, String name, String surname, int age) {
        Node student = gdb.createNode();
        student.setProperty("name", name);
        student.setProperty("surname", surname);
        student.setProperty("age", age);
        student.addLabel(() -> "Student");
        student.addLabel(() -> "Person");
        return student;
    }

}
