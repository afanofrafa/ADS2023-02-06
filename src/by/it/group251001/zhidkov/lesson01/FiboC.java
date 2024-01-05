package by.it.group251001.zhidkov.lesson01;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m.
 * Время расчета должно быть не более 2 секунд.
 */

public class FiboC {

    // Засекаем начальное время выполнения программы
    private final long startTime = System.currentTimeMillis();

    // Метод для получения времени выполнения программы
    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {
        // Создаем объект класса FiboC
        FiboC fibo = new FiboC();

        // Задаем значения переменных n и m
        int n = 999999999;
        int m = 321;

        // Выводим результат работы метода fasterC и время выполнения программы
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        // Решение использует период Пизано для оптимизации вычислений
        // Период Пизано - это периодичность остатков от деления чисел Фибоначчи на m

        // Инициализируем массив для хранения остатков
        long[] Arr = new long[10 * m];

        // Задаем начальные значения остатков для чисел Фибоначчи
        Arr[0] = 0;
        Arr[1] = 1;
        Arr[2] = 1;

        // Индекс, используемый для заполнения массива
        int i = 3;

        // Флаг для определения конца периода Пизано
        boolean flag = true;

        // Заполняем массив остатками от деления чисел Фибоначчи на m
        while (flag) {
            Arr[i] = (Arr[i-1] + Arr[i-2]) % m;

            // Проверяем, достигнут ли конец периода Пизано
            if (Arr[i-1] == Arr[0] && Arr[i] == Arr[1]) {
                flag = false;
            } else {
                i++;
            }
        }

        // Находим остаток от деления n-го числа Фибоначчи на m с использованием Периода Пизано
        long num = n % (i - 1);

        return Arr[(int)num];
    }
}
