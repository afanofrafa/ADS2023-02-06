package by.it.group251001.zhidkov.lesson13;
import java.util.*;
import java.util.Map.Entry;

public class GraphC {
    static Integer currTime = 0;
// Статическая переменная для отслеживания текущего времени в процессе DFS.

    private static void dfsTime(String node, Map<String, List<String>> graph, Set<String> visited, Map<String, Integer> time) {
// Функция DFS для расчета времени завершения для каждой вершины в ориентированном графе.
        visited.add(node);
        // Помечаем текущую вершину как посещенную.

        if (graph.get(node) != null)
            for (String next_node : graph.get(node)) {
                if (!visited.contains(next_node)) {
                    currTime++;
                    dfsTime(next_node, graph, visited, time);
                }
            }
        // Итерируем по смежным вершинам текущей вершины.
        // Если смежная вершина не посещена, увеличиваем currTime и рекурсивно вызываем dfsTime.

        time.put(node, currTime++);
        // После посещения всех смежных вершин, присваиваем текущей вершине время завершения (currTime) и увеличиваем currTime еще раз.
    }


    private static void dfs(String node, Map<String, List<String>> graph, Set<String> visited, List<String> path) {
// Функция DFS для поиска пути в глубину в ориентированном графе.
        visited.add(node);
        // Помечаем текущую вершину как посещенную.

        path.add(node);
        // Добавляем текущую вершину в путь.

        if (graph.get(node) != null)
            for (String next_node : graph.get(node)) {
                if (!visited.contains(next_node)) {
                    dfs(next_node, graph, visited, path);
                }
            }
        // Итерируем по смежным вершинам текущей вершины.
        // Если смежная вершина не посещена, рекурсивно вызываем dfs.
    }


    static class MapEntryComparator implements Comparator<Map.Entry<String, Integer>> {
        // Компаратор для сравнения записей (ключ, значение) карты по значению, а затем по ключу.

        @Override
        public int compare(Entry<String, Integer> entry1, Entry<String, Integer> entry2) {
            // Переопределенный метод сравнения.

            int valueComparison = entry1.getValue().compareTo(entry2.getValue());
            // Сравниваем значения.

            if (valueComparison == 0) {
                return entry2.getKey().compareTo(entry1.getKey());
                // Если значения равны, сравниваем ключи в обратном порядке.
            }

            return valueComparison;
            // Возвращаем результат сравнения значений.
        }
    }


    public static void main(String[] args) {
        Map<String, List<String>> graph = new HashMap<>();
// Исходный граф.

        Map<String, List<String>> graphReversed = new HashMap<>();
// Обратный граф.

        Set<String> visited = new HashSet<>();
// Множество посещенных вершин.

        Map<String, Integer> time = new HashMap<>();
// Время посещения вершин.

        try (Scanner scanner = new Scanner(System.in)) {
            // Используем try-with-resources для автоматического закрытия Scanner.

            String input = scanner.nextLine();
            // Считываем ввод.

            String[] nodes = input.split("\\s*,\\s*");
            // Разбиваем ввод на вершины.

            for (String node : nodes) {
                // Обрабатываем каждую вершину.

                String[] vertexes = node.split("\\s*->\\s*");
                // Разбиваем вершину на начальную и конечную.

                if (graph.containsKey(vertexes[0])) {
                    graph.get(vertexes[0]).add(vertexes[1]);
                    // Если вершина уже есть в графе, добавляем конечную вершину.
                } else {
                    List<String> list = new ArrayList<>();
                    list.add(vertexes[1]);
                    graph.put(vertexes[0], list);
                    // Если вершина новая, создаем новый список и добавляем в граф.
                }

                if (graphReversed.containsKey(vertexes[1])) {
                    graphReversed.get(vertexes[1]).add(vertexes[0]);
                    continue;
                    // Если обратная вершина уже есть, добавляем начальную вершину.
                }

                List<String> list = new ArrayList<>();
                list.add(vertexes[0]);
                graphReversed.put(vertexes[1], list);
                // Если обратной вершины нет, создаем новый список и добавляем в обратный граф.
            }
        }

        for (String node : graph.keySet()) {
            // Проходим по всем вершинам графа.

            if (!visited.contains(node)) {
                dfsTime(node, graph, visited, time);
                // Если вершина не посещена, запускаем обход в глубину.
            }
        }

        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(time.entrySet());
// Создаем список записей из мапы времени.

        entryList.sort(new MapEntryComparator());
// Сортируем список по времени.

        String[] vertices = new String[entryList.size()];
// Создаем массив для отсортированных вершин.

        for (int i = entryList.size() - 1; i > -1; i--) {
            vertices[entryList.size() - 1 - i] = entryList.get(i).getKey();
            // Заполняем массив отсортированными вершинами в обратном порядке.
        }


        visited = new HashSet<>();
// Сбрасываем посещенные вершины перед следующим этапом.

        for (String node : vertices) {
            // Проходим по отсортированным вершинам.

            if (!visited.contains(node)) {
                List<String> path = new ArrayList<>();
                // Создаем список для хранения пути обхода.

                dfs(node, graphReversed, visited, path);
                // Запускаем обход в глубину для текущей вершины.

                path.sort(CharSequence::compare);
                // Сортируем путь обхода.

                for (String n : path) {
                    System.out.print(n);
                    // Выводим вершины в порядке обхода.
                }

                System.out.println();
                // Печатаем новую строку после завершения обхода для следующей компоненты связности.
            }
        }

    }
}