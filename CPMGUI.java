import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

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

public class CPMGUI extends JFrame {
    private List<Activity> activities;
    private List<Activity> criticalPath;
    private int cpmTime;

    private DirectedSparseGraph<Activity, String> graph;  // JUNG graph

    public CPMGUI() {
        activities = new ArrayList<>();
        criticalPath = new ArrayList<>();
        cpmTime = 0;
        graph = new DirectedSparseGraph<>();

        setTitle("CPM Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JTextField activityNameField = new JTextField(15);
        JTextField durationField = new JTextField(5);
        JTextField dependenciesField = new JTextField(15);
        JButton addActivityButton = new JButton("Add Activity");
        JButton calculateCPMButton = new JButton("Calculate CPM");

        inputPanel.add(new JLabel("Activity Name:"));
        inputPanel.add(activityNameField);
        inputPanel.add(new JLabel("Duration:"));
        inputPanel.add(durationField);
        inputPanel.add(new JLabel("Dependencies (comma-separated):"));
        inputPanel.add(dependenciesField);
        inputPanel.add(addActivityButton);
        inputPanel.add(calculateCPMButton);

        // Output panel
        JTextArea resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

        // Add components to the main frame
        add(inputPanel, BorderLayout.NORTH);
        add(resultScrollPane, BorderLayout.CENTER);

        addActivityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = activityNameField.getText();
                int duration = Integer.parseInt(durationField.getText());
                String[] dependencies = dependenciesField.getText().split(",");

                Activity activity = new Activity(name, duration);

                for (String dependency : dependencies) {
                    String trimmedDependency = dependency.trim();
                    if (!trimmedDependency.isEmpty()) {
                        Activity dependencyActivity = activities.stream()
                                .filter(a -> a.name.equals(trimmedDependency))
                                .findFirst()
                                .orElse(null);
                        if (dependencyActivity != null) {
                            activity.addDependency(dependencyActivity);
                            // Add JUNG graph nodes and edges
                            graph.addEdge(name + " -> " + trimmedDependency, activity, dependencyActivity);
                        }
                    }
                }

                activities.add(activity);
                activityNameField.setText("");
                durationField.setText("");
                dependenciesField.setText("");
            }
        });

        calculateCPMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                // Calculate the CPM time (earliest finish time of the last activity on the critical path)
                cpmTime = endActivity.earliestFinish;

                resultArea.setText("Critical Path:\n");
                for (int i = criticalPath.size() - 1; i >= 0; i--) {
                    Activity activity = criticalPath.get(i);
                    resultArea.append(activity.name + "\n");
                }

                resultArea.append("\nCPM Time: " + cpmTime);

                // Create the JUNG visualization
                CircleLayout<Activity, String> layout = new CircleLayout<>(graph);
                layout.setSize(new Dimension(300, 300));
                BasicVisualizationServer<Activity, String> vv = new BasicVisualizationServer<>(layout);
                vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<>());
                vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<>());

                // Create a new JFrame to display the graph
                JFrame graphFrame = new JFrame("Activity Graph");
                graphFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                graphFrame.getContentPane().add(vv);
                graphFrame.pack();
                graphFrame.setVisible(true);
            }
        });

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CPMGUI cpmGUI = new CPMGUI();
            cpmGUI.setVisible(true);
        });
    }
}
