package sk.uniza.fri.objednavka;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.printing.PDFPageable;
import sk.uniza.fri.tovar.Tovar;

import javax.swing.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 04/04/2021 - 12:49
 * Trieda vytvára pdf dokument v ktorej je ulozeny doklad o kupe. Na toto používam externú knižnicu PDFBox od firmy Apache.
 * link - https://pdfbox.apache.org/index.html
 *
 * @author romanlojko
 */
public class VytvaracPDF {
    private PDDocument dokument;
    private PDPage strana;
    private final PDFont font = PDType1Font.HELVETICA;

    /**
     * Parametrický konštruktor, ktorý sa stará a vytvorenie inštancie triedy PDDocument z externej knižnice PDFBOX a
     * o kontrolu či nechcem našu faktúru rovno vytlačiť.
     *
     * @param cisloObj
     * @param cena
     * @param zoznamTovaru
     * @param meno
     * @param priezvisko
     * @param mail
     * @param telefon
     * @param mesto
     * @param ulica
     * @param psc
     */
    public VytvaracPDF(int cisloObj, int cena, ArrayList<Tovar> zoznamTovaru, String meno, String priezvisko, String mail,
                       String telefon, String mesto, String ulica, String psc) {
        this.dokument = new PDDocument();
        this.strana = new PDPage(PDRectangle.A4);
        this.dokument.addPage(strana);

        if (this.naplnDokument(cisloObj, cena, zoznamTovaru, meno, priezvisko, mail, telefon, mesto, ulica, psc)) {
            Object[] moznosti = {"Áno", "Nie"};
            int x = JOptionPane.showOptionDialog(null, "Chete vytlačiť novú objednávku ?", "Vytlačit ?", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, moznosti, moznosti[0]);
            // 0 je Ano, 1 ja Nie
            if (x == 0) {
                try {
                    if (this.vytlacPDF( String.valueOf(cisloObj))) {
                        System.out.println("podarilo sa vytlacit");
                    }
                } catch (IOException ioException) { }
            }
        }
    }

    /**
     * Privátna metoda naplnDokument slúži na vytvorenie samotného dokumentu s názvom ako číslo objednávky.
     *
     * @param cisloObjednavky
     * @param cena
     * @param zoznamTovaru
     * @param meno
     * @param priezvisko
     * @param mail
     * @param telefon
     * @param mesto
     * @param ulica
     * @param psc
     * @return
     */
    private boolean naplnDokument(int cisloObjednavky, int cena, ArrayList<Tovar> zoznamTovaru, String meno, String priezvisko, String mail,
                               String telefon, String mesto, String ulica, String psc) {
        try {
            PDPageContentStream kontentStream = new PDPageContentStream(dokument, strana);

            String nadpis = "Objednavka c." + cisloObjednavky;

            kontentStream.beginText();
            kontentStream.setFont(this.font, 11);
            kontentStream.setLeading(14.5f);
            kontentStream.newLineAtOffset(25, 775);

            kontentStream.showText(nadpis);
            kontentStream.newLine();
            kontentStream.newLine();
            kontentStream.newLine();
            kontentStream.newLine();
            kontentStream.newLine();

            kontentStream.showText("Prijemnca: " + priezvisko + " " + meno);
            kontentStream.newLine();
            kontentStream.showText("Mail: " + mail);
            kontentStream.newLine();
            kontentStream.showText("Telefon: " + telefon);
            kontentStream.newLine();
            kontentStream.showText("Adresa: " + mesto + ", " + ulica + ", " + psc + " " + mesto);
            kontentStream.newLine();
            kontentStream.newLine();

            kontentStream.showText("Odosielatel: Lojko Roman");
            kontentStream.newLine();
            kontentStream.showText("Mail: rlojko91@gmail.com");
            kontentStream.newLine();
            kontentStream.showText("Telefon: 0948458675");
            kontentStream.newLine();
            kontentStream.showText("Adres: Topolcany, Polovnicka 45, 95501 Topolcany");
            kontentStream.newLine();
            kontentStream.newLine();

            kontentStream.showText("Objednany tovar:");
            kontentStream.newLine();

            for (Tovar aktTovar : zoznamTovaru) {
                kontentStream.showText(aktTovar.getVyrobca() + " " + aktTovar.getModel() + " " + aktTovar.getRokVyroby() + " . . . . . " + aktTovar.getCena() + " eur");
                kontentStream.newLine();
                kontentStream.showText(aktTovar.popisPrePdf());
                kontentStream.newLine();
            }


            kontentStream.newLine();
            kontentStream.newLine();
            kontentStream.newLine();
            kontentStream.newLine();
            kontentStream.newLine();
            kontentStream.newLine();
            kontentStream.newLine();
            kontentStream.newLine();
            kontentStream.showText("Cena spolu: " + cena + " eur");


            kontentStream.endText();

            kontentStream.close();

            this.dokument.save("objednavky/" + cisloObjednavky + ".pdf");
            this.dokument.close();
            return true;
        } catch (IOException e) {
            throw new NullPointerException("Nepodarilo s");
        }
    }

    /**
     * Metoda vytlacPDF slúži na tlačenie pdf súboru pomocou externej knižnice PDFBOX a triedy Loader
     * @param cisloObjednavky
     * @return
     * @throws IOException
     */
    public boolean vytlacPDF(String cisloObjednavky) throws IOException {
        try (PDDocument dokument = Loader.loadPDF(new File("objednavky/" + cisloObjednavky + ".pdf"))) {
            this.print(dokument);
            return true;
        } catch (PrinterException exception) { }

        return false;
    }

    /**
     * Pomocná trieda print, ktorá používa triedu PrinterJob na tlačenie súboru
     * link - https://apache.googlesource.com/pdfbox/+/trunk/examples/src/main/java/org/apache/pdfbox/examples/printing/Printing.java
     * @param document
     * @throws PrinterException
     */
    private void print(PDDocument document) throws PrinterException {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));
        job.print();
    }
}
