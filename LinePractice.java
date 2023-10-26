
//Line using user input
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.*;

public class LinePractice extends JFrame {
    static int x1, y1, l;x1=y1=200;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        x1 = y1 = 200;
        // System.out.println("Enter length of line: ");
        // l = sc.nextInt();
        l = 200;
        sc.close();
        LinePractice ob = new LinePractice();
        ob.setVisible(true);
    }

    public void paint(Graphics g1) {
        super.paint(g1);
        Graphics2D g2 = (Graphics2D) g1;
        g2.setStroke(new BasicStroke(4));
        g1.setColor(Color.BLUE);
        Line2D lin = new Line2D.Float(x1, y1, x1 + 200, y1);
        g2.draw(lin);
    }

    public LinePractice() {
        JPanel panel = new JPanel();
        getContentPane().add(panel);
        setSize(550, 300);
    }
}
