package com.example.solution;

import java.io.BufferedReader;      //считывает текст из символьного потока ввода, буферизируя прочитанные символы
import java.io.IOException;         //класс исключения ввода-вывода
import java.io.InputStreamReader;   //мост от потоков байтов до символьных потоков: читает байты и декодирует их в символы
import java.io.PrintWriter;         //для вывода информации на консоль, в файл или любой другой поток вывода
import java.util.ArrayList;         //автоматически расширяемый массив
import java.util.Arrays;            //для работы с массивами
import java.util.StringTokenizer;   //для разбиения входящей строки на слова

public class Solution {

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
    private int[] component; //массив компонент сильной связности графа
    int componentNum; //количество компонент связности в орграфе

    private BufferedReader cin;
    private PrintWriter cout;
    private StringTokenizer tokenizer;

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
    private void ccs(int v, int componentID) {
        //если вершина уже пройдена, то не производим из нее вызов процедуры
        if (usedV[v]) {
            return;
        }
        usedV[v] = true; //помечаем вершину как пройденную
        component[v] = componentID;
        //запускаем обход из всех вершин, смежных с вершиной v
        for (int i = 0; i < GraphT[v].size(); ++i) {
            int w = GraphT[v].get(i);
            ccs(w, componentID); //вызов обхода в глубину от вершины w, смежной с вершиной v в транспонированном графе
        }
    }

    //ввод данных с консоли
    private void getData() throws IOException {
        cin = new BufferedReader(new InputStreamReader(System.in));
        cout = new PrintWriter(System.out);

        tokenizer = new StringTokenizer(cin.readLine());
        numV = Integer.parseInt(tokenizer.nextToken()); //считываем количество вершин графа
        numE = Integer.parseInt(tokenizer.nextToken()); //считываем количество ребер графа

        //инициализация списка инцидентности графа и транспонированного графа
        Graph = new ArrayList[numV];
        GraphT = new ArrayList[numV];
        for (int i = 0; i < numV; ++i) {
            Graph[i] = new ArrayList<Integer>();
            GraphT[i] = new ArrayList<Integer>();
        }

        //считываем граф, заданный списком ребер
        for (int i = 0; i < numE; ++i) {
            tokenizer = new StringTokenizer(cin.readLine());
            int v = Integer.parseInt(tokenizer.nextToken());
            int w = Integer.parseInt(tokenizer.nextToken());
            v--;
            w--;
            Graph[v].add(w);
            //добавляем обратную дугу в транспонированный граф
            GraphT[w].add(v);
        }

        //помечаем все вершины графа, как непосещенные
        usedV = new boolean[numV];
        Arrays.fill(usedV, false);

        component = new int[numV];
        Arrays.fill(component, 0);

        topSort = new ArrayList<Integer>();
    }

    //вывод результата
    private void printData() throws IOException {

        cout.println(componentNum);
        for (int i = 0; i < numV; ++i) {
            cout.print((component[i] + 1) + " ");
        }
        cout.println();

        cin.close();
        cout.close();
    }

    private void run() throws IOException {
        getData();
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
                ccs(v, componentID);
                componentID++;
            }
        }
        componentNum = componentID;
        printData();
    }

    public static void main(String[] args) throws IOException {
        Solution solution = new Solution();
        solution.run();
    }
}
