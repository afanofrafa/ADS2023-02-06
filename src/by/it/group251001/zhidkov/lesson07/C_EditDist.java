package by.it.group251001.zhidkov.lesson07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Задача на программирование: расстояние Левенштейна
    https://ru.wikipedia.org/wiki/Расстояние_Левенштейна
    http://planetcalc.ru/1721/
Дано:
    Две данных непустые строки длины не более 100, содержащие строчные буквы латинского алфавита.
Необходимо:
    Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ
    Итерационно вычислить алгоритм преобразования двух данных непустых строк
    Вывести через запятую редакционное предписание в формате:
     операция("+" вставка, "-" удаление, "~" замена, "#" копирование)
     символ замены или вставки
    Sample Input 1:
    ab
    ab
    Sample Output 1:
    #,#,
    Sample Input 2:
    short
    ports
    Sample Output 2:
    -s,~p,#,#,#,+s,
    Sample Input 3:
    distance
    editing
    Sample Output 2:
    +e,#,#,-s,#,~i,#,-c,~g,
    P.S. В литературе обычно действия редакционных предписаний обозначаются так:
    - D (англ. delete) — удалить,
    + I (англ. insert) — вставить,
    ~ R (replace) — заменить,
    # M (match) — совпадение.
*/


public class C_EditDist {
    // Функция для определения замены символа (равенство или несоответствие)
    int m(int i0, int j0, String s1, String s2) {
        i0--;
        j0--;
        if (s1.charAt(i0) == s2.charAt(j0)) {
            return 0;
        } else {
            return 1;
        }
    }

    // Функция для определения минимального значения из трех чисел
    int min(int n1, int n2, int n3) {
        if (n1 > n2) {
            n1 = n2;
        }
        if (n1 > n3) {
            n1 = n3;
        }
        return n1;
    }

    // Метод для вычисления редакционного предписания (расстояния Левенштейна) между двумя строками
    String getDistanceEdinting(String one, String two) {
        int n = one.length();
        int m = two.length();
        int[][] matrix = new int[n + 1][m + 1]; // Инициализация матрицы для хранения расстояний

        // Инициализация матрицы
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                if ((i == 0) && (j == 0)) {
                    matrix[i][j] = 0; // Начальное значение: нулевое расстояние между пустыми строками
                } else if (j == 0) {
                    matrix[i][j] = i; // Нулевое расстояние от пустой строки до строки one
                } else if (i == 0) {
                    matrix[i][j] = j; // Нулевое расстояние от пустой строки до строки two
                } else {
                    // Обновление значения ячейки matrix[i][j] в матрице Левенштейна.
                    // Выбирается минимальное из трех вариантов:
                    // 1. matrix[i][j - 1] + 1: Вставка символа в строку one.
                    // 2. matrix[i - 1][j] + 1: Удаление символа из строки one.
                    // 3. matrix[i - 1][j - 1] + m(i, j, one, two): Замена символа, если символы не равны (m возвращает 1), или совпадение (m возвращает 0).
                    matrix[i][j] = min(matrix[i][j - 1] + 1, matrix[i - 1][j] + 1, matrix[i - 1][j - 1] + m(i, j, one, two));
                    // Заполнение ячейки матрицы в соответствии с алгоритмом Левенштейна
                }
            }
        }

        StringBuilder result = new StringBuilder(); // Создание StringBuilder для хранения редакционного предписания
        int i = n, j = m; // Начальные значения индексов

        // Построение редакционного предписания в обратном порядке
        while (i > 0 && j > 0) {
            if (one.charAt(i - 1) == two.charAt(j - 1)) {
                result.insert(0, "#" + ","); // Совпадение символов
                i--;
                j--;
            } else if (matrix[i][j] == matrix[i - 1][j - 1] + 1) {
                result.insert(0, "~" + two.charAt(j - 1) + ","); // Замена символа
                i--;
                j--;
            } else if (matrix[i][j] == matrix[i - 1][j] + 1) {
                result.insert(0, "-" + one.charAt(i - 1) + ","); // Удаление символа
                i--;
            } else if (matrix[i][j] == matrix[i][j - 1] + 1) {
                result.insert(0, "+" + two.charAt(j - 1) + ","); // Вставка символа
                j--;
            }
        }

        return result.toString(); // Возвращение строки с редакционным предписанием
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson07/dataABC.txt");
        C_EditDist instance = new C_EditDist();
        Scanner scanner = new Scanner(stream);

        // Вывод результатов расчета расстояния Левенштейна для трех пар строк
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
    }
}