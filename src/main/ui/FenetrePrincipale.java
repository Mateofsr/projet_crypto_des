package main.ui;

import javax.swing.*;

import main.chiffrement.Chiffrement;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Window containing all of the application's features. It allows you to
 * encrypt and decipher a message by changing the encryption type (DES or triple
 * DES) and encoding (utf-8, utf-16, etc.).
 */
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
        
        JPanel panelCentre = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panelCentre.add(new JLabel("Texte clair :", SwingConstants.CENTER), gbc);

        gbc.gridx = 1;
        panelCentre.add(new JScrollPane(texteClair), gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCentre.add(new JLabel("Texte chiffré :", SwingConstants.CENTER), gbc);

        gbc.gridx = 1;
        panelCentre.add(new JScrollPane(texteCrypte), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelCentre.add(new JLabel("Texte déchiffré :", SwingConstants.CENTER), gbc);

        gbc.gridx = 1;
        panelCentre.add(new JScrollPane(texteDecrypte), gbc);
        
        JPanel panelBas = new JPanel();
        panelBas.add(modeChoix);
        panelBas.add(encodageChoix);
        panelBas.add(boutonCrypter);
        panelBas.add(boutonDecrypter);
        panelBas.add(boutonClear);

        add(panelCentre, BorderLayout.CENTER);
        add(panelBas, BorderLayout.SOUTH);

        // Encryption of the message enter in the dedicated text field.
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

        // Decryption of the bit list in the dedicated text field.
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

        // Clear all the text field
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