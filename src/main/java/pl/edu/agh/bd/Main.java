package pl.edu.agh.bd;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Solution solution = new Solution();
//        resetDatabase(solution);
//        solution.databaseStatistics();
        solution.viewNodes();
//        solution.viewSchema();
//        System.out.println(solution.getRelationshipsById("39"));
//        solution.getNodesRelationships("Course", "name", "Programming").forEach(System.out::println);

        System.out.println(solution.shortestPathBetweenNodesById("1", "2", "15"));
    }

    private static void resetDatabase(Solution solution) {
        solution.deleteAllData();
        solution.fillDatabase();
    }

}
