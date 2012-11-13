/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Aufgabe1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;


public class DictionaryMenu extends JMenuBar implements ActionListener{

    private Woerterbuch wBuch;
    private JRadioButtonMenuItem itemSArray;
    private JRadioButtonMenuItem itemHash;
    private JRadioButtonMenuItem itemTree;
    private JRadioButtonMenuItem itemMap;
//    private JRadioButtonMenuItem itemHashMap;
//    private JRadioButtonMenuItem itemTreeMap;
    private JMenuItem itemLaden;
    private JMenuItem itemSpeichern;
    private JMenuItem itemPerformance;
    private ButtonGroup bgArt;
    private JFileChooser fc;

    public DictionaryMenu(Woerterbuch d) {
        super();
        wBuch = d;
        JMenu menuDatei = new JMenu("Datei");

        itemLaden = new JMenuItem("Laden");
        itemLaden.addActionListener(this);
        itemSpeichern = new JMenuItem("Speichern");
        itemSpeichern.addActionListener(this);
        itemPerformance = new JMenuItem("Performance messen");
        itemPerformance.addActionListener(this);
        menuDatei.add(itemLaden);
        menuDatei.add(itemSpeichern);
        menuDatei.add(new JSeparator());
        menuDatei.add(itemPerformance);

        JMenu menuImplementierung = new JMenu("Implementierung");
        itemSArray = new JRadioButtonMenuItem("SortedArrayDictionary");
        itemHash = new JRadioButtonMenuItem("HashDictionary");
        itemTree = new JRadioButtonMenuItem("TreeDictionary");
        itemMap = new JRadioButtonMenuItem("HashMapDictionary");
//        itemHashMap = new JRadioButtonMenuItem("HashMapDictionary");
//        itemTreeMap = new JRadioButtonMenuItem("TreeMapDictionary");
        itemSArray.setSelected(true);
        itemSArray.addActionListener(this);
        itemHash.addActionListener(this);
        itemTree.addActionListener(this);
        itemMap.addActionListener(this);
//        itemHashMap.addActionListener(this);
//        itemTreeMap.addActionListener(this);
        menuImplementierung.add(itemSArray);
        menuImplementierung.add(itemHash);
        menuImplementierung.add(itemTree);
        menuImplementierung.add(itemMap);
//        menuImplementierung.add(itemHashMap);
//        menuImplementierung.add(itemTreeMap);


        bgArt = new ButtonGroup();
        bgArt.add(itemSArray);
        bgArt.add(itemHash);
        bgArt.add(itemTree);
        bgArt.add(itemMap);
//        bgArt.add(itemHashMap);
//        bgArt.add(itemTreeMap);

        this.add(menuDatei);
        this.add(menuImplementierung);

        fc = new JFileChooser();
        fc.setSelectedFile(new File("."));
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        /*if (source == itemBeenden){
            if (JOptionPane.showConfirmDialog(this,
                    "Möchten Sie wirklich beenden?", "Beenden",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
        else*/ if (source == itemLaden) {
            fc.setDialogTitle("TelefonBuch lesen");
            if (fc.showOpenDialog(this) == JFileChooser.CANCEL_OPTION)
                return;
            try {
                wBuch.read(fc.getSelectedFile());
            }catch(IllegalArgumentException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
        else if (source == itemSpeichern){
            fc.setDialogTitle("TelefonBuch speichern");
            if (fc.showSaveDialog(this) == JFileChooser.CANCEL_OPTION)
                return;
            String fileName = fc.getSelectedFile().getPath();
            if (!fileName.endsWith(".txt")){
                fileName += ".txt";
            }
            File f = new File(fileName);
            System.out.println(f.getPath());
            if (f.exists()) {
                if (JOptionPane.showConfirmDialog(this,
                        "Datei existiert bereits. Möchten Sie überschreiben?",
                        "Überschreiben", JOptionPane.YES_NO_OPTION)
                        == JOptionPane.NO_OPTION) {
                    return;
                }
            }
            try {
                f.createNewFile();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }

            wBuch.save(f);
        }
        else if(source == itemSArray)
            wBuch.changeImplementation(Woerterbuch.SARRAY);
        else if(source == itemHash)
            wBuch.changeImplementation(Woerterbuch.HASH);
        else if(source == itemTree)
            wBuch.changeImplementation(Woerterbuch.TREE);
        else if(source == itemMap)
        	wBuch.changeImplementation(Woerterbuch.MAP);
//        else if(source == itemHashMap)
//            wBuch.changeImplementation(Woerterbuch.HASHMAP);
//        else if(source == itemTreeMap)
//            wBuch.changeImplementation(Woerterbuch.TREEMAP);
        else if(source == itemPerformance)
            JOptionPane.showMessageDialog(this, wBuch.performance());
    }


    
    
}
