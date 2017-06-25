import com.trolltech.qt.core.QObject;
import com.trolltech.qt.gui.*;

import java.util.List;

//объект виджета, который принимает данные визуализированного графа и выводит их на окно
public class Canvas extends QGraphicsScene {
    private VGraph content;

    public Canvas(QObject parent) {
        super(parent);
    }

    public void setContent(MyGraph data) {
        content = new VGraph(data);
        content.generateLayout();
    }

    public void redraw() {
        this.items().clear();

        List<Coord> ver = content.getCoord();
        for (Coord a: ver) {
            this.addEllipse(a.x-10, a.y-10, 20, 20);
        }
    }
}
