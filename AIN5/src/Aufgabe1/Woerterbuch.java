package Aufgabe1;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Woerterbuch implements Dictionary<String, String> {
	
	public Dictionary<String, String> wb;
	private static int i;
	public static final int SARRAY = 0;
	public static final int HASH = 1;
	public static final int TREE = 2;
	public static final int MAP = 3;
	public static final File file = new File("./src/Aufgabe1/dtengl.txt");
	
	public Woerterbuch(){
		wb = new SortedArrayDictionary<String, String>();
	}
	
	@Override
	public String insert(String key, String value) {
        if (key.equals("") || value.equals("")) {
            throw new IllegalArgumentException("Please insert both words.");
        }
		return wb.insert(key, value);
	}

	@Override
	public String search(String key) {
		return wb.search(key);
	}

	@Override
	public String remove(String key) {
		return wb.remove(key);
	}
	
	@Override
    public String toString() {
        return wb.toString();
    }
	
	public void choice(){

						
		switch (i) {
			case SARRAY:
				wb = new SortedArrayDictionary<String, String>();
				break;
			case HASH:
				wb = new HashDictionary<String, String>();
				break;
			case TREE:
				wb = new TreeDictionary<String, String>();
				break;
//			case MAP:
//				wb = new MapDictionary<String, String>();
				//TODO weitere cases f¸r wb
			default:
				System.err.println("Wrong Argument");
		}
	}
	
    public void changeImplementation(int im) {
        switch (im) {
            case SARRAY:
            case HASH:
            case TREE:
            case MAP:
              i = im;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
	
    public void read(File f) {
        LineNumberReader in = null;
        try {
            in = new LineNumberReader(new FileReader(f));
            String line;
            choice();
            while ((line = in.readLine()) != null) {
                String[] s = line.trim().split(" ");
                if (s.length == 2) {
                    wb.insert(s[0], s[1]);
                }
            }
            in.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }	
	
    public void save(File f) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(f);
            out.print(wb);
            out.close();
        } catch (IOException ex) {
            System.err.println("Hopala: " + ex.getMessage());
        }
    }

    public String performance() {
        try {
            File f;
            try {
                f = new File(this.getClass().getResource("dtengl.txt").toURI());
            } catch (URISyntaxException ex) {
                    f = new File("./dtengl.txt");
            }
            LinkedList<String> dt = new LinkedList<String>();
            LinkedList<String> en = new LinkedList<String>();
            LineNumberReader in = null;
            in = new LineNumberReader(new FileReader(f));
            String line;
            choice();
            while ((line = in.readLine()) != null) {
                String[] s = line.trim().split(" ");
                if (s.length == 2) {
                    dt.add(s[0]);
                    en.add(s[1]);
                }
            }
            in.close();
            Iterator<String> itdt = dt.listIterator();
            Iterator<String> iten = en.listIterator();
            long time = System.nanoTime();
            while (itdt.hasNext() && iten.hasNext()) {
                wb.insert(itdt.next(), iten.next());
            }
            time = System.nanoTime() - time;
            double aufbau = time / 1e9d;
            time = System.nanoTime();
            itdt = dt.listIterator();
            while (itdt.hasNext()) {
                wb.search(itdt.next());
            }
            time = System.nanoTime() - time;
            double erfolg = time / 1e9d;
            time = System.nanoTime();
            iten = en.listIterator();
            while (iten.hasNext()) {
                wb.search(iten.next());
            }
            time = System.nanoTime() - time;
            double nErfolg = time / 1e9d;
            return String.format(
                    "-----\nPerformance Messung\n-----\n"
                    + "Aufbau mit %d Eintr√§gen:                   %f Sekunden\n"
                    + "Erfolreich Suchen von %d Eintr√§gen:        %f Sekunden\n"
                    + "Nicht Erfolgreich Suchen von %d Eintr√§gen: %f Sekunden\n"
                    + "-----",
                    dt.size(), aufbau, dt.size(), erfolg, en.size(), nErfolg);
        } catch (IOException ex) {
            return ex.getMessage();
        }
    }
//	public static void main (String[] args){
//		Scanner in = new Scanner(System.in);
//		
//		System.out.println("Bitte w‰hlen:\n"+
//							"0. SortedArrayDictionary\n"+
//							"1. Hashdictionary\n"+
//							"2. TreeDictionary");
//		i = in.nextInt();
//		
//		Woerterbuch woertb = new Woerterbuch();
//		woertb.choice();
//		woertb.read(file);
//		
//		System.out.println(woertb.wb.search("lesen"));
		
		

//		Dictionary<String, String> wb;
				
//		wb.insert("lesen", "read");
//		wb.insert("schreiben", "write");
//		
//		System.out.println(wb.search("lesen"));
//	}


}
