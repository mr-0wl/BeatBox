import javax.swing.*;

public class SimpleGui1E {
  public static void main (String[] args) {
    JFrame frame = new JFrame();
    MyDrawPanel1B pic = new MyDrawPanel1B();

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.getContentPane() .add(pic);

    frame.setSize(300, 300);

    frame.setVisible(true);
  }
}
