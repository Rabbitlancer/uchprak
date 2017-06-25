package com.example.TwoClasses;

import java.util.ArrayList;

public class MyGraph {
    public ArrayList<Integer> IncidList[];  //список инцидентности графа
    public int numV;    //кол-во вершин
    public int numE;    //кол-во ребер
    public int[] component; //массив компонент сильной связности графа
    int componentNum; //количество компонент связности в орграфе

    // метод транспонирования графа
    public MyGraph Transpose(MyGraph Graph){
        MyGraph GraphT = new MyGraph();
        GraphT.IncidList = new ArrayList[Graph.numV];
        for (int i = 0; i < Graph.numV; ++i) {
            GraphT.IncidList[i] = new ArrayList<Integer>();       //создали транспонированный граф (пустой)
        }
        for (int i = 0; i < Graph.numV; ++i) {    //проходим по всем спискам
            for (int j = 0; j < Graph.IncidList[i].size(); ++j) {   //проходим по всем элементам одного (текущего)списка
                int v = Graph.IncidList[i].get(j);
                GraphT.IncidList[v].add(i);       //добавляем обратную дугу в транспонированный граф
            }
        }
        return GraphT;
    }
}
