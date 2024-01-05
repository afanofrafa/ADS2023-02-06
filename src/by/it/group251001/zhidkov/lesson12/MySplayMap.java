package by.it.group251001.zhidkov.lesson12;
import java.util.*;
public class MySplayMap implements NavigableMap<Integer, String> {
    // Вложенный класс Node представляет узел дерева с ключом и значением
    // Вложенный класс Node представляет узел в дереве
    static private class Node {
        Integer key;      // Ключ узла
        String value;     // Значение узла
        Node left;        // Левый потомок узла
        Node right;       // Правый потомок узла
        Node parent;      // Родитель узла

        // Конструктор узла, устанавливающий ключ и значение
        public Node(int key, String value) {
            this.key = key;
            this.value = value;
        }
    }


    // Поля класса MySplayMap
    private int size = 0;  // Размер дерева
    private Node root = null;  // Корень дерева

    // Метод Max возвращает узел с максимальным ключом в поддереве, начиная с заданного узла
    private Node Max(Node node) {
        if (node == null) {
            return null;
        }
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    // Метод Min возвращает узел с минимальным ключом в поддереве, начиная с заданного узла
    private Node Min(Node node) {
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // Метод rotateLeft выполняет левый поворот вокруг заданного узла
    private Node rotateLeft(Node node) {
        Node x = node.right;    // Сохраняем ссылку на правого потомка
        node.right = x.left;    // Поднимаем левого потомка правого потомка
        x.left = node;          // Заменяем левого потомка правого потомка на исходный узел
        return x;               // Возвращаем новый корень поддерева
    }

    // Метод rotateRight выполняет правый поворот вокруг заданного узла
    private Node rotateRight(Node node) {
        Node x = node.left;     // Сохраняем ссылку на левого потомка
        node.left = x.right;    // Поднимаем правого потомка левого потомка
        x.right = node;         // Заменяем правого потомка левого потомка на исходный узел
        return x;               // Возвращаем новый корень поддерева
    }



    // Переопределение метода toString для возврата строкового представления дерева
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();   // Создание объекта StringBuilder для построения строки
        res.append("{");                          // Добавление открывающей фигурной скобки
        if (root != null) {                       // Проверка наличия корня в дереве
            toStringHelper(root, res);            // Вызов вспомогательного метода для построения строки
            res.replace(res.length() - 2, res.length(), "}");  // Замена последней запятой на закрывающую фигурную скобку
        } else {
            res.append("}");                       // Добавление закрывающей фигурной скобки, если дерево пустое
        }
        return res.toString();                    // Возврат строки
    }

    // Вспомогательный метод для рекурсивного построения строки представления дерева
    private void toStringHelper(Node node, StringBuilder res) {
        if (node != null) {                       // Проверка, что узел не пустой
            if (node.left != null) {              // Рекурсивный вызов для левого поддерева
                toStringHelper(node.left, res);
            }
            res.append(node.key).append("=").append(node.value).append(", ");  // Добавление ключа и значения узла
            if (node.right != null) {             // Рекурсивный вызов для правого поддерева
                toStringHelper(node.right, res);
            }
        }
    }


    // Возвращает наибольший ключ, который меньше заданного ключа, или null, если такого ключа нет
    @Override
    public Integer lowerKey(Integer key) {
        return LowerKey(root, key).key;
    }

    // Вспомогательный метод для поиска наибольшего ключа, который меньше заданного ключа
    private Node LowerKey(Node node, Integer key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp > 0) {
            // Рекурсивный вызов для правого поддерева, если ключ больше текущего узла
            Node lowerInRight = LowerKey(node.right, key);
            return (lowerInRight != null) ? lowerInRight : node;
        } else {
            if (cmp < 0) {
                // Рекурсивный вызов для левого поддерева, если ключ меньше текущего узла
                return LowerKey(node.left, key);
            } else {
                if (node.left != null) {
                    // Если ключ равен текущему узлу, возвращаем максимальный ключ из левого поддерева
                    return Max(node.left);
                } else {
                    // Возвращаем null, так как нет узлов с ключами меньше заданного
                    return null;
                }
            }
        }
    }


    // Возвращает наибольший ключ, который меньше или равен заданному ключу, или null, если такого ключа нет
    @Override
    public Integer floorKey(Integer key) {
        // Если дерево пусто, возвращаем null
        if (root == null) {
            return null;
        }
        // Находим узел с наибольшим ключом, который меньше или равен заданному ключу
        Node node = FloorKey(root, key);
        if (node != null) {
            // Возвращаем найденный ключ
            return node.key;
        }
        // Если узел не найден, возвращаем null
        return null;
    }

