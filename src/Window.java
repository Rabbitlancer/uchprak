import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

import EDU.oswego.cs.dl.util.concurrent.Slot;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.*;
import sun.misc.Signal;

import javax.swing.*;

public class Window extends QMainWindow {

    private QLabel descLabel;
    private QLabel resLabel;
    private QTextEdit graphEdit;
    private QPushButton buttonLoad;
    private QPushButton buttonInit;
    private QPushButton buttonStep;
    private QPushButton buttonRun;

    private Canvas canvas;

    private InputOutput io;

    public static void main(String[] args) {
        QApplication.initialize(args);
        Window window = new Window();
        window.show();
        QApplication.execStatic();
        QApplication.shutdown();
    }

    public Window() {
        io = new InputOutput();

        QLabel label = new QLabel(this);
        label.setText("Graph data:");
        label.setGeometry(12,12,64,12);

        //строка для указания кол-ва вершин
        this.graphEdit = new QTextEdit(this);
        this.graphEdit.setGeometry(12,32,128,144);
        this.graphEdit.setText("2 2\n1 2\n2 1");

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
        this.canvas = new Canvas(this);
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


        buttonLoad.clicked.connect(this, "onButtonLoadPressed(Boolean)");
        buttonInit.clicked.connect(this, "onButtonInitPressed(Boolean)");
        buttonStep.clicked.connect(this, "onButtonStepPressed(Boolean)");
        buttonRun.clicked.connect (this, "onButtonRunPressed(Boolean)");
    }

    public void onButtonLoadPressed(Boolean clicked) {

    }

    public void onButtonInitPressed(Boolean clicked) {
        try {
            this.canvas.setContent(io.getData(new MyGraph(), new BufferedReader(new StringReader(graphEdit.toPlainText()))));
            this.descLabel.setText("Description: algorithm initialized.");
        } catch (Exception e) {
            this.descLabel.setText("Description: exception! "+e.getClass().getName()+": "+e.getMessage());
        }
    }

    public void onButtonStepPressed(Boolean clicked) {

    }

    public void onButtonRunPressed(Boolean clicked) {

    }


}
