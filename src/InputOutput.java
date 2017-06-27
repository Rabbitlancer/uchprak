import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class InputOutput {
    private PrintWriter cout;

    //ввод данных с читателя cin
    public MyGraph getData(MyGraph Graph, BufferedReader cin) throws IOException {//передаем ссылку на граф, в который надо считать данные,
        cout = new PrintWriter(System.out);
        Graph = new MyGraph();

        StringTokenizer tokenizer = new StringTokenizer(cin.readLine());
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
            cout.print((Graph.component[i] + 1) + " ");
        }
        cout.println();
        cout.close();
    }
}

