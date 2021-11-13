package sk.uniza.fri.tovar;

/**
 * 04/04/2021 - 12:49
 * Enum TypTovaru
 *
 * @author romanlojko
 */
public enum TypTovaru {
    MOBIL("Mobil"),
    POCITAC("Pocitac"),
    HERNA_KONZOLA("HernaKonzola");

    private String typTovaru;

    TypTovaru(String typTovaru) {
        this.typTovaru = typTovaru;
    }

    /**
     * Metóda ktorá vráti String reťazec inštancie triedy enum
     * @return
     */
    public String getAsString() {
        return this.typTovaru;
    }
}
