import com.sun.glass.ui.Size;

import javax.swing.*;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;

import java.io.*;

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

        this.resLabel = new JLabel();
        this.resLabel.setBounds(156,340,428,16);
        this.resLabel.setText("Result (connected groups found): -");

        //строка для указания кол-ва вершин
        this.graphEdit = new JTextArea("");
        this.graphEdit.setBounds(12,32,128,144);
        this.graphEdit.setAutoscrolls(true);
        this.graphEdit.setText("8 10\n1 2\n2 3\n2 4\n3 4\n4 3\n4 5\n4 6\n6 7\n7 8\n8 6");

        this.canvas = new Canvas();
        this.canvas.setBounds(156,12,428,300);

        //что С ГРАФИКОЙ??????? вроде норм

        this.canvas.setVisible(true);
        this.canvas.init();     //для примера вывода графа на canvas

        this.rootPanel.add(this.canvas);

        //устанавливаем виимость всех объектов
        this.buttonLoad.setVisible(true);
        this.buttonInit.setVisible(true);
        this.buttonStep.setVisible(true);
        this.buttonRun.setVisible(true);
        this.descLabel.setVisible(true);
        this.resLabel.setVisible(true);

        //добавляем объекты на панель
        this.rootPanel.add(this.buttonLoad);
        this.rootPanel.add(this.buttonInit);
        this.rootPanel.add(this.buttonStep);
        this.rootPanel.add(this.buttonRun);
        this.rootPanel.add(label);
        this.rootPanel.add(this.graphEdit);
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
                int ret = fd.showDialog(null, "Open file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fd.getSelectedFile();       //получение выбранного файла

                    try(BufferedReader reader =new BufferedReader(new FileReader(file)))
                    {
                        // читаем из файла построчно
                        String line;
                        graphEdit.setText("");
                        while ((line = reader.readLine()) != null) {
                            graphEdit.append(line + "\n");
                        }
                    }
                    catch(IOException ex){
                        resLabel.setText(ex.getMessage());
                    }
                }
            }
        });

        //инициализация графа (ДОДЕЛАТЬ)!!!!!
        buttonInit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    graph = new MyGraph();
                    graph = io.getData(graph, new BufferedReader(new StringReader(graphEdit.getText())));
                    canvas.setContent(graph);
                    solution = new Algorithm();
                    descLabel.setText("Description: algorithm initialized.");
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
                    canvas.setContent(graph.Transpose(graph));
                }

                if (res == -1) {
                    canvas.select(solution.v);
                } else {
                    resLabel.setText("Result (connected groups found): "+String.valueOf(res));
                    buttonRun.setEnabled(false);
                    buttonStep.setEnabled(false);
                    canvas.select(-1);
                }
            }
        });

        setVisible(true);   //для самого окна
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }



    public void onButtonLoadPressed(Boolean clicked) {
        JFileChooser fd = new JFileChooser();
        int ret = fd.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fd.getSelectedFile();

        }
    }

    public static void main(String[] args) {
        new MyGUIForm();
    }
}

/*
import java.awt.Dimension;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import javax.swing.AbstractButton;
        import javax.swing.Icon;
        import javax.swing.JButton;
        import javax.swing.JFrame;
        import javax.swing.JPanel;
        import javax.swing.UIManager;
public class MyGUIForm {
    private JPanel panel;
    public static void createGUI() {
        //JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Test frame");

        JPanel panel = new JPanel();

       // Icon leftIcon = UIManager.getIcon("OptionPane.errorIcon");

        JButton leftButton = new JButton("Enable");
        leftButton.setVerticalTextPosition(AbstractButton.CENTER);
        leftButton.setHorizontalTextPosition(AbstractButton.LEADING);
      //  leftButton.setIcon(leftIcon);
        panel.add(leftButton);

        Icon centerIcon = UIManager.getIcon("OptionPane.informationIcon");

        final JButton centerButton = new JButton("Center");
        centerButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        centerButton.setHorizontalTextPosition(AbstractButton.CENTER);
        centerButton.setIcon(centerIcon);
        centerButton.setEnabled(false);
        panel.add(centerButton);

        centerButton.setPreferredSize(new Dimension(100, 100));

        Icon rightIcon = UIManager.getIcon("OptionPane.questionIcon");

        final JButton rightButton = new JButton("Disable");
        rightButton.setIcon(rightIcon);
        rightButton.setEnabled(false);
        panel.add(rightButton);

        leftButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                leftButton.setEnabled(false);
                centerButton.setEnabled(true);
                rightButton.setEnabled(true);
            }
        });

        rightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                leftButton.setEnabled(true);
                centerButton.setEnabled(false);
                rightButton.setEnabled(false);
            }
        });

        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(350, 145));

        frame.pack();

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI();
            }
        });
    }
}*/