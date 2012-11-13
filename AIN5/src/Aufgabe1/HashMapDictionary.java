/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Aufgabe1;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Felix
 */
public class HashMapDictionary<K, V> implements Dictionary<K, V>{

    private HashMap<K, V> map;

    public HashMapDictionary() {
        map = new HashMap<K, V>();
    }

    public V insert(K key, V value) {
        return map.put(key, value);
    }

    public V search(K  key) {
        return map.get(key);
    }

    public V remove(K key) {
        return map.remove(key);
    }

    @Override
    public String toString(){
        Set<K> set = new TreeSet<K>();
        StringBuilder sb = new StringBuilder();
        set = map.keySet();
        for(K key: set) {
            sb
                .append(key)
                .append(" ")
                .append(map.get(key))
                .append("\n");
        }
        return sb.toString();
    }

}
