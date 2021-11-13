package sk.uniza.fri.tovar;


/**
 * 04/04/2021 - 12:49
 * Trieda MObil je potomkom triedy tovar
 *
 * @author romanlojko
 */
public class Mobil extends Tovar {
    private int fotoaparatMGpx;
    private String generaciaPripojenia;
    private double velkostDispleja;

    /**
     * Parametrický konštruktor
     * @param stavTovaru
     * @param pocetNaSklade
     * @param cena
     * @param rokVyroby
     * @param vyrobca
     * @param model
     * @param fotoaparatMGpx
     * @param generaciaPripojenia
     * @param velkostDispleja
     */
    public Mobil(StavTovaru stavTovaru, int pocetNaSklade, int cena, int rokVyroby,
                 String vyrobca, String model, int fotoaparatMGpx, String generaciaPripojenia,
                 double velkostDispleja) {
        super(stavTovaru, pocetNaSklade, cena, rokVyroby, vyrobca, model);
        this.fotoaparatMGpx = fotoaparatMGpx;
        this.generaciaPripojenia = generaciaPripojenia;
        this.velkostDispleja = velkostDispleja;
    }

    /**
     * Getter pre TypTovaru
     * @return
     */
    @Override
    public TypTovaru getTypTovaru() {
        return TypTovaru.MOBIL;
    }

    /**
     * Metóda popisPrePdf slúži ako informácia o produkte vo faktúre
     * @return
     */
    @Override
    public String popisPrePdf() {
        return "[ Rok výroby: " + getRokVyroby() + ", fotoaparát: " + this.fotoaparatMGpx + "MGpx,\n pripojenie: " + this.generaciaPripojenia
                + ",  velkost displeja: " + this.velkostDispleja + " ]";
    }
}
