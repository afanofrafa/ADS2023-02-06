package by.it.group251001.zhidkov.lesson14;

import java.util.Scanner;
import java.util.*;
/*
Инициализация DSU:

Создание объекта DisjointSetUnion с массивами parent и rank.
Каждый сайт начинает как отдельная компонента связности.
Обработка ввода:

Каждая пара добавляется в DSU, создавая или объединяя компоненты связности.
Объединение компонент связности (метод unionSets):

Нахождение корней компонент связности для каждого сайта (findSet).
Сравнение рангов (высот) компонент и объединение с учетом ранга:
Если ранг компоненты a меньше ранга компоненты b, то a становится поддеревом b.
Иначе, b становится поддеревом a. Если ранги равны, увеличивается ранг a.
Вычисление размеров кластеров:

Создание массива dsuSizeArray.
Для каждого сайта подсчет размера его компоненты связности (findSet).
Сортировка размеров кластеров:

Сортировка размеров кластеров в порядке возрастания.
Вывод результата:

Вывод размеров кластеров на консоль.
Структура данных DSU (Disjoint Set Union):

parent - массив, хранящий представителя (корень) компоненты связности для каждого сайта.
rank - массив, хранящий ранг (высоту) поддеревьев.
Оптимизации:

Использование эвристик по рангу и сжатию пути в DSU для улучшения эффективности операций.
 */
public class SitesB {

    private static class DisjointSetUnion {
        int[] parent;
        int[] rank;

        DisjointSetUnion(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                makeSet(i);
            }
        }

        public void makeSet(int v) {
            parent[v] = v;
            rank[v] = 0;
        }

        int findSet(int v) {
            if (v == parent[v])
                return v;
            return parent[v] = findSet(parent[v]);
        }

        void unionSets(int a, int b) {
            a = findSet(a);
            b = findSet(b);
            if (a != b) {
                if (rank[a] < rank[b]) {
                    int temp = a;
                    a = b;
                    b = temp;
                }
                parent[b] = a;
                if (rank[a] == rank[b])
                    rank[a]++;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] points = new String[1000];
        Arrays.fill(points, "");

        DisjointSetUnion dsu = new DisjointSetUnion(1000);

        String line = scanner.next();
        while (!line.equals("end")) {
            String[] pair = line.split("\\+");
            int x1 = -1;
            for (int i = 0; i < points.length; i++) {
                if (points[i].equals(pair[0])) {
                    x1 = i;
                    break;
                } else if (points[i].equals("")) {
                    x1 = i;
                    points[i] = pair[0];
                    dsu.makeSet(x1);
                    break;
                }
            }

            int x2 = -1;
            for (int i = 0; i < points.length; i++) {
                if (points[i].equals(pair[1])) {
                    x2 = i;
                    break;
                } else if (points[i].equals("")) {
                    x2 = i;
                    points[i] = pair[1];
                    dsu.makeSet(x2);
                    break;
                }
            }

            dsu.unionSets(x1, x2);
            line = scanner.next();
        }

        int maxSize = points.length;
        int[] dsuSizeArray = new int[maxSize];
        for (int i = 0; i < maxSize; i++) {
            if (!points[i].equals("")) {
                dsuSizeArray[dsu.findSet(i)]++;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxSize; i++) {
            int max = i;
            for (int j = i + 1; j < maxSize; j++) {
                if (dsuSizeArray[max] < dsuSizeArray[j]) {
                    max = j;
                }
            }
            if (dsuSizeArray[max] == 0) {
                break;
            }
            int temp = dsuSizeArray[max];
            dsuSizeArray[max] = dsuSizeArray[i];
            dsuSizeArray[i] = temp;
            sb.append(dsuSizeArray[i]);
            sb.append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb);
    }
}

