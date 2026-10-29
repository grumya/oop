package ru.nsu.msafonova1.task1_2_2;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Параметризованная хеш-таблица с разрешением коллизий методом цепочек.
 *
 * @param <K> тип ключей
 * @param <V> тип значений
 */
public class HashTable<K, V> implements Iterable<HashTable.Entry<K, V>> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Entry<K, V>[] table;
    private int size;
    private int modCount;
    private final float loadFactor;

    /**
     * Узел хеш-таблицы.
     *
     * @param <K> тип ключа
     * @param <V> тип значения
     */
    public static class Entry<K, V> {
        final int hash;
        final K key;
        V value;
        Entry<K, V> next;

        public Entry(int hash, K key, V value, Entry<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public boolean equals(Object entryObject) {
            if (this == entryObject) {
                return true;
            }
            if (!(entryObject instanceof Entry)) {
                return false;
            }
            Entry<?, ?> entry = (Entry<?, ?>) entryObject;
            return Objects.equals(key, entry.key) && Objects.equals(value, entry.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }

    /**
     * Конструктор пустой хеш-таблицы.
     */
    @SuppressWarnings("unchecked")
    public HashTable() {
        this.table = new Entry[DEFAULT_CAPACITY];
        this.size = 0;
        this.modCount = 0;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }

    private int getIndex(K key, int capacity) {
        if (key == null) {
            return 0;
        }
        int hash = key.hashCode();
        return (hash & 0x7fffffff) % capacity;
    }

    /**
     * Добавление пары ключ-значение.
     *
     * @param key ключ
     * @param value значение
     */
    public void put(K key, V value) {
        ensureCapacity();
        int hash = (key == null) ? 0 : key.hashCode();
        int index = getIndex(key, table.length);

        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.hash == hash && Objects.equals(current.key, key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        table[index] = new Entry<>(hash, key, value, table[index]);
        size++;
        modCount++;
    }

    /**
     * Удаление по ключу.
     *
     * @param key ключ
     * @return true, если элемент удален
     */
    public boolean remove(K key) {
        int hash = (key == null) ? 0 : key.hashCode();
        int index = getIndex(key, table.length);

        Entry<K, V> previous = null;
        Entry<K, V> current = table[index];

        while (current != null) {
            if (current.hash == hash && Objects.equals(current.key, key)) {
                if (previous == null) {
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                modCount++;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    /**
     * Получение значения по ключу.
     *
     * @param key ключ
     * @return значение или null
     */
    public V get(K key) {
        int hash = (key == null) ? 0 : key.hashCode();
        int index = getIndex(key, table.length);

        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.hash == hash && Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Обновление значения по ключу.
     *
     * @param key ключ
     * @param value новое значение
     */
    public void update(K key, V value) {
        int hash = (key == null) ? 0 : key.hashCode();
        int index = getIndex(key, table.length);

        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.hash == hash && Objects.equals(current.key, key)) {
                current.value = value;
                modCount++;
                return;
            }
            current = current.next;
        }
        put(key, value);
    }

    /**
     * Проверка наличия ключа.
     *
     * @param key ключ
     * @return true, если ключ есть
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        if ((float) size / table.length >= loadFactor) {
            int newCapacity = table.length * 2;
            Entry<K, V>[] oldTable = table;
            table = new Entry[newCapacity];
            size = 0;

            for (Entry<K, V> head : oldTable) {
                Entry<K, V> current = head;
                while (current != null) {
                    put(current.key, current.value);
                    current = current.next;
                }
            }
        }
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<>() {
            private final int expectedModCount = modCount;
            private int currentIndex = 0;
            private Entry<K, V> nextEntry = findFirstNonEmpty();

            private Entry<K, V> findFirstNonEmpty() {
                while (currentIndex < table.length) {
                    if (table[currentIndex] != null) {
                        return table[currentIndex];
                    }
                    currentIndex++;
                }
                return null;
            }

            @Override
            public boolean hasNext() {
                return nextEntry != null;
            }

            @Override
            public Entry<K, V> next() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                Entry<K, V> result = nextEntry;
                if (result.next != null) {
                    nextEntry = result.next;
                } else {
                    currentIndex++;
                    nextEntry = findFirstNonEmpty();
                }
                return result;
            }
        };
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof HashTable)) {
            return false;
        }
        HashTable otherTable = (HashTable) otherObject;
        if (this.size != otherTable.size) {
            return false;
        }
        for (Entry<K, V> entry : this) {
            Object otherValue = otherTable.get(entry.key);
            if (!Objects.equals(entry.value, otherValue)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hashSum = 0;
        for (Entry<K, V> head : table) {
            Entry<K, V> current = head;
            while (current != null) {
                hashSum += current.hashCode();
                current = current.next;
            }
        }
        return hashSum;
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        for (Entry<K, V> entry : this) {
            stringJoiner.add(entry.key + "=" + entry.value);
        }
        return stringJoiner.toString();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}