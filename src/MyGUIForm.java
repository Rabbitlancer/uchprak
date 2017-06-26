import javax.swing.*;
import java.awt.Graphics;

//добавить: алгоритм! работу кнопок, вывод графа, ввод из файла

public class MyGUIForm extends JFrame{
    private JLabel descLabel;
    private JLabel resLabel;
    private JTextField graphEdit;
    private JButton buttonLoad;
    private JButton buttonInit;
    private JButton buttonStep;
    private JButton buttonRun;

    private Canvas canvas;

    Graphics g;

    //private InputOutput io;
    //private Algorithm solution;
    //private MyGraph graph;

    private JPanel rootPanel;

    // ключевое слово super, которое обозначает суперкласс, т.е. класс, производным от которого является текущий класс
    public MyGUIForm() {
        //setBounds(x, y, w, h) - указывает координаты верхней левой вершины окна, а также его ширину и высоту.
        //завершающие настройки
        this.setSize(600,400);
        this.setTitle("Strongly-connected connectivity search. 5304tech (R)");
        this.rootPanel = new JPanel();
        rootPanel.setLayout(null);      //абсолютное позиционирование

        this.setBounds(100,100,450,400);
        setContentPane(rootPanel);

        JLabel label = new JLabel();
        label.setText("Graph data: ");
        label.setBounds(12,12,64,12);
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
        this.graphEdit = new JTextField("");
        this.graphEdit.setBounds(12,32,128,144);
        this.graphEdit.setText("2 2\n1 2\n2 1");        //sth strange

        this.canvas = new Canvas();
        this.canvas.setBounds(156,12,428,300);

        //что С ГРАФИКОЙ??????? вроде норм

        this.canvas.setVisible(true);
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

        rootPanel.setVisible(true);

        setVisible(true);   //для самого окна
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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