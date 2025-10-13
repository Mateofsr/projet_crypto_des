package main.ui;

import javax.swing.*;

import main.chiffrement.Chiffrement;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetrePrincipale extends JFrame {
    private JTextArea texteClair;
    private JTextArea texteCrypte;
    private JTextArea texteDecrypte;
    private JButton boutonCrypter;
    private JButton boutonDecrypter;
    private JButton boutonClear;
    private JComboBox<String> modeChoix;
    private JComboBox<String> encodageChoix;
    

    private Chiffrement chiffrement;
    private int[] encryptedMessage;

    public FenetrePrincipale() {
        super("Chiffrement DES / TripleDES");
        chiffrement = new Chiffrement();

        texteClair = new JTextArea(5, 30);
        texteCrypte = new JTextArea(5, 30);
        texteDecrypte = new JTextArea(5, 30);
        texteDecrypte.setEditable(false);

        boutonCrypter = new JButton("Crypter");
        boutonDecrypter = new JButton("Décrypter");
        boutonClear = new JButton("Nettoyer");
        modeChoix = new JComboBox<>(new String[]{"DES", "TripleDES"});
        encodageChoix = new JComboBox<>(new String[]{"UTF-8", "UTF-16BE", "UTF-32BE"});
        
        setLayout(new BorderLayout());
        JPanel panelCentre = new JPanel(new GridLayout(3, 3, 10, 10));
        panelCentre.add(new JLabel("Texte clair :"));
        panelCentre.add(new JScrollPane(texteClair));
        panelCentre.add(new JLabel("Texte chiffré :"));
        panelCentre.add(new JScrollPane(texteCrypte));
        panelCentre.add(new JLabel("Texte déchiffré :"));
        panelCentre.add(new JScrollPane(texteDecrypte));
        
        JPanel panelBas = new JPanel();
        panelBas.add(modeChoix);
        panelBas.add(encodageChoix);
        panelBas.add(boutonCrypter);
        panelBas.add(boutonDecrypter);
        panelBas.add(boutonClear);

        add(panelCentre, BorderLayout.CENTER);
        add(panelBas, BorderLayout.SOUTH);

        boutonCrypter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texte = texteClair.getText();
                if (!texte.isEmpty()) {
	                String mode = (String) modeChoix.getSelectedItem();
	                chiffrement.setEncodage((String) encodageChoix.getSelectedItem(), mode);
	                encryptedMessage = chiffrement.crypter(texte, mode);
	                texteCrypte.setText(chiffrement.toString(encryptedMessage));
                }
            }
        });

        boutonDecrypter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texte = texteCrypte.getText();
                if (!texte.isEmpty()) {
	                String mode = (String) modeChoix.getSelectedItem();
	                chiffrement.setEncodage((String) encodageChoix.getSelectedItem(), mode);
	                try {
	                	String resultat = chiffrement.decrypter(chiffrement.fromString(texte),mode);
	                	texteDecrypte.setText(resultat);
	                } catch (IllegalArgumentException e1) {
	                	FenetreErreur.showError("La liste crypté n'est pas décryptable, un élément a probablement été altéré!");
	                }
                }
            }
        });

        
        boutonClear.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		texteClair.setText("");
        		texteCrypte.setText("");
        		texteDecrypte.setText("");
        	}
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}