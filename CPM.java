import java.util.*;

class Activity {
    String name;
    int duration;
    List<Activity> dependencies;
    int earliestStart;
    int earliestFinish;
    int latestStart;
    int latestFinish;
    int slack;

    Activity(String name, int duration) {
        this.name = name;
        this.duration = duration;
        this.dependencies = new ArrayList<>();
    }

    void addDependency(Activity activity) {
        dependencies.add(activity);
    }
}

public class CPM {
    private List<Activity> activities;
    private List<Activity> criticalPath;
    private int cpmTime;

    public CPM() {
        activities = new ArrayList<>();
        criticalPath = new ArrayList<>();
        cpmTime = 0;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void calculateCPM() {
        // Calculate earliest start and finish times for the first activity
        Activity startActivity = activities.get(0);
        startActivity.earliestStart = 0;
        startActivity.earliestFinish = startActivity.duration;

        // Perform forward pass
        for (int i = 1; i < activities.size(); i++) {
            Activity currentActivity = activities.get(i);

            // Calculate earliest start and finish times for 'currentActivity'
            int maxEarliestFinish = 0;
            for (Activity dependency : currentActivity.dependencies) {
                if (dependency.earliestFinish > maxEarliestFinish) {
                    maxEarliestFinish = dependency.earliestFinish;
                }
            }
            currentActivity.earliestStart = maxEarliestFinish;
            currentActivity.earliestFinish = currentActivity.earliestStart + currentActivity.duration;
        }

        // Calculate latest start and finish times for the last activity
        Activity endActivity = activities.get(activities.size() - 1);
        endActivity.latestFinish = endActivity.earliestFinish;
        endActivity.latestStart = endActivity.latestFinish - endActivity.duration;

        // Perform backward pass
        for (int i = activities.size() - 2; i >= 0; i--) {
            Activity currentActivity = activities.get(i);

            // Calculate latest start and finish times for 'currentActivity'
            int minLatestStart = Integer.MAX_VALUE;
            for (Activity dependentActivity : activities) {
                if (dependentActivity.dependencies.contains(currentActivity)) {
                    int candidateLatestStart = dependentActivity.latestStart - currentActivity.duration;
                    if (candidateLatestStart < minLatestStart) {
                        minLatestStart = candidateLatestStart;
                    }
                }
            }
            currentActivity.latestStart = minLatestStart;
            currentActivity.latestFinish = currentActivity.latestStart + currentActivity.duration;

            // Calculate slack for 'currentActivity'
            currentActivity.slack = currentActivity.latestStart - currentActivity.earliestStart;

            // Check if 'currentActivity' is on the critical path
            if (currentActivity.slack == 0 ) {
                criticalPath.add(currentActivity);
            }
        }

        // Calculate the CPM time (earliest finish time of the last activity 'I')
        cpmTime = endActivity.earliestFinish;
    }

    public List<Activity> getCriticalPath() {
        return criticalPath;
    }

    public int getCPMTime() {
        return cpmTime;
    }

    public static void main(String[] args) {
        CPM cpm = new CPM();

        // Create activities and add them to the CPM
        Activity A = new Activity("A", 6);
        Activity B = new Activity("B", 8);
        Activity C = new Activity("C", 8);
        Activity D = new Activity("D", 20);
        Activity E = new Activity("E", 18);
        Activity F = new Activity("F", 10);
        Activity G = new Activity("G", 8);
        Activity H = new Activity("H", 2);
        Activity I = new Activity("I", 7);

        // Define dependencies
        B.addDependency(A);
        C.addDependency(A);
        D.addDependency(B);
        E.addDependency(B);
        F.addDependency(C);
        G.addDependency(E);
        H.addDependency(D);
        H.addDependency(F);
        I.addDependency(G);
        I.addDependency(H);

        // Add activities to the CPM
        cpm.addActivity(A);
        cpm.addActivity(B);
        cpm.addActivity(C);
        cpm.addActivity(D);
        cpm.addActivity(E);
        cpm.addActivity(F);
        cpm.addActivity(G);
        cpm.addActivity(H);
        cpm.addActivity(I);

        // Calculate the Critical Path and CPM time
        cpm.calculateCPM();

        // Get and print the Critical Path in reverse order with arrows
        List<Activity> criticalPath = cpm.getCriticalPath();
        System.out.println("Critical Path:");
        for (int i = criticalPath.size() - 1; i >= 0; i--) {
            Activity activity = criticalPath.get(i);
            System.out.print(activity.name);
            if (i > 0) {
                System.out.print(" -> ");
            }
        }
        System.out.println();

        // Print the CPM time
        System.out.println("CPM Time: " + cpm.getCPMTime());
    }
}
