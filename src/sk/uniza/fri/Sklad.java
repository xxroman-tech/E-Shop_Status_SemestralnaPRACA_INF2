package sk.uniza.fri;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import sk.uniza.fri.objednavka.*;
import sk.uniza.fri.tovar.*;

/**
 * 04/04/2021 - 12:49
 * Trieda Sklad slúži na načítanie jednotlivých skladov zo súboru .txt
 *
 * @author romanlojko
 */
public class Sklad {
    private ArrayList<Tovar> zoznamTovaru;
    private ArrayList<Tovar> zoznamObjednavok;

    public Sklad() { }

    /**
     * Parametrický konštruktor
     * @param cestaKSuboru
     */
    public Sklad(String cestaKSuboru) {
        this.zoznamTovaru = new ArrayList<>();
        this.naplnSkladZoSuboru(cestaKSuboru);
    }

    /**
     * Metóda ktorá načíta dáta z vašeho zvoleného .txt skladu
     * @param cestaKSuboru
     */
    private void naplnSkladZoSuboru(String cestaKSuboru) {
        try {
            File suborNovy = new File(cestaKSuboru);
            Scanner citac = new Scanner(suborNovy);

            while (citac.hasNext()) {

                String typTovaru = citac.next();
                StavTovaru stavTovaru = null;

                if (typTovaru.equals("Mobil")) {
                    if (citac.next().equals("skladom")) {
                        stavTovaru = StavTovaru.NA_SKLADE;
                    } else {
                        stavTovaru = StavTovaru.NA_OBJEDNAVKU;
                    }
                    int pocetKusov = citac.nextInt();
                    int cena = citac.nextInt();
                    int rokVyroby = citac.nextInt();
                    String vyrobca = citac.next();
                    String model = citac.next();
                    int fotoaparat = citac.nextInt();
                    String genPripojenia = citac.next();
                    int velkostDispleja = citac.nextInt();

                    Mobil mobil = new Mobil(stavTovaru, pocetKusov, cena, rokVyroby,
                            vyrobca, model, fotoaparat, genPripojenia, velkostDispleja);

                    this.zoznamTovaru.add(mobil);
                } else if (typTovaru.equals("Pocitac")) {

                    if (citac.next().equals("skladom")) {
                        stavTovaru = StavTovaru.NA_SKLADE;
                    } else {
                        stavTovaru = StavTovaru.NA_OBJEDNAVKU;
                    }

                    int pocetKusov = citac.nextInt();
                    int cena = citac.nextInt();
                    int rokVyroby = citac.nextInt();
                    String vyrobca = citac.next();
                    String model = citac.next();
                    int velkostDispleja = citac.nextInt();
                    String externaGrafKarta = citac.next();
                    String znackaGrafickejKarty = citac.next();
                    String typKlavesnice = citac.next();

                    if (externaGrafKarta.equals("true")) {
                        Pocitac pc = new Pocitac(stavTovaru, pocetKusov, cena, rokVyroby, vyrobca, model,
                                velkostDispleja, true, znackaGrafickejKarty, typKlavesnice);
                        this.zoznamTovaru.add(pc);
                    } else if (externaGrafKarta.equals("false")) {
                        Pocitac pc = new Pocitac(stavTovaru, pocetKusov, cena, rokVyroby, vyrobca, model,
                                velkostDispleja, false, typKlavesnice);
                        this.zoznamTovaru.add(pc);
                    }

                } else if (typTovaru.equals("HernaKonzola")) {
                    if (citac.next().equals("skladom")) {
                        stavTovaru = StavTovaru.NA_SKLADE;
                    } else {
                        stavTovaru = StavTovaru.NA_OBJEDNAVKU;
                    }
                    int pocetKusov = citac.nextInt();
                    int cena = citac.nextInt();
                    int rokVyroby = citac.nextInt();
                    String vyrobca = citac.next();
                    String model = citac.next();
                    String podporovaneRozlisenie = citac.next();
                    String obsahujeOvladac = citac.next();

                    if (obsahujeOvladac.equals("true")) {
                        HernaKonzola hk = new HernaKonzola(stavTovaru, pocetKusov, cena, rokVyroby, vyrobca,
                                model, podporovaneRozlisenie, true);
                        this.zoznamTovaru.add(hk);
                    } else {
                        HernaKonzola hk = new HernaKonzola(stavTovaru, pocetKusov, cena, rokVyroby, vyrobca,
                                model, podporovaneRozlisenie, false);
                        this.zoznamTovaru.add(hk);
                    }
                }
            }

            citac.close();
        } catch (IOException ioe) {
            throw new NullPointerException("Nepodarilo sa nacitac zo suboru");
        }
    }

    /**
     * Pomocna metoda pre naplnenie skladu ako test
     */
    private void naplnSklad() {
        Mobil mobil1 = new Mobil(StavTovaru.NA_SKLADE, 1, 5,
                2020, "Apple", "Xs", 18,
                "5G", 5.5);
        Mobil mobil2 = new Mobil(StavTovaru.NA_SKLADE, 1, 5,
                2020, "Apple", "Xs", 18,
                "5G", 5.5);
        Mobil mobil3 = new Mobil(StavTovaru.NA_SKLADE, 1, 5,
                2020, "Apple", "Xs", 18,
                "5G", 5.5);
        Pocitac pc1 = new Pocitac(StavTovaru.NA_OBJEDNAVKU, 10, 10,
                2020, "Apple", "Macbook Air", 15,
                false, "Intel", "numericka");

        HernaKonzola hk1 = new HernaKonzola(StavTovaru.NA_OBJEDNAVKU, 5, 5000,
                2020, "Sony", "Playstation 5", "4K", true);

        this.zoznamTovaru.add(mobil1);
        this.zoznamTovaru.add(mobil2);
        this.zoznamTovaru.add(mobil2);
        this.zoznamTovaru.add(pc1);
        this.zoznamTovaru.add(pc1);
        this.zoznamTovaru.add(hk1);
    }

    /**
     * Načíta objednávku a vytvorí jej pdf súbor
     * @param zoznam
     * @param stavObjednavky
     * @param cena
     * @param meno
     * @param priezvisko
     * @param mail
     * @param telefon
     * @param mesto
     * @param ulica
     * @param psc
     */
    public void nacitajObjednavku(ArrayList<Tovar> zoznam, StavObjednavky stavObjednavky, int cena, String meno, String priezvisko, String mail,
                                  String telefon, String mesto, String ulica, String psc) {
        this.zoznamObjednavok = new ArrayList<>(zoznam);
        GeneratorCislaObjednavky generator = new GeneratorCislaObjednavky();
        int cisloObj = generator.vygenerujCislo();
        ZapisovacDatObjednavok zapisovac = new ZapisovacDatObjednavok(this.zoznamObjednavok, cisloObj, stavObjednavky, cena);
        VytvaracPDF pdf = new VytvaracPDF(cisloObj, cena, zoznam, meno, priezvisko, mail, telefon, mesto, ulica, psc);
    }

    /**
     * Getter zoznamu v ArrayList
     * @return
     */
    public ArrayList<Tovar> getZoznamTovaru() {
        ArrayList<Tovar> kopia = new ArrayList<Tovar>(this.zoznamTovaru);
        return kopia;
    }

}
