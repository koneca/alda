package Aufgabe1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.UIManager;


public class DictionaryApplication extends JPanel implements ActionListener{

    private JFrame frame;
    private Woerterbuch wBuch;
    private JTextField tfDe;
    private JTextField tfEn;
    private JTextField tfSuchen;
    private JButton bSuchen;
    private JButton bLoeschen;
    private JToggleButton bPlus;
    private JTextArea ausgabe;
    private JPanel test;
    
    public DictionaryApplication() {
        super();
        wBuch = new Woerterbuch();
        try {
            wBuch.read(new File("./src/Aufgabe1/dtengl.txt"));
        } catch (Exception ex) {
        }
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Wörterbuch");
        frame.setLocation(100, 100);
        frame.setSize(300,200);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        //Menu
        JMenuBar menu = new DictionaryMenu(wBuch);
        frame.setJMenuBar(menu);

        //Zusammensetzen
        this.setLayout(new BorderLayout());
        init();

//        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
//                this.getClass().getResource("img/dictionary.png")));
        frame.setTitle("W�rterbuch");
        frame.setContentPane(this);
        frame.pack();
        frame.setMinimumSize(frame.getSize());
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((d.width - this.getWidth())/2,
                (d.height - this.getHeight())/2);
        frame.setVisible(true);
    }

    public static void main(String[] args){
        DictionaryApplication d = new DictionaryApplication();
    }

    private void init() {
        JPanel top = new JPanel(new GridBagLayout());

        test = new JPanel(new GridBagLayout());
        test.setVisible(false);
        top.setBorder(BorderFactory.createTitledBorder("Controls"));
        tfSuchen = new JTextField();
        tfSuchen.setPreferredSize(new Dimension(200, 20));
        tfSuchen.addActionListener(this);
        bSuchen = new JButton();
//        bSuchen.setIcon(new ImageIcon(this.getClass().getResource("img/search.png")));
        bSuchen.setText("search");
        bSuchen.addActionListener(this);
        bPlus = new JToggleButton();
//        bPlus.setIcon(new ImageIcon(this.getClass().getResource("img/add.png")));
        bPlus.setText("Add");
        bPlus.addActionListener(this);
        bLoeschen = new JButton();
//        bLoeschen.setIcon(new ImageIcon(this.getClass().getResource("img/delete.png")));
        bLoeschen.setText("remove");
        bLoeschen.addActionListener(this);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        top.add(bPlus, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        top.add(tfSuchen, gbc);
        gbc.gridx = 2;
        gbc.weightx = 0;
        top.add(bSuchen, gbc);
        gbc.gridx = 3;
        top.add(bLoeschen, gbc);
        this.add(top, BorderLayout.NORTH);

        
//        JLabel lDe = new JLabel(new ImageIcon(this.getClass().getResource("img/de.png")));
//        JLabel lEn = new JLabel(new ImageIcon(this.getClass().getResource("img/en.png")));
        tfDe = new JTextField();
        tfEn = new JTextField();
        tfDe.addActionListener(this);
        tfEn.addActionListener(this);
        gbc.gridx = 0;
//        test.add(lDe, gbc);
        test.setToolTipText("DE");
        gbc.gridy = 1;
//        test.add(lEn, gbc);
        test.setToolTipText("EN");
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.insets = new Insets(2, 2, 2, 2);
        test.add(tfEn, gbc);
        gbc.gridy = 0;
        test.add(tfDe, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        top.add(test, gbc);

        ausgabe = new JTextArea(10, 20);
        JScrollPane center = new JScrollPane(ausgabe);
        center.setBorder(BorderFactory.createTitledBorder("Ausgabe"));
        this.add(center, BorderLayout.CENTER);

    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == bSuchen || source == tfSuchen) {
            String ret = tfSuchen.getText().trim().toLowerCase();
            if (ret.equals("")) {
                ausgabe.setText(wBuch.toString());
                return;
            }
            ret = wBuch.search(ret);
            if (ret == null) {
                JOptionPane.showMessageDialog(frame, "Suche nicht erfolgreich");
                ausgabe.setText(wBuch.toString());
            } else {
                ausgabe.setText(ret);
                tfSuchen.setText("");
            }
        }
        else if (source == bPlus) {
            if(bPlus.isSelected()) {
                test.setVisible(true);
            } else {
                test.setVisible(false);
                add();
            }
        }
        else if (source == bLoeschen) {
            if(tfSuchen.getText().equals("")){
                JOptionPane.showMessageDialog(frame,
                        "Bitte deutsches Wort ausfüllen.", "",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            wBuch.remove(tfSuchen.getText().trim().toLowerCase());
            JOptionPane.showMessageDialog(frame,"Löschen erfolgreich");
            tfSuchen.setText("");
            ausgabe.setText(wBuch.toString());
        }
        else if (source == tfDe || source == tfEn) {
            bPlus.setSelected(false);
            test.setVisible(false);
            add();
        }

    }

    private void add() {
        String deu = tfDe.getText().trim().toLowerCase();
        String eng = tfEn.getText().trim().toLowerCase();
        if(deu.equals("") || eng.equals("")) {
            JOptionPane.showMessageDialog(frame,
                    "Bitte beide werte ausfülen",
                    "Eingabefehler", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String ret = wBuch.search(deu);
        if(ret != null) {
            int r = JOptionPane.showConfirmDialog(frame,
                    deu + " existiert bereits. Übersetzung: " + ret
                    + "\n Möchten Sie Überschreiben?",
                    "Überschreiben?", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.NO_OPTION)
                return;
        }
        wBuch.insert(deu, eng);
        tfDe.setText("");
        tfEn.setText("");
        ausgabe.setText(wBuch.toString());
    }

}
