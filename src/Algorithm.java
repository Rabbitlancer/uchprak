package com.example.TwoClasses;

//import путь.к.классу.ИмяКласса;
//import com.example.TwoClasses.InputOutput;
//import java.io.BufferedReader;      //считывает текст из символьного потока ввода, буферизируя прочитанные символы
import java.io.IOException;         //класс исключения ввода-вывода
//import java.io.InputStreamReader;   //мост от потоков байтов до символьных потоков: читает байты и декодирует их в символы
import java.io.PrintWriter;         //для вывода информации на консоль, в файл или любой другой поток вывода
import java.util.ArrayList;         //автоматически расширяемый массив
import java.util.Arrays;            //для работы с массивами
//import java.util.StringTokenizer;   //для разбиения входящей строки на слова

public class Algorithm {

    private int numV; //количество вершин в орграфе
    private int numE; //количество дуг в орграфе
    //список инцидентности орграфа
    private ArrayList<Integer> Graph[];

    //список инцидентности транспонированного орграфа (массив списков)
    private ArrayList<Integer> GraphT[];
    //массив для хранения информации о пройденных и не пройденных вершинах
    private boolean usedV[];
    //топологически упорядоченная перестановка номеров вершин графа
    ArrayList<Integer> topSort;
    int componentNum; //количество компонент связности в орграфе

    //обход в глубину
    private void dfs(int v) {
        //если вершина уже рассмотрена, то не производим из нее вызов процедуры
        if (usedV[v]) {
            return;
        }
        usedV[v] = true; //помечаем вершину как пройденную
        //запускаем обход из всех вершин, смежных v
        for (int i = 0; i < Graph[v].size(); ++i) {
            int w = Graph[v].get(i);
            dfs(w); //вызов обхода в глубину от вершины w, смежной с вершиной v
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
        for (int i = 0; i < GraphT[v].size(); ++i) {
            int w = GraphT[v].get(i);
            ccs(w, componentID,component); //вызов обхода в глубину от вершины w, смежной с вершиной v в транспонированном графе
        }
    }

    // метод транспонирования графа
    private ArrayList[] Transpose(){
        GraphT = new ArrayList[numV];
        for (int i = 0; i < numV; ++i) {
            GraphT[i] = new ArrayList<Integer>();       //создали транспонированный граф (пустой)
        }
        for (int i = 0; i < numV; ++i) {    //проходим по всем спискам
            for (int j = 0; j < Graph[i].size(); ++j) {   //проходим по всем элементам одного (текущего)списка
                int v = Graph[i].get(j);
                GraphT[v].add(i);       //добавляем обратную дугу в транспонированный граф
            }
        }
        return GraphT;
    }

    //Чтобы узнать, есть в массиве какой-либо элемент, можно воспользоваться методом contains(),
    // который вернёт логическое значение true или false в зависимости от присутствия элемента в наборе
       //     System.out.println (list.contains("Картошка") + "");

    public int run(int []component) throws IOException {
       //InputOutput.getData(Graph);      //получаем данные
       GraphT = Transpose();    //транспонируем граф

        //помечаем все вершины графа, как непосещенные
        usedV = new boolean[numV];
        Arrays.fill(usedV, false);

        component = new int[numV];
        Arrays.fill(component, 0);

        topSort = new ArrayList<Integer>();

        //запускаем обход в глубину для расчета времени выхода каждой вершины
        for (int v = 0; v < numV; ++v) {
            dfs(v);
        }

        //запускаем поиск компонент сильной связности в порядке уменьшения времени выхода вершин
        Arrays.fill(usedV, false);
        int componentID = 0;
        for (int i = topSort.size() - 1; i >= 0; --i) {
            int v = topSort.get(i);
            if (!usedV[v]) {
                ccs(v, componentID,component);
                componentID++;
            }
        }
        componentNum = componentID;
      //  InputOutput.printData(componentNum, numV, component);
        return componentNum;        //!!!
    }
}


