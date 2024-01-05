package by.it.group251001.zhidkov.lesson10;

import java.util.*;

public class MyPriorityQueue<E> implements Deque<E> {
    private Object[] Queue; // Массив для хранения элементов кучи.
    private int size; // Текущий размер кучи.

    // Конструктор для инициализации пустой кучи.
    public MyPriorityQueue(){
        size = 0; // Устанавливаем начальный размер кучи в 0.
        Queue = (new Object[10]); // Инициализируем массив начальной емкостью 10.
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("["); // Создаем объект для формирования строки.
        for (int i = 0; i < size; i++) {
            result.append(Queue[i]); // Добавляем элемент в строку.
            if (i < size - 1) {
                result.append(", "); // Добавляем разделитель, если элемент не последний.
            }
        }
        result.append("]"); // Завершаем строку.
        return result.toString(); // Возвращаем строковое представление кучи.
    }


    @Override
    public void addFirst(E e) {
    }

    @Override
    public void addLast(E e) {
    }

    @Override
    public boolean offerFirst(E e) {
        return false;
    }

    @Override
    public boolean offerLast(E e) {
        return false;
    }

    @Override
    public E removeFirst() {
        return null;
    }

    @Override
    public E removeLast() {
        return null;
    }

    @Override
    public E pollFirst() {
        return null;
    }

    @Override
    public E pollLast() {
        return null;
    }

    @Override
    public E getFirst() {
        return null;
    }

    @Override
    public E getLast() {
        return null;
    }

    @Override
    public E peekFirst() {
        return null;
    }

    @Override
    public E peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean add(E e) {
        // Добавляет элемент в кучу.
        if (size >= Queue.length) {
            // Если текущая емкость кучи достигнута или превышена...
            if (size != 0) {
                // Если размер кучи не равен нулю (куча не пуста)...
                E[] newQueue = (E[]) (new Object[size * 2]); // Создаем новый массив с увеличенной емкостью.
                for (int i = 0; i < size; i++) {
                    newQueue[i] = (E) Queue[i]; // Копируем элементы из старого массива в новый.
                }
                Queue = newQueue; // Присваиваем новый массив вместо старого.
            } else {
                // Если размер кучи равен нулю (куча пуста)...
                E[] newQueue = (E[]) (new Object[10]); // Создаем новый массив с начальной емкостью 10.
                Queue = newQueue; // Присваиваем новый массив вместо старого.
            }
        }
        Queue[size] = e; // Добавляем новый элемент в конец кучи.
        int Child = size; // Индекс нового элемента.
        size++;
        boolean bool = true;
        while (Child != 0 && bool) {
            int Parent = (Child - 1) / 2; // Индекс родителя.
            if ((int) Queue[Parent] >= (int) Queue[Child]) {
                // Если значение родителя больше или равно значению нового элемента...
                E temp = (E) Queue[Child];
                Queue[Child] = Queue[Parent]; // Меняем местами с родителем.
                Queue[Parent] = temp;
                Child = Parent; // Переходим к родителю.
            } else {
                bool = false; // Если условие кучи соблюдено, выходим из цикла.
            }
        }
        return true;
    }

    @Override
    public boolean offer(E e) {
        // Добавляет элемент в кучу, всегда успешно.
        return add(e);
    }


    @Override
    public E remove() {
        // Удаляет и возвращает элемент с вершины кучи (корень).
        if (size == 0) {
            // Если куча пуста, создаем новый массив с начальной емкостью 10.
            E[] newQueue = (E[]) (new Object[10]);
            Queue = newQueue;
            return null;
        }
        int Parent = 0;
        E remEl = (E) Queue[0]; // Сохраняем удаляемый элемент.
        size--;
        Queue[0] = Queue[size]; // Перемещаем последний элемент на вершину.
        Queue[size] = null; // Очищаем последний элемент (избавляемся от дубликата в конце).
        boolean bool = true;
        while (((Parent * 2) + 1 < size) && bool) {
            int ChildRight = (Parent * 2) + 2;
            int ChildLeft = (Parent * 2) + 1;
            if ((Parent * 2) + 2 >= size) {
                ChildRight = ChildLeft;
            }

            if (((int) Queue[Parent] >= (int) Queue[ChildLeft]) || ((int) Queue[Parent] >= (int) Queue[ChildRight])) {
                if ((int) Queue[ChildRight] < (int) Queue[ChildLeft]) {
                    // Если значение правого потомка меньше, меняем местами с вершиной.
                    E temp = (E) Queue[ChildRight];
                    Queue[ChildRight] = Queue[Parent];
                    Queue[Parent] = temp;
                    Parent = ChildRight;
                } else {
                    // Если значение левого потомка меньше или равно, меняем местами с вершиной.
                    E temp = (E) Queue[ChildLeft];
                    Queue[ChildLeft] = Queue[Parent];
                    Queue[Parent] = temp;
                    Parent = ChildLeft;
                }
            } else {
                // Если условие кучи соблюдено, выходим из цикла.
                bool = false;
            }
        }
        return remEl;
    }

