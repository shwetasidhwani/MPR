import javax.swing.*;
class TextFieldExample
{
    public static void main(String[] args) {
        JFrame f=new JFrame("TextField Example");
        JTextField t1,t2;
        t1=new JTextField("This is Shweta's Project on textfields");
        t1.setBounds(50, 100, 200, 30);
        t2=new JTextField("Java swings is an extension of awt");
        t2.setBounds(50, 150, 200, 30);
        f.add(t1);
        f.add(t2);
        f.setSize(400, 400);
        f.setLayout(null);
        f.setVisible(true);
    }
}