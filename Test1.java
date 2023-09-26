import java.util.ArrayList;
import java.util.List;

class PERTTask {
    String name;
    int optimisticDuration;
    int mostLikelyDuration;
    int pessimisticDuration;

    public PERTTask(String name, int optimisticDuration, int mostLikelyDuration, int pessimisticDuration) {
        this.name = name;
        this.optimisticDuration = optimisticDuration;
        this.mostLikelyDuration = mostLikelyDuration;
        this.pessimisticDuration = pessimisticDuration;
    }
}

public class Test1 {
    public static void main(String[] args) {
        List<PERTTask> tasks = new ArrayList<>();

        tasks.add(new PERTTask("A", 2, 5, 14));
        tasks.add(new PERTTask("B", 9, 12, 15));
        tasks.add(new PERTTask("C", 5, 14, 17));
        tasks.add(new PERTTask("D", 2, 5, 8));
        tasks.add(new PERTTask("E", 8, 17, 20));
        tasks.add(new PERTTask("F", 6, 6, 12));

        // Calculate expected time, variance, and duration of the project
        int projectExpectedTime = 0;
        int projectVariance = 0;

        System.out.println("Task\tExpected Time\tVariance");
        for (PERTTask task : tasks) {
            int expected = (task.optimisticDuration + 4 * task.mostLikelyDuration + task.pessimisticDuration) / 6;
            int var = ((task.pessimisticDuration - task.optimisticDuration) / 6) * ((task.pessimisticDuration - task.optimisticDuration) / 6);

            projectExpectedTime += expected;
            projectVariance += var;

            

            System.out.printf("%s\t%d\t\t%d\n", task.name, expected, var);
        }

        System.out.println("Project Duration (Expected): " + projectExpectedTime);
        System.out.println("Project Variance: " + projectVariance);
    }
}