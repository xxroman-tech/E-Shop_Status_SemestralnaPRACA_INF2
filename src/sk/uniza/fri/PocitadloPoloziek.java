package sk.uniza.fri;

import sk.uniza.fri.objednavka.Objednavka;
import sk.uniza.fri.tovar.Tovar;
import sk.uniza.fri.tovar.TypTovaru;
import java.util.ArrayList;

/**
 * 04/04/2021 - 12:49
 * Trieda PocitadloPoloziek slúži na sčítavanie celkovejhodnoty a celkového počtu položiek
 * daného typu. V mojom programe o type rozhoduje Enum TypTovaru.
 *
 * @author romanlojko
 */
public class PocitadloPoloziek {
    private Sklad skladPoloziek;
    private int celkovaHodnotaPolozky;
    private int celkovyPocetPolzky;


    private ArrayList<Tovar> polozky;
    private ArrayList<Tovar> zoznamZakliknutychPoloziek;

    /**
     * Parametrický konštruktor
     * @param cestaKSuboru
     */
    public PocitadloPoloziek(String cestaKSuboru) {
        this.skladPoloziek = new Sklad(cestaKSuboru);
        this.polozky = new ArrayList<>(this.skladPoloziek.getZoznamTovaru());
        this.zoznamZakliknutychPoloziek = new ArrayList<>();
        this.celkovyPocetPolzky = 0;
        this.celkovaHodnotaPolozky = 0;
    }

    /**
     * Metóda ktorá spočíta, celkovú hodnotu položiek a počet kusov na sklade v jednotlivých kategóriách
     * @param polozka
     */
    private void spocitajPolozky(TypTovaru polozka) {
        for (Tovar aktTovar : this.polozky) {
            if (aktTovar.getTypTovaru().equals(polozka)) {
                this.celkovaHodnotaPolozky += aktTovar.getCena();
                this.celkovyPocetPolzky += aktTovar.getPocetNaSklade();
            }
        }
    }

    /**
     * Metóda ktorá bracia pole String pre zobrazenie to JTable v hlavnej Aplikácií
     * @param polozka
     * @return
     */
    public String[] getPolozky(TypTovaru polozka) {
        this.spocitajPolozky(polozka);
        return new String[] { polozka.getAsString(), String.valueOf(this.celkovyPocetPolzky), String.valueOf(this.celkovaHodnotaPolozky) };
    }

    /**
     * Metóda ktorá slúži na zrátanie zakliknutýhc položiek v jednotlivých kategóriách
     * @param polozka
     */
    private void spocitajZakliknutePolozky(TypTovaru polozka) {
        for (Tovar aktTovar : this.polozky) {
            if (aktTovar.getTypTovaru().equals(polozka)) {
                this.zoznamZakliknutychPoloziek.add(aktTovar);
            }
        }
    }

    /**
     * Getter ktorý vracia zoznam zakliknutýhc položiek v Jtable
     * @param polozka
     * @return
     */
    public ArrayList<Tovar> getZoznamZakliknutychPoloziek(TypTovaru polozka) {
        this.spocitajZakliknutePolozky(polozka);
        ArrayList<Tovar> kopia = new ArrayList<Tovar>(this.zoznamZakliknutychPoloziek);
        return kopia;
    }
}
