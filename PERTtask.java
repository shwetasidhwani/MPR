import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;   //allows the code to respond when we click on the calculate pert and clear screen button  
import java.awt.event.ActionListener; //used for the 'calculate pert' and 'clear screen' button
import java.text.DecimalFormat; //allows the results to be displayed in specific decimal formats for the answers(probability and all)
 import java.util.ArrayList;  //without these the lists part throws errors
 import java.util.List;

class PERTTask {
    String name;
    int optimisticDuration;
    int mostLikelyDuration;  //the stuff required to calculate pert
    int pessimisticDuration;
    double expected;
    double variance;
    double standardDeviation;

    PERTTask(String name, int optimisticDuration, int mostLikelyDuration, int pessimisticDuration) {  //constructor to initialize the PERTTask class to use its values 
        this.name = name;
        this.optimisticDuration = optimisticDuration;
        this.mostLikelyDuration = mostLikelyDuration;
        this.pessimisticDuration = pessimisticDuration;

        calculatePERTValues(); //this method uses the pert task ka values and computes them
    }

    private void calculatePERTValues() {  //calculation of the PERTvalues(formula based)
        expected = (optimisticDuration + 4 * mostLikelyDuration + pessimisticDuration) / 6.0;
        variance = ((pessimisticDuration - optimisticDuration) / 6.0) * ((pessimisticDuration - optimisticDuration) / 6.0);
        standardDeviation = Math.sqrt(variance);
    }
}

public class PERTCalculatorGUI {  //the GUI Part begins here
    private List<PERTTask> tasks;
    private double projectTime;   //STORES overrall project time, variance and Std Deviation
    private double projectVariance;
    private double projectStandardDeviation;

    private JFrame frame; //full frame initialized
    private JPanel inputPanel;   //the input boxes area initialized
    private JPanel outputPanel; //initializes where the outputs will be displayed
    private JButton calculateButton; //calculatePERT wala button - triggers the calculation using the action listener
    private JButton clearButton; //Clear Screen wala button
    private JTextArea outputTextArea;  //part within the output panel where the outputs are displayed

