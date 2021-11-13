package sk.uniza.fri.objednavka;

import sk.uniza.fri.tovar.Tovar;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 04/04/2021 - 12:49
 * Trieda slúži na ukladanie pomocných údajov pre čísla objednávok, aby som vedel spätne
 * najst cestu ku ich pridadeným súborom.
 *
 * @author romanlojko
 */
public class ZapisovacDatObjednavok {

    /**
     * Bezparametrický konštruktor požítý keď som chcel použíť metodu nacitajUdajeOObjednavke
     */
    public ZapisovacDatObjednavok() { }

    /**
     * Parametrický konštruktor
     * @param zoznamTovaru
     * @param cisloObjednavky
     * @param stavObjednavky
     * @param cena
     */
    public ZapisovacDatObjednavok(ArrayList<Tovar> zoznamTovaru, int cisloObjednavky, StavObjednavky stavObjednavky, int cena) {
        this.ulozDataObjednavke(zoznamTovaru, cisloObjednavky, stavObjednavky, cena);
    }

    /**
     * Metóda ulozDataObjednavke slúži na spomínané uloženie dát objednávke ktorá obsahuje aj zoznam nakúpených produktov
     * @param zoznamTovaru
     * @param cisloObjednavky
     * @param stavObjednavky
     * @param cena
     */
    public void ulozDataObjednavke(ArrayList<Tovar> zoznamTovaru, int cisloObjednavky, StavObjednavky stavObjednavky, int cena) {
        ArrayList<Tovar> zoznam = new ArrayList<Tovar>(zoznamTovaru);
        if (zoznam != null) {
            try {
                String cestaKSuboru = "objednavky/" + cisloObjednavky + ".txt";
                File subor = new File(cestaKSuboru);
                PrintWriter zapisovac = new PrintWriter(subor);
                zapisovac.write(cisloObjednavky + " " + stavObjednavky.getAsString() + " " + cena + "\n");
                for (Tovar aktTovar : zoznam) {
                    zapisovac.write(aktTovar.getTypTovaru().getAsString() + " " + aktTovar.getStavTovaru().getAsString() + " "
                            + aktTovar.getPocetNaSklade() + " " + aktTovar.getCena() + " " + aktTovar.getRokVyroby() + " " + aktTovar.getVyrobca() + " "
                            + aktTovar.getModel() + "\n");
                }
                zapisovac.close();
            } catch (IOException e) {
                System.err.println("IOException: " + e.getMessage());
            }
            this.ulozDataObjednavok(cisloObjednavky);
        }
    }

    /**
     * Ulozi informaćiu o cestek súboru a čísle objednávky do data suboru
     * @param cisloObjednavky
     */
    public void ulozDataObjednavok(int cisloObjednavky) {
        try {
            String subor = "objednavky/objednavkyData.txt";
            FileWriter zapisovac = new FileWriter(subor, true);
            zapisovac.write(cisloObjednavky + " " + "objednavky/" + cisloObjednavky + ".txt" + " objednavky/" + cisloObjednavky + ".pdf" + "\n");
            zapisovac.close();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }

    /**
     * Táto metóda slúži na načítanie dát z objednávky do String pola pre zobrazenie v tabuľke v Aplikácií
     * @param cestaKSuboru
     * @return
     */
    public String[] nacitajUdajeOObjednavke(String cestaKSuboru) {
        int cisloObjednavky = 0;
        String stavObjednavky = "";
        int celkovaCiastka = 0;
        try {
            File subor = new File(cestaKSuboru);
            Scanner citac = new Scanner(subor);

            cisloObjednavky = citac.nextInt();
            stavObjednavky = citac.next();
            celkovaCiastka = citac.nextInt();

            citac.close();
        } catch (IOException ioe) { }

        return new String[] {String.valueOf(cisloObjednavky), stavObjednavky, String.valueOf(celkovaCiastka)};
    }
}
