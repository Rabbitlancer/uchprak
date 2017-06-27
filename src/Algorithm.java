import java.util.ArrayList;         //автоматически расширяемый массив
import java.util.Arrays;            //для работы с массивами


public class Algorithm {

    //список инцидентности транспонированного орграфа (массив списков)
    private MyGraph GraphT;
    //массив для хранения информации о пройденных и не пройденных вершинах
    private boolean usedV[];
    //топологически упорядоченная перестановка номеров вершин графа
    private ArrayList<Integer> topSort;

    private boolean init = false;
    private boolean state = false;
    public int v;
    public int myi;
    private int componentID;

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

    public int run(MyGraph Graph, boolean onestep) {
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
        if (!state) {
            do {
                dfs(v, Graph.IncidList);
                ++v;
            } while (!onestep && v < Graph.numV);
            if (v == Graph.numV) {
                Arrays.fill(usedV, false);
                componentID = 0;
                state = true;
                myi = topSort.size() - 1;
            }
        }
        if (state) {
            do {
                v = topSort.get(myi);
                if (!usedV[v]) {
                    ccs(v, componentID, Graph.component);
                    componentID++;
                }
                myi--;
            } while (!onestep && myi >= 0);
            if (myi == -1) {
                int componentNum = componentID;
                return componentNum;        //!!!
            }
        }
        //запускаем поиск компонент сильной связности в порядке уменьшения времени выхода вершин
        return -1;
    }
}


