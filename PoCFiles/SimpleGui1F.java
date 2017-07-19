import javax.swing.*;
import java.awt.*;

class MyDrawPanel1C extends JPanel {
public void paintComponent(Graphics g) {
  Graphics2D g2d = (Graphics2D) g;
  GradientPaint gradient = new GradientPaint(70, 70, Color.blue, 150,150, Color.orange);
  g2d.setPaint(gradient);
  g2d.fillOval(70,70,100,100);
}
}

public class SimpleGui1F {
  public static void main (String[] args) {
    JFrame frame = new JFrame();
    MyDrawPanel1C grad = new MyDrawPanel1C();

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.getContentPane() .add(grad);

    frame.setSize(400, 400);

    frame.setVisible(true);
  }
}
