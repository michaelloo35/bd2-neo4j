package pl.edu.agh.bd;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Solution solution = new Solution();
        resetDatabase(solution);
        solution.databaseStatistics();
        solution.viewNodes();
        solution.viewSchema();
    }

    private static void resetDatabase(Solution solution) {
        solution.deleteAllData();
        solution.fillDatabase();
    }

}
