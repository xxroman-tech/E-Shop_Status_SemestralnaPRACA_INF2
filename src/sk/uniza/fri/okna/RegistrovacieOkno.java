package sk.uniza.fri.okna;

import sk.uniza.fri.CitacHesiel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * 04/04/2021 - 12:49
 * GUI trieda slúži na registráciu nového používateľa
 *
 * @author romanlojko
 */
public class RegistrovacieOkno extends JFrame {
    private JLabel nazovAplikacie;
    private JTextField menoTextovePole;
    private JPasswordField hesloPrvePole;
    private JPasswordField hesloDruhePole;
    private JButton registrovatButton;
    private JPanel registrovacieOkno;

    /**
     * Bezparametricky konstruktor
     */
    public RegistrovacieOkno() {
        this.add(this.registrovacieOkno);
        this.pack();
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);

        this.registrovatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (vytvorenieKontaHelper()) {
                    dispose();
                }
            }
        });
    }

    /**
     * Kontroluje či sa nenechádza už dané použiváteľske meno v mojom systéme ak nie tak kontroluje či sa zadane hesla zhodujú
     * a či je vyplnený formulár
     * @return
     */
    private boolean vytvorenieKontaHelper() {
        String meno = this.menoTextovePole.getText();
        String heslo = String.valueOf(this.hesloPrvePole.getPassword());
        String heslo2 = String.valueOf(this.hesloDruhePole.getPassword());

        if (this.kontrolaVyplneniaFormulara(meno, heslo, heslo2)) {
            if (this.kontrolaZopakovaniaHesla(heslo, heslo2)) {
                try {
                    CitacHesiel citac = new CitacHesiel();
                    if (!citac.kontrolaPouzivatelskehoMena(meno)) {
                        citac.ulozHesla(meno, heslo);
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(
                                null, "Meno uz existuje", "Chyba", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException e) {
                }
            }
        } else {
            JOptionPane.showMessageDialog(
                    null, "Nevyplnil si formular", "Chyba", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    /**
     * Pomocnna metoda pre kontrolu vyplnenia formulara
     * @return
     */
    private boolean kontrolaVyplneniaFormulara(String meno, String heslo1, String heslo2) {
        if (!meno.equals("") || !heslo1.equals("") || !heslo2.equals("")) {
            return true;
        }

        return false;
    }

    /**
     * Pomocna metoda pre kontrolu zopakovania hesla dva krat
     * @param prveHeslo
     * @param druheHeslo
     * @return
     */
    private boolean kontrolaZopakovaniaHesla(String prveHeslo, String druheHeslo) {
        if (prveHeslo.equals(druheHeslo)) {
            return true;
        } else {
            JOptionPane.showMessageDialog(
                    null, "Nezadal si spravne heslo", "Chyba", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

}
