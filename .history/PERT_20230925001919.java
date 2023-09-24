import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class PERT {
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

        Activities[] Early = earlyTime(allActivities);

        System.out.println("Early times for all are :");
        printEarlyTime(Early);
    }

    public static void printEarlyTime(Activities[] activities) {
        for (int i = 0; i < activities.length; i++) {
            Activities activity = activities[i];
          System.out.println(activity.name+ "        " +activity.Early ) ;  
        }
    }

    public static class Activities {
        String name;
        int Optimistic;
        int Pessimistic;
        int Expected;
        int Estimate;
        double variance;

        int Late;
        int Early;

        public HashSet<Activities> dependencies = new HashSet<>();

        Activities(String name, int Optimistic, int Pessimistic, int Estimate) {
            this.name = name;
            this.Optimistic = Optimistic;
            this.Pessimistic = Pessimistic;
            this.Estimate = Estimate;
        }

        public void addDependency(Activities Activity) {
            this.dependencies.add(Activity);
        }

    }
    // public class Events{

    // Events(int Late,int Early){
    // this.Early=Early;
    // this.Late=Late;
    // }
    // }

    public static Activities[] earlyTime(Map<String, Activities> activities) {
        HashSet<String> completed = new HashSet<>(); // is empty FOR NOW
        HashSet<String> remaining = new HashSet<>(activities.keySet()); // stores the keys for all the elements in the
                                                                        // map
        while (!remaining.isEmpty()) { // loop while there are still elements in the remaining HashSet
            boolean progress = false; // set progress to false FOR NOW
            HashSet<String> remainingCopy = new HashSet<>(remaining); // Create a copy

            for (String activityName : remainingCopy) { // for each taskName in remainingCopy HashSet, do the following
                Activities activity = activities.get(activityName);// returns the value corresponding to activityName
                                                                   // which is a key
                if (completed.containsAll(activity.dependencies.stream().map(a -> a.name).toList())) {
                    // used to map each element in dependencies to corresponding name and converting
                    // it all to a list
                    // containsAll returns a TRUE or FALSE value
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

        Activities[] ret = activities.values().toArray(new Activities[0]);
        Arrays.sort(ret, Comparator.comparingInt(a -> a.Early));

        return ret;
    }
}







