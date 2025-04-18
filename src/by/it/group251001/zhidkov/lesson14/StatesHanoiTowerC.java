package by.it.group251001.zhidkov.lesson14;

import java.util.Arrays;
import java.util.Scanner;
/*
Создание DSU:
Создается структура данных DSU, где каждый шаг начальной конфигурации Ханойских башен представляет собой отдельный элемент в DSU.

Итерации по Возможным Шагам:
Проходятся все возможные шаги Ханойских башен.
Если два шага могут быть достигнуты друг из друга, они объединяются в одну компоненту связности в DSU.

Размеры Кластеров:
После обработки всех шагов, размеры кластеров (количество шагов в каждом кластере) выводятся в порядке убывания.
 */
public class StatesHanoiTowerC {

    // Определение вложенного статического класса DisjointSetUnion (DSU)
    private static class DisjointSetUnion {
        // Массив для хранения родительских элементов
        int[] parent;
        // Массив для хранения размеров компонент связности
        int[] size;

        // Конструктор класса, принимающий размер структуры данных DSU
        DisjointSetUnion(int size) {
            // Инициализация массивов parent и size с заданным размером
            this.parent = new int[size];
            this.size = new int[size];
        }

        // Метод makeSet устанавливает начальные значения для нового элемента
        void makeSet(int v) {
            parent[v] = v;  // Каждый элемент начинает себя как свой собственный родитель
            size[v] = 1;    // Начальный размер компоненты связности равен 1
        }

        // Метод size возвращает размер компоненты связности, к которой принадлежит элемент v
        int size(int v) {
            return size[findSet(v)];  // Размер определяется размером родительской компоненты
        }

        // Метод findSet возвращает представителя (родителя) компоненты связности, к которой принадлежит элемент v
        int findSet(int v) {
            // Если v - это корень своей компоненты, возвращаем v
            return (v == parent[v]) ? v : (parent[v] = findSet(parent[v]));
            // Иначе, рекурсивно обновляем родителя элемента v и возвращаем его
        }

        // Метод unionSets объединяет две компоненты связности, к которым принадлежат элементы a и b
        void unionSets(int a, int b) {
            a = findSet(a);  // Находим представителя компоненты связности элемента a
            b = findSet(b);  // Находим представителя компоненты связности элемента b
            if (a != b) {     // Если a и b принадлежат к разным компонентам связности
                // Случай, когда размер компоненты a меньше размера компоненты b
                if (size[a] < size[b]) {
                    int temp = a;
                    a = b;
                    b = temp;
                }
                // Объединяем компоненты связности, делая b поддеревом a
                parent[b] = a;
                size[a] += size[b];
            }
        }
    }


    // Метод max возвращает максимальное значение из трех высот
    static int max(int[] heights) {
        return Math.max(Math.max(heights[0], heights[1]), heights[2]);
    }

    static int[] carryingOver(int N, int step, int k) {
        // Объявление переменных для индексов осей
        int t, axisX, axisY, axisZ;

// Проверка четности переменной N
        if (N % 2 == 0) {
            // Если N четное, устанавливаем оси в порядке 0, 1, 2
            axisX = 0;
            axisY = 1;
            axisZ = 2;
        } else {
            // Если N нечетное, устанавливаем оси в порядке 0, 2, 1
            axisX = 0;
            axisY = 2;
            axisZ = 1;
        }

// Создание массива для хранения результата
        int[] result = new int[3];

// Вычисление значения переменной t
        t = (step / (1 << (k - 1)) - 1) / 2;

// Инициализация переменных from и to
        int from = 0, to = 0;


        // Проверка на четность переменной k
        if (k % 2 != 0) {
            // Если k нечетное, выбираем оси в зависимости от значения t по модулю 3
            switch (t % 3) {
                case 0:
                    // Если t % 3 == 0, присваиваем значения осей X и Y переменным from и to
                    from = axisX;
                    to = axisY;
                    break;
                case 1:
                    // Если t % 3 == 1, присваиваем значения осей Y и Z переменным from и to
                    from = axisY;
                    to = axisZ;
                    break;
                case 2:
                    // Если t % 3 == 2, присваиваем значения осей Z и X переменным from и to
                    from = axisZ;
                    to = axisX;
                    break;
            }
        } else {
            // Если k четное, выбираем оси в зависимости от значения t по модулю 3
            switch (t % 3) {
                case 0:
                    // Если t % 3 == 0, присваиваем значения осей X и Z переменным from и to
                    from = axisX;
                    to = axisZ;
                    break;
                case 1:
                    // Если t % 3 == 1, присваиваем значения осей Z и Y переменным from и to
                    from = axisZ;
                    to = axisY;
                    break;
                case 2:
                    // Если t % 3 == 2, присваиваем значения осей Y и X переменным from и to
                    from = axisY;
                    to = axisX;
                    break;
            }
        }

// Присваиваем значения -1 и 1 соответствующим осям в массиве result
        result[from] = -1;
        result[to] = 1;

// Возвращаем результат в виде массива
        return result;

    }

