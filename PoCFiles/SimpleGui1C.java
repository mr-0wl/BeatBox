import javax.swing.*;

public class SimpleGui1C {
  public static void main (String[] args) {
    JFrame frame = new JFrame();
    MyDrawPanel rect = new MyDrawPanel();

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.getContentPane() .add(rect);

    frame.setSize(300, 300);

    frame.setVisible(true);
  }
}
