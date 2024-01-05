package by.it.group251001.zhidkov.lesson09;

import java.util.*;
import java.lang.StringBuilder;
public class ListC<E> implements List<E> {

    // Инициализация массива данных (пустой массив) и размера списка
    private Object[] data = new Object[0];
    private int size = 0;

    // Переопределенный метод toString() для возвращения строкового представления списка
    @Override
    public String toString() {
        // Создание объекта StringBuilder для эффективной конкатенации строк
        StringBuilder sb = new StringBuilder("[");

        // Итерация по элементам списка
        for (int i = 0; i < size; i++) {
            sb.append(data[i]); // Добавление элемента к StringBuilder

            // Проверка, является ли текущий элемент последним в списке
            if (i == size - 1) {
                sb.append("]"); // Закрытие списка символом ']'
                return sb.toString(); // Возвращение строкового представления списка
            }

            sb.append(", "); // Если элемент не последний, добавление запятой и пробела для разделения
        }

        sb.append("]"); // Закрытие списка, если он был пустым
        return sb.toString(); // Возвращение строкового представления списка
    }


    // Переопределенный метод для добавления элемента в конец списка
    @Override
    public boolean add(E e) {
        ensureCapacity(size + 1); // Проверка и, если необходимо, увеличение емкости списка
        data[size++] = e; // Добавление элемента в конец списка и увеличение размера
        return true; // Всегда возвращает true, поскольку метод add в List не предполагает ошибок добавления
    }

    // Переопределенный метод для удаления элемента по индексу из списка
    @Override
    public E remove(int index) {
        if (index >= size) {
            return null; // Если индекс выходит за пределы размера, возвращает null
        }
        E rem = (E) data[index]; // Сохранение удаляемого элемента
        System.arraycopy(data, index + 1, data, index, size - 1 - index); // Смещение элементов для удаления
        size--; // Уменьшение размера списка
        return rem; // Возвращение удаленного элемента
    }

    // Переопределенный метод для возврата текущего размера списка
    @Override
    public int size() {
        return size;
    }

    // Переопределенный метод для добавления элемента по индексу в список
    @Override
    public void add(int index, E element) {
        ensureCapacity(size + 1); // Проверка и, если необходимо, увеличение емкости списка
        System.arraycopy(data, index, data, index + 1, size - index); // Смещение элементов для вставки
        data[index] = element; // Добавление элемента по указанному индексу
        size++; // Увеличение размера списка
    }


