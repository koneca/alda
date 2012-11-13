package Aufgabe1;

import java.util.Iterator;
import java.util.LinkedList;


public class HashDictionary<K extends Comparable<? super K>, V>
implements Dictionary <K, V> {
	
    @SuppressWarnings("rawtypes")
	private LinkedList<Entry>[] tab;

    private static final int DEF_CAP = 19000;
	
	
    @SuppressWarnings("unchecked")
	public HashDictionary(){
        tab = new LinkedList[getHighestPrim(DEF_CAP)];
        for (int i = 0; i < tab.length; i++) {
            tab[i] = new LinkedList<Entry>();
        }
    }
	
    public V insert(K key, V value) {
        int index = Math.abs(key.hashCode()) % tab.length;
        for(Entry<K,V> e: tab[index]) {
            if(e.key.equals(key)){
                V old = e.value;
                e.value = value;
                return old;
            }
        }
        tab[index].add(new Entry<K,V>(key, value));
        return null;
    }

    public V search(K key) {
        int index = Math.abs(key.hashCode()) % tab.length;
        for(Entry<K,V> e: tab[index]) {
            if(e.key.equals(key)){
                return e.value;
            }
        }
        return null;
    }

    public V remove(K key) {
        int index = Math.abs(key.hashCode()) % tab.length;
        for (Iterator it = tab[index].iterator(); it.hasNext();) {
            Entry<K,V> e = (Entry<K,V>)it.next();
            if(e.key.equals(key)) {
                it.remove();
                return e.value;
            }
        }
        return null;
    }

    public static class Entry<K, V> {
        private K key;
        private V value;
        Entry( K k, V v){
            key = k;
            value = v;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < tab.length; i++){
            if( tab[i].size() == 0)
                continue;
            for (Entry<K, V> e: tab[i]) {
                sb.append(e.key)
                        .append(" ")
                        .append(e.value)
                        .append("\n");
            }
        }
        return sb.toString();
    }

    private int getHighestPrim(int to){
        boolean[] p = new boolean[to];
        for(int i = 0; i < to; i++)
            p[i] = true;
        for(int i = 2; i < Math.sqrt(to); i++) {
            if (p[i] == false)
                continue;
            for(int j = i*i; j < to; j += i ){
                p[j] = false;
            }
        }

        for(int i = to-1; i > 2; i--){
            if(p[i])
                return i;
        }
        return 0;
    }

	
	
	
//	private int m = 8009;
//	private Node Hashtab[] = new Node[m];
//	private Node firstNode = null;
//	
//	
//
//	private class Node<V> {
//		
//		private String engWord = null;
//		private V gerWord = null;
//		
//		private Node nextNode = null;
//				
//		public Node (String key, V value){
//			this.engWord = key;
//			this.gerWord = value;
//		}
//
//	}
//	
//
//	
//	
//	
//	
//		
//	public int erzeugeHashcode(K key) {
//		
//		int adr = key.hashCode();
//		System.out.println(adr);
//		if(adr < 0)
//			adr = -adr;
//		adr = adr % m;
//		
//		return adr;
//	}
//		
//	
//
//	@Override
//	public V insert(K key, V value) {
//		/** TODO Auto-generated method stub
//		suche in tab 7 h(key)] nach Schlüssel key;
//		if (key gefunden)
//			retrurn Daten des gefdunden SChlüssels;
//		else
//			return null; **/
//		
//		
//		
//		return null;
//	}
//
//	@Override
//	public V search(K key) {
//		/** TODO Auto-generated method stub
//		suche in tab[ h(key)] nach Schlüssel key;
//		if(key gefunden)
//			return Daten des gefunden Datensatzes;
//		else
//			return null;**/
//		return null;
//	}
//
//	@Override
//	public V remove(K key) {
//		/** TODO Auto-generated method stub
//		 * suche in tab [h(key)] nach Schlüssel key;
//		 * if(key gefunden){
//		 * 		entferne Knoten k aus der Liste;
//		 * }
//		 */
//		return null;
//	}
//
	
	
	
}
