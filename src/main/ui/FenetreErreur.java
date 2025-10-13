package main.ui;

import javax.swing.JOptionPane;
public class FenetreErreur {

    public static void showError(String message) {
        JOptionPane.showMessageDialog(
            null,
            message,
            "Une erreur est survenue",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
