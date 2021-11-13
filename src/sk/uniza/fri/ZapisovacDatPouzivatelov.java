package sk.uniza.fri;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * 04/04/2021 - 12:49
 * Metóda slúži na pridelenie každému používateľovi cestu ku jeho osobnému skladu
 *
 * @author romanlojko
 */
public class ZapisovacDatPouzivatelov {
    private HashMap<String, String> zoznamDat;
    private final String cestaKSuboru = "uzivatelskeUcty/data.txt";

    /**
     * Bezparametrický konštruktor
     */
    public ZapisovacDatPouzivatelov() {
        this.zoznamDat = new HashMap<>();
        this.nacitajData();
    }

    /**
     * Vracia String reťazec s dátami o uzivateľovi
     * @param meno
     * @return
     */
    public String priradUzivateloviData(String meno) {
        this.nacitajData();
        System.out.println(this.zoznamDat.get(meno));
        return this.zoznamDat.get(meno);
    }

    /**
     * Nacítava dáta zo suboru
     */
    public void nacitajData() {
        try {
            File subor = new File(this.cestaKSuboru);
            Scanner citac = new Scanner(subor);

            while (citac.hasNext()) {
                String meno = citac.next();
                String cestaKSuboru = citac.next();

                this.zoznamDat.put(meno, cestaKSuboru);
            }

            citac.close();
        } catch (IOException ioe) { }
    }

    /**
     * Ukladá dáta do súboru
     * @param meno
     * @param cestaKSuboru
     */
    public void ulozData(String meno, String cestaKSuboru) {
        if (!this.zoznamDat.containsKey(meno)) {
            try {
                String subor = this.cestaKSuboru;
                FileWriter zapisovac = new FileWriter(subor, true);
                zapisovac.write(meno + " " + cestaKSuboru  + "\n");
                zapisovac.close();
            } catch (IOException e) {
                System.err.println("IOException: " + e.getMessage());
            }
        } else if (!this.zoznamDat.get(meno).equals(cestaKSuboru)) {
            this.nacitajData();
            try {
                this.vymazData();
                String subor = this.cestaKSuboru;
                FileWriter zapisovac = new FileWriter(subor, true);
                for (String aktUser : this.zoznamDat.keySet()) {
                    if (aktUser.equals(meno)) {
                        zapisovac.write(aktUser + " " + cestaKSuboru + "\n");
                    } else {
                        zapisovac.write(aktUser + " " + this.zoznamDat.get(aktUser) + "\n");
                    }
                }
                zapisovac.close();
            } catch (IOException e) {
                System.err.println("IOException: " + e.getMessage());
            }
        }
    }

    /**
     * Pomocná metoda ktorá zmaže data
     * @throws IOException
     */
    public void vymazData() throws IOException {
        File subor = new File(this.cestaKSuboru);
        PrintWriter zapisovac = new PrintWriter(subor);

        zapisovac.close();
    }
}