    public PERTCalculatorGUI() {
        tasks = new ArrayList<>(); //list for all the tasks (taken from the input)
        frame = new JFrame("PERT Calculator"); //pert calculator wala window
        frame.setLayout(new BorderLayout());  

         inputPanel = new JPanel(); //initializes the panel
        inputPanel.setLayout(new GridLayout(0, 3)); //0 rows is a default for however many rows we ll use and 3 columns is the three columns used in the pert calculator one with task name and pessimistic duration 2nd with optimistic and dependancies and 3rd with most likely duration

        JLabel nameLabel = new JLabel("Task Name"); //initializes the labels(display area) with task name,dependancies,etc
        JLabel optimisticLabel = new JLabel("Optimistic Duration");
        JLabel mostLikelyLabel = new JLabel("Most Likely Duration");
        JLabel pessimisticLabel = new JLabel("Pessimistic Duration");
        JLabel dependenciesLabel = new JLabel("Dependencies (comma-separated)"); //displays dependencies

        JTextField nameField = new JTextField();
        JTextField optimisticField = new JTextField(); //allows user to input the data
        JTextField mostLikelyField = new JTextField();    //JTextField is used to initialize a single line text field
        JTextField pessimisticField = new JTextField();
        JTextField dependenciesField = new JTextField();

        calculateButton = new JButton("Calculate PERT");  //button the calculate pert
        clearButton = new JButton("Clear Screen");
        outputTextArea = new JTextArea(10, 30); //text area with 10 rows and 30 columns 
        outputTextArea.setEditable(false);
//Action listeners tell the code what should be done when a certain operation is performed
        calculateButton.addActionListener(new ActionListener() { //when we click on calculate button what should happen
            @Override
            public void actionPerformed(ActionEvent e) { //this method gets executed when the calculateButton button is clicked
                String name = nameField.getText();  //inputs the name TextField using getText() method and stores it in name 
                int optimistic = Integer.parseInt(optimisticField.getText()); //we convert the string that we enter using getText() into a string that is stored in optimistic 
                int mostLikely = Integer.parseInt(mostLikelyField.getText());
                int pessimistic = Integer.parseInt(pessimisticField.getText());
                // String[] dependencies = dependenciesField.getText().split(",");  //dependenciesField ka text is stored in dependencies ka array
                    //split makes an array of strings and each string represents a dependency and that is stored in dependecies named array , -> delimiter
                PERTTask task = new PERTTask(name, optimistic, mostLikely, pessimistic); //all inputs are passed as values for the constructor
                tasks.add(task); //adds the name,optimistic,most likely , pessimistic values in the constructor

                // for (String dep : dependencies) { //in the array of dependencies iterate thru every dependency and simulataneously check each task in the list of tasks
                    // for (PERTTask t : tasks) {
                    //     if (t.name.equals(dep.trim())) {   //if the name of the task matches the dependency (after removal of white spaces to avoid confooosion) then:
                            // Assuming dependencies are names of tasks
                            // You can customize this part to link activities
                            // for critical path calculations
                //         }
                //     }
                // }

                calculatePERT();
            }
        });

        clearButton.addActionListener(new ActionListener() {  //action listener for the clear button wala part
            @Override
            public void actionPerformed(ActionEvent e) { //this method is called when the clear screen wala button is clicked
                tasks.clear();
                projectTime = 0.0;
                projectVariance = 0.0;
                projectStandardDeviation = 0.0;
                outputTextArea.setText("");
                pessimisticField.setText("");  //KHUD SE ADD KIYA 
                optimisticField.setText("");
                mostLikelyField.setText("");
                nameField.setText("");
                dependenciesField.setText("");
            }
        });
            //displays all the parts of the GUI from the labels to the textarea
        inputPanel.add(nameLabel);  //Task name wala text ko display karta hai londa
        inputPanel.add(optimisticLabel);
        inputPanel.add(mostLikelyLabel);
        inputPanel.add(nameField);
        inputPanel.add(optimisticField);
        inputPanel.add(mostLikelyField);
        inputPanel.add(pessimisticLabel);
        inputPanel.add(dependenciesLabel);
        inputPanel.add(new JLabel(""));
        inputPanel.add(pessimisticField);
        inputPanel.add(dependenciesField);
        inputPanel.add(calculateButton);
        inputPanel.add(new JLabel(""));
        inputPanel.add(clearButton);

        outputPanel = new JPanel();
        outputPanel.add(outputTextArea); //Adds the text area to the Output panel

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(outputPanel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //  frame.pack(); //adjusts the dsisplay according to the componenets looks fake tho
        frame.setVisible(true);
        frame.setSize(500, 600); //does the same thing lol
        frame.setResizable(false); 
    }

    private void calculatePERT() {
        double totalExpected = 0.0;  
        double totalVariance = 0.0;

        for (PERTTask task : tasks) 
        {
            double taskExpected = task.expected;
            double taskVariance = task.variance;

            totalExpected += taskExpected;
            totalVariance += taskVariance;
        }

        projectTime = totalExpected;
        projectVariance = totalVariance;
        projectStandardDeviation = Math.sqrt(totalVariance);

        displayPERTResults();
    }

    private void displayPERTResults() {
        DecimalFormat df = new DecimalFormat("#.##");

        outputTextArea.setText("PERT Project Time: " + df.format(projectTime) + "\n");
        outputTextArea.append("Variance of Total Project: " + df.format(projectVariance) + "\n");
        outputTextArea.append("Standard Deviation: " + df.format(projectStandardDeviation) + "\n");
        outputTextArea.append("Probability of Completion:\n");

        for (PERTTask task : tasks) {
            double probability = calculateProbability(task.expected, task.variance);
            outputTextArea.append(task.name + ": " + df.format(probability) + "\n");
        }
    }

    private double calculateProbability(double expected, double variance) {
        double z = (projectTime - expected) / projectStandardDeviation;
        return 1 - cumulativeDistribution(z);
    }

    private double cumulativeDistribution(double z) {
        double t = 1 / (1 + 0.2316419 * Math.abs(z));
        double y = (((((1.330274429 * t - 1.821255978) * t + 1.781477937) * t - 0.356563782) * t + 0.319381530) * t) / (2 * Math.PI) + 0.5;

        if (z > 0) {
            return 1 - y;
        } else {
            return y;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PERTCalculatorGUI());
    }
}