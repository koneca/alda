package Aufgabe1;

//import java.util.Arrays;

public class SortedArrayDictionary<K extends Comparable<? super K>, V>
implements Dictionary <K, V> {

	private static class Entry<K, V>{
		K key;
		V value;
		Entry(K k, V v){
			key = k; value = v;
		}
	};
	
//	public int compareThem(K k, K k2){
//		System.out.println(k.compareTo(k2));
//		return k.compareTo(k2);
//	}	
	
	private static final int DEF_CAPACITY = 16;
	private int size;
	private Entry<K, V>[] data;


	@SuppressWarnings("unchecked")
	public SortedArrayDictionary(){
		size = 0;
		data = new Entry[DEF_CAPACITY];
		
		K k =(K) " ";
		V v = (V) " ";
		for (int i = 0; i < DEF_CAPACITY; i++) {
			 data[i] = new Entry<K, V>( k, v);
			
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private void ensureCapacity(int newCapacity){
		if(newCapacity < size)
			return;
		Entry<K ,V>[] old = data;
		data = new Entry[newCapacity];
		System.arraycopy(old, 0, data, 0, size);
	}
	

	@Override
	public V insert(K key, V value) {
		int i = searchKey(key);
		
		// Vorhandener Eintrag wird überschrieben:
		if(i != -1){
			V r = data[i].value;
			data[i].value = value;
			return r;
		}
		
		// Neueintrag:
		if(data.length == size){
			ensureCapacity(2*size);
		}
		int j = size-1;
		while(j >= 0 && key.compareTo(data[j].key) < 0){
			data[j+1] = data[j];
			j--;
		}
		data[j+1] = new Entry<K, V>(key, value);
		size++;
		return null;
	}
	
	private int searchKey(K k){
		int li = 0;
		int re = data.length-1;
		
		while (re >= li){
			int m = (li + re)/2;
						
			System.out.print(k+" m="+m+";");
			if(k.compareTo(data[m].key) < 0){
//			if(compareThem(key, data[m].key) < 0){				
				System.out.println(data[m].key);
				re = m - 1;				
			}
			else if (k.compareTo(data[m].key) > 0){
//			else if (compareThem(key, data[m].key) > 0){				
				System.out.println(data[m].key);
				li = m + 1;
			}
			else
				return m; //key gefunden
										
		}
		return -1; // key nicht gefunden
	}

	@Override
	public V search(K key) {
		int i = searchKey(key);
		if(i >= 0)
			return data[i].value;
		else
			return null;
	}

	public V remove(K key) {
		int i = searchKey(key);
		if(i == -1)
			return null;
		
		// Datensatz loeschen und Lücke schließen
		V r = data[i].value;
		for (int j = i; j < size-1; j++)
			data[j] = data[j+1];
		data[--size] = null;
		return r;
	}
}
