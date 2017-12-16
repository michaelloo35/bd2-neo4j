package pl.edu.agh.bd;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Solution solution = new Solution();
//        solution.clearNodes();
        solution.databaseStatistics();
//        solution.fillDatabase();
        solution.viewSchema();
    }

}
