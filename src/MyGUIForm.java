import com.sun.glass.ui.Size;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StringReader;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//добавить: алгоритм! работу кнопок, вывод графа, ввод из файла

public class MyGUIForm extends JFrame{
    private JLabel descLabel;
    private JLabel resLabel;
    private JTextArea graphEdit;
    private JButton buttonLoad;
    private JButton buttonInit;
    private JButton buttonStep;
    private JButton buttonRun;

    private Canvas canvas;

    Graphics g;

    private InputOutput io;
    private Algorithm solution;
    private MyGraph graph;

    private JPanel rootPanel;

    // ключевое слово super, которое обозначает суперкласс, т.е. класс, производным от которого является текущий класс
    public MyGUIForm() {
        //setBounds(x, y, w, h) - указывает координаты верхней левой вершины окна, а также его ширину и высоту.
        //завершающие настройки
        this.setSize(600,400);
        this.setResizable(false);
        this.setMinimumSize(new Dimension(600,400));
        this.setTitle("Strongly-connected connectivity search. 5304tech (R)");
        this.rootPanel = new JPanel();
        rootPanel.setLayout(null);      //абсолютное позиционирование
        rootPanel.setBounds(0,0,600,400);

        this.setBounds(100,100,450,400);
        setContentPane(rootPanel);

        JLabel label = new JLabel();
        label.setText("Graph data: ");
        label.setBounds(12,12,80,12);
        label.setVisible(true);

        //кнопки управления
        this.buttonLoad = new JButton("Load");
        this.buttonInit = new JButton("Init");
        this.buttonStep = new JButton("Step");
        this.buttonRun = new JButton("Run");

        //устанавливаем размеры кнопок
        this.buttonLoad.setBounds(12,200,63,24);
        this.buttonInit.setBounds(80,200,63,24);
        this.buttonStep.setBounds(12,232,63,24);
        this.buttonRun.setBounds(80,232,63,24);

        //поясняющие надписи
        this.descLabel = new JLabel();
        this.descLabel.setBounds(156,300,428,48);
        this.descLabel.setText("Description: ");
        this.descLabel.setAutoscrolls(true);

        this.resLabel = new JLabel();
        this.resLabel.setBounds(156,340,428,16);
        this.resLabel.setText("Result (connected groups found): -");

        //строка для указания кол-ва вершин
        this.graphEdit = new JTextArea("");
        this.graphEdit.setBounds(12,32,128,14400);
        this.graphEdit.setText("8 10\n1 2\n2 3\n2 4\n3 4\n4 3\n4 5\n4 6\n6 7\n7 8\n8 6");
        JScrollPane scroll = new JScrollPane(graphEdit);
        scroll.setBounds(12,32,128,144);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        this.canvas = new Canvas();
        this.canvas.setBounds(156,12,428,300);
        this.canvas.setVisible(true);

        this.rootPanel.add(this.canvas);

        //устанавливаем виимость всех объектов
        this.buttonLoad.setVisible(true);
        this.buttonInit.setVisible(true);
        this.buttonStep.setVisible(true);
        this.buttonRun.setVisible(true);
        this.descLabel.setVisible(true);
        this.resLabel.setVisible(true);
        scroll.setVisible(true);

        //добавляем объекты на панель
        this.rootPanel.add(this.buttonLoad);
        this.rootPanel.add(this.buttonInit);
        this.rootPanel.add(this.buttonStep);
        this.rootPanel.add(this.buttonRun);
        this.rootPanel.add(label);
        this.rootPanel.add(scroll);
        //this.rootPanel.add(this.graphEdit);
        this.rootPanel.add(this.descLabel);
        this.rootPanel.add(this.resLabel);

        buttonRun.setEnabled(false);
        buttonStep.setEnabled(false);

        rootPanel.setVisible(true);

        io = new InputOutput();

        //открытие файла для чтения
        buttonLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fd = new JFileChooser();
                int ret = fd.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fd.getSelectedFile();
                    label.setText(file.getName());
                }
            }
        });

        //инициализация графа
        buttonInit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    graph = new MyGraph();
                    graph = io.getData(graph, new BufferedReader(new StringReader(graphEdit.getText())));
                    canvas.setContent(graph.Transpose(graph));
                    canvas.select(0);
                    solution = new Algorithm();
                    descLabel.setText("Description: algorithm initialized.");
                    resLabel.setText("Result (connected groups found): -");
                    buttonRun.setEnabled(true);
                    buttonStep.setEnabled(true);
                    buttonLoad.setEnabled(false);
                    buttonInit.setEnabled(false);
                } catch (Exception e) {
                    descLabel.setText("Description: exception! "+e.getClass().getName()+": "+e.getMessage());
                }
            }
        });

        //выполнение алгоритма
        buttonStep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                boolean lastState = solution.state;
                int res = solution.run(graph, true);
                if (solution.state ^ lastState) {
                    canvas.setContent(graph);
                }

                if (!solution.state) {
                    canvas.colorVisited(solution.usedV);
                } else {
                    canvas.colorComponents(graph.component);
                }
                canvas.select(solution.v);

                if (res == -1) {
                    String str = "Description: ";
                    str += "next vertex: v";
                    str += String.valueOf(solution.v+1);
                    str += "; ";
                    if (solution.state ^ lastState) {
                        str += "graph transposed.";
                    } else if (!solution.state) {
                        str += "performing DFS...";
                    } else {
                        str += "will detect component #";
                        str += String.valueOf(solution.componentID);
                    }

                    descLabel.setText(str);
                } else {
                    canvas.colorComponents(graph.component);
                    descLabel.setText("Description: algorithm finished.");
                    resLabel.setText("Result (connected groups found): "+String.valueOf(res));
                    buttonRun.setEnabled(false);
                    buttonStep.setEnabled(false);
                    buttonInit.setEnabled(true);
                    buttonLoad.setEnabled(true);
                }
            }
        });

        buttonRun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                int res = solution.run(graph, false);
                descLabel.setText("Description: skip description.");
                resLabel.setText("Result (connected groups found): "+String.valueOf(res));
                buttonRun.setEnabled(false);
                buttonStep.setEnabled(false);
            }
        });

        setVisible(true);   //для самого окна
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new MyGUIForm();
    }
}