import com.trolltech.qt.gui.*;

import java.util.List;

//объект виджета, который принимает данные визуализированного графа и выводит их на окно
public class Canvas extends QGraphicsView {
    private VGraph content;
    private QGraphicsScene scene;
    private QBrush brWhite = new QBrush(new QColor(255,255,255));
    private QBrush brBlack = new QBrush(new QColor(0,0,0));
    private QPen pnBlack = new QPen(new QColor(0,0,0));

    public Canvas(QWidget parent) {
        super(parent);
        this.scene = new QGraphicsScene(0,0,428,300,this);
        this.setScene(this.scene);
        this.scene.setBackgroundBrush(new QBrush(new QColor(255,255,255)));
        this.scene.setForegroundBrush(new QBrush(new QColor(255,255,255)));
        this.ensureVisible(0,0,428,300,0,0);
        this.pnBlack.setBrush(brBlack);
        this.pnBlack.setWidth(2);
        this.centerOn(214,150);
    }

    public void setContent(MyGraph data) {
        content = new VGraph(data);
        content.generateLayout();
        this.redraw();
    }

    public void redraw() {
        this.scene.setForegroundBrush(brWhite);
        this.scene.items().add(new QGraphicsRectItem(0,0,428,300));

        List<Coord> ver = content.getCoord();
        for (Coord a: ver) {
            this.scene.addEllipse(a.x-10, a.y-10, 20, 20, pnBlack, brWhite);
        }

        this.setScene(this.scene);
        this.ensureVisible(this.scene.sceneRect());
        this.update();
        this.repaint();
        this.show();
    }
}
