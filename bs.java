import java.util.*;

public class CPMWithLateTimes {

    public static void main(String[] args) {
        // Create a map of activities to their durations
        Map<String, Integer> activities = new HashMap<>();
        activities.put("A", 6);
        activities.put("B", 8);
        activities.put("C", 8);
        activities.put("D", 20);
        activities.put("E", 18);
        activities.put("F", 10);
        activities.put("G", 8);
        activities.put("H", 2);
        activities.put("I", 7);

        // Create a map of activities to their predecessors
        Map<String, List<String>> predecessors = new HashMap<>();
        predecessors.put("A", new ArrayList<>());
        predecessors.put("B", Arrays.asList("A"));
        predecessors.put("C", Arrays.asList("A"));
        predecessors.put("D", Arrays.asList("B"));
        predecessors.put("E", Arrays.asList("B"));
        predecessors.put("F", Arrays.asList("C"));
        predecessors.put("G", Arrays.asList("E"));
        predecessors.put("H", Arrays.asList("D", "F"));
        predecessors.put("I", Arrays.asList("G", "H"));

        // Create a list of all activities
        List<String> allActivities = new ArrayList<>(activities.keySet());

        // Initialize the earliest start times for all activities
        Map<String, Integer> earliestStartTimes = new HashMap<>();
        for (String activity : allActivities) {
            earliestStartTimes.put(activity, 0);
        }

        // Calculate the earliest start times for all activities
        for (String activity : allActivities) {
            for (String predecessor : predecessors.get(activity)) {
                earliestStartTimes.put(activity, Math.max(earliestStartTimes.get(activity), earliestStartTimes.get(predecessor) + 
                activities.get(predecessor)));
            }

        }

        // Calculate the latest finish times for all activities
        Map<String, Integer> latestFinishTimes = new HashMap<>();
        int projectDuration = calculateLatestTimes(activities, predecessors, allActivities, earliestStartTimes, latestFinishTimes);

        // Calculate the critical path
        List<String> criticalPath = new ArrayList<>();
        for (String activity : allActivities) {
            if (latestFinishTimes.get(activity) - earliestStartTimes.get(activity) == 0) {
                criticalPath.add(activity);
            }
        }

        // Print the critical path
        System.out.println("Critical path:");
        for (String activity : criticalPath) {
            System.out.print(activity + " ");
        }
        System.out.println();

        // Print the earliest start, early finish, late start, and late finish times for all activities
        System.out.println("Activity\tES\tEF\tLS\tLF");
        for (String activity : allActivities) {
            int es = earliestStartTimes.get(activity);
            int ef = es + activities.get(activity);
            int ls = latestFinishTimes.get(activity) - activities.get(activity);
            int lf = latestFinishTimes.get(activity);
            System.out.println(activity + "\t\t" + es + "\t" + ef + "\t" + ls + "\t" + lf);
        }

        // Print the project duration
        System.out.println("Project Duration: " + projectDuration);
    }

    public static int calculateLatestTimes(Map<String, Integer> activities, Map<String, List<String>> predecessors,
                                           List<String> allActivities, Map<String, Integer> earliestStartTimes,
                                           Map<String, Integer> latestFinishTimes) {
        // Calculate the latest finish times for all activities starting from the end
        List<String> reversedActivities = new ArrayList<>(allActivities);
        Collections.reverse(reversedActivities);

        // Set the latest finish time for the last activity to the project duration
        String lastActivity = reversedActivities.get(0);
        latestFinishTimes.put(lastActivity, earliestStartTimes.get(lastActivity) + activities.get(lastActivity));

        // Calculate the latest finish times for the rest of the activities
        for (int i = 1; i < reversedActivities.size(); i++) {
            String activity = reversedActivities.get(i);
            int lf = latestFinishTimes.get(activity);
            for (String successor : predecessors.get(activity)) {
                int successorLf = lf - activities.get(activity);
                if (latestFinishTimes.containsKey(successor)) {
                    lf = Math.min(lf, latestFinishTimes.get(successor));
                } else {
                    latestFinishTimes.put(successor, successorLf);
                    lf = successorLf;
                }
            }
            latestFinishTimes.put(activity, lf);
        }

        // Project duration is the LF of the first activity (usually the starting node)
        return latestFinishTimes.get(reversedActivities.get(0));
    }
}
