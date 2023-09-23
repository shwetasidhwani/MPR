import java.util.*;

public class DijkstraAlgorithm {
    private static final int INFINITY = Integer.MAX_VALUE;

    public void findShortestPath(int[][] graph, int start, int end, int[] path) {
        int numberOfNodes = graph.length;
        int[] distances = new int[numberOfNodes]; // Array to store shortest distances
        boolean[] visited = new boolean[numberOfNodes]; // Array to mark visited nodes
        int[] previous = new int[numberOfNodes]; // Array to store the previous node in the shortest path

        // Set all distances to infinity (we don't know the shortest path yet)
        for (int i = 0; i < numberOfNodes; i++) {
            distances[i] = INFINITY;
        }

        // The distance from the start node to itself is 0
        distances[start] = 0;

        for (int i = 0; i < numberOfNodes - 1; i++) {
            int currentNode = findClosestUnvisitedNode(distances, visited);

            // Mark the current node as visited
            visited[currentNode] = true;

            for (int neighborNode = 0; neighborNode < numberOfNodes; neighborNode++) {
                // If the neighbor is unvisited, there's a path, and it's a shorter path, update the distance
                if (!visited[neighborNode] && graph[currentNode][neighborNode] != 0
                        && distances[currentNode] != INFINITY
                        && distances[currentNode] + graph[currentNode][neighborNode] < distances[neighborNode]) {
                    distances[neighborNode] = distances[currentNode] + graph[currentNode][neighborNode];
                    previous[neighborNode] = currentNode;
                }
            }
        }

        // Reconstruct the shortest path
        int pathLength = 0;
        int currentNode = end;

        while (currentNode != start) {
            path[pathLength++] = currentNode;
            currentNode = previous[currentNode];
        }

        path[pathLength++] = start;

        // Reverse the path to get it in the correct order
        reverseArray(path, pathLength);

        // Print the shortest path and distance
        System.out.println("Shortest Path:");
        for (int i = 0; i < pathLength; i++) {
            System.out.print(path[i] + "   ");
        }
        System.out.println("\nShortest distance from start to end: " + distances[end]);
    }

    private void reverseArray(int[] arr, int length) {
        int start = 0;
        int end = length - 1;

        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }

    private int findClosestUnvisitedNode(int[] distances, boolean[] visited) {
        int minDistance = INFINITY;
        int minNode = -1;

        for (int i = 0; i < distances.length; i++) {
            if (!visited[i] && distances[i] < minDistance) {
                minDistance = distances[i];
                minNode = i;
            }
        }
        return minNode;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of vertices:");
        int numberOfVertices = scanner.nextInt();

        int[][] graph = new int[numberOfVertices][numberOfVertices];

        System.out.println("Enter the adjacency matrix for the graph:");
        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = 0; j < numberOfVertices; j++) {
                graph[i][j] = scanner.nextInt();
            }
        }

        System.out.println("Enter the destination vertex:");
        int destination = scanner.nextInt();

        int[] path = new int[numberOfVertices];
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm();
        dijkstra.findShortestPath(graph, 0, destination, path);
        scanner.close();
    }
}
