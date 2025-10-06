package main.ui;

import javax.swing.*;

import main.chiffrement.Chiffrement;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetrePrincipale extends JFrame {
    private JTextArea texteClair;
    private JTextArea texteCrypte;
    private JButton boutonCrypter;
    private JButton boutonDecrypter;
    private JComboBox<String> modeChoix;

    private Chiffrement chiffrement;
    private int[] encryptedMessage;

    public FenetrePrincipale() {
        super("Chiffrement DES / TripleDES");
        chiffrement = new Chiffrement();

        texteClair = new JTextArea(5, 30);
        texteCrypte = new JTextArea(5, 30);
        texteCrypte.setEditable(false);

        boutonCrypter = new JButton("Crypter");
        boutonDecrypter = new JButton("Décrypter");
        modeChoix = new JComboBox<>(new String[]{"DES", "TripleDES"});

        setLayout(new BorderLayout());
        JPanel panelCentre = new JPanel(new GridLayout(2, 2, 10, 10));
        panelCentre.add(new JLabel("Texte clair :"));
        panelCentre.add(new JScrollPane(texteClair));
        panelCentre.add(new JLabel("Texte chiffré :"));
        panelCentre.add(new JScrollPane(texteCrypte));

        JPanel panelBas = new JPanel();
        panelBas.add(modeChoix);
        panelBas.add(boutonCrypter);
        panelBas.add(boutonDecrypter);

        add(panelCentre, BorderLayout.CENTER);
        add(panelBas, BorderLayout.SOUTH);

        boutonCrypter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texte = texteClair.getText();
                if (!texte.isEmpty()) {
	                String mode = (String) modeChoix.getSelectedItem();
	                encryptedMessage = chiffrement.crypter(texte, mode);
	                texteCrypte.setText(chiffrement.bitsToString(encryptedMessage));
                }
            }
        });

        boutonDecrypter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texte = texteCrypte.getText();
                if (!texte.isEmpty()) {
	                String mode = (String) modeChoix.getSelectedItem();
	                String resultat = chiffrement.decrypter(encryptedMessage,mode);
	                texteClair.setText(resultat);
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}