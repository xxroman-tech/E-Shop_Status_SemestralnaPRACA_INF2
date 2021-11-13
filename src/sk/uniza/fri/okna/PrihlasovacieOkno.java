package sk.uniza.fri.okna;

import sk.uniza.fri.CitacHesiel;
import sk.uniza.fri.ZapisovacDatPouzivatelov;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 04/04/2021 - 12:49
 * GUI trieda ktorá slúži na pustenie do systému po overení mena a hesla
 *
 * @author romanlojko
 */
public class PrihlasovacieOkno extends JFrame {
    private JTextField oknoPrihlasovacieMeno;
    private JLabel prihlasovacieMeno;
    private JLabel prihlasovacieHeslo;
    private JButton prihlasovacieTlacidlo;
    private JLabel nazovAplikacie;
    private JPanel main;
    private JPasswordField oknoPrihlasovocaieHeslo;
    private JButton registraciaButton;
    private JPanel login;

    private CitacHesiel citacHesiel;
    private String loginMeno;

    /**
     * Bezparametrický konštruktor
     */
    public PrihlasovacieOkno() {
        this.add(this.main);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);

        this.prihlasovacieTlacidlo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stlaceniePrihlasit();
            }
        });

        this.registraciaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stlacenieRegistrovat();
            }
        });

        this.oknoPrihlasovocaieHeslo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    stlaceniePrihlasit();
                }
            }
        });
    }

    /**
     * Pomocná metoda pre stlaćenie tlačidla Prihlásiť
     */
    private void stlaceniePrihlasit() {
        try {
            this.citacHesiel = new CitacHesiel();
            this.citacHesiel.nacitajPrihlasovacieUdaje();
        } catch (Exception ioe) {
        }

        if (this.kontrolaHeslaHelper()) {
            this.dispose();
            Aplikacia frame = new Aplikacia(this.loginMeno, this.getCestuKSuboru(this.loginMeno));
            frame.setTitle("e-shop STATUS");
            frame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(
                    null, "Zadal si zle prihlasovacie udaje.", "Chyba", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Pomocná metoda pre stlaćenie tlačidla Registrovať
     */
    private void stlacenieRegistrovat() {
        RegistrovacieOkno frame = new RegistrovacieOkno();
        frame.setTitle("Registrácia");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Pomocná metoda pre kontrolu hesla
     * @return
     */
    private boolean kontrolaHeslaHelper() {
        String meno = this.oknoPrihlasovacieMeno.getText();
        String heslo = String.valueOf(this.oknoPrihlasovocaieHeslo.getPassword());

        if (this.citacHesiel.kontrolaPrihlasenia(meno, heslo)) {
            this.loginMeno = meno;
            return true;
        }

        return false;
    }

    /**
     * Metoda ktora vracia cestu k suborom pre jednotliveho pouzivatela
     * @param meno
     * @return
     */
    private String getCestuKSuboru(String meno) {
        ZapisovacDatPouzivatelov data = new ZapisovacDatPouzivatelov();
        if (data.priradUzivateloviData(meno) != null) {
            return data.priradUzivateloviData(meno);
        }

        return "dataNotFound";
    }

}
