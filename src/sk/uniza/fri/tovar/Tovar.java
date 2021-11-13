package sk.uniza.fri.tovar;

/**
 * 04/04/2021 - 12:49
 * Abstrakná trieda tovar ktorá je predkom tried Mobil, Pocitac, HernaKonzola
 *
 * @author romanlojko
 */
public abstract class Tovar {
    private StavTovaru stavTovaru;
    private int pocetNaSklade;
    private int cena;
    private int rokVyroby;
    private String vyrobca;
    private String model;

    /**
     * Parametrický konštruktor
     * @param stavTovaru
     * @param pocetNaSklade
     * @param cena
     * @param rokVyroby
     * @param vyrobca
     * @param model
     */
    public Tovar(StavTovaru stavTovaru, int pocetNaSklade, int cena, int rokVyroby,
                 String vyrobca, String model) {
        this.stavTovaru = stavTovaru;
        this.pocetNaSklade = pocetNaSklade;
        this.cena = cena;
        this.rokVyroby = rokVyroby;
        this.vyrobca = vyrobca;
        this.model = model;
    }

    public abstract TypTovaru getTypTovaru();

    /**
     * Gettery triedy
     * @return
     */
    public StavTovaru getStavTovaru() {
        return this.stavTovaru;
    }

    public int getPocetNaSklade() {
        return this.pocetNaSklade;
    }

    public int getCena() {
        return this.cena;
    }

    public int getRokVyroby() {
        return this.rokVyroby;
    }

    public String getVyrobca() {
        return this.vyrobca;
    }

    public String getModel() {
        return this.model;
    }

    /**
     * Metoda ktora vracia popis tovaru pre fakturu
     * @return
     */
    public String popisPrePdf() {
        return "Tovar:";
    }
}
