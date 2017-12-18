package pl.edu.agh.bd;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;

import java.io.File;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public final class GraphDatabase {

    private final GraphDatabaseService graphDatabaseService;
    private static final String GRAPH_DIR_LOC = "./neo4j";

    public static GraphDatabase createDatabase() {
        return new GraphDatabase();
    }

    private GraphDatabase() {
        graphDatabaseService = new GraphDatabaseFactory()
                .newEmbeddedDatabaseBuilder(new File(GRAPH_DIR_LOC))
                .setConfig(GraphDatabaseSettings.allow_upgrade, "true")
                .newGraphDatabase();
        registerShutdownHook();
    }

    private void registerShutdownHook() {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDatabaseService.shutdown();
            }
        });
    }

    public String runCypher(final String cypher) {
        try (Transaction transaction = graphDatabaseService.beginTx()) {
            final Result result = graphDatabaseService.execute(cypher);
            transaction.success();
            return result.resultAsString();
        }
    }

    public List<String> findNodesRelationships(final String label, final String key, final Object value) {
        try (Transaction transaction = graphDatabaseService.beginTx()) {
            final ResourceIterator<Node> result = graphDatabaseService.findNodes(Label.label(label), key, value);
            transaction.success();
            return result
                    .stream()
                    .map(node -> node.getRelationships().spliterator())
                    .flatMap(split -> StreamSupport.stream(split, false))
                    .map(Object::toString)
                    .collect(toList());
        }
    }


    public GraphDatabaseService getGraphDatabaseService() {
        return graphDatabaseService;
    }
}
