import java.util.*;
public class CriticalPathAnalysis {
    public static void main(String[] args) {
        
    }
}

public class DijkstraAlgorithm {
    private static final int INF = Integer.MAX_VALUE;

    public void dijkstra(int[][] graph, int source, int destination, int[] path) {
        int n = graph.length;
        int[] dist = new int[n]; // array to store shortest distances
        boolean[] visited = new boolean[n]; // array to mark visited nodes
        int[] prev = new int[n]; // array to store the previous vertex in the shortest path

        Arrays.fill(dist, INF);
        dist[source] = 0;

        for (int i = 0; i < n - 1; i++) {
            int u = minDistance(dist, visited);
            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (!visited[v] && graph[u][v] != 0 && dist[u] != INF && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                    prev[v] = u;
                }
            }
        }

        int pathLength = 0;
        int currentVertex = destination;

        while (currentVertex != source) {
            path[pathLength++] = currentVertex;
            currentVertex = prev[currentVertex];
        }

        path[pathLength++] = source;

        // Reverse the path to get it in the correct order
        reverseArray(path, pathLength);

        System.out.println("Shortest Path:");
        for (int i = 0; i < pathLength; i++) {
            System.out.print(path[i]   + "  ");
        }
        System.out.println("\nShortest distance from source to destination: " + dist[destination]);
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

    private int minDistance(int[] dist, boolean[] visited) {
        int minDist = INF;
        int minVertex = -1;
        for (int i = 0; i < dist.length; i++) {
            if (!visited[i] && dist[i] < minDist) {
                minDist = dist[i];
                minVertex = i;
            }
        }
        return minVertex;
    }
