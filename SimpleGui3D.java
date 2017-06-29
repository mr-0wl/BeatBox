import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class MyDrawPanel1C extends JPanel {
public void paintComponent(Graphics g) {
  Graphics2D g2d = (Graphics2D) g;
  int red = (int) (Math.random() * 256);
  int blue = (int) (Math.random() * 256);
  int green = (int) (Math.random() * 256);
  Color startColor = new Color(red, blue, green);

  red = (int) (Math.random() * 256);
  green = (int) (Math.random() * 256);
  blue = (int) (Math.random() * 256);
  Color endColor = new Color(red, blue, green);

  GradientPaint gradient = new GradientPaint(70,70,startColor, 150,150, endColor);
  g2d.setPaint(gradient);
  g2d.fillOval(70,70,100,100);
}
}

public class SimpleGui3D {
  JFrame frame;
  JLabel label;

  public static void main (String[] args) {
    SimpleGui3D gui = new SimpleGui3D();
    gui.go();
  }

  public void go() {
    frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JButton labelButton = new JButton("Change Label");
    labelButton.addActionListener(new LabelListener());

    JButton colorButton = new JButton("Change Circle");
    colorButton.addActionListener(new ColorListener());

    label = new JLabel("I'm a label");
    MyDrawPanel1C drawPanel = new MyDrawPanel1C();

    frame.getContentPane(). add(BorderLayout.SOUTH, colorButton);
    frame.getContentPane(). add(BorderLayout.CENTER, drawPanel);
    frame.getContentPane(). add(BorderLayout.EAST, labelButton);
    frame.getContentPane(). add(BorderLayout.WEST, label);

    frame.setSize(600,600);
    frame.setVisible(true);
  }

  class LabelListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      int text = (int) (Math.random() * 3);
      text = (int) (Math.random() * 3);
      if (text == 1) {
        label.setText("one, nerd");
      }
      else if (text == 2) {
        label.setText("two, ass");
      } else {
        label.setText("three, me");
      } // end ifs



    }

  } // close inner class
  class ColorListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      frame.repaint();
    }
  } // close inner class
}
