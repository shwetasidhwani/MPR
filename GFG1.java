//program to draw a line

import java.awt.*;
import javax.swing.*;
import java.awt.geom.Line2D;

class MyCanvas extends JComponent {
    public void paint(Graphics g1) {

        Graphics2D g2 = (Graphics2D) g1;
        g2.setStroke(new BasicStroke(4));
        g1.setColor(Color.BLUE);
        g1.drawLine(300, 200, 400, 200);// a
        g1.drawLine(400, 200, 500, 300);// c
        // g.drawLine(80, 20, 55, -10);
        g1.drawLine(400, 200, 500, 100);// b
        g1.drawLine(500, 300, 600, 200);// f
        g1.drawLine(500, 100, 600, 200);// d
        g1.drawLine(500, 100, 600, 0);// e
        g1.drawLine(600, 0, 700, 200);// g
        g1.drawLine(600, 200, 700, 200);// h
        g1.drawLine(700, 200, 800, 200);

    }
}

public class GFG1 {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        Label a, b, c, d, e, f, g, h, i;
        a = new Label("A");
        b = new Label("B");
        c = new Label("C");
        d = new Label("D");
        e = new Label("E");
        f = new Label("F");
        g = new Label("G");
        h = new Label("H");
        i = new Label("I");

        a.setBounds(350, 200, 10, 10);
        c.setBounds(450, 250, 10, 10);
        b.setBounds(450, 150, 10, 10);
        d.setBounds(550, 150, 10, 10);
        f.setBounds(550, 250, 10, 10);
        e.setBounds(550, 50, 10, 10);
        g.setBounds(650, 100, 10, 10);
        h.setBounds(650, 200, 10, 10);
        i.setBounds(750, 200, 10, 10);
        window.add(a);
        window.add(c);
        window.add(b);
        window.add(d);
        window.add(e);
        window.add(f);
        window.add(g);
        window.add(h);
        window.add(i);
        window.setBounds(30, 30, 200, 200);
        window.getContentPane().add(new MyCanvas());
        window.setVisible(true);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}