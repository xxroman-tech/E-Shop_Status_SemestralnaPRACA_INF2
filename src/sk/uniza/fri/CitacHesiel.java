package sk.uniza.fri;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * 04/04/2021 - 12:49
 * Trieda slúži na ukladanie a načítanie používaťeľských hesiel
 *
 * @author romanlojko
 */
public class CitacHesiel {
    private HashMap<String, String> prihlasovacieUdaje;
    private KoderHesiel koderHesiel;

    /**
     * Bezparmetrický konštuktor
     * @throws IOException
     */
    public CitacHesiel() throws IOException {
        this.koderHesiel = new KoderHesiel();
        this.prihlasovacieUdaje = new HashMap<>();
    }

    /**
     * Pomocná metóda pre kontrolu či sa meno nachádza v zozname s požadovaným heslom
     * @param meno
     * @param heslo
     * @return
     */
    public boolean kontrolaPrihlasenia(String meno, String heslo) {
        if (this.prihlasovacieUdaje.containsKey(meno)) {
            String hesloZPamate = this.prihlasovacieUdaje.get(meno);
            if (hesloZPamate.equals(this.koderHesiel.siforvanie(heslo))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metóda ktorá kontroluje či sa meno nachádza v načítanom zozname
     * @param meno
     * @return
     */
    public boolean kontrolaPouzivatelskehoMena(String meno) {
        this.nacitajPrihlasovacieUdaje();
        if (this.prihlasovacieUdaje.containsKey(meno)) {
            return true;
        }

        return false;
    }

    /**
     * Metóda ktorá načítava prihlasovacie údaje do HashMap z .txt suboru
     */
    public void nacitajPrihlasovacieUdaje() {
        try {
            File subor = new File("uzivatelskeUcty/ucty.txt");
            Scanner citac = new Scanner(subor);

            while (citac.hasNext()) {
                String meno = citac.next();
                String heslo = citac.next();

                this.prihlasovacieUdaje.put(meno, heslo);
            }

            citac.close();
        } catch (FileNotFoundException ioe) {
            System.err.println("FileNotFoundException: " + ioe.getMessage());
        }
    }

    /**
     * Metóda slúži na uloženie mena a zašifrováneho hesla
     * @param meno
     * @param heslo
     */
    public void ulozHesla(String meno, String heslo) {
        try {
            String subor = "uzivatelskeUcty/ucty.txt";
            FileWriter zapisovac = new FileWriter(subor, true);
            zapisovac.write(meno + " " + this.koderHesiel.siforvanie(heslo) + "\n");
            zapisovac.close();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }
}



