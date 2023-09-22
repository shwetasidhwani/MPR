import java.util.Arrays;

public class Main {

  private static final int INF = Integer.MAX_VALUE;

  public void dijkstra(int[][] graph, int source) {
    int n = graph.length;
    int[] dist = new int[n]; // array to store shortest distances
    boolean[] visited = new boolean[n]; // array to mark visited nodes
    Arrays.fill(dist, INF); // initialize distances as infinity
    dist[source] = 0; // distance from source to source is 0

    for (int i = 0; i < n - 1; i++) {
      // select the vertex with the minimum distance
      int u = minDistance(dist, visited);
      visited[u] = true; // mark the vertex as visited

      // update distances of adjacent vertices
      for (int v = 0; v < n; v++) {
        if (
          !visited[v] &&
          graph[u][v] != 0 &&
          dist[u] != INF &&
          dist[u] + graph[u][v] < dist[v]
        ) {
          dist[v] = dist[u] + graph[u][v];
        }
      }
    }
    printDistances(dist); // print the shortest distances
  }

  // helper function to find the vertex with the minimum distance
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

  // helper function to print the shortest distances
  private void printDistances(int[] dist) {
    System.out.println("Shortest distances from the source:");
    for (int i = 0; i < dist.length; i++) {
      System.out.println("Vertex " + i + ": " + dist[i]);
    }
  }

  public static void main(String[] args) {
    int[][] graph = {
      { 0, 2, 0, 1, 0 },
      { 2, 0, 4, 3, 0 },
      { 0, 4, 0, 0, 6 },
      { 1, 3, 0, 0, 5 },
      { 0, 0, 6, 5, 0 },
    };
    Main dijkstra = new Main();
    dijkstra.dijkstra(graph, 0);
  }
}
