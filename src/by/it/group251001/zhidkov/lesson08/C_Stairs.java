package by.it.group251001.zhidkov.lesson08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Даны число 1<=n<=100 ступенек лестницы и
целые числа −10000<=a[1],…,a[n]<=10000, которыми помечены ступеньки.
Найдите максимальную сумму, которую можно получить, идя по лестнице
снизу вверх (от нулевой до n-й ступеньки), каждый раз поднимаясь на
одну или на две ступеньки.

Sample Input 1:
2
1 2
Sample Output 1:
3

Sample Input 2:
2
2 -1
Sample Output 2:
1

Sample Input 3:
3
-1 2 1
Sample Output 3:
3

*/

// Объявление класса C_Stairs
public class C_Stairs {
    // Метод для вычисления максимальной суммы при подъеме по лестнице
    int getMaxSum(InputStream stream) {
        // Чтение входных данных
        Scanner scanner = new Scanner(stream);
        int n = scanner.nextInt(); // Число ступенек лестницы
        int[] stairs = new int[n]; // Числа, которыми помечены ступеньки

        // Заполнение массива stairs значениями из входных данных
        for (int i = 0; i < n; i++) {
            stairs[i] = scanner.nextInt();
        }

        // Создаем массив sum размером n + 1, где sum[i] будет содержать максимальную сумму до ступеньки i.
        int[] sum = new int[n + 1];

        // Инициализация базовых случаев:
        sum[0] = 0; // Максимальная сумма для подъема на нулевую ступеньку равна 0.
        sum[1] = stairs[0]; // Максимальная сумма для подъема на первую ступеньку равна значению первой ступеньки.

        // Заполняем массив sum методом динамического программирования.
        for (int i = 2; i <= n; i++) {
            // Вычисляем максимальную сумму для ступеньки i, выбирая максимум из двух вариантов:
            // 1. Поднимаемся на одну ступеньку (stairs[i - 1]) и прибавляем максимальную сумму до предыдущей ступеньки (sum[i - 1]).
            // 2. Поднимаемся на две ступеньки (stairs[i - 1]) и прибавляем максимальную сумму до ступеньки, находящейся на две ступеньки ниже (sum[i - 2]).
            sum[i] = Math.max(stairs[i - 1] + sum[i - 1], stairs[i - 1] + sum[i - 2]);
        }


        return sum[n]; // Возвращение максимальной суммы при подъеме по лестнице
    }

    // Метод main для запуска программы
    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson08/dataC.txt");
        C_Stairs instance = new C_Stairs();
        int res = instance.getMaxSum(stream);
        System.out.println(res); // Вывод результата
    }
}

