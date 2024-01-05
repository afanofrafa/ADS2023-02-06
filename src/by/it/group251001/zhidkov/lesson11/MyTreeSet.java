package by.it.group251001.zhidkov.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet<E extends Comparable<E>> implements Set<E> {
    // Создание массива для хранения элементов структуры данных
    private E[] array = (E[]) new Comparable[0];

    // Переменная для отслеживания текущего размера структуры данных
    private int size;

    // Метод для выполнения бинарного поиска элемента в массиве
    private int binarySearch(E key) {
        int left = 0;
        int right = size - 1;

        // Начало цикла бинарного поиска
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comp = array[mid].compareTo(key);

            // Обновление границ поиска в зависимости от результата сравнения
            if (comp < 0) {
                left = mid + 1;
            } else {
                if (comp > 0) {
                    right = mid - 1;
                } else {
                    // Элемент найден
                    return -(mid);
                }
            }
        }

        // Элемент не найден, возвращается индекс, на котором должен быть вставлен элемент
        return left + 1;
    }

    // Переопределение метода toString() для представления структуры данных в виде строки
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");

        // Проверка, что структура данных не пуста
        if (size > 0) {
            // Начало цикла для обхода элементов массива
            for (int i = 0; i < size; i++) {
                // Добавление элемента в строку с разделителями (запятая и пробел), если элемент не последний
                if (i != size - 1)
                    result.append(array[i]).append(", ");
                else
                    result.append(array[i]);
            }
        }

        // Завершение строки и возврат ее представления
        result.append("]");
        return result.toString();
    }

    // Переопределение метода size(), возвращающего текущий размер структуры данных
    @Override
    public int size() {
        return size;
    }

    // Переопределение метода isEmpty(), возвращающего true, если структура данных пуста
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Переопределение метода contains(Object o), проверяющего, содержится ли элемент o в структуре данных
    @Override
    public boolean contains(Object o) {
        if (size > 0) {
            return binarySearch((E) o) <= 0;
        } else {
            return false;
        }
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

    // Переопределение метода add(E e) для добавления элемента в структуру данных
    @Override
    public boolean add(E e) {
        // Проверка, нужно ли увеличивать размер массива
        if (size == array.length) {
            // Создание нового массива с увеличенной длиной
            E[] newArray = (E[]) new Comparable[size * 2 + 1];

            // Копирование элементов из старого массива в новый
            for (int i = 0; i < size; i++) {
                newArray[i] = array[i];
            }

            // Замена старого массива на новый
            array = newArray;
        }

        // Проверка наличия элемента и непустоты структуры данных
        if (e != null && !isEmpty()) {
            // Получение индекса для вставки элемента
            int index = binarySearch(e);

            // Проверка, что элемент уже есть в массиве
            if (index <= 0) {
                return false;
            } else {
                // Уменьшение индекса для вставки
                index -= 1;

                // Смещение элементов после вставки
                System.arraycopy(array, index, array, index + 1, size - index);
                array[index] = e;
            }
            size++;
            return true;
        } else {
            // Добавление элемента в пустой массив или игнорирование null
            if (e == null) {
                return false;
            } else {
                array[0] = e;
                size++;
                return true;
            }
        }
    }

    // Переопределение метода remove(Object o) для удаления элемента из структуры данных
    @Override
    public boolean remove(Object o) {
        // Проверка наличия элемента и непустоты структуры данных
        if (o == null || isEmpty()) {
            return false;
        }

        // Получение индекса элемента для удаления
        int index = -(binarySearch((E) o));

        // Проверка, что элемент для удаления найден
        if (index < 0) {
            return false;
        }

        // Смещение элементов после удаления
        for (int i = index; i < size; i++) {
            array[i] = array[i + 1];
        }

        // Уменьшение размера структуры данных
        size--;
        return true;
    }

    // Переопределение метода containsAll(Collection<?> c) для проверки наличия всех элементов из коллекции в структуре данных
    @Override
    public boolean containsAll(Collection<?> c) {
        boolean b = true;
        for (Object o : c) {
            // Проверка наличия каждого элемента в структуре данных
            if (!contains(o)) {
                b = false;
            }
        }
        return b;
    }

    // Переопределение метода addAll(Collection<? extends E> c) для добавления всех элементов из коллекции в структуру данных
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean b = false;
        for (E e : c) {
            // Добавление каждого элемента из коллекции в структуру данных
            if (add(e)) {
                b = true;
            }
        }
        return b;
    }


    // Переопределение метода retainAll(Collection<?> c) для сохранения только элементов, присутствующих в коллекции
    @Override
    public boolean retainAll(Collection<?> c) {
        // Если коллекция пуста, очищаем структуру данных и возвращаем true
        if (c.isEmpty()) {
            clear();
            return true;
        }

        boolean b = false;
        int i = 0;
        while (i < size) {
            // Если элемент из структуры данных не присутствует в коллекции, удаляем его
            if (!c.contains(array[i])) {
                remove(array[i]);
                b = true;
                i--;
            }
            i++;
        }
        return b;
    }

    // Переопределение метода removeAll(Collection<?> c) для удаления всех элементов из коллекции
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean b = false;
        for (Object o : c) {
            // Удаление каждого элемента из коллекции из структуры данных
            if (remove(o)) {
                b = true;
            }
        }
        return b;
    }

    // Переопределение метода clear() для очистки структуры данных
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            // Замещение каждого элемента в массиве на null
            array[i] = null;
        }
        // Установка размера структуры данных в 0
        size = 0;
    }

}
