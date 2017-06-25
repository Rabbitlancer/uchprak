import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Arrays;
import java.util.StringTokenizer;

public class InputOutput {

    private BufferedReader cin;
    private PrintWriter cout;
    private StringTokenizer tokenizer;

    //====БЫЛО ДО myGraph
   /* private int numV; //количество вершин в орграфе
    private int numE; //количество дуг в орграфе
    //список инцидентности орграфа
    private ArrayList<Integer> Graph[];

    private int[] component; //массив компонент сильной связности графа
    int componentNum; //количество компонент связности в орграфе
    */

    private MyGraph Graph;

    //ввод данных с консоли
    public MyGraph getData(MyGraph Graph, BufferedReader cin) throws IOException {//передаем ссылку на граф, в который надо считать данные,
       // cin = new BufferedReader(new InputStreamReader(System.in));
        cout = new PrintWriter(System.out);
        Graph = new MyGraph();

        tokenizer = new StringTokenizer(cin.readLine());
        Graph.numV = Integer.parseInt(tokenizer.nextToken()); //считываем количество вершин графа
        Graph.numE = Integer.parseInt(tokenizer.nextToken()); //считываем количество ребер графа

        //инициализация списка инцидентности графа
        Graph.IncidList = new ArrayList[Graph.numV];
        for (int i = 0; i < Graph.numV; ++i) {
            Graph.IncidList[i] = new ArrayList<Integer>();
        }
        //считываем граф, заданный списком ребер
        for (int i = 0; i < Graph.numE; ++i) {
            tokenizer = new StringTokenizer(cin.readLine());
            int v = Integer.parseInt(tokenizer.nextToken());
            int w = Integer.parseInt(tokenizer.nextToken());
            v--;
            w--;
            Graph.IncidList[v].add(w);
        }
        return Graph;
    }

    //вывод результата.   передаем в метод кол-во компонент связности, кол-во вершин, массив компонет сявзности - данные, которые надо вывести
    public void printData(MyGraph Graph) throws IOException {

        cout.println(Graph.componentNum);
        for (int i = 0; i < Graph.numV; ++i) {
            //РАЗОБРАТЬСЯ, ПОЧЕМУ НЕ ВЫВОДИТ КОМПОНЕНТЫ СВЯЗНОСТИ!!! (проблема с передачей массива component)
             cout.print((Graph.component[i] + 1) + " ");
            //cout.print("c");
        }
        cout.println();

        //cin.close();
        cout.close();
    }

    //Статические методы/свойства классов - это такие методы/свойства, к которым можно обратиться не создавая объект данного класса.
    public static void main(String[] args) throws IOException {
        boolean onestep = false; //переменная означает, один шаг алгортма делаем или все (ее надо запросить у юзера)
        InputOutput myIO = new InputOutput();     //создаем объект класса ввода-вывода
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));  //создаем объект класса ввода (???)
        myIO.Graph = myIO.getData(myIO.Graph, cin);    //считываем данные в граф (граф - свойство нашего класса ввода-вывода)
        Algorithm solution = new Algorithm();     //создаем объект класса алгоритм
        myIO.Graph.componentNum = solution.run(myIO.Graph, onestep);   //!!!!!!!алгоритм поиска CCS возвращает нам кол-во компонент связности

        myIO.printData(myIO.Graph);    //вывод результатов (в аргументах - те данные, которые надо вывести)
        cin.close();
    }
}

