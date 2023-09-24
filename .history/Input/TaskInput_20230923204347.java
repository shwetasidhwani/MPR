import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Task {
    String name;
    int duration;
    List<Task> predecessors;

    public Task(String name, int duration) {
        this.name = name;
        this.duration = duration;
        this.predecessors = new ArrayList<>();
    }

    public void addPredecessor(Task predecessor) {
        predecessors.add(predecessor);
    }
}

public class TaskInput {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of tasks: ");
        int numTasks = scanner.nextInt();
        scanner.nextLine();

        List<Task> tasks = new ArrayList<>();

        for (int taskVar = 1; taskVar <= numTasks; taskVar++) {
            System.out.print("Enter the name of task " + taskVar + ": ");
            String taskName = scanner.nextLine();

            System.out.print("Enter the duration of task " + taskVar + ": ");
            int taskDuration = scanner.nextInt();
            scanner.nextLine();

            Task task = new Task(taskName, taskDuration);

            System.out.print("Does task " + taskVar + " have any predecessors? (y/n): ");
            char pre = scanner.nextLine().charAt(0);

            if (pre == 'y') {
                System.out.print("Enter the names of predecessors (Separated by commas): ");
                String[] predecessorNames = scanner.nextLine().split(",");// Storing all predecessors in an array
                for (String predecessorName : predecessorNames) {
                    for (Task t : tasks) {
                        if (t.name.equals(predecessorName.trim())) {
                            task.addPredecessor(t);
                            break; // found the predecessor, no need to continue searching
                        }
                    }
                }
            }

            tasks.add(task);// ADDING CURRENT TASK TO TASK LIST
        }
        scanner.close();

        // printing the inputted values
        // System.out.println("Sr.No.      Name        Duration        Predecessors");
        // for (int i = 1; i <= numTasks; i++) {
        //     // System.out.println(i+"          "+tasks[i].Name+"        "+taskDuration+"        ");
        // }

        for(int i=0;i<tasks.size();i++){
            System.out.println(tasks.get(i));
        }
    }
}