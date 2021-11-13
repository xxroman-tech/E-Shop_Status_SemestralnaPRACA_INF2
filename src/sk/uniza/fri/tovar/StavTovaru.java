package sk.uniza.fri.tovar;

/**
 * 04/04/2021 - 12:49
 * Enum trieda StavTovaru slúži na zobrazenie či je tovar na sklade alebo je na objednávku
 *
 * @author romanlojko
 */
public enum StavTovaru {
    NA_SKLADE("skladom"),
    NA_OBJEDNAVKU("na objednávku");

    private String stavTovaru;

    StavTovaru(String stavTovaru) {
        this.stavTovaru = stavTovaru;
    }

    /**
     * Metóda vráti String reperezentáciu inštancie enumu
     * @return
     */
    public String getAsString() {
        return this.stavTovaru;
    }
}
