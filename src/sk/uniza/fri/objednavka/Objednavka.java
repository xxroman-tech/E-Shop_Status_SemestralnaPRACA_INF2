package sk.uniza.fri.objednavka;

import sk.uniza.fri.tovar.Tovar;

import java.util.ArrayList;

/**
 * 04/04/2021 - 12:49
 * Trieda ktorá slúži ako zoznam pre objednaný tovar, slúži hlavne pre zobrazenie v tabulke v hlavnej Aplikacii
 *
 * @author romanlojko
 */
public class Objednavka {
    private StavObjednavky stavObjednavky;
    private int cena;
    private int cisloObjednavky;
    private ArrayList<Tovar> zoznamPloziek;

    /**
     * Parametrický konštrukor
     * @param stavObjednavky
     * @param cena
     */
    public Objednavka(StavObjednavky stavObjednavky, int cena) {
        this.zoznamPloziek = new ArrayList<>();
        this.stavObjednavky = stavObjednavky;
        this.cena = cena;
        this.cisloObjednavky = 1;
    }

    /**
     * Metoda ktorá pridá objekt triedy Tovar do zoznamu Poloziek
     * @param tovar
     */
    public void pridajDoObjednavky(Tovar tovar) {
        this.zoznamPloziek.add(tovar);
    }

    /**
     * Getter pre zoznam ArrayList, keďže som nechcel porušiť zapúzdrenie, tak som vytvoril kópiu zoznamu a tú posúvam ďalej
     * @return
     */
    public ArrayList<Tovar> getZoznamPloziek() {
        ArrayList<Tovar> kopia = new ArrayList<Tovar>(this.zoznamPloziek);
        return kopia;
    }
}
