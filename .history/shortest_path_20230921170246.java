// First program to find out the shortest path in Java using Dijkstra algorithm 
//1) Program to implement graphs in java
//2)  Shortest distance from initial node to all other nodes

import java.util.*;
class Vertex
{
    String label; // here, label can represent any possible entity such as person or city
    Vertex(String label){
        this.label = label;
        //'this' refers to the current object in a Constructor or Method
        //refer this: https://www.w3schools.com/java/ref_keyword_this.asp#:~:text=The%20this%20keyword%20refers%20to,a%20method%20or%20constructor%20parameter).
    }
}

class Graph<T>
{
    //T is the type of element returned
    private Map<T, List <T>> map= new HashMap <>();
    // the class Graph is using Map from Java Collections to define the adjacency list.

    //'<>' determines the type parameter/ variable 'T'
    // A type variable can be any non-primitive type you specify: any class type, any interface type, any array type, or even another type variable. 
    public void addNewVertex(T  s)
    {
        map.put(s, new LinkedList<T>());
    }
    
}

//graph mutation operations


