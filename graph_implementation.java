public class graph_implementation {
    class Edge {
        int source, destination;
    }
    //inner class used to catalogue edges, their source nodes and destinations

    int vertices, edges;
    //number of vertices and edges

    Edge[] edge;
    //array to store all edges

    graph_implementation(int vertices, int edges){
        this.vertices=vertices;
        this.edges=edges;
        edge = new Edge[edges];
        for(int i=0;i<edges;i++){
            
        }
    }
}
