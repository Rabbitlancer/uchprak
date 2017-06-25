<<<<<<< HEAD
=======
package com.example.TwoClasses;

>>>>>>> c14d5b6112b2cfcb9b9b5066daec1616a6271e92
//import путь.к.классу.ИмяКласса;
//import com.example.TwoClasses.InputOutput;
//import java.io.BufferedReader;      //считывает текст из символьного потока ввода, буферизируя прочитанные символы
import java.io.IOException;         //класс исключения ввода-вывода
//import java.io.InputStreamReader;   //мост от потоков байтов до символьных потоков: читает байты и декодирует их в символы
//import java.io.PrintWriter;         //для вывода информации на консоль, в файл или любой другой поток вывода
import java.util.ArrayList;         //автоматически расширяемый массив
import java.util.Arrays;            //для работы с массивами
//import java.util.StringTokenizer;   //для разбиения входящей строки на слова


//похоже, основная проблема - передача графа в методы. NullPtrException возникает при работе с полученным в качестве аргумента графом
public class Algorithm {

    //список инцидентности транспонированного орграфа (массив списков)
    private MyGraph GraphT;
    //массив для хранения информации о пройденных и не пройденных вершинах
    private boolean usedV[];
    //топологически упорядоченная перестановка номеров вершин графа
    ArrayList<Integer> topSort;
<<<<<<< HEAD
    //  int componentNum; //количество компонент связности в орграфе
=======
  //  int componentNum; //количество компонент связности в орграфе
>>>>>>> c14d5b6112b2cfcb9b9b5066daec1616a6271e92

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

    // метод транспонирования графа
    /*private ArrayList[] Transpose(MyGraph Graph){
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
    }*/

    //Чтобы узнать, есть в массиве какой-либо элемент, можно воспользоваться методом contains(),
    // который вернёт логическое значение true или false в зависимости от присутствия элемента в наборе
<<<<<<< HEAD
    //     System.out.println (list.contains("Картошка") + "");

    public int run(MyGraph Graph) throws IOException {
        //InputOutput.getData(Graph);      //получаем данные
        GraphT = Graph.Transpose(Graph);    //транспонируем граф
=======
       //     System.out.println (list.contains("Картошка") + "");

    public int run(MyGraph Graph) throws IOException {
       //InputOutput.getData(Graph);      //получаем данные
       GraphT = Graph.Transpose(Graph);    //транспонируем граф
>>>>>>> c14d5b6112b2cfcb9b9b5066daec1616a6271e92

        //помечаем все вершины графа, как непосещенные
        usedV = new boolean[Graph.numV];
        Arrays.fill(usedV, false);

        Graph.component = new int[Graph.numV];  //массив для компонент связности
        Arrays.fill(Graph.component, 0);  //заполняем нулями

        topSort = new ArrayList<Integer>();

        //запускаем обход в глубину для расчета времени выхода каждой вершины
        for (int v = 0; v < Graph.numV; ++v) {
            dfs(v,Graph.IncidList);
        }

        //запускаем поиск компонент сильной связности в порядке уменьшения времени выхода вершин
        Arrays.fill(usedV, false);
        int componentID = 0;
        for (int i = topSort.size() - 1; i >= 0; --i) {
            int v = topSort.get(i);
            if (!usedV[v]) {
                ccs(v, componentID,Graph.component);
                componentID++;
            }
        }
        int componentNum = componentID;
<<<<<<< HEAD
        //  InputOutput.printData(componentNum, numV, component);
=======
      //  InputOutput.printData(componentNum, numV, component);
>>>>>>> c14d5b6112b2cfcb9b9b5066daec1616a6271e92
        return componentNum;        //!!!
    }
}


