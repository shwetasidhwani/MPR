import java.util.*;
// import java.io.*;
// import java.lang.*;

public class dijkstra_algo {
    private static final int INF = Integer.MAX_VALUE;

    public void dijkstra(int[][] graph, int source) {
        int n = graph.length;
        int[] dist = new int[n]; // array to store shortest distances
        boolean[] visited = new boolean[n];// array to mark visited nodes
        Arrays.fill(dist, INF);//// initialize all distances to infinite
        dist[source] = 0;// distance from source is 0
        for (int i = 0; i < n - 1; i++) {
            int u = minDistance(dist, visited);// selecting the vertex with minimum distance
            visited[u] = true;// mark the vertex as visited

            for (int v = 0; v < n; v++) {
                if (!visited[n] && graph[u][v] != 0 && dist[u] != INF && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                }
            }
        }
        printDistance(dist);
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

    private void printDistance(int[] dist) {
        System.out.println("Shortest distance from source: ");
        for (int i = 0; i < dist.length; i++) {
            System.out.println("Vertex " + i + ":" + dist[i]);
        }
    }

    public static void main(String[] args) {
        // Scanner sc=new Scanner(System.in);
        // System.out.println("Enter the number of vertices :");
        // int vNum=sc.nextInt();
        // int[][] graph=new int[vNum][vNum];
        // for(int i=0;i<vNum;i++){
        // for(int j=0;j<i;j++){
        // System.out.println("Enter whether or not edge exists between "+i+" and "+j+"
        // (0/1) :");
        // int choice=sc.nextInt();
        // if(choice==1){
        // System.out.println("Enter distance: ");
        // int d=sc.nextInt();
        // graph[j][i]=graph[i][j]=d;
        // }
        // }
        // }
        int[][] graph = {
                { 0, 2, 0, 1, 0 },
                { 2, 0, 4, 3, 0 },
                { 0, 4, 0, 0, 6 },
                { 1, 3, 0, 0, 5 },
                { 0, 0, 6, 5, 0 },
        };
        dijkstra_algo ob = new dijkstra_algo();
        ob.dijkstra(graph, 0);
        // sc.close();
    }
}
