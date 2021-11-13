package sk.uniza.fri.tovar;

/**
 * 04/04/2021 - 12:49
 * Trieda Pocitac je potomkom Triedy Tovar
 *
 * @author romanlojko
 */
public class Pocitac extends Tovar {
    private int velkostDispleja;
    private boolean externaGrafickaKarta;
    private String znackaGrafickejKarty;
    private String typKlavesnice;

    /**
     * Parametrický konštruktor
     * @param stavTovaru
     * @param pocetNaSklade
     * @param cena
     * @param rokVyroby
     * @param vyrobca
     * @param model
     * @param velkostDispleja
     * @param externaGrafickaKarta
     * @param znackaGrafickejKarty
     * @param typKlavesnice
     */
    public Pocitac(StavTovaru stavTovaru, int pocetNaSklade, int cena, int rokVyroby,
                   String vyrobca, String model, int velkostDispleja, boolean externaGrafickaKarta,
                   String znackaGrafickejKarty, String typKlavesnice) {
        super(stavTovaru, pocetNaSklade, cena, rokVyroby, vyrobca, model);
        this.velkostDispleja = velkostDispleja;
        this.externaGrafickaKarta = externaGrafickaKarta;
        this.znackaGrafickejKarty = znackaGrafickejKarty;
        this.typKlavesnice = typKlavesnice;
    }

    /**
     * Používam viacnásobný konštruktor kvoli tomu že počítač môže mať externu graficku kartu ale aj nemusí
     * @param stavTovaru
     * @param pocetNaSklade
     * @param cena
     * @param rokVyroby
     * @param vyrobca
     * @param model
     * @param velkostDispleja
     * @param externaGrafickaKarta
     * @param typKlavesnice
     */
    public Pocitac(StavTovaru stavTovaru, int pocetNaSklade, int cena, int rokVyroby,
                   String vyrobca, String model, int velkostDispleja, boolean externaGrafickaKarta, String typKlavesnice) {
        super(stavTovaru, pocetNaSklade, cena, rokVyroby, vyrobca, model);
        this.velkostDispleja = velkostDispleja;
        this.externaGrafickaKarta = externaGrafickaKarta;
        this.typKlavesnice = typKlavesnice;
    }

    /**
     * Pomocná metóda ktorá určuje či počítač má eternú grafickú kartu
     * @return
     */
    private String jeExternaGrafickaKarta() {
        if (this.externaGrafickaKarta) {
            return "externá grafická karta";
        } else {
            return "integrovaná grafická karta";
        }
    }

    /**
     * Getter pre TypTovaru
     * @return
     */
    @Override
    public TypTovaru getTypTovaru() {
        return TypTovaru.POCITAC;
    }

    /**
     * Metóda popisPrePdf slúži ako informácia o produkte vo faktúre
     * @return
     */
    @Override
    public String popisPrePdf() {
        return "[ Rok výroby: " + getRokVyroby() + ", " + getVyrobca() + " " + getModel() + ", "
                + this.jeExternaGrafickaKarta() + " " + this.znackaGrafickejKarty + ", "
                + this.typKlavesnice + " klávesnica, " + this.velkostDispleja + " ]";
    }
}
