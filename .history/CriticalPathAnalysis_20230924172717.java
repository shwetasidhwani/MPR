import java.util.*;

public class CriticalPathAnalysis {

    public static class Task {  //constructor function
        public int cost;        //each object of Task has its own copy of instance variables
        public int criticalCost;
        public String name;
        public HashSet<Task> dependencies = new HashSet<>();

        public Task(String name, int cost) {      //see main function function
            this.name = name;
            this.cost = cost;
        }

        public void addDependency(Task task) {      //see main function function
            this.dependencies.add(task);
        }
    }
    public static void main(String[] args) {
        Map<String, Task> allTasks = new HashMap<>();
        Task A = new Task("A", 6);
        Task B = new Task("B", 6);
        Task C = new Task("C", 8);
        Task D = new Task("D", 20);
        Task E = new Task("E", 18);
        Task F = new Task("F", 10);
        Task G = new Task("G", 8);
        Task H = new Task("H", 2);
        Task I = new Task("I", 7);

        // Define task dependencies
        B.addDependency(A);
        C.addDependency(A);
        D.addDependency(B);
        E.addDependency(B);
        F.addDependency(C);
        G.addDependency(E);
        H.addDependency(F);
        H.addDependency(D);
        I.addDependency(H);
        I.addDependency(G);

        allTasks.put("A", A);
        allTasks.put("B", B);
        allTasks.put("C", C);
        allTasks.put("D", D);
        allTasks.put("E", E);
        allTasks.put("F", F);
        allTasks.put("G", G);
        allTasks.put("H", H);
        allTasks.put("I", I);

        // Task[] criticalPath = criticalPath(allTasks);

        // System.out.println("Critical Path:");
        // printCriticalPath(criticalPath);
    }

    int earlyStart(){
        
    }
}
