import java.util.ArrayList;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.*;

import javax.swing.*;

public class Window extends QMainWindow {

    private QLabel descLabel;
    private QLabel resLabel;
    private QLineEdit vertEdit;
    private QScrollArea edgeList;
    private QPushButton buttonLoad;
    private QPushButton buttonInit;
    private QPushButton buttonStep;
    private QPushButton buttonRun;

    private QFrame canvas;

    private QFrame edgeFrame;
    private ArrayList<QLineEdit> edgeEdits;

    public static void main(String[] args) {
        QApplication.initialize(args);
        Window window = new Window();
        window.show();
        QApplication.execStatic();
        QApplication.shutdown();
    }

    private void setEdgeList(int amount) {
        for (int i = 0; i<edgeEdits.size(); ++i) {
            edgeEdits.get(i).del();
        }
        edgeEdits.clear();

        edgeFrame.setGeometry(0,0,128,16*amount);

        QLineEdit cur;
        for (int i = 0; i<amount; ++i) {
            cur = new QLineEdit(edgeFrame);
            cur.setGeometry(0,16*i,112,16);
            edgeEdits.add(cur);
        }
    }

    public Window() {
        QLabel label = new QLabel(this);
        label.setText("Vertices:");
        label.setGeometry(12,12,64,12);

        label = new QLabel(this);
        label.setText("Edges:");
        label.setGeometry(12,48,64,12);

        //строка для указания кол-ва вершин
        this.vertEdit = new QLineEdit(this);
        this.vertEdit.setGeometry(12,24,128,16);
        this.vertEdit.setText("2");

        //штука для списка инцидентности
        this.edgeList = new QScrollArea(this);
        this.edgeList.setGeometry(12,64,128,128);
        this.edgeFrame = new QFrame();
        this.edgeFrame.setGeometry(0,0,128,1);
        this.edgeList.setWidget(edgeFrame);
        this.edgeList.setHorizontalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAlwaysOff);
        this.edgeList.setVerticalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAlwaysOn);

        this.edgeEdits = new ArrayList<>(0);
        this.setEdgeList(Integer.parseInt(this.vertEdit.text()));

        //кнопки
        this.buttonLoad = new QPushButton(this);
        this.buttonLoad.setText("Load...");
        this.buttonLoad.setGeometry(12,200,60,24);
        this.buttonInit = new QPushButton(this);
        this.buttonInit.setText("Init");
        this.buttonInit.setGeometry(80,200,60,24);
        this.buttonStep = new QPushButton(this);
        this.buttonStep.setText("Step");
        this.buttonStep.setGeometry(12,232,60,24);
        this.buttonRun = new QPushButton(this);
        this.buttonRun.setText("Run!");
        this.buttonRun.setGeometry(80,232,60,24);

        //рисовальник - в теории (возможно, потом надо будет заменить другим классом)
        this.canvas = new QFrame(this);
        this.canvas.setGeometry(156,12,428,300);
        this.canvas.setFrameShape(QFrame.Shape.Panel);

        //поясняющие надписи
        this.descLabel = new QLabel(this);
        this.descLabel.setGeometry(156,300,428,48);
        this.descLabel.setText("Description: ");
        this.resLabel = new QLabel(this);
        this.resLabel.setGeometry(156,364,428,16);
        this.resLabel.setText("Result (connected groups found): -");

        //завершающие настройки
        this.setFixedSize(600,400);
        this.setWindowTitle("Strongly-connected connectivity search. 5304tech (R)");
    }

}
