package sk.uniza.fri.okna;

import sk.uniza.fri.PocitadloPoloziek;
import sk.uniza.fri.ZapisovacDatPouzivatelov;
import sk.uniza.fri.objednavka.GeneratorCislaObjednavky;
import sk.uniza.fri.objednavka.ZapisovacDatObjednavok;
import sk.uniza.fri.tovar.Tovar;
import sk.uniza.fri.tovar.TypTovaru;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 04/04/2021 - 12:49
 * Trieda je hlavnou gui aplikáciou, zobrazuje Stav na sklade, objednávky, jednotlivé položky, vieme cez ňu vytvárať objednávky
 * a vyskladnovať a naskladňovať tovar.
 *
 * @author romanlojko
 */
public class Aplikacia extends JFrame {
    private JPanel aplikacia;
    private JTable tabulka;
    private JButton naskladnitButton;
    private JButton vyskladnitButton;
    private JButton objednavkyButton;
    private JButton vytvorObjednavkuButton;
    private JButton odhlasenieButton;
    private JScrollPane scrollPane;
    private JButton spatButton;

    private boolean uzivatelJePrihlaseny;
    private PocitadloPoloziek pocitadloPoloziek;
    private DefaultTableModel model;
    private String meno;
    private String cestaKSuboru;

    /**
     * Parametrický konśtruktor
     * @param meno
     * @param paCestaKSuboru
     */
    public Aplikacia(String meno, String paCestaKSuboru) {
        this.nastavOkno();
        this.meno = meno;
        if (!paCestaKSuboru.equals("dataNotFound")) {
            this.cestaKSuboru = paCestaKSuboru;
            this.naplnTabulku(model, cestaKSuboru);
        }
        this.uzivatelJePrihlaseny = true;

        this.actionListenery();
    }

    /**
     * Metóda ktorá vytvára tabuľku
     */
    private void vytvorTabulku() {
        String[] nazvyStlpcov = {"Typ tovaru", "Počet ks", "Celková hodnota (€)"};
        this.model = new DefaultTableModel(nazvyStlpcov, 0);
        this.tabulka = new JTable(model);
        this.tabulka.getTableHeader().setFont(new Font(".AppleSystemUIFontMonospaced", Font.BOLD, 16));
    }

    /**
     * Metóda ktorá je použitá pri stlačení tlačidla späť
     */
    private void vratTabulkuDoPovodnehoStavu() {
        String[] nazvyStlpcov = {"Typ tovaru", "Počet ks", "Celková hodnota (€)"};
        this.model = new DefaultTableModel(nazvyStlpcov, 0);
        this.tabulka.setModel(this.model);
        this.tabulka.getTableHeader().setFont(new Font(".AppleSystemUIFontMonospaced", Font.BOLD, 16));
        this.naplnTabulku(this.model, this.cestaKSuboru);
    }

    /**
     * Naplni tabuĽku kategóriami
     * @param model
     * @param cestaKSuboru
     */
    private void naplnTabulku(DefaultTableModel model, String cestaKSuboru) {
        model.addRow(new PocitadloPoloziek(cestaKSuboru).getPolozky(TypTovaru.MOBIL));
        model.addRow(new PocitadloPoloziek(cestaKSuboru).getPolozky(TypTovaru.POCITAC));
        model.addRow(new PocitadloPoloziek(cestaKSuboru).getPolozky(TypTovaru.HERNA_KONZOLA));
    }

    /**
     * Naplní tabuĽku zakliknutými položkami
     * @param typTovaru
     */
    private void naplnTabulkuZakliknutymiPolozkami(TypTovaru typTovaru) {
        ArrayList<Tovar> zoznam = new ArrayList<>(new PocitadloPoloziek(cestaKSuboru).getZoznamZakliknutychPoloziek(typTovaru));
        for (Tovar aktTovar : zoznam) {
            this.model.addRow(new String[] {aktTovar.getVyrobca(), aktTovar.getModel(),
                    String.valueOf(aktTovar.getPocetNaSklade()), String.valueOf(aktTovar.getCena())});
        }
    }

    /**
     * Metoda pre zobrazenie nakliknutych položiek v kategóriach do JTable
     * @param typTovaru
     */
    private void zobrazZakliknutePolozky(TypTovaru typTovaru) {
        this.vymazObsahTabulky();
        String[] nazvyStlpcov = {"Výrobca", "Model",  "Počet ks", "Cena (€)"};
        this.model = new DefaultTableModel(nazvyStlpcov, 0);
        this.tabulka.setModel(this.model);
        this.tabulka.getTableHeader().setFont(new Font(".AppleSystemUIFontMonospaced", Font.BOLD, 16));
        this.naplnTabulkuZakliknutymiPolozkami(typTovaru);
    }

    /**
     * Metoda pre nastavenie rozmerov okna, aby bolo v strede
     */
    private void nastavOkno() {
        this.add(this.aplikacia);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
    }

