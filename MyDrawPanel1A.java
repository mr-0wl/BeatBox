import java.awt.*;
import javax.swing.*;

class MyDrawPanel1A extends JPanel {

  public void paintComponent(Graphics g) {
    Image image = new ImageIcon("profile_pic_cropped2.jpg").getImage();
    g.drawImage(image,3,4,this);
  }
}
