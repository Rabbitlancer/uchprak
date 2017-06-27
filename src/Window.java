import java.io.BufferedReader;
import java.io.StringReader;
import com.trolltech.qt.gui.*;

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
    private Algorithm solution;
    private MyGraph graph;

    public static void main(String[] args) {
        QApplication.initialize(args);
        Window window = new Window();
        window.show();
        QApplication.execStatic();
        QApplication.shutdown();
    }

    private Window() {
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
        /*this.canvas = new Canvas(this);
        this.canvas.setGeometry(156,12,428,300);
        this.canvas.setFrameShape(QFrame.Shape.Panel);*/

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

        buttonRun.setEnabled(false);
        buttonStep.setEnabled(false);
    }

    public void onButtonLoadPressed(Boolean clicked) {
        QFileDialog fd = new QFileDialog(null,"Select graph file...");
        fd.setAcceptMode(QFileDialog.AcceptMode.AcceptOpen);
        fd.setFilter("Text files | (*.txt)");
        fd.exec();
    }

    public void onButtonInitPressed(Boolean clicked) {
        try {
            graph = new MyGraph();
            graph = io.getData(graph, new BufferedReader(new StringReader(graphEdit.toPlainText())));
            this.canvas.setContent(graph);
            solution = new Algorithm();
            this.descLabel.setText("Description: algorithm initialized.");
            buttonRun.setEnabled(true);
            buttonStep.setEnabled(true);
            buttonLoad.setEnabled(false);
            buttonInit.setEnabled(false);
        } catch (Exception e) {
            this.descLabel.setText("Description: exception! "+e.getClass().getName()+": "+e.getMessage());
        }
    }

    public void onButtonStepPressed(Boolean clicked) {
        int res = solution.run(graph, true);
        //this.canvas.redraw();
        if (res == -1) {
            this.resLabel.setText("Result (connected groups found): working...");
        } else {
            this.resLabel.setText("Result (connected groups found): "+String.valueOf(res));
            buttonRun.setEnabled(false);
            buttonStep.setEnabled(false);
        }
    }

    public void onButtonRunPressed(Boolean clicked) {
        int res = solution.run(graph, false);
        //this.canvas.redraw();
        this.resLabel.setText("Result (connected groups found): "+String.valueOf(res));
        buttonRun.setEnabled(false);
        buttonStep.setEnabled(false);
    }
}
