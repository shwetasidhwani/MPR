package djikstraAlgo;

import java.util.Arrays;

public class CriticalPath {

	public static void main(String[] args) {
		int V = 7;  // Number of vertices
        Graph graph = new Graph(V);

        // Add edges and weights
        graph.addEdge(0, 1, 3);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 3, 2);
        graph.addEdge(2, 3, 3);
        graph.addEdge(3, 4, 2);
        graph.addEdge(3, 5, 3);
        graph.addEdge(4, 6, 3);
        graph.addEdge(5, 6, 2);

        int[] criticalPathLengths = graph.criticalPath();

        System.out.println("Critical Path Lengths: " + Arrays.toString(criticalPathLengths));

	}

}
