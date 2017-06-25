import com.trolltech.qt.gui.*;

//объект виджета, который принимает данные визуализированного графа и выводит их на окно
public class Canvas extends QGraphicsScene {
    private VGraph content;

    public void redraw() {
        this.items().clear();

        for (int i = 0; i<content.vertices; ++i) {
            Coord pos = content.getCoord(i);
            this.addEllipse(pos.x-10,pos.y-10,20,20);
        }
    }
}
