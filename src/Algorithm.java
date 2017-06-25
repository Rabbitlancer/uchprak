//import путь.к.классу.ИмяКласса;
//import com.example.TwoClasses.InputOutput;
//import java.io.BufferedReader;      //считывает текст из символьного потока ввода, буферизируя прочитанные символы
import java.io.IOException;         //класс исключения ввода-вывода
//import java.io.InputStreamReader;   //мост от потоков байтов до символьных потоков: читает байты и декодирует их в символы
//import java.io.PrintWriter;         //для вывода информации на консоль, в файл или любой другой поток вывода
import java.util.ArrayList;         //автоматически расширяемый массив
import java.util.Arrays;            //для работы с массивами
//import java.util.StringTokenizer;   //для разбиения входящей строки на слова


public class Algorithm {

    //список инцидентности транспонированного орграфа (массив списков)
    private MyGraph GraphT;
    //массив для хранения информации о пройденных и не пройденных вершинах
    private boolean usedV[];
    //топологически упорядоченная перестановка номеров вершин графа
    ArrayList<Integer> topSort;
    //  int componentNum; //количество компонент связности в орграфе
    private boolean init = false;
    private boolean state = false;
    public int v;
    private int myi;
    int componentID;

    //обход в глубину
    private void dfs(int v, ArrayList<Integer>[] Graph) {
        //если вершина уже рассмотрена, то не производим из нее вызов процедуры
        if (usedV[v]) {
            return;
        }
        usedV[v] = true; //помечаем вершину как пройденную
        //запускаем обход из всех вершин, смежных v
        for (int i = 0; i < Graph[v].size(); ++i) {
            int w = Graph[v].get(i);
            dfs(w,Graph); //вызов обхода в глубину от вершины w, смежной с вершиной v
        }
        //добавляем посещенную вершину в топологический порядок
        topSort.add(v);
    }

    //поиск компонент сильной связности орграфа
    private void ccs(int v, int componentID, int[] component) {
        //если вершина уже пройдена, то не производим из нее вызов процедуры
        if (usedV[v]) {
            return;
        }
        usedV[v] = true; //помечаем вершину как пройденную
        component[v] = componentID;
        //запускаем обход из всех вершин, смежных с вершиной v
        for (int i = 0; i < GraphT.IncidList[v].size(); ++i) {
            int w = GraphT.IncidList[v].get(i);
            ccs(w, componentID,component); //вызов обхода в глубину от вершины w, смежной с вершиной v в транспонированном графе
        }
    }
    //Чтобы узнать, есть в массиве какой-либо элемент, можно воспользоваться методом contains(),
    // который вернёт логическое значение true или false в зависимости от присутствия элемента в наборе
    //     System.out.println (list.contains("Картошка") + "");

    public int run(MyGraph Graph, boolean onestep) throws IOException {
        if (!init) runInit(Graph, onestep);
        int res = runCycle(Graph,onestep);
        return res;
    }

    public void runInit (MyGraph Graph, boolean onestep) {
        GraphT = Graph.Transpose(Graph);    //транспонируем граф
        //помечаем все вершины графа, как непосещенные
        usedV = new boolean[Graph.numV];
        Arrays.fill(usedV, false);

        Graph.component = new int[Graph.numV];  //массив для компонент связности
        Arrays.fill(Graph.component, 0);  //заполняем нулями

        topSort = new ArrayList<Integer>();
        init = true;
        v=0;
    }

    public int runCycle (MyGraph Graph, boolean onestep) {
        //запускаем обход в глубину для расчета времени выхода каждой вершины
        while (!onestep) {
            if (!state) {
                while (!onestep && v < Graph.numV) {
                    dfs(v, Graph.IncidList);
                    ++v;
                }
                if (v == Graph.numV) {
                    Arrays.fill(usedV, false);
                    componentID = 0;
                    state = true;
                    myi = topSort.size() - 1;
                }
            } else {
                while (!onestep && myi >= 0) {
                    v = topSort.get(myi);
                    if (!usedV[v]) {
                        ccs(v, componentID, Graph.component);
                        componentID++;
                    }
                    myi--;
                }
                if (myi == -1) {
                    int componentNum = componentID;
                    return componentNum;        //!!!
                }
            }
        }
        //запускаем поиск компонент сильной связности в порядке уменьшения времени выхода вершин
        return -1;
        }
}


