
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.*;

public class Canvas extends  JPanel {
    private VGraph content;

    //конструктор
    public Canvas() {
        this.setLayout(null);
    }

    //обновление содержимого
    public void setContent(MyGraph data) {
        content = new VGraph(data);
        this.add(content);
        this.revalidate();
        this.repaint();
    }
}