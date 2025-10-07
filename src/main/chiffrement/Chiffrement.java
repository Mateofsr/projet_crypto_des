package main.chiffrement;

public class Chiffrement {
	
	private DES des = new DES();
    private TripleDES tripleDes = new TripleDES();

    public int[] crypter(String message, String mode) {
        if (mode.equals("DES")) {
            return des.crypte(message);
        } else {
            return tripleDes.crypteTripleDES(message);
        }
    }

    public String decrypter(int[] encryptedMessage, String mode) {
        if (mode.equals("DES")) {
            return des.decrypte(encryptedMessage);
        } else {
            return tripleDes.decryptTripleDES(encryptedMessage);
        }
    }
    
    public int[] stringToBits(String message) {
    	return des.stringToBits(message, des.getEncodage());
    }
    
    public String bitsToString(int[] bloc) {
    	return des.bitsToString(bloc, des.getEncodage());
    }
    
    public void setEncodage(String encodage, String mode) {
    	if (mode.equals("DES")) {
    		des.setEncodage(encodage);
    	} else {
    		tripleDes.setEncodage(encodage);
    	}
    }
}
