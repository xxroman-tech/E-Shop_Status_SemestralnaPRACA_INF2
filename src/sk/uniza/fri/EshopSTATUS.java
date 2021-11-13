package sk.uniza.fri;

import sk.uniza.fri.okna.PrihlasovacieOkno;

/**
 * Trieda EshopSTATUS ktorá spúšťa aplikáciu
 * User: romanlojko
 * Date: 04/04/2021
 * Time: 12:49
 */
public class EshopSTATUS {
    /**
     * Metoda main ktorá vytvára inštnaciu Prihlasovacieho okna
     * @param args
     */
    public static void main(String[] args) {
        PrihlasovacieOkno frame = new PrihlasovacieOkno();
        frame.setTitle("Prihlásenie");
        frame.setVisible(true);
    }
}