    // Переопределенный метод для удаления первого вхождения указанного элемента из списка
    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(data[i])) { // Поиск элемента в списке
                remove(i); // Если найден, вызывается метод remove для удаления элемента по индексу
                return true; // Возвращает true после успешного удаления
            }
        }
        return false; // Возвращает false, если элемент не найден
    }

    // Переопределенный метод для замены элемента по индексу в списке
    @Override
    public E set(int index, E element) {
        if (index >= size) {
            return null; // Если индекс выходит за пределы размера, возвращает null
        }
        E tmp = (E) data[index]; // Сохранение заменяемого элемента
        data[index] = element; // Замена элемента
        return tmp; // Возвращение замененного элемента
    }

    // Переопределенный метод для проверки, пуст ли список
    @Override
    public boolean isEmpty() {
        return size == 0; // Возвращает true, если размер списка равен 0, иначе false
    }

    // Переопределенный метод для очистки списка
    @Override
    public void clear() {
        data = new Object[0]; // Создание нового массива с нулевой емкостью
        size = 0; // Установка размера списка в 0
    }

    // Переопределенный метод для поиска индекса первого вхождения указанного элемента в списке
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(data[i]))
                return i; // Возвращает индекс, если элемент найден
        }
        return -1; // Возвращает -1, если элемент не найден
    }


    // Переопределенный метод для получения элемента по индексу из списка
    @Override
    public E get(int index) {
        if (index >= size)
            return null; // Возвращает null, если индекс выходит за пределы размера
        return (E) data[index]; // Возвращает элемент по указанному индексу
    }

    // Переопределенный метод для проверки, содержится ли указанный элемент в списке
    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++)
            if (o.equals(data[i]))
                return true; // Возвращает true, если элемент найден в списке
        return false; // Возвращает false, если элемент не найден в списке
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
    public int lastIndexOf(Object o) {
        // Начинаем итерацию с последнего элемента списка
        for (int i = size - 1; i >= 0; i--) {
            // Проверяем, равен ли текущий элемент элементу o
            if (o.equals(data[i]))
                // Если равен, возвращаем текущий индекс
                return i;
        }
        // Если цикл завершается и совпадение не найдено, возвращаем -1
        return -1;
    }


    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    /**
     * Проверяет, содержатся ли все элементы коллекции в списке.
     * @param c Коллекция для проверки.
     * @return true, если все элементы из коллекции содержатся в списке, в противном случае false.
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        // Преобразование коллекции в массив объектов.
        Object[] tmp = c.toArray();
        // Перебор элементов массива.
        for (int i = 0; i < c.size(); i++) {
            // Если текущий элемент не содержится в списке, возвращается false.
            if (!this.contains(tmp[i]))
                return false;
        }
        // Если все элементы коллекции содержатся в списке, возвращается true.
        return true;
    }

    /**
     * Добавляет все элементы из коллекции в конец списка.
     * @param c Коллекция для добавления.
     * @return true, если хотя бы один элемент был добавлен, в противном случае false.
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        // Если коллекция пуста, возвращается false.
        if (c.size() == 0)
            return false;
        // Обеспечение достаточной емкости списка.
        ensureCapacity(size + c.size());
        // Копирование элементов из коллекции в конец списка.
        System.arraycopy(c.toArray(), 0, data, size, c.size());
        // Обновление размера списка.
        size += c.size();
        // Возвращается true, так как хотя бы один элемент был добавлен.
        return true;
    }

    /**
     * Добавляет все элементы из коллекции в указанное место в списке.
     * @param index Индекс, с которого начинается добавление элементов коллекции.
     * @param c Коллекция для добавления.
     * @return true, если хотя бы один элемент был добавлен, в противном случае false.
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        // Если коллекция пуста, возвращается false.
        if (c.size() == 0)
            return false;
        // Обеспечение достаточной емкости списка.
        ensureCapacity(size + c.size());
        // Сдвиг элементов вправо, чтобы освободить место для новых элементов.
        System.arraycopy(data, index, data, index + c.size(), size - index);
        // Копирование элементов из коллекции в указанное место списка.
        System.arraycopy(c.toArray(), 0, data, index, c.size());
        // Обновление размера списка.
        size += c.size();
        // Возвращается true, так как хотя бы один элемент был добавлен.
        return true;
    }


    /**
     * Удаляет из списка все элементы, которые содержатся в указанной коллекции.
     * @param c Коллекция элементов для удаления.
     * @return true, если хотя бы один элемент был удален, в противном случае false.
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        // Флаг для отслеживания внесенных изменений в список.
        boolean edited = false;
        // Индекс для перебора элементов списка.
        int i = 0;
        // Перебор элементов списка.
        while (i < size) {
            // Если текущий элемент содержится в указанной коллекции, он удаляется из списка.
            if (c.contains(data[i])) {
                this.remove(i);
                // Установка флага в true, так как элемент был удален.
                edited = true;
            } else
                // Если элемент не был удален, индекс увеличивается для перехода к следующему элементу.
                i++;
        }
        // Возвращается true, если хотя бы один элемент был удален.
        return edited;
    }

    /**
     * Удаляет из списка все элементы, которые не содержатся в указанной коллекции.
     * @param c Коллекция элементов, которые должны быть сохранены в списке.
     * @return true, если хотя бы один элемент был удален, в противном случае false.
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        // Флаг для отслеживания внесенных изменений в список.
        boolean edited = false;
        // Индекс для перебора элементов списка.
        int i = 0;
        // Перебор элементов списка.
        while (i < size) {
            // Если текущий элемент не содержится в указанной коллекции, он удаляется из списка.
            if (!c.contains(data[i])) {
                this.remove(i);
                // Установка флага в true, так как элемент был удален.
                edited = true;
            } else
                // Если элемент не был удален, индекс увеличивается для перехода к следующему элементу.
                i++;
        }
        // Возвращается true, если хотя бы один элемент был удален.
        return edited;
    }

    /**
     * Обеспечивает, что список имеет достаточную емкость для хранения указанного количества элементов.
     * @param minCapacity Минимальная емкость списка.
     */
    private void ensureCapacity(int minCapacity) {
        // Если минимальная емкость превышает текущую, производится увеличение емкости.
        if (minCapacity > data.length) {
            // Новая емкость устанавливается как максимум из удвоенной текущей емкости и значения 10.
            int newCapacity = Math.max(data.length << 1, 10);
            // Если новая емкость все равно меньше минимальной, она устанавливается в минимальное значение.
            if (newCapacity < minCapacity)
                newCapacity = minCapacity;
            // Создается новый массив с увеличенной емкостью и копируются все существующие элементы.
            data = Arrays.copyOf(data, newCapacity);
        }
    }


    // Остальные методы можно оставить неизменными
}

