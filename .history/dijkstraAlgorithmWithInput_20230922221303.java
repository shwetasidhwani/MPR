import java.util.*;
// import javax.swing.*;
// import java.awt.*;

public class dijkstraAlgorithmWithInput {
    public static void main(String[] args) {
        // JFrame frame = new JFrame();
        Scanner sc = new Scanner(System.in);
        System.out.println("*************MENU*************");
        System.out.println("Enter the number of tasks :");
        int n = sc.nextInt();
        int[] duration = new int[n];
        String[][] predecessors = new String[n][n];
        for (int i = 0; i < n; i++) {
            System.out.println("Enter duration for activity 1: ");
            duration[i] = sc.nextInt();
            System.out.println("Enter number of predecessors for activity 1 :");
            int preNum = sc.nextInt();
            for (int j = 0; j < preNum; j++) {
                System.out.println("Enter predecessors: ");
                predecessors[i][j] = sc.nextLine();
            }
        }

        System.out.println("*************DATA RECEIVED*************");
        System.out.println("Activity Number         Duration         Predecessors");
        for (int i = 0; i < n; i++) {
            System.out.println(i + "        " + duration[i] + "       " + predecessors[i]);
        }
        sc.close();
    }
}