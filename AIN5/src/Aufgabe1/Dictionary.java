// O. Bittel
// 19.9.2011

package Aufgabe1;


public interface Dictionary<K,V>{
    V insert(K key, V value);
    // Associates the specified value with the specified key in this map.
    // If the map previously contained a mapping for the key,
    // the old value is replaced by the specified value.
    // Returns the previous value associated with key,
    // or null if there was no mapping for key.

    V search(K key);
    // Returns the value to which the specified key is mapped,
    // or null if this map contains no mapping for the key.

    V remove(K key);
    // Removes the key-value-pair associated with the key.
    // Returns the value to which the key was previously associated,
    // or null if the key is not contained in the dictionary.
}