    @Override
    public E poll() {
        // Удаляет и возвращает элемент с вершины кучи (корень), возвращает null, если куча пуста.
        return remove();
    }

    @Override
    public E element() {
        // Возвращает элемент с вершины кучи (корень) без его удаления, генерирует исключение, если куча пуста.
        return peek();
    }


    @Override
    public E peek() {
        // Возвращает элемент с вершины кучи (корень), не удаляя его.
        if (size > 0) {
            // Если размер кучи больше 0, возвращаем элемент с вершины (индекс 0).
            return (E) Queue[0];
        } else {
            // Если куча пуста, возвращаем null.
            return null;
        }
    }


    @Override
    public boolean addAll(Collection<? extends E> c) {
        // Добавляет все элементы из указанной коллекции в кучу.
        boolean b = false;

        for (E element : c) {
            // Для каждого элемента в коллекции вызываем метод add.
            // Если хотя бы одно добавление прошло успешно, устанавливаем флаг в true.
            if (add(element)) {
                b = true;
            }
        }
        // Возвращаем true, если хотя бы один элемент был успешно добавлен.
        return b;
    }


    @Override
    public boolean removeAll(Collection<?> c) {
        if (size == 0) {
            // Если очередь пуста, создаем новый массив с начальной емкостью 10.
            E[] newQueue = (E[]) (new Object[10]);
            Queue = newQueue;
            return false;
        }

        int j = 0;
        // Индекс для нового массива, который будет хранить элементы, не содержащиеся в коллекции c.

        for (int i = 0; i < size; i++) {
            if (!c.contains(Queue[i])) {
                // Если текущий элемент не содержится в коллекции c, добавляем его в новый массив.
                Queue[j] = Queue[i];
                j++;
            }
        }

        boolean b = false;
        // Флаг для проверки, были ли удалены какие-либо элементы.

        if (j < size) {
            for (int i = j; i < size; i++) {
                Queue[i] = null; // Очищаем оставшиеся элементы в старом массиве.
                b = true;
            }
            size = j;
            // Устанавливаем новый размер очереди после удаления элементов.
        }

        for (int i = size / 2 - 1; i >= 0; i--) {
            // Проталкиваем элементы вниз для поддержания свойства кучи после удаления элементов.
            int LChild = (2 * i) + 1;
            int RChild = (2 * i) + 2;
            int Parent = i;

            while ((Parent * 2 + 1) < size) {
                // Запускаем цикл, который будет выполняться, пока у текущего узла есть хотя бы один потомок.

                if (RChild >= size) {
                    RChild = LChild;
                }
                // Проверяем, есть ли правый потомок. Если его индекс больше или равен размеру массива (size),
                // то делаем его равным левому потомку. Это обеспечивает корректное сравнение значений потомков,
                // даже если правый потомок отсутствует.

                if ((int) Queue[RChild] < (int) Queue[LChild]) {
                    if ((int) Queue[Parent] >= (int) Queue[RChild]) {
                        E temp = (E) Queue[Parent];
                        Queue[Parent] = Queue[RChild];
                        Queue[RChild] = temp;
                        Parent = RChild;
                        RChild = (Parent * 2 + 2);
                        LChild = (Parent * 2 + 1);
                    } else {
                        break; // Если условие кучи соблюдено, выходим из цикла.
                    }
                } else {
                    if ((int) Queue[Parent] >= (int) Queue[LChild]) {
                        E temp = (E) Queue[Parent];
                        Queue[Parent] = Queue[LChild];
                        Queue[LChild] = temp;
                        Parent = LChild;
                        RChild = (Parent * 2 + 2);
                        LChild = (Parent * 2 + 1);
                    } else {
                        break; // Если условие кучи соблюдено, выходим из цикла.
                    }
                }
            }
        }

        return b;
    }


