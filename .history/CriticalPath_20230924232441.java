public class CriticalPath {

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

        Task[] critialPath = criticalPath(allTasks);

        System.out.println("Critical Path:");
        printCriticalPath(criticalPath);
    }

    //  helper method to print the critical path in the correct order
    public static void printCriticalPath(Task[] tasks) {
        for (int i = 0; i < tasks.length; i++) {
            Task task = tasks[i];
            System.out.print(task.name + " (" + task.criticalCost + ")");
            if (i < tasks.length - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    // Task class and criticalPath method (unchanged)
    public static class Task {
        public int cost;
        public int criticalCost;
        public String name;
        public HashSet<Task> dependencies = new HashSet<>();

        public Task(String name, int cost) {
            this.name = name;
            this.cost = cost;
        }

        public void addDependency(Task task) {
            this.dependencies.add(task);
        }
    }

    public static Task[] criticalPath(Map<String, Task> tasks) {
        HashSet<String> completed = new HashSet<>();
        HashSet<String> remaining = new HashSet<>(tasks.keySet());

        while (!remaining.isEmpty()) {
            boolean progress = false;
            HashSet<String> remainingCopy = new HashSet<>(remaining); // Create a copy

            for (String taskName : remainingCopy) {  
                Task task = tasks.get(taskName);
                if (completed.containsAll(task.dependencies.stream().map(t -> t.name).toList())) {
                    int critical = 0;
                    for (Task t : task.dependencies) {
                        if (t.criticalCost + t.cost > critical) {
                            critical = t.criticalCost + t.cost;
                        }
                    }
                    task.criticalCost = critical;
                    completed.add(taskName);
                    remaining.remove(taskName);
                    progress = true;
                }
            }
            
            if (!progress) {
                throw new RuntimeException("Cyclic dependency, algorithm stopped!");
            }
        }

        Task[] ret = tasks.values().toArray(new Task[0]);
        Arrays.sort(ret, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                int i = o2.criticalCost - o1.criticalCost;
                if (i != 0)
                    return i;
                return 0;
            }
        });

        return ret;
    }
}

// import java.util.Arrays;
// public class CriticalPath {
//     	public static void main(String[] args) {
// 		int V = 7;  // Number of vertices
//         Graph graph = new Graph(V);

//         // Add edges and weights
//         graph.addEdge(0, 1, 3);
//         graph.addEdge(0, 2, 2);
//         graph.addEdge(1, 3, 2);
//         graph.addEdge(2, 3, 3);
//         graph.addEdge(3, 4, 2);
//         graph.addEdge(3, 5, 3);
//         graph.addEdge(4, 6, 3);
//         graph.addEdge(5, 6, 2);

//         int[] criticalPathLengths = graph.criticalPath();

//         System.out.println("Critical Path Lengths: " + Arrays.toString(criticalPathLengths));

// 	}

// }

