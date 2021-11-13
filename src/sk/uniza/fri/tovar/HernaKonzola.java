package sk.uniza.fri.tovar;

/**
 * 04/04/2021 - 12:49
 * Trieda HernáKonozola je potomok triedy Tovar
 *
 * @author romanlojko
 */
public class HernaKonzola extends Tovar {
    private String podporovaneRozlisenie;
    private boolean obsahujeOvladac;

    /**
     * Parametrický konštruktor
     * @param stavTovaru
     * @param pocetNaSklade
     * @param cena
     * @param rokVyroby
     * @param vyrobca
     * @param model
     * @param podporovaneRozlisenie
     * @param obsahujeOvladac
     */
    public HernaKonzola(StavTovaru stavTovaru, int pocetNaSklade, int cena, int rokVyroby,
                        String vyrobca, String model, String podporovaneRozlisenie, boolean obsahujeOvladac) {
        super(stavTovaru, pocetNaSklade, cena, rokVyroby, vyrobca, model);
        this.podporovaneRozlisenie = podporovaneRozlisenie;
        this.obsahujeOvladac = obsahujeOvladac;
    }

    /**
     * Getter pre TypTovaru
     * @return
     */
    @Override
    public TypTovaru getTypTovaru() {
        return TypTovaru.HERNA_KONZOLA;
    }

    /**
     * Metóda popisPrePdf slúži ako informácia o produkte vo faktúre
     * @return
     */
    @Override
    public String popisPrePdf() {
        return "INFO: [ Rok vyroby: " + getRokVyroby() + ", podporuje rozlisenie: " +
                this.podporovaneRozlisenie + ", obsahuje ovladace: " + this.obsahujeOvladac + " ]";
    }
}
