import javax.swing.*;

public class SimpleGui1D {
  public static void main (String[] args) {
    JFrame frame = new JFrame();
    MyDrawPanel1A pic = new MyDrawPanel1A();

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.getContentPane() .add(pic);

    frame.setSize(300, 300);

    frame.setVisible(true);
  }
}
