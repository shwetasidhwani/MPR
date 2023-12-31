import java.util.*;
// import java.io.*;
// import java.lang.*;

public class dijkstra_algo {
    private static final int INF = Integer.MAX_VALUE;

    public void dijkstra(int[][] graph, int source, int destination, int[] path)
    {
        // int x=0;
        int n = graph.length;
        int[] dist = new int[n]; // array to store shortest distances
        boolean[] visited = new boolean[n];// array to mark visited nodes
        int[] prev = new int[n];
        Arrays.fill(dist, INF);// initialize all distances to infinite
        dist[source] = 0;// distance from source is 0
        for (int i = 0; i < n - 1; i++) 
        {
            int u = minDistance(dist, visited);// selecting the vertex with minimum distance
            visited[u] = true;// mark the vertex as visited

            for (int v = 0; v < n; v++)
            {
            if (!visited[v] && graph[u][v] != 0 && dist[u] != INF && dist[u] + graph[u][v] < dist[v]) 
                {
                    dist[v] = dist[u] + graph[u][v];
                    prev[v] = u;
                }
            }
        }
        printPath(path);
        printDistance(dist, destination);
    }
    // printPath(path);
    
    // void path(int[] path,int[] dist,boolean[] visited){
    // int x=0;
    // int minDist = INF;
    // // int minVertex = -1;
    // for (int i = 0; i < dist.length; i++) {
    // if (!visited[i] && dist[i] < minDist) {

    // }
    // }
    // printPath(path);
    // }

    private int minDistance(int[] dist, boolean[] visited) {
        // int x=0;
        int minDist = INF;
        int minVertex =0; 
        for (int i = 0; i < dist.length; i++) {
            if (!visited[i] && dist[i] < minDist) {
                minDist = dist[i];
                minVertex = i;
                // path[x]=i;
            }
        }
        return minVertex;
    }

    private void printDistance(int[] dist, int dest) {
        System.out.println("Shortest distance from source: " + dist[dest]);
    }

    private void printPath(int[] path) {
        for (int i = 0; i < path.length; i++) {
            System.out.println(path[i]);
        }
    }

    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the final vertex :");
        int v = sc.nextInt();
        int[][] graph = {
                { 0, 2, 0, 1, 0 },
                { 2, 0, 4, 3, 0 },
                { 0, 4, 0, 0, 6 },
                { 1, 3, 0, 0, 5 },
                { 0, 0, 6, 5, 0 },
        };
        int[] path = new int[6];
        dijkstra_algo ob = new dijkstra_algo();
        ob.dijkstra(graph, 0, v, path);
        sc.close();
    }
}
