
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.*;

public class Canvas extends  JPanel {
    private VGraph content;
    private static final Color[] compColor = {Color.white,  Color.blue, Color.green, Color.yellow,   Color.magenta,
                                              Color.cyan,   Color.gray, Color.pink,  Color.darkGray, Color.red};

    //конструктор
    public Canvas() {
        this.setLayout(null);
    }

    public void colorVisited(boolean[] visited) {
        for (int i = 0; i<visited.length; ++i) {
            if (visited[i]) {
                content.recolor(i, Color.gray);
            }
        }
        content.repaint();
    }

    public void colorComponents(int[] component) {
        for (int i = 0; i<component.length; ++i) {
            content.recolor(i, compColor[component[i] % 10]);
        }
        content.repaint();
    }

    public void select(int id) {
        content.recolor(id, Color.pink);
        content.repaint();
    }

    //обновление содержимого
    public void setContent(MyGraph data) {
        content = new VGraph(data);
        this.add(content);
        this.revalidate();
        this.repaint();
    }
}