    // Метод для сохранения в очереди только тех элементов, которые присутствуют в заданной коллекции
    @Override
    public boolean retainAll(Collection<?> c) {
        // Проверка, если очередь пуста, создаем новую очередь с начальной емкостью 10
        if (size == 0) {
            E[] newQueue = (E[]) (new Object[10]);
            Queue = newQueue;
            return false;
        }

        // Инициализация переменных
        int j = 0;

        // Перебор элементов текущей очереди
        for (int i = 0; i < size; i++) {
            // Если текущий элемент присутствует в заданной коллекции, сохраняем его
            if (c.contains(Queue[i])) {
                Queue[j] = Queue[i];
                j++;
            }
        }

        // Переменная для отслеживания изменений в очереди
        boolean b = false;

        // Если были удалены элементы из очереди, производим пересчет кучи
        if (j < size) {
            for (int i = j; i < size; i++) {
                Queue[i] = null; // Очищаем оставшиеся элементы
                b = true;
            }
            size = j;
        }

        // Пересчет кучи
        for (int i = size / 2 - 1; i >= 0; i--) {
            // Начинаем итерацию с последнего уровня в дереве и двигаемся к корню.
            // Делаем это, чтобы начать с узлов, у которых есть потомки.

            int LChild = (2 * i) + 1;
            int RChild = (2 * i) + 2;
            int Parent = i;
            // Вычисляем индексы левого и правого потомков текущего узла,
            // а также сохраняем индекс текущего узла.

            while ((Parent * 2 + 1) < size) {
                // Запускаем цикл, который будет выполняться, пока у текущего узла есть хотя бы один потомок.

                if (RChild >= size) {
                    RChild = LChild;
                }
                // Проверяем, есть ли правый потомок. Если его индекс больше или равен размеру массива (size),
                // то делаем его равным левому потомку. Это обеспечивает корректное сравнение значений потомков,
                // даже если правый потомок отсутствует.

                if ((int) Queue[RChild] < (int) Queue[LChild]) {
                    if ((int) Queue[Parent] >= (int) Queue[RChild]) {
                        E temp = (E) Queue[Parent];
                        Queue[Parent] = Queue[RChild];
                        Queue[RChild] = temp;
                        Parent = RChild;
                        RChild = (Parent * 2 + 2);
                        LChild = (Parent * 2 + 1);
                    } else {
                        break; // Если условие кучи соблюдено, выходим из цикла
                    }
                } else {
                    if ((int) Queue[Parent] >= (int) Queue[LChild]) {
                        E temp = (E) Queue[Parent];
                        Queue[Parent] = Queue[LChild];
                        Queue[LChild] = temp;
                        Parent = LChild;
                        RChild = (Parent * 2 + 2);
                        LChild = (Parent * 2 + 1);
                    } else {
                        break; // Если условие кучи соблюдено, выходим из цикла
                    }
                }
            }
        }


        // Возвращаем результат - были ли изменения в очереди
        return b;
    }


    // Метод для очистки очереди
    @Override
    public void clear() {
        // Устанавливаем все элементы в очереди как null и обнуляем размер
        for (int i = 0; i < size; i++) {
            Queue[i] = null;
        }
        size = 0;
    }

    @Override
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
    }

    // Метод для удаления элемента из очереди по значению
    @Override
    public boolean remove(Object o) {
        // Проверка, если очередь пуста, создаем новую очередь с начальной емкостью 10
        if (size == 0) {
            E[] newQueue = (E[]) (new Object[10]);
            Queue = newQueue;
            return false;
        }

        // Инициализация переменных
        int i = 0;
        boolean b = false;

        // Поиск индекса элемента в очереди
        while (!b && i < size) {
            if (contains(o)) {
                b = true;
            } else {
                i++;
            }
        }

        // Если элемент не найден, возвращаем false
        if (!b) {
            return false;
        }

        // Получение удаляемого элемента и замена его последним элементом в очереди
        E remEl = (E) Queue[i];
        Queue[i] = Queue[size - 1];
        size--;

        // Устанавливаем значение последнего элемента в очереди как null и пересчитываем кучу
        Queue[size] = null;
        int Parent = i;
        int Child;
        while ((Parent + 1) * 2 < size) {
            Child = (Parent + 1) * 2;
            E temp = (E) Queue[Child];
            Queue[Child] = Queue[Parent];
            Queue[Parent] = temp;
            Parent = Child;
        }

        // Возвращаем true, т.к. удаление прошло успешно
        return true;
    }

    // Метод для проверки наличия всех элементов коллекции в очереди
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            // Если хотя бы один элемент из коллекции не содержится в очереди, возвращаем false
            if (!contains(element)) {
                return false;
            }
        }
        // Все элементы коллекции найдены в очереди, возвращаем true
        return true;
    }

    // Метод для проверки наличия элемента в очереди
    @Override
    public boolean contains(Object o) {
        // Инициализация переменных
        boolean bool = false;
        int i = 0;

        // Поиск элемента в очереди
        while (!bool && i < size) {
            // Сравнение элемента с текущим элементом в очереди
            if (o.equals(Queue[i])) {
                bool = true;
            }
            i++;
        }

        // Возвращаем результат поиска (true, если элемент найден, иначе false)
        return bool;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }
}
