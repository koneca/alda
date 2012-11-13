package Aufgabe1;



public class TreeDictionary<K extends Comparable<? super K>, V>
        implements Dictionary<K, V> {

    private Node root = null;
    private int size = 0;

    //----- Insert

    public V insert(K key, V value) {
        root = insertR(key, value, root);
        return null;
    }

    private Node<K,V> insertR(K key, V value, Node<K, V> p) {
    	System.out.print("key="+key);
        if (p == null)
            p = new Node<K, V>(key, value);
        else if (key.compareTo(p.key) < 0){
//        	System.out.println(p.key);
            p.left = insertR(key, value, p.left);}
        else if (key.compareTo(p.key) > 0)
            p.right = insertR(key, value, p.right);
        else
            p.value = value;
        p = balance(p);
        return p;
    }

    //----- Search

    public V search(K key) {
        return searchR(key, root);
    }

    private V searchR(K key, Node<K, V> p) {
	if (p == null)
            return null;
	else if (key.compareTo(p.key) < 0)
            return searchR(key, p.left);
	else if (key.compareTo(p.key) > 0)
            return searchR(key, p.right);
	else
            return p.value;
    }

    //----- Remove

    public V remove(K key) {
        removeR(key, root);
        return null;
    }

    private Node<K, V> removeR(K key, Node<K, V> p) {
        if (p == null)
            return null;
	if (key.compareTo(p.key) < 0)
            p.left = removeR(key, p.left);
	else if (key.compareTo(p.key) > 0)
            p.right = removeR(key, p.right);
        else {
            // Knoten loeschen:
            if (p.left == null || p.right == null) {
                // One or no child can be deleted directly:
                size--;
                p = (p.left != null) ? p.left : p.right;
            }
            else {
                // Two children
                Node<K, V> q = getMin(p.right);
                p.key = q.key;
                p.value = q.value;
                p.right = removeR(p.key,p.right);
            }
        }
        p = balance(p);
	return p;
    }

    private Node<K, V> getMin(Node<K, V> p) {
        assert(p != null);
        while(p.left != null)
            p = p.left;
        return p;
    }

    //----- Balance and Rotation

    private Node<K, V> balance(Node<K, V> p) {
        if (p == null)
            return null;
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        if (getBalance(p) == -2) {
            if (getBalance(p.left) <= 0) 
                p = rotateRight(p);
            else
                p = rotateLeftRight(p);
        } else if (getBalance(p) == 2) {
            if (getBalance(p.right) >= 0)
                p = rotateLeft(p);
            else
                p = rotateRightLeft(p);
        }
        return p;
    }

    private Node<K, V> rotateRight(Node <K, V> p) {
        assert p.left != null;
        Node <K, V> q = p.left;
        p.left = q.right;
        q.right = p;
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        q.height = Math.max(getHeight(q.left), getHeight(q.right)) + 1;
        return q;
    }

    private Node<K, V> rotateLeft(Node <K, V> p) {
        assert p.right != null;
        Node <K, V> q = p.right;
        p.right = q.left;
        q.left = p;
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        q.height = Math.max(getHeight(q.left), getHeight(q.right)) + 1;
        return q;
    }

    private Node<K, V> rotateLeftRight(Node <K, V> p) {
        assert p.left != null;
        p.left = rotateLeft(p.left);
        return rotateRight(p);
    }
    
    private Node<K, V> rotateRightLeft(Node <K, V> p) {
        assert p.right != null;
        p.right = rotateRight(p.right);
        return rotateLeft(p);
    }


    private int getHeight(Node<K, V> p) {
        if (p == null)
            return -1;
        else
            return p.height;
    }

    private int getBalance(Node<K, V> p) {
        if (p == null)
            return 0;
        else
            return getHeight(p.right) - getHeight(p.left);
    }

    // ----- Innere Knotenklasse

    static private class Node<K extends Comparable<? super K>, V> {
        int height;
        K key;
        V value;
        Node <K, V> left;
        Node <K, V> right;

        private Node(K k, V v){
            height = 0;
            key = k;
            value = v;
            left = null;
            right = null;
        }
    }

    //----- Textausgabe

    @Override
    public String toString() {
        return toStringR(root).toString();
    }

    private StringBuilder toStringR(Node<K, V> p) {
        StringBuilder sb = new StringBuilder();
        if (p == null)
            return sb;
        sb.append(toStringR(p.left));
        sb.append(p.key).append(" ").append(p.value).append("\n");
        sb.append(toStringR(p.right));
        return sb;
    }
}
