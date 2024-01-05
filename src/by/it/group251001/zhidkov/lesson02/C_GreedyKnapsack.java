package by.it.group251001.zhidkov.lesson02;
/*
Даны
1) объем рюкзака 4
2) число возможных предметов 60
3) сам набор предметов
    100 50
    120 30
    100 50
Все это указано в файле (by/it/a_khmelev/lesson02/greedyKnapsack.txt)

Необходимо собрать наиболее дорогой вариант рюкзака для этого объема
Предметы можно резать на кусочки (т.е. алгоритм будет жадным)
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class C_GreedyKnapsack {
    // Класс предмета
    private static class Item implements Comparable<Item> {
        int cost;  // стоимость предмета
        int weight;  // вес предмета

        // Конструктор класса Item
        Item(int cost, int weight) {
            this.cost = cost;
            this.weight = weight;
        }

        // Переопределение метода toString для удобного вывода информации о предмете
        @Override
        public String toString() {
            return "Item{" +
                    "cost=" + cost +
                    ", weight=" + weight +
                    '}';
        }

        // Реализация метода compareTo интерфейса Comparable для сравнения предметов
        @Override
        public int compareTo(Item other) {
            // Реализация метода compareTo интерфейса Comparable
            // для сравнения предметов по стоимости на единицу веса.

            // Рассчитываем стоимость на единицу веса текущего предмета
            double costPerUnitOfWeight = cost / (double) weight;

            // Рассчитываем стоимость на единицу веса другого предмета
            double costPerUnitOfWeightOther = other.cost / (double) other.weight;

            // Сравниваем стоимость на единицу веса двух предметов
            // Используем Double.compare для устранения проблем с плавающей точкой
            return Double.compare(costPerUnitOfWeightOther, costPerUnitOfWeight);
        }

    }

    // Метод для расчета общей стоимости рюкзака
    double calc(File source) throws FileNotFoundException {
        // Создаем объект Scanner для чтения данных из файла
        Scanner input = new Scanner(source);

        // Считываем количество предметов и вместимость рюкзака
        int n = input.nextInt();
        int W = input.nextInt();

        // Создаем массив предметов
        Item[] items = new Item[n];

        // Заполняем массив предметов данными из файла
        for (int i = 0; i < n; i++) {
            items[i] = new Item(input.nextInt(), input.nextInt());
        }

        // Выводим информацию о предметах
        for (Item item : items) {
            System.out.println(item);
        }
        System.out.printf("Всего предметов: %d. Рюкзак вмещает %d кг.\n", n, W);

        // Сортируем массив предметов по стоимости на единицу веса
        Arrays.sort(items);

        // Инициализируем переменные для хранения результата и текущего веса
        double result = 0;
        int weightSoFar = 0;
        int current = 0;

        // Жадный алгоритм: пока есть предметы и рюкзак не заполнен
        while (current < items.length && weightSoFar != W) {
            if (weightSoFar + items[current].weight < W) {
                // Берем предмет целиком, если его вес не превышает оставшуюся вместимость рюкзака
                result += items[current].cost;
                weightSoFar += items[current].weight;
            } else {
                // Берем предмет частично, если его вес превышает оставшуюся вместимость рюкзака
                result += ((W - weightSoFar) / (double) items[current].weight) * items[current].cost;
                weightSoFar = W;  // Рюкзак заполнился
            }
            current++;  // Переходим к следующему предмету
        }

        // Выводим результат
        System.out.printf("Удалось собрать рюкзак на сумму %f\n", result);
        return result;
    }

    // Точка входа в программу
    public static void main(String[] args) throws FileNotFoundException {
        // Засекаем время начала выполнения программы
        long startTime = System.currentTimeMillis();

        // Определяем путь к файлу с данными
        String root = System.getProperty("user.dir");
        Path filePath = FileSystems.getDefault().getPath(root, "src", "by", "it", "a_khmelev", "lesson02", "greedyKnapsack.txt");
        File f = filePath.toFile();

        // Создаем объект класса C_GreedyKnapsack и вызываем метод calc для расчета стоимости рюкзака
        double costFinal = new C_GreedyKnapsack().calc(f);

        // Засекаем время завершения выполнения программы
        long finishTime = System.currentTimeMillis();

        // Выводим общую стоимость и время выполнения программы
        System.out.printf("Общая стоимость %f (время %d)", costFinal, finishTime - startTime);
    }
}

