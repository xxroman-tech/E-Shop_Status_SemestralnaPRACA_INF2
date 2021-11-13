package sk.uniza.fri.okna;

import sk.uniza.fri.PocitadloPoloziek;
import sk.uniza.fri.Sklad;
import sk.uniza.fri.objednavka.Objednavka;
import sk.uniza.fri.objednavka.StavObjednavky;
import sk.uniza.fri.tovar.Tovar;
import sk.uniza.fri.tovar.TypTovaru;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 04/04/2021 - 12:49
 * GUI trieda ktorá slúži na vytvorenie novej objednávky
 *
 * @author romanlojko
 */
public class VytvoritObjednavkuOkno extends JFrame {
    private JPanel panel1;
    private JTextField menoTextField;
    private JTextField priezviskoTextField;
    private JTextField mailTextField;
    private JTextField telefonTextField;
    private JTextField mestoTextField;
    private JTextField ulicaTextField;
    private JTextField pscTextField;
    private JButton objednatButton;
    private JComboBox comboBoxKategoria;
    private JList<String> nakupnyList;
    private JLabel cenaSpolu;
    private int celkovaCiastka;

    private String meno;
    private String priezvisko;
    private String mail;
    private String telefon;
    private String mesto;
    private String ulica;
    private String psc;

    private String cestaKSuboru;
    private String cestaKSuboruObjednavok;
    private HashMap<String, Integer> praveZobrazeneProdukty;
    private ArrayList<Tovar> nakupenePolozky;
    private Sklad sklad;

