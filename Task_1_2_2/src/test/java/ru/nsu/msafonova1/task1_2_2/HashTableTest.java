package ru.nsu.msafonova1.task1_2_2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import org.junit.jupiter.api.Test;

class HashTableTest {

    @Test
    void testTaskExample() {
        HashTable<String, Object> hashTable = new HashTable<>();
        hashTable.put("one", 1);
        hashTable.update("one", 1.0);
        assertEquals(1.0, hashTable.get("one"));
    }

    @Test
    void testPutAndGet() {
        HashTable<String, String> table = new HashTable<>();
        table.put("key1", "val1");
        table.put("key2", "val2");
        assertEquals("val1", table.get("key1"));
        assertEquals("val2", table.get("key2"));
        assertTrue(table.containsKey("key1"));
    }

    @Test
    void testRemove() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("a", 100);
        assertTrue(table.remove("a"));
        assertFalse(table.containsKey("a"));
        assertFalse(table.remove("nonexistent"));
    }

    @Test
    void testResizeAndCollisions() {
        HashTable<Integer, String> table = new HashTable<>();
        for (int i = 0; i < 100; i++) {
            table.put(i, "val" + i);
        }
        assertEquals(100, table.size());
        assertEquals("val50", table.get(50));
    }

    @Test
    void testConcurrentModification() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("a", 1);
        table.put("b", 2);

        assertThrows(ConcurrentModificationException.class, () -> {
            for (var entry : table) {
                table.put("c", 3);
            }
        });
    }

    @Test
    void testEqualsAndToString() {
        HashTable<String, Integer> t1 = new HashTable<>();
        HashTable<String, Integer> t2 = new HashTable<>();
        t1.put("x", 10);
        t2.put("x", 10);

        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());
        assertNotNull(t1.toString());
    }
}