    private void stlacenieNaskladnit() {
        Object[] moznosti = {"Ručne", "Zo súboru"};
        if (JOptionPane.showOptionDialog(this.aplikacia, "Zvolte si spôsob naskladenia", "Naskladenit", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, moznosti, moznosti[0]) == JOptionPane.YES_OPTION) {
            System.out.println("stlacil si rucne");
        } else {
            JFileChooser vyberacSuboru = new JFileChooser();
            int vybranySubor = vyberacSuboru.showOpenDialog(vyberacSuboru);
            if (vybranySubor == JFileChooser.APPROVE_OPTION) {
                this.vymazObsahTabulky();
                File oznacenySubor = vyberacSuboru.getSelectedFile();
                System.out.println(oznacenySubor.getPath());
                this.cestaKSuboru = oznacenySubor.getPath();
                this.naplnTabulku(this.model, this.cestaKSuboru);
            }
            System.out.println("stlacil si zo suboru");
        }
    }

    /**
     * Zisti ktora polozka resp. kategoria je zaklinkuta
     * @param selectionModel
     */
    private void zakliknutiePolozky(ListSelectionModel selectionModel) {
        int zakliknutyRiadok = selectionModel.getMinSelectionIndex();
        switch (zakliknutyRiadok) {
            case 0:
                zobrazZakliknutePolozky(TypTovaru.MOBIL);
                break;
            case 1:
                zobrazZakliknutePolozky(TypTovaru.POCITAC);
                break;
            case 2:
                zobrazZakliknutePolozky(TypTovaru.HERNA_KONZOLA);
                break;
        }
    }

    private void stlacenieOdhlasit(ActionEvent e) {
        if (this.uzivatelJePrihlaseny) {
            Object[] moznosti = {"Áno", "Nie"};
            int x = JOptionPane.showOptionDialog(this.aplikacia, "Chete uložiť svoje dáta ?", "Pozor!", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, moznosti, moznosti[0]);
            // 0 je Ano, 1 ja Nie
            if (x == 0) {
                ZapisovacDatPouzivatelov data = new ZapisovacDatPouzivatelov();
                data.ulozData(this.meno, this.cestaKSuboru);
                this.uzivatelJePrihlaseny = false;
                System.exit(1);
            } else if (x == 1) {
                int odpoved = JOptionPane.showConfirmDialog(
                        null, "Vaše dáta budú zmazané\nNaozaj chcete pokračovať ?", "Pozor!", JOptionPane.ERROR_MESSAGE);
                if (odpoved == 0) {
                    this.uzivatelJePrihlaseny = false;
                    System.exit(1);
                }
            }
        } else {
            JOptionPane.showMessageDialog(
                    null, "Nastal problem pri odhlasovani", "Chyba", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void vymazObsahTabulky() {
        while (this.model.getRowCount() > 0) {
            this.model.removeRow(0);
        }
    }

    private void createUIComponents() {
        this.vytvorTabulku();
    }

    private void stlacenieVytvoritObjednavku() {
        if (this.cestaKSuboru != null) {
            VytvoritObjednavkuOkno vytvorenieObjednavkyOkno = new VytvoritObjednavkuOkno(this.cestaKSuboru);
            vytvorenieObjednavkyOkno.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(
                    null, "Nemáte nič na sklade!", "Chyba", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void stlacenieTlacidlaObjednavky() {
        this.vymazObsahTabulky();
        String[] nazvyStlpcov = {"Číslo objednávky", "Stav objednávky",  "Čiastka na úhradu (€)"};
        this.model = new DefaultTableModel(nazvyStlpcov, 0);
        this.tabulka.setModel(this.model);
        this.tabulka.getTableHeader().setFont(new Font(".AppleSystemUIFontMonospaced", Font.BOLD, 16));

        HashMap<Integer, String> zoznam = new HashMap<>(new GeneratorCislaObjednavky().getZoznam());
        for (String objednavka : zoznam.values()) {
            this.model.addRow(new ZapisovacDatObjednavok().nacitajUdajeOObjednavke(objednavka));
        }
    }

    /**
     * Metoda actionListenery pre sprehladnenie konštruktora
     */
    private void actionListenery() {
        this.odhlasenieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stlacenieOdhlasit(e);
            }
        });

        this.naskladnitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stlacenieNaskladnit();
            }
        });

        ListSelectionModel selectionModel = tabulka.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                zakliknutiePolozky(selectionModel);
            }
        });

        this.vyskladnitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vymazObsahTabulky();
            }
        });

        this.vytvorObjednavkuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stlacenieVytvoritObjednavku();
            }
        });

        this.spatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vymazObsahTabulky();
                vratTabulkuDoPovodnehoStavu();
            }
        });

        this.objednavkyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stlacenieTlacidlaObjednavky();
            }
        });
    }

}