    /**
     * Parametrický konštruktor s actionListenermi
     * @param cestaKSuboru
     */
    public VytvoritObjednavkuOkno(String cestaKSuboru) {
        this.praveZobrazeneProdukty = new HashMap<>();
        this.sklad = new Sklad(cestaKSuboru);
        this.nakupenePolozky = new ArrayList<>(this.sklad.getZoznamTovaru());
        this.nastavOkno();
        this.pridajPolozkyDoComboBoxu();

        this.cestaKSuboru = cestaKSuboru;

        this.comboBoxKategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rolovacieKategorie();
            }
        });

        this.nakupnyList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                zratajVybranePolozky();
            }
        });

        this.objednatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nacitajFormulare()) {
                    priradPolozkuDoObjednanych();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(
                            null, "Nevyplnil si formular", "Chyba", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Metoda ktorá nastaví hodnotu JLabelu na celkovú čiastku za vyznačené produkty
     * @param cenaSpolu
     */
    public void setCenaSpolu(int cenaSpolu) {
        this.cenaSpolu.setText(String.valueOf(cenaSpolu));
    }

    /**
     * Pomocná metóda pre nastavenieOkna
     */
    private void nastavOkno() {
        this.add(this.panel1);
        this.setTitle("Vytvoriť objednávku");
        this.pack();
        this.setSize(900, 700);
        this.setLocationRelativeTo(null);
        this.nakupnyList.setPreferredSize(new Dimension(250, 250));
    }

    /**
     * Pomocná metóda pre vloženie položiek do rolovacieho ComboBoxu
     */
    private void pridajPolozkyDoComboBoxu() {
        this.comboBoxKategoria.addItem("...");
        this.comboBoxKategoria.addItem(TypTovaru.MOBIL.getAsString());
        this.comboBoxKategoria.addItem(TypTovaru.POCITAC.getAsString());
        this.comboBoxKategoria.addItem(TypTovaru.HERNA_KONZOLA.getAsString());
    }

    /**
     * Metóda ktorá podľa zakliknutých položiek v comboBoxe nastaví List a zobrazí v nich udaje o tovaroch
     */
    private void rolovacieKategorie() {
        int vybrataKategoria = comboBoxKategoria.getSelectedIndex();
        this.nakupnyList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        DefaultListModel listModel = new DefaultListModel();
        ArrayList<Tovar> zoznamPoloziek = null;

        switch (vybrataKategoria) {
            case 2:
                this.praveZobrazeneProdukty.clear();
                zoznamPoloziek = new ArrayList<>(new PocitadloPoloziek(this.cestaKSuboru).getZoznamZakliknutychPoloziek(TypTovaru.MOBIL));
                for (Tovar aktTovar : zoznamPoloziek) {
                    String obsah = aktTovar.getVyrobca() + " " + aktTovar.getModel() + " -  " + aktTovar.getCena() + "€";
                    this.praveZobrazeneProdukty.put(obsah, aktTovar.getCena());
                    listModel.addElement(obsah);
                }
                this.nakupnyList.setModel(listModel);
                break;
            case 3:
                this.praveZobrazeneProdukty.clear();
                zoznamPoloziek = new ArrayList<>(new PocitadloPoloziek(this.cestaKSuboru).getZoznamZakliknutychPoloziek(TypTovaru.POCITAC));
                for (Tovar aktTovar : zoznamPoloziek) {
                    String obsah = aktTovar.getVyrobca() + " " + aktTovar.getModel() + " - " + aktTovar.getCena() + "€";
                    this.praveZobrazeneProdukty.put(obsah, aktTovar.getCena());
                    listModel.addElement(obsah);
                }
                this.nakupnyList.setModel(listModel);
                break;
            case 4:
                this.praveZobrazeneProdukty.clear();
                zoznamPoloziek = new ArrayList<>(new PocitadloPoloziek(this.cestaKSuboru).getZoznamZakliknutychPoloziek(TypTovaru.HERNA_KONZOLA));
                for (Tovar aktTovar : zoznamPoloziek) {
                    String obsah = aktTovar.getVyrobca() + " " + aktTovar.getModel() + " - " + aktTovar.getCena() + "€";
                    this.praveZobrazeneProdukty.put(obsah, aktTovar.getCena());
                    listModel.addElement(obsah);
                }
                this.nakupnyList.setModel(listModel);
                break;
        }
    }

    /**
     * Pomocná metóda pre zrátenie zakliknutých položiek
     */
    private void zratajVybranePolozky() {
        this.celkovaCiastka = 0;
        List<String> oznaceneProdukty = this.nakupnyList.getSelectedValuesList();
        for (String oznacenaPolozka: oznaceneProdukty) {
            if (this.praveZobrazeneProdukty.containsKey(oznacenaPolozka)) {
                this.celkovaCiastka += this.praveZobrazeneProdukty.get(oznacenaPolozka);
                this.setCenaSpolu(this.celkovaCiastka);
            }
        }
    }

    /**
     * Pomocná metóda pre pridanie polozky do objednaných
     */
    private void priradPolozkuDoObjednanych() {
        List<String> oznaceneProdukty = this.nakupnyList.getSelectedValuesList();
        Objednavka objednavka = new Objednavka(StavObjednavky.CAKA_NA_VYBAVENIE, this.celkovaCiastka);
        for (Tovar aktTovar : this.nakupenePolozky) {
            String obsah = aktTovar.getVyrobca() + " " + aktTovar.getModel() + " - " + aktTovar.getCena() + "€";
            for (String oznacenaPolozka : oznaceneProdukty) {
                if (oznacenaPolozka.equals(obsah)) {
                    System.out.println(aktTovar.getModel());
                    objednavka.pridajDoObjednavky(aktTovar);
                }
            }
        }
        this.sklad.nacitajObjednavku(objednavka.getZoznamPloziek(), StavObjednavky.CAKA_NA_VYBAVENIE, this.celkovaCiastka, this.meno, this.priezvisko,
                this.mail, this.telefon, this.mesto, this.ulica, this.psc);
    }

    /**
     * Pomocná metóda pre načitanie údajov z Formuláru
     * @return
     */
    private boolean nacitajFormulare() {
        this.meno = this.menoTextField.getText();
        this.priezvisko = this.priezviskoTextField.getText();
        this.mail = this.mailTextField.getText();
        this.telefon = this.telefonTextField.getText();
        this.mesto = this.mestoTextField.getText();
        this.ulica = this.ulicaTextField.getText();
        this.psc = this.pscTextField.getText();

        if (kontrolaVyplneniaFormulara()) {
            return true;
        }

        return false;
    }

    /**
     * Pomocná metóda pre kontrolu či sú vyplnené všetky formuláre
     * @return
     */
    private boolean kontrolaVyplneniaFormulara() {
        if (!this.meno.equals("") || !this.priezvisko.equals("") || !this.mail.equals("") || !this.telefon.equals("")
            || !this.mesto.equals("") || !this.ulica.equals("") || !this.psc.equals("")) {
            return true;
        }

        return false;
    }
}
