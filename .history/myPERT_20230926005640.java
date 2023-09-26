import java.util.LinkedList;
import java.util.Scanner;

public class myPERT {

    // public class Node {
    // int value;
    // Node next;

    // public Node(int value, Node next) {
    // this.value = value;
    // this.next = next;
    // }
    // }

    // class listOfActivities {
    // Node head;

    // public listOfActivities(Node head) {
    // this.head = head;
    // }

    // public void addNode(int value) {
    // Node newNode = new Node(value, null);

    // Node current = head;

    // if (current != null) {
    // while (current.next != null) {
    // current = current.next;
    // }
    // current.next = newNode;
    // }

    // else {
    // head = newNode;
    // }
    // }
    // }

    public static class Activity {
        String name;
        int optimistic;
        int pessimistic;
        int mostLikely;
        int estimate = (optimistic + 4 * mostLikely + pessimistic) / 6;

        int lateStart;
        int earlyStart;
        int earlyFinish;
        int lateFinish;
        int slack;

        LinkedList<Activity> dependsOn;

        Activity(String name, int optimistic, int pessimistic, int mostLikely) {
            this.name = name;
            int estimate = (optimistic + 4 * mostLikely + pessimistic) / 6;
            this.optimistic = optimistic;
            this.pessimistic = pessimistic;
            this.mostLikely = mostLikely;
            this.dependsOn = new LinkedList<>();
        }

        public void addDependency(Activity activity) {
            this.dependsOn.add(activity);
        }

        @Override
        public String toString() {
            return "Activity: " + name + "\n" +
                    "Optimistic Time: " + optimistic + "\n" +
                    "Pessimistic Time: " + pessimistic + "\n" +
                    "Most Likely Time: " + mostLikely + "\n" +
                    "Estimate: " + estimate + "\n" +
                    "Early Start: " + earlyStart + "\n" +
                    "Early Finish: " + earlyFinish + "\n" +
                    "Late Start: " + lateStart + "\n" +
                    "Late Finish: " + lateFinish + "\n" +
                    "Slack: " + slack + "\n";
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LinkedList<Activity> allActivities = new LinkedList<Activity>();
        LinkedList<Activity> criticalPath = new LinkedList<Activity>();
        // System.out.println("Enter number of tasks: ");
        // int num = sc.nextInt();
        // for (int i = 0; i < num; i++) {

        // System.out.println("Enter activity-name:");
        // sc.nextLine();
        // String name = sc.nextLine();
        // System.out.println("Enter optimistic time: ");
        // int to = sc.nextInt();
        // System.out.println("Enter pessimistic time:");
        // int tp = sc.nextInt();
        // System.out.println("Enter most likely time:");
        // int tm = sc.nextInt();
        // Activity temp = new Activity(name, to, tp, tm);

        // System.out.println("Does Activity have predecessors? ");
        // char pre = sc.next().charAt(0);
        // if (pre == 'y') {
        // System.out.print("Enter the names of predecessors (Separated by commas): ");
        // sc.nextLine();
        // String[] predecessorNames = sc.nextLine().split(",");// Storing all
        // predecessors in an array
        // for (String predecessorName : predecessorNames) {
        // for (Activity a : allActivities) {
        // if (a.name.equals(predecessorName.trim())) {
        // temp.addDependency(a);
        // break; // found the predecessor, no need to continue searching
        // }
        // }
        // }
        // }
        // allActivities.add(temp);
        // }
        // sc.close();
        Activity A = new Activity("A", 2, 14, 5);
        Activity B = new Activity("B", 9, 15, 12);
        Activity C = new Activity("C", 5, 17, 14);
        Activity D = new Activity("D", 2, 8, 5);
        Activity E = new Activity("E", 8, 20, 17);
        Activity F = new Activity("F", 6, 12, 6);

        allActivities.add(A);
        allActivities.add(B);
        allActivities.add(C);
        allActivities.add(D);
        allActivities.add(E);
        allActivities.add(F);

        C.addDependency(A);
        D.addDependency(B);
        E.addDependency(B);
        F.addDependency(C);
        F.addDependency(D);

        Activity startActivity = allActivities.get(0);
        startActivity.earlyStart = 0;
        startActivity.earlyFinish = startActivity.estimate;

        for (int i = 1; i < allActivities.size(); i++) {
            Activity current = allActivities.get(i);

            int maxEarliestFin = Integer.MAX_VALUE;
            for (Activity dependency : current.dependsOn) {
                if (dependency.earlyStart <maxEarliestFin) {
                    maxEarliestFin = dependency.earlyStart;
                }
            }

            current.earlyStart = maxEarliestFin;
            current.earlyFinish = current.earlyStart + current.estimate;
        }

        Activity endActivity = allActivities.get(allActivities.size() - 1);
        endActivity.lateFinish = endActivity.earlyFinish;
        endActivity.lateStart = endActivity.lateFinish - endActivity.estimate;

        for (int i = allActivities.size() - 2; i >= 0; i--) {
            Activity current = allActivities.get(i);

            int minLatestStart = Integer.MAX_VALUE;
            for (Activity dependent : current.dependsOn) {
                int dependentLateStart = dependent.lateStart;
                if (dependentLateStart < minLatestStart) {
                    minLatestStart = dependentLateStart;
                }
            }
            current.lateStart = minLatestStart;
            current.lateFinish = current.lateStart + current.estimate;

            current.slack = current.lateStart - current.earlyStart;

            int maxTotalFloat = Integer.MIN_VALUE;
            for (Activity activity : allActivities) {
                if (activity.slack > maxTotalFloat) {
                    maxTotalFloat = activity.slack;
                }
            }

            if (current.slack == 0 || current.slack==maxTotalFloat) {
                criticalPath.add(current);
            }
        }

        int DURATION = endActivity.earlyFinish;

        System.out.println("***********OUTPUT***********");

        for (Activity temp1 : allActivities) {
            System.out.println(temp1);
        }
        
        System.out.println("Duration is : " + DURATION);
        
        
        // for (Activity temp1 : criticalPath) {
        //     System.out.print(temp1);
        // }

    }

}