    public static void main(String[] args) {
        // Создаем объект Scanner для ввода данных с консоли
        Scanner scanner = new Scanner(System.in);

// Считываем значение переменной N
        int N = scanner.nextInt();

// Вычисляем максимальный размер массива steps_heights
        int max_size = (1 << N) - 1;

// Создаем объект для хранения набора непересекающихся множеств (DSU) с максимальным размером max_size
        DisjointSetUnion dsu = new DisjointSetUnion(max_size);

// Создаем массив heights для хранения высот башен на каждом шаге
        int[] steps_heights = new int[N];

// Заполняем массив steps_heights значениями -1
        Arrays.fill(steps_heights, -1);

// Создаем массив heights для хранения текущей высоты башен на каждом из трех столбцов
        int[] heights = new int[3];
        heights[0] = N;

// Итерируемся по всем шагам Ханойских башен (от 1 до max_size)
        for (int i = 0; i < max_size; i++) {
            // Вычисляем номер текущего шага
            int step = i + 1;

            // Инициализируем массив delta значениями, определяющими перемещение дисков на текущем шаге
            int[] delta;

            // Проверяем, является ли текущий шаг нечетным
            if (step % 2 != 0) {
                // Если шаг нечетный, вызываем метод carryingOver для определения перемещения дисков
                delta = carryingOver(N, step, 1);
            } else {
                // Если шаг четный, определяем количество дисков, участвующих в текущем шаге
                int count = step;
                int countDisk = 0;

                // Пока количество дисков четное, уменьшаем его и увеличиваем счетчик countDisk
                while (count % 2 == 0) {
                    countDisk++;
                    count /= 2;
                }

                // Вызываем метод carryingOver для определения перемещения дисков с учетом количества
                delta = carryingOver(N, step, countDisk + 1);
            }

            // Обновляем высоты башен после перемещения дисков на текущем шаге
            for (int j = 0; j < 3; j++)
                heights[j] += delta[j];

            // Находим максимальную высоту башни на текущем шаге
            int max = max(heights);

            // Создаем новый набор в DSU для текущего шага
            dsu.makeSet(i);

            // Если в массиве steps_heights для максимальной высоты еще нет значения, добавляем текущий шаг
            if (steps_heights[max - 1] == -1)
                steps_heights[max - 1] = i;
            else
                // Если значение уже есть, объединяем соответствующие наборы в DSU
                dsu.unionSets(steps_heights[max - 1], i);
        }


        // Создаем массив sizes для хранения размеров кластеров (количество шагов в каждом кластере)
        int[] sizes = new int[N];

// Итерируемся по всем возможным высотам башен
        for (int i = 0; i < N; i++)
            // Проверяем, были ли шаги на данной высоте
            if (steps_heights[i] != -1)
                // Заполняем массив sizes значениями размеров соответствующих кластеров
                sizes[i] = dsu.size(steps_heights[i]);

// Создаем объект StringBuilder для построения строки вывода
        StringBuilder sb = new StringBuilder();

// Итерируемся по всем возможным размерам кластеров (в порядке убывания)
        for (int i = 0; i < N; i++) {
            int max = i;
            //Кластер в данном контексте - это группа шагов, которые могут быть достигнуты друг из друга
            // Находим максимальный размер кластера
            for (int j = i + 1; j < N; j++)
                if (sizes[max] < sizes[j])
                    max = j;

            // Если размер кластера равен 0, завершаем итерацию
            if (sizes[max] == 0)
                break;

            // Меняем местами размеры кластеров
            int temp = sizes[max];
            sizes[max] = sizes[i];
            sizes[i] = temp;

            // Добавляем текущий размер кластера в начало строки
            sb.insert(0, sizes[i] + " ");
        }

// Удаляем последний пробел из строки
        sb.deleteCharAt(sb.length() - 1);

// Выводим строку на консоль
        System.out.println(sb);

    }
}