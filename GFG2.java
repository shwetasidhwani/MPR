import java.util.*;
import java.util.List;

import javax.swing.JComponent;

import java.awt.*;
import java.swing.*;
import java.awt.geom.Line2D;

public class GFG2 extends JComponent {
    String name;
    List<GFG2> dependencies;

    GFG2(String name) {
        this.name = name;
        this.dependencies = new ArrayList<>();
    }

    void addDependency(GFG2 activity) {
        dependencies.add(activity);
    }

    public void paint(Graphics g1) {
        Graphics2D g2 = (Graphics2D) g1;
        g2.setStrone(new BasicStroke(4));
        g1.setColor(Color.BLUE);

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of lines to be made: ");
        int numLines = sc.nextInt();

        int i = 0;
        while (i < numLines) {
            String name = sc.nextLine();
            GFG2 activity = new GFG2(name);
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
            i++;
        }

    }

}
