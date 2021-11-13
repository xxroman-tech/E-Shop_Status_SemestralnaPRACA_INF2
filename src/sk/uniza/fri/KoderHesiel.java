package sk.uniza.fri;

/**
 * 04/04/2021 - 12:49
 * Tried KoderHesiel slúži na jednoduché zašifrovanie hesla pomocou Kluca
 * Tento algoritmus som našiel na stránka teda nieje moj
 * link - som bohužial nenašiel, keďže som si ho neuložil
 *
 * @author romanlojko
 */
public class KoderHesiel {
    private static final int KLUC = 2435;

    public KoderHesiel() { }

    /**
     * Metoda Sifrovania
     * @param heslo
     * @return
     */
    public String siforvanie(String heslo) {
        char[] pomPole = heslo.toCharArray();

        for (int i = 0; i < pomPole.length; i++) {
            pomPole[i] += KLUC;
        }

        return String.valueOf(pomPole);
    }
}
