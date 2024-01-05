package by.it.group251001.zhidkov.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Видеорегистраторы и площадь 2.
Условие то же что и в задаче А.

        По сравнению с задачей A доработайте алгоритм так, чтобы
        1) он оптимально использовал время и память:
            - за стек отвечает элиминация хвостовой рекурсии *
            - за сам массив отрезков - сортировка на месте
            - рекурсивные вызовы должны проводиться на основе 3-разбиения

        2) при поиске подходящих отрезков для точки реализуйте метод бинарного поиска
        для первого отрезка решения, а затем найдите оставшуюся часть решения
        (т.е. отрезков, подходящих для точки, может быть много)

    Sample Input:
    2 3
    0 5
    7 10
    1 6 11
    Sample Output:
    1 0 0

*/


public class C_QSortOptimized {

    //отрезок
    // Внутренний статический класс Segment, реализующий интерфейс Comparable
    private static class Segment implements Comparable<Segment> {
        int start;
        int stop;

        // Конструктор класса, принимающий начальное и конечное значения отрезка
        Segment(int start, int stop) {
            this.start = start;
            this.stop = stop;
        }

        // Реализация метода compareTo интерфейса Comparable
        // Сравнивает отрезки на основе их конечных точек
        @Override
        public int compareTo(Segment o) {
            return this.stop - o.stop;
        }
    }


    int[] getAccessory2(InputStream stream) {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        // Число отрезков отсортированного массива
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        //число точек
        int m = scanner.nextInt();
        // Создаем массив для хранения координат точек
        int[] points = new int[m];
        // Создаем массив для хранения результатов (количество отрезков, содержащих каждую точку)
        int[] result = new int[m];

        // Читаем сами отрезки
        for (int i = 0; i < n; i++) {
            // Читаем начало и конец каждого отрезка
            segments[i] = new Segment(scanner.nextInt(), scanner.nextInt());
        }
        // Читаем точки
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }


        // Вызываем метод оптимизированной быстрой сортировки для массива отрезков
        QSortOpt(segments, 0, segments.length - 1);

// Проходим по каждой точке
        for (int i = 0; i < m; i++) {
            // Ищем индекс первого отрезка, содержащего текущую точку, с помощью бинарного поиска
            int res_bin_search = BinarySearch(segments, points[i], 0, segments.length - 1);

            // Если такой отрезок найден
            if (res_bin_search > -1) {
                // Инициализируем счетчик отрезков, содержащих текущую точку
                int count = 1;

                // Перемещаемся вправо от найденного отрезка и увеличиваем счетчик, если точка принадлежит отрезку
                int j = res_bin_search + 1;
                while (j < n && points[i] <= segments[j].stop) {
                    if (segments[j].start <= points[i])
                        count++;
                    j++;
                }

                // Перемещаемся влево от найденного отрезка и увеличиваем счетчик, если точка принадлежит отрезку
                j = res_bin_search - 1;
                while (j >= 0 && points[i] <= segments[j].stop) {
                    if (segments[j].start <= points[i])
                        count++;
                    j--;
                }

                // Сохраняем результат в массиве result для текущей точки
                result[i] = count;
            } else {
                // Если отрезок не найден, присваиваем результату 0
                result[i] = 0;
            }
        }


        return result;
    }
    // Класс Partition представляет группу элементов, ограниченных левой и правой границами
    public static class Partition {
        int left;   // Левая граница группы элементов
        int right;  // Правая граница группы элементов

        // Конструктор класса Partition
        public Partition() {
        }
    }

    // Метод для создания нового Partition при 3-разбиении
    Partition new_Partition(Segment[] A, int left, int right) {
        int lt = left;           // Индекс, с которого начинается группа элементов, меньших опорного
        int current = left;       // Индекс текущего рассматриваемого элемента
        int gt = right;           // Индекс, с которого начинается группа элементов, больших опорного
        Segment value = A[left];   // Опорный элемент, выбранный в качестве значения для сравнения

        // Цикл 3-разбиения
        while (current <= gt) {
            if (A[current].compareTo(value) < 0) {
                // Обмен элементов, если текущий элемент меньше опорного
                Segment temp = A[current];
                A[current] = A[lt];
                A[lt] = temp;
                lt++;
                current++;
            } else {
                if (A[current].compareTo(value) == 0)
                    current++;
                else {
                    // Обмен элементов, если текущий элемент больше опорного
                    Segment temp = A[current];
                    A[current] = A[gt];
                    A[gt] = temp;
                    gt--;
                }
            }
        }

        return new Partition();  // Возвращаем новый объект Partition с границами групп элементов
    }

    void QSortOpt(Segment[] A, int left, int right) {
        // Основной цикл оптимизированной быстрой сортировки
        while (left < right) {
            // Получение нового Partition
            Partition middlePartition = new_Partition(A, left, right);
            // Рекурсивный вызов для левой части
            QSortOpt(A, left, middlePartition.left - 1);
            // Установка начала следующей итерации для правой части
            left = middlePartition.right + 1;
        }
    }
    // Метод для бинарного поиска
    int BinarySearch(Segment[] A, int key, int left, int right) {
        boolean isFound = false;

        // Цикл бинарного поиска
        while (left <= right && !isFound) {
            int mid = (left + right) / 2;

            // Проверка, в какой половине массива находится искомый элемент
            if (A[mid].start > key)
                right = mid - 1;
            else if (A[mid].stop < key)
                left = mid + 1;
            else {
                // Элемент найден
                isFound = true;
                return mid;
            }
        }

        // Элемент не найден
        return -1;
    }

    public static void main(String[] args) throws FileNotFoundException {
        // Получение пути к текущей директории
        String root = System.getProperty("user.dir") + "/src/";
        // Создание входного потока stream для чтения данных из файла dataC.txt
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataC.txt");
        // Создание экземпляра класса C_QSortOptimized
        C_QSortOptimized instance = new C_QSortOptimized();
        // Вызов метода getAccessory2 для получения результата
        int[] result = instance.getAccessory2(stream);

        // Вывод результата на экран
        for (int index : result) {
            System.out.print(index + " ");
        }
    }

}