package sk.uniza.fri.objednavka;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * 04/04/2021 - 12:49
 *
 * Trieda GeneratorCislaObjednávky slúži na generovanie čísel objednávok, pomocou Random triedy. Ďalej ju používam na
 * nacítanie císla objednávky pre dalsie pracovanie snim.
 *
 * @author romanlojko
 */
public class GeneratorCislaObjednavky {
    private HashMap<Integer, String> zoznamCisielObjednavok;

    /**
     * Bezparametricky konštruktor, ktorý vždy zavolá metódu na načítanie dát z .txt súboru
     */
    public GeneratorCislaObjednavky() {
        this.zoznamCisielObjednavok = new HashMap<>();
        this.nacitajCislaObjednavok("objednavky/objednavkyData.txt");
    }

    public void nacitajCislaObjednavok(String cestaKSuboru) {
        try {
            File subor = new File(cestaKSuboru);
            Scanner citac = new Scanner(subor);

            while (citac.hasNext()) {
                int cisloObjednavky = citac.nextInt();
                String cestaKSuboruNacitana = citac.next();
                String cestaKPDF = citac.next();

                this.zoznamCisielObjednavok.put(cisloObjednavky, cestaKSuboruNacitana);
            }

            citac.close();
        } catch (IOException ioe) { }
    }

    /**
     * Generator random cisla
     *
     * @return
     */
    public int vygenerujCislo() {
        Random rand = new Random();
        int cislo = rand.nextInt(1000);
        if (!this.zoznamCisielObjednavok.containsKey(cislo)) {
            return cislo;
        } else {
            this.vygenerujCislo();
        }

        return 0;
    }

    /**
     * Getter pre zoznam HashMap, keďže som nechcel porušiť zapúzdrenie, tak som vytvoril kópiu zoznamu a tú posúvam ďalej
     * @return
     */
    public HashMap<Integer, String> getZoznam() {
        HashMap<Integer, String> kopia = new HashMap<Integer, String>(this.zoznamCisielObjednavok);
        return kopia;
    }
}