    // Вспомогательный метод для поиска узла с наибольшим ключом, который меньше или равен заданному ключу
    private Node FloorKey(Node node, Integer key) {
        // Если узел пуст, возвращаем null
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp >= 0) {
            // Рекурсивный вызов для правого поддерева, если ключ больше или равен текущего узла
            Node floorInLeft = FloorKey(node.right, key);
            return (floorInLeft != null) ? floorInLeft : node;
        } else {
            // Рекурсивный вызов для левого поддерева, если ключ меньше текущего узла
            return FloorKey(node.left, key);
        }
    }


    // Возвращает наименьший ключ, который больше или равен заданному ключу, или null, если такого ключа нет
    @Override
    public Integer ceilingKey(Integer key) {
        // Возвращаем ключ, найденный с помощью вспомогательного метода
        return CeilingKey(root, key).key;
    }

    // Вспомогательный метод для поиска узла с наименьшим ключом, который больше или равен заданному ключу
    private Node CeilingKey(Node node, Integer key) {
        // Если узел пуст, возвращаем null
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp > 0) {
            // Рекурсивный вызов для правого поддерева, если ключ больше текущего узла
            return CeilingKey(node.right, key);
        } else {
            // Рекурсивный вызов для левого поддерева, если ключ меньше или равен текущему узлу
            Node ceilingInLeft = CeilingKey(node.left, key);
            return ceilingInLeft != null ? ceilingInLeft : node;
        }
    }


    // Возвращает наименьший ключ, который больше заданного ключа, или null, если такого ключа нет
    @Override
    public Integer higherKey(Integer key) {
        // Возвращаем ключ, найденный с помощью вспомогательного метода
        return HigherKey(root, key).key;
    }

    // Вспомогательный метод для поиска узла с наименьшим ключом, который больше заданного ключа
    private Node HigherKey(Node node, Integer key) {
        // Если узел пуст, возвращаем null
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp >= 0) {
            // Рекурсивный вызов для правого поддерева, если ключ больше или равен текущему узлу
            return HigherKey(node.right, key);
        } else {
            // Рекурсивный вызов для левого поддерева, если ключ меньше текущего узла
            Node higherInLeft = HigherKey(node.left, key);
            return higherInLeft != null ? higherInLeft : node;
        }
    }


    // Возвращает поддерево в виде сортированной карты для заданного узла и ключа
    private SortedMap<Integer, String> resMap(Node node, Integer key, boolean isHead) {
        // Создаем новый экземпляр MySplayMap
        MySplayMap Map = new MySplayMap();
        // Вызываем метод subMap для заполнения карты
        subMap(node, key, Map, isHead);
        // Возвращаем заполненную карту
        return Map;
    }

    // Возвращает сортированное поддерево в виде карты для ключей, меньших заданного toKey
    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        // Вызываем resMap для корня дерева и заданного toKey с указанием, что это "голова"
        return resMap(root, toKey, true);
    }

    // Вспомогательный метод для заполнения карты значениями из поддерева
    private void subMap(Node node, Integer key, MySplayMap map, boolean isHead) {
        // Если узел пуст, завершаем рекурсию
        if (node == null) {
            return;
        }
        // Если требуется "голова" (head), добавляем значения из правого поддерева
        if (isHead) {
            if (node.key.compareTo(key) < 0) {
                // Если ключ текущего узла меньше toKey, добавляем его в карту и рекурсивно вызываем для правого поддерева
                map.put(node.key, node.value);
                subMap(node.right, key, map, isHead);
            }
            // Рекурсивно вызываем для левого поддерева в любом случае
            subMap(node.left, key, map, isHead);
        } else {
            // Если не "голова", добавляем значения из левого поддерева
            if (node.key.compareTo(key) >= 0) {
                // Если ключ текущего узла больше или равен toKey, добавляем его в карту и рекурсивно вызываем для левого поддерева
                map.put(node.key, node.value);
                subMap(node.left, key, map, isHead);
            }
            // Рекурсивно вызываем для правого поддерева в любом случае
            subMap(node.right, key, map, isHead);
        }
    }


    // Возвращает сортированное поддерево в виде карты для ключей, больших или равных заданного fromKey
    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        // Вызываем resMap для корня дерева и заданного fromKey с указанием, что это не "голова"
        return resMap(root, fromKey, false);
    }

    // Возвращает первый (наименьший) ключ в дереве
    @Override
    public Integer firstKey() {
        // Возвращаем ключ узла, который является минимальным в дереве
        return Min(root).key;
    }

    // Возвращает последний (наибольший) ключ в дереве
    @Override
    public Integer lastKey() {
        // Возвращаем ключ узла, который является максимальным в дереве
        return Max(root).key;
    }

    // Возвращает текущий размер дерева
    @Override
    public int size() {
        // Возвращаем значение переменной size
        return size;
    }

    // Проверяет, пусто ли дерево
    @Override
    public boolean isEmpty() {
        // Возвращаем результат сравнения size с 0
        return size == 0;
    }

    // Очищает дерево (удаляет все элементы)
    @Override
    public void clear() {
        // Устанавливаем size в 0 и root в null
        size = 0;
        root = null;
    }


    // Проверяет, содержится ли указанный ключ в дереве
    @Override
    public boolean containsKey(Object key) {
        // Используем метод get для получения значения по ключу, проверяем, не является ли результат null
        return get(key) != null;
    }

    // Проверяет, содержится ли указанное значение в дереве
    @Override
    public boolean containsValue(Object value) {
        // Проверяем, является ли значение null или не является ли строкой
        if (value == null || !(value instanceof String)) {
            return false;
        }
        // Используем рекурсивный метод containsValue для проверки наличия значения в дереве
        return containsValue(root, (String) value);
    }

    // Рекурсивно проверяет наличие указанного значения в поддереве, начиная с заданного узла
    public boolean containsValue(Node node, String value) {
        // Если узел равен null, значит, значение не найдено
        if (node == null) {
            return false;
        }
        // Если значение узла соответствует искомому, возвращаем true
        if (node.value.equals(value)) {
            return true;
        }
        // Рекурсивно проверяем правое и левое поддеревья на наличие значения
        return containsValue(root.right, value) || containsValue(root.left, value);
    }



    // Метод splay выполняет операцию splay для узла с указанным ключом
    private Node splay(Node node, Integer key) {
        // Если узел пустой, возвращаем null
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        // Если ключ меньше, выполняем операции для левого поддерева
        if (cmp < 0) {
            // Если левого поддерева нет, возвращаем текущий узел
            if (node.left == null) {
                return node;
            }
            // ZIG-ZIG операция
            if (key.compareTo(node.left.key) < 0) {
                // Рекурсивно выполняем splay для левого левого поддерева
                node.left.left = splay(node.left.left, key);
                // Выполняем правый поворот для текущего узла
                node = rotateRight(node);
            }
            // ZIG-ZAG операция
            else if (key.compareTo(node.left.key) > 0) {
                // Рекурсивно выполняем splay для левого правого поддерева
                node.left.right = splay(node.left.right, key);
                // Если у левого правого поддерева не пустое, выполняем левый поворот для левого поддерева
                if (node.left.right != null) {
                    node.left = rotateLeft(node.left);
                }
            }
            // Возвращаем результат справа
            return (node.left == null) ? node : rotateRight(node);
        }
        // Если ключ больше, выполняем операции для правого поддерева
        else if (cmp > 0) {
            // Если правого поддерева нет, возвращаем текущий узел
            if (node.right == null) {
                return node;
            }
            // ZAG-ZAG операция
            if (key.compareTo(node.right.key) < 0) {
                // Рекурсивно выполняем splay для правого левого поддерева
                node.right.left = splay(node.right.left, key);
                // Если у правого левого поддерева не пустое, выполняем правый поворот для правого поддерева
                if (node.right.left != null) {
                    node.right = rotateRight(node.right);
                }
            }
            // ZAG-ZIG операция
            else if (key.compareTo(node.right.key) > 0) {
                // Рекурсивно выполняем splay для правого правого поддерева
                node.right.right = splay(node.right.right, key);
                // Выполняем левый поворот для текущего узла
                node = rotateLeft(node);
            }
            // Возвращаем результат слева
            return (node.right == null) ? node : rotateLeft(node);
        }
        // Если ключ совпадает, возвращаем текущий узел
        else {
            return node;
        }
    }


    // Получает значение по указанному ключу
    @Override
    public String get(Object key) {
        // Ищем узел с заданным ключом в дереве
        Node node = search(root, (Integer) key);
        // Если узел не найден, возвращаем null
        if (node == null) {
            return null;
        }
        // Выполняем операцию splay для найденного узла
        root = splay(root, node.key);
        // Возвращаем значение найденного узла
        return node.value;
    }

    // Рекурсивно ищет узел с указанным ключом в поддереве
    private Node search(Node node, Integer key) {
        // Если узел пустой, возвращаем null
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        // Если ключ меньше, идем в левое поддерево
        if (cmp < 0) {
            return search(node.left, key);
            // Если ключ больше, идем в правое поддерево
        } else if (cmp > 0) {
            return search(node.right, key);
            // Если ключ совпадает, возвращаем узел
        } else {
            return node;
        }
    }


    // Добавляет элемент с указанным ключом и значением в дерево
    @Override
    public String put(Integer key, String value) {
        // Проверяем наличие ключа
        if (key == null) {
            throw new NullPointerException();
        }
        // Если дерево пустое, создаем корень
        if (root == null) {
            root = new Node(key, value);
            size++;
            return null;
        }
        // Получаем предыдущее значение по ключу
        String prev = get(key);
        // Вставляем новый узел в дерево
        root = insert(root, key, value);
        // Если предыдущего значения не было, увеличиваем размер дерева
        if (prev == null) {
            size++;
        }
        // Возвращаем предыдущее значение
        return prev;
    }

    // Рекурсивно вставляет узел с указанным ключом и значением в поддерево
    private Node insert(Node node, Integer key, String value) {
        // Если узел пустой, создаем новый узел
        if (node == null) {
            return new Node(key, value);
        }
        int cmp = key.compareTo(node.key);
        // Если ключ меньше, идем в левое поддерево
        if (cmp < 0) {
            node.left = insert(node.left, key, value);
            // Если ключ больше, идем в правое поддерево
        } else if (cmp > 0) {
            node.right = insert(node.right, key, value);
            // Если ключ совпадает, обновляем значение узла
        } else {
            node.value = value;
            return node;
        }
        // Выполняем операцию splay для поддерева
        return splay(node, key);
    }


    // Удаляет элемент с указанным ключом из дерева
    @Override
    public String remove(Object key) {
        // Проверяем наличие корня и корректности ключа
        if (root == null || key == null || !(key instanceof Integer)) {
            return null;
        }
        // Выполняем операцию splay для узла с указанным ключом
        root = splay(root, (Integer) key);
        int cmp = ((Integer) key).compareTo(root.key);
        // Если ключи не совпадают, возвращаем null
        if (cmp != 0) {
            return null;
        }
        // Сохраняем значение удаляемого узла
        String removedValue = root.value;
        // Если у удаляемого узла нет левого поддерева
        if (root.left == null) {
            // Устанавливаем в корень правое поддерево
            root = root.right;
            if (root != null) {
                root.parent = null;
            }
        } else {
            // Если есть правое поддерево
            Node newRoot = root.right;
            if (newRoot != null) {
                // Выполняем splay для правого поддерева
                newRoot = splay(newRoot, (Integer) key);
                // Устанавливаем в левое поддерево корня левое поддерево удаляемого узла
                newRoot.left = root.left;
                newRoot.left.parent = newRoot;
                // Обновляем корень
                root = newRoot;
            } else {
                // Если нет правого поддерева, устанавливаем в корень левое поддерево
                root = root.left;
                if (root != null) {
                    root.parent = null;
                }
            }
        }
        // Уменьшаем размер дерева и возвращаем удаленное значение
        size--;
        return removedValue;
    }

    // Рекурсивно удаляет узел с указанным ключом из поддерева
    private Node remove(Node node, Integer key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        // Если ключ больше, идем в правое поддерево
        if (cmp > 0) {
            node.right = remove(node.right, key);
            // Если ключ меньше, идем в левое поддерево
        } else if (cmp < 0) {
            node.left = remove(node.left, key);
        } else {
            // Найден узел для удаления
            if (node.left == null) {
                // Если нет левого поддерева, возвращаем правое поддерево
                return node.right;
            } else if (node.right == null) {
                // Если нет правого поддерева, возвращаем левое поддерево
                return node.left;
            }
            // Если есть оба поддерева
            Node minRight = Min(node.right);
            // Заменяем удаляемый узел на минимальный из правого поддерева
            node.key = minRight.key;
            node.value = minRight.value;
            // Рекурсивно удаляем минимальный узел из правого поддерева
            node.right = remove(node.right, minRight.key);
        }
        return node;
    }


    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

    }


    @Override
    public Set<Integer> keySet() {
        return null;
    }

    @Override
    public Collection<String> values() {
        return null;
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        return null;
    }

    @Override
    public Entry<Integer, String> floorEntry(Integer key) {
        return null;
    }

    @Override
    public Entry<Integer, String> lowerEntry(Integer key) {
        return null;
    }

    @Override
    public Entry<Integer, String> higherEntry(Integer key) {
        return null;
    }

    @Override
    public Entry<Integer, String> ceilingEntry(Integer key) {
        return null;
    }

    @Override
    public Entry<Integer, String> firstEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> lastEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> pollFirstEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> pollLastEntry() {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> descendingMap() {
        return null;
    }

    @Override
    public NavigableSet<Integer> navigableKeySet() {
        return null;
    }

    @Override
    public NavigableSet<Integer> descendingKeySet() {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> subMap(Integer fromKey, boolean fromInclusive, Integer toKey, boolean toInclusive) {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> headMap(Integer toKey, boolean inclusive) {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> tailMap(Integer fromKey, boolean inclusive) {
        return null;
    }

    @Override
    public Comparator<? super Integer> comparator() {
        return null;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        return null;
    }
}
