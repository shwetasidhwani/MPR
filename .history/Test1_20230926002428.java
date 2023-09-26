import java.util.LinkedList;

public class MyPERT {

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
        int slack;
        LinkedList<Activity> dependsOn;

        Activity(String name, int optimistic, int pessimistic, int mostLikely) {
            this.name = name;
            this.optimistic = optimistic;
            this.pessimistic = pessimistic;
            this.mostLikely = mostLikely;
            this.estimate = (optimistic + (4 * mostLikely) + pessimistic) / 6;
            this.dependsOn = new LinkedList<>();
        }

        public void addDependency(Activity activity) {
            this.dependsOn.add(activity);
        }

        @Override
        public String toString() {
            return "Activity{" +
                    "name='" + name + '\'' +
                    ", optimistic=" + optimistic +
                    ", pessimistic=" + pessimistic +
                    ", mostLikely=" + mostLikely +
                    ", estimate=" + estimate +
                    ", lateStart=" + lateStart +
                    ", earlyStart=" + earlyStart +
                    ", earlyFinish=" + earlyFinish +
                    ", lateFinish=" + lateFinish +
                    ", slack=" + slack +
                    '}';
        }
    }

    public static void main(String[] args) {
        LinkedList<Activity> allActivities = new LinkedList<>();
        LinkedList<Activity> criticalPath = new LinkedList<>();

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

            int maxEarliestFin = 0;
            for (Activity dependency : current.dependsOn) {
                if (dependency.earlyFinish > maxEarliestFin) {
                    maxEarliestFin = dependency.earlyFinish;
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

            if (current.slack == 0 || current == endActivity) {
                criticalPath.add(current);
            }
        }

        int DURATION = endActivity.earlyFinish;

        System.out.println("***OUTPUT***");
        for (Activity temp1 : allActivities) {
            System.out.println(temp1);
        }
        System.out.println("Duration is : " + DURATION);
        System.out.print("Critical Path: ");
        for (Activity temp1 : criticalPath) {
            System.out.print(temp1.name + " -> ");
        }
    }
}