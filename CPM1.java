import java.util.*;
import java.util.List;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.Line2D;

class Activity {
    String name;
    int duration;
    List<Activity> dependencies;
    int earliestStart;
    int earliestFinish;
    int latestStart;
    int latestFinish;
    int slack;
    int x1;
    int y1;
    int x2;
    int y2;

    Activity(String name, int duration) {
        this.name = name;
        this.duration = duration;
        this.dependencies = new ArrayList<>();
    }

    Activity(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
    }

    void addDependency(Activity activity) {
        dependencies.add(activity);
    }
}

public class CPM1 {
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

        criticalPath.add(endActivity);

        // Perform backward pass
        for (int i = activities.size() - 2; i >= 0; i--) {
            Activity currentActivity = activities.get(i);
            // Calculate latest start and finish times for 'currentActivity'
            int minLatestStart = Integer.MAX_VALUE;
            for (Activity dependentActivity : activities) {
                if (dependentActivity.dependencies.contains(currentActivity)) {
                    int latestStart = dependentActivity.latestStart - currentActivity.duration;
                    if (latestStart < minLatestStart) {
                        minLatestStart = latestStart;
                    }
                }
            }
            currentActivity.latestStart = minLatestStart;
            currentActivity.latestFinish = currentActivity.latestStart + currentActivity.duration;

            // Calculate slack for 'currentActivity'
            currentActivity.slack = currentActivity.latestStart - currentActivity.earliestStart;

            // Check if 'currentActivity' is on the critical path
            if (currentActivity.slack == 0 || (currentActivity == endActivity)) {
                criticalPath.add(currentActivity);
            }
        }

        // Calculate the CPM time (earliest finish time of the last activity on the
        // critical path)
        cpmTime = endActivity.earliestFinish;
    }

    public List<Activity> getCriticalPath() {
        return criticalPath;
    }

    public int getCPMTime() {
        return cpmTime;
    }

    public static void main(String[] args) {
        CPM1 cpm = new CPM1();
        Scanner scanner = new Scanner(System.in);

        // Allow the user to input activities and their details
        while (true) {
            System.out.print("Enter activity name (or 'done' to finish): ");
            String name = scanner.nextLine();

            if (name.equals("done")) {
                break;
            }

            System.out.print("Enter duration: ");
            int duration = Integer.parseInt(scanner.nextLine());

            Activity activity = new Activity(name, duration);
            System.out.print("Enter dependencies (comma-separated, or press Enter if none): ");
            String dependencyLine = scanner.nextLine();
            String[] dependencies = dependencyLine.split(",");

            for (String dependency : dependencies) {
                String trimmedDependency = dependency.trim();
                if (!trimmedDependency.isEmpty()) {
                    // Add the specified dependencies
                    Activity dependencyActivity = cpm.activities.stream()
                            .filter(a -> a.name.equals(trimmedDependency))
                            .findFirst()
                            .orElse(null);
                    if (dependencyActivity != null) {
                        activity.addDependency(dependencyActivity);
                    }
                }
            }

            cpm.addActivity(activity);
        }

        // Calculate the Critical Path and CPM time
        cpm.calculateCPM();

        // Display the Critical Path and CPM time
        System.out.println("\nCritical Path:");
        List<Activity> criticalPath = cpm.getCriticalPath();

        for (int i = criticalPath.size() - 1; i >= 0; i--) {
            Activity activity = criticalPath.get(i);
            System.out.println(activity.name);
        }

        System.out.println("\nCPM Time: " + cpm.getCPMTime());

        scanner.close();
    }

    class MyCanvas extends JComponent {
        public void paint(Graphics g1) {

            Graphics2D g2 = (Graphics2D) g1;
            g2.setStroke(new BasicStroke(4));
            g1.setColor(Color.BLUE);
            int x1 = 200;
            int y1 = 200;
            for (Activity activity : activities) {

                if (activity.dependencies != null) {
                    for (Activity dependency : activity.dependencies) {
                        int num = activity.dependencies.size();
                        dependency.x1 = activity.x2;
                    }

                } else {
                    activity.x = x1;
                    activity.y = y1;
                    g1.drawLine(activity.x, activity.y, (activity.x) + 100, activity.y);
                }
            }

        }

        public class GFG1 {
            public static void main(String[] args) {
                JFrame window = new JFrame();
                Label a, b, c, d, e, f, g, h, i;
                a = new Label("A");
                b = new Label("B");
                c = new Label("C");
                d = new Label("D");
                e = new Label("E");
                f = new Label("F");
                g = new Label("G");
                h = new Label("H");
                i = new Label("I");

                a.setBounds(350, 200, 10, 10);
                c.setBounds(450, 250, 10, 10);
                b.setBounds(450, 150, 10, 10);
                d.setBounds(550, 150, 10, 10);
                f.setBounds(550, 250, 10, 10);
                e.setBounds(550, 50, 10, 10);
                g.setBounds(650, 100, 10, 10);
                h.setBounds(650, 200, 10, 10);
                i.setBounds(750, 200, 10, 10);
                window.add(a);
                window.add(c);
                window.add(b);
                window.add(d);
                window.add(e);
                window.add(f);
                window.add(g);
                window.add(h);
                window.add(i);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setBounds(30, 30, 200, 200);
                window.getContentPane().add(new MyCanvas());
                window.setVisible(true);
            }
        }
    }
}