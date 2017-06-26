import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Canvas extends JPanel {
    public Canvas() {
    }
    public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        Color MyColor  = new Color(0,0,0);
        g.setColor(MyColor);
        g.drawOval(0, 0, width, height);
    }
    public static void main(String args[]) {
        JFrame frame = new JFrame("Oval Sample");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Canvas());
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}