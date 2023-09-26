import java.util.LinkedList;
import java.util.Scanner;

public class myPERT {
    

    public class Node {
        int value;
        Node next;

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    class listOfActivities {
        Node head;

        public listOfActivities(Node head) {
            this.head = head;
        }

        public void addNode(int value) {
            Node newNode = new Node(value, null);

            Node current = head;

            if (current != null) {
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }

            else {
                head = newNode;
            }
        }
    }

    public static class Activity {
        String name;
        int optimistic;
        int pessimistic;
        int mostLikely;
        int estimate;
        int lateStart;
        int earlyStart;
        int earlyFinish;
        int lateFinish;

        LinkedList<Activity> dependsOn;

        Activity(String name, int optimistic, int pessimistic, int mostLikely) {
            this.name = name;
            this.optimistic = optimistic;
            this.pessimistic = pessimistic;
            this.mostLikely = mostLikely;

            this.dependsOn = new LinkedList<>();
        }

        public void addDependency(Activity activity) {
            this.dependsOn.add(activity);
        }
    }
   

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LinkedList<Activity> allActivities = new LinkedList<Activity>();
        System.out.println("Enter number of tasks: ");
        int num = sc.nextInt();
        for (int i = 0; i < num; i++) {

            System.out.println("Enter 'done' to finish");
            System.out.println("Enter activity-name:");
            String name = sc.nextLine();
            System.out.println("Enter optimistic time: ");
            int to = sc.nextInt();
            System.out.println("Enter pessimistic time:");
            int tp = sc.nextInt();
            System.out.println("Enter most likely time:");
            int tm = sc.nextInt();
            Activity temp = new Activity(name, to, tp, tm);

            System.out.println("Does Activity have predecessors? ");
            char pre = sc.nextLine().charAt(0);
            if (pre == 'y') {
                System.out.print("Enter the names of predecessors (Separated by commas): ");
                String[] predecessorNames = sc.nextLine().split(",");// Storing all predecessors in an array
                for (String predecessorName : predecessorNames) {
                    for (Activity a : allActivities) {
                        if (a.name.equals(predecessorName.trim())) {
                            temp.addDependency(a);
                            break; // found the predecessor, no need to continue searching
                        }
                    }
                }
            }
            allActivities.add(temp);
        }
        // Activities A = new Activities("A", 1, 7, 13);
        // Activities B = new Activities("B", 2, 5, 14);
        // Activities C = new Activities("C", 2, 14, 26);
        // Activities D = new Activities("D", 2, 5, 8);
        // Activities E = new Activities("E", 7, 10, 19);
        // Activities F = new Activities("F", 5, 5, 17);
        // Activities G = new Activities("G", 5, 8, 29);
        // Activities H = new Activities("H", 3, 3, 9);
        // Activities I = new Activities("I", 8, 17, 32);

        // allActivities.add(A);
        // allActivities.add(B);
        // allActivities.add(C);
        // allActivities.add(D);
        // allActivities.add(E);
        // allActivities.add(F);
        // allActivities.add(G);
        // allActivities.add(H);
        // allActivities.add(I);

        // C.addDependency(A);
        // D.addDependency(A);
        // G.addDependency(B);
        // I.addDependency(G);
        // F.addDependency(D);
        // E.addDependency(C);
        // H.addDependency(E);
        // H.addDependency(F);
    }

}
