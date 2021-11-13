package sk.uniza.fri.objednavka;

/**
 * 04/04/2021 - 12:49
 * Trieda Enum StavObjednávky slúži na menenie stavu objednávky, či je vybavená, expedovaná, alebo sa ěstelen vybavuje
 * @author romanlojko
 */
public enum StavObjednavky {
    VYBAVENA("vybavena"),
    CAKA_NA_DODANIE("doručuje_sa"),
    CAKA_NA_VYBAVENIE("vybavuje_sa");

    private String stavObjednavky;

    StavObjednavky(String stavObjednavky) {
        this.stavObjednavky = stavObjednavky;
    }

    /**
     * Metóda vracia String hodnotu enumovej inštancie
     * @return
     */
    public String getAsString() {
        return this.stavObjednavky;
    }
}
