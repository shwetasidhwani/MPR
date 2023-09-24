import java.util.*;

public class PERT2 {
    public static void main(String[] args) {
        Map<String, Activities> allActivities = new HashMap<>();

        Activities A = new Activities("A", 1, 7, 13);
        Activities B = new Activities("B", 2, 5, 14);
        Activities C = new Activities("C", 2, 14, 26);
        Activities D = new Activities("D", 2, 5, 8);
        Activities E = new Activities("E", 7, 10, 19);
        Activities F = new Activities("F", 5, 5, 17);
        Activities G = new Activities("G", 5, 8, 29);
        Activities H = new Activities("H", 3, 3, 9);
        Activities I = new Activities("I", 8, 17, 32);

        allActivities.put("A", A);
        allActivities.put("B", B);
        allActivities.put("C", C);
        allActivities.put("D", D);
        allActivities.put("E", E);
        allActivities.put("F", F);
        allActivities.put("G", G);
        allActivities.put("H", H);
        allActivities.put("I", I);

        C.addDependency(A);
        D.addDependency(A);
        G.addDependency(B);
        I.addDependency(G);
        F.addDependency(D);
        E.addDependency(C);
        H.addDependency(E);
        H.addDependency(F);

        Activities[] early = earlyTime(allActivities);

        System.out.println("Early times for all activities:");
        printEarlyTime(early);
    }

    public static void printEarlyTime(Activities[] activities) {
        for (Activities activity : activities) {
            System.out.println(activity.name + "        " + activity.Early);
        }
    }

    public static class Activities {
        String name;
        int Optimistic;
        int Pessimistic;
        int Expected;
        int Estimate;
        int Late;
        int Early;

        public Set<Activities> dependencies = new HashSet<>();

        Activities(String name, int Optimistic, int Pessimistic, int Expected) {
            this.name = name;
            this.Optimistic = Optimistic;
            this.Pessimistic = Pessimistic;
            this.Expected = Expected;
        }

        public void addDependency(Activities activity) {
            this.dependencies.add(activity);
        }
    }

    public static Activities[] earlyTime(Map<String, Activities> activitiesMap) {
        Set<String> completed = new HashSet<>();
        Set<String> remaining = new HashSet<>(activitiesMap.keySet());

        while (!remaining.isEmpty()) {
            boolean progress = false;
            Set<String> remainingCopy = new HashSet<>(remaining);

            for (String activityName : remainingCopy) {
                Activities activity = activitiesMap.get(activityName);
                if (completed.containsAll(activity.dependencies.stream().map(a -> a.name).toList())) {
                    int early = 0;
                    for (Activities a : activity.dependencies) {
                        a.Estimate = (a.Optimistic + (4 * a.Expected) + a.Pessimistic) / 6;
                        if (a.Early + a.Estimate > early) {
                            early = a.Early + a.Estimate;
                        }
                    }
                    activity.Early = early;
                    completed.add(activityName);
                    remaining.remove(activityName);
                    progress = true;
                }
            }

            if (!progress) {
                throw new RuntimeException("Cyclic dependency, algorithm stopped!");
            }
        }

        Activities[] ret = activitiesMap.values().toArray(new Activities[0]);
        Arrays.sort(ret, Comparator.comparingInt(a -> a.Early));

        return ret;
    }
}
