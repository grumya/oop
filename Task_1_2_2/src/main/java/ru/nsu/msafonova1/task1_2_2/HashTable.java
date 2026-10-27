package ru.nsu.msafonova1.task1_2_2;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Параметризованная хеш-таблица с разрешением коллизий методом цепочек.
 * Поддерживает полный набор операций контейнера.
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
     * Узел хеш-таблицы, хранящий пару ключ-значение.
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
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Entry)) {
                return false;
            }
            Entry<?, ?> entry = (Entry<?, ?>) o;
            return Objects.equals(key, entry.key) && Objects.equals(value, entry.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }

    /**
     * Создание пустой хеш-таблицы.
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

    private Entry<K, V> findEntry(K key) {
        int index = getIndex(key, table.length);
        int hash = key == null ? 0 : key.hashCode();
        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.hash == hash && Objects.equals(current.key, key)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Добавление пары ключ-значение (k, v).
     *
     * @param key   ключ
     * @param value значение
     */
    public void put(K key, V value) {
        ensureCapacity();
        int index = getIndex(key, table.length);
        int hash = key == null ? 0 : key.hashCode();

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
     * Удаление пары ключ-значение по ключу.
     *
     * @param key ключ
     * @return true, если пара была найдена и удалена
     */
    public boolean remove(K key) {
        int index = getIndex(key, table.length);
        int hash = key == null ? 0 : key.hashCode();

        Entry<K, V> prev = null;
        Entry<K, V> current = table[index];

        while (current != null) {
            if (current.hash == hash && Objects.equals(current.key, key)) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                modCount++;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    /**
     * Поиск значения по ключу.
     *
     * @param key ключ
     * @return значение или null, если ключ не найден
     */
    public V get(K key) {
        Entry<K, V> entry = findEntry(key);
        return entry == null ? null : entry.value;
    }

    /**
     * Обновление значения по ключу.
     *
     * @param key   ключ
     * @param value новое значение
     */
    public void update(K key, V value) {
        Entry<K, V> existing = findEntry(key);
        if (existing != null) {
            existing.value = value;
            modCount++;
            return;
        }
        put(key, value);
    }

    /**
     * Проверка наличия значения по ключу.
     *
     * @param key ключ
     * @return true, если ключ содержится в таблице
     */
    public boolean containsKey(K key) {
        return findEntry(key) != null;
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
                    putInternal(current.hash, current.key, current.value);
                    current = current.next;
                }
            }
        }
    }

    private void putInternal(int hash, K key, V value) {
        int index = getIndex(key, table.length);
        table[index] = new Entry<>(hash, key, value, table[index]);
        size++;
    }

    /**
     * Итерирование по элементам коллекции с обработкой ConcurrentModificationException.
     */
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
                    throw new ConcurrentModificationException(
                            "Table was modified during iteration"
                    );
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

    /**
     * Сравнение на равенство с другой хеш-таблицей.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HashTable)) {
            return false;
        }
        HashTable<?, ?> other = (HashTable<?, ?>) o;
        if (this.size != other.size) {
            return false;
        }

        return equalsHelper(other);
    }

    private <K2, V2> boolean equalsHelper(HashTable<K2, V2> other) {
        for (Entry<K, V> entry : this) {
            if (!other.containsKey((K2) entry.key)) {
                return false;
            }
            if (!Objects.equals(entry.value, other.get((K2) entry.key))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int h = 0;
        for (Entry<K, V> head : table) {
            Entry<K, V> current = head;
            while (current != null) {
                h += current.hashCode();
                current = current.next;
            }
        }
        return h;
    }

    /**
     * Вывод в строку.
     */
    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "{", "}");
        for (Entry<K, V> entry : this) {
            sj.add(entry.key + "=" + entry.value);
        }
        return sj.toString();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}