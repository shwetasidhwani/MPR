package djikstraAlgo;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

class Edge {
    int target;
    int weight;

    public Edge(int target, int weight) {
        this.target = target;
        this.weight = weight;
    }
}

class Graph {
    private int V;
    private ArrayList<Edge>[] adjList;

    public Graph(int V) {
        this.V = V;
        adjList = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adjList[i] = new ArrayList<>();
        }
    }

    public void addEdge(int u, int v, int weight) {
        adjList[u].add(new Edge(v, weight));
    }

    public int[] criticalPath() {
        int[] indegree = new int[V];
        for (int u = 0; u < V; u++) {
            for (Edge edge : adjList[u]) {
                indegree[edge.target]++;
            }
        }

        int[] earliestStart = new int[V];
        Arrays.fill(earliestStart, 0);

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < V; i++) {
            if (indegree[i] == 0) {
                stack.push(i);
            }
        }

        while (!stack.isEmpty()) {
            int u = stack.pop();

            for (Edge edge : adjList[u]) {
                int v = edge.target;
                int weight = edge.weight;
                earliestStart[v] = Math.max(earliestStart[v], earliestStart[u] + weight);

                indegree[v]--;
                if (indegree[v] == 0) {
                    stack.push(v);
                }
            }
        }

        return earliestStart;
    }
}
 class CriticalPathMethod{
    public static void main(String args[]) {
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