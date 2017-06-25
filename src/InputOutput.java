package com.example.TwoClasses;

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

    private int numV; //количество вершин в орграфе
    private int numE; //количество дуг в орграфе
    //список инцидентности орграфа
    private ArrayList<Integer> Graph[];
    private int[] component; //массив компонент сильной связности графа
    int componentNum; //количество компонент связности в орграфе


    //ввод данных с консоли
    public ArrayList[] getData(ArrayList<Integer>[] Graph) throws IOException {//передаем ссылку на граф, в который надо считать данные,
        cin = new BufferedReader(new InputStreamReader(System.in));
        cout = new PrintWriter(System.out);

        tokenizer = new StringTokenizer(cin.readLine());
        numV = Integer.parseInt(tokenizer.nextToken()); //считываем количество вершин графа
        numE = Integer.parseInt(tokenizer.nextToken()); //считываем количество ребер графа

        //инициализация списка инцидентности графа
        Graph = new ArrayList[numV];
        for (int i = 0; i < numV; ++i) {
            Graph[i] = new ArrayList<Integer>();
        }
        //считываем граф, заданный списком ребер
        for (int i = 0; i < numE; ++i) {
            tokenizer = new StringTokenizer(cin.readLine());
            int v = Integer.parseInt(tokenizer.nextToken());
            int w = Integer.parseInt(tokenizer.nextToken());
            v--;
            w--;
            Graph[v].add(w);
        }
        return Graph;
    }

    //вывод результата.   передаем в метод кол-во компонент связности, кол-во вершин, массив компонет сявзности - данные, которые надо вывести
    public void printData(int componentNum,int numV,int[] component) throws IOException {

        cout.println(componentNum);
        for (int i = 0; i < numV; ++i) {
            //РАЗОБРАТЬСЯ, ПОЧЕМУ НЕ ВЫВОДИТ КОМПОНЕНТЫ СВЯЗНОСТИ!!! (проблема с передачей массива component)
//            cout.print((component[i] + 1) + " ");
            cout.print("c");
        }
        cout.println();

        cin.close();
        cout.close();
    }

    //Статические методы/свойства классов - это такие методы/свойства, к которым можно обратиться не создавая объект данного класса.
    public static void main(String[] args) throws IOException {
        InputOutput myIO = new InputOutput();       //создаем объект класса ввода-вывода
        myIO.Graph = myIO.getData(myIO.Graph);    //считываем данные в граф (граф - свойство нашего класса ввода-вывода)
        Algorithm solution = new Algorithm();   //создаем объект класса алгоритм
        myIO.componentNum = solution.run(myIO.Graph,myIO.numV, myIO.numE,myIO.component);   //алгоритм поиска CCS возвращает нам кол-во компонент связности

        //теперь искл вылетает только тут: (nullptrexc)
        myIO.printData(myIO.componentNum, myIO.numV,myIO.component);    //вывод результатов (в аргументах - те данные, которые надо вывести)
    }
}

