package main.chiffrement;

public class Chiffrement {
	
	private DES des = new DES();
    private TripleDES tripleDes = new TripleDES();

    public int[] crypter(String message, String mode) {
        if (mode.equals("DES")) {
            return des.crypte(des.stringToBits(message,des.getEncodage()));
        }
        return tripleDes.crypteTripleDES(message);
    }

    public String decrypter(int[] encryptedMessage, String mode) {
        if (mode.equals("DES")) {
            return des.bitsToString(des.decrypte(encryptedMessage), des.getEncodage());
        }
        return tripleDes.decryptTripleDES(encryptedMessage);
    }
    
    public void setEncodage(String encodage, String mode) {
    	if (mode.equals("DES")) {
    		des.setEncodage(encodage);
    	} else {
    		tripleDes.setEncodage(encodage);
    	}
    }
    
    /**
     * Converts the list into a string.
     * @param bloc to transform
     * @return string representing the bloc as a string
     */
    public String toString(int[] bloc) {
    	if (bloc == null || bloc.length == 0) {
            return "";
        }
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < bloc.length; i++) {
    		sb.append(bloc[i]);
    		if (i < bloc.length-1) {
    			sb.append(",");
    		}
    	}
    	return sb.toString();
    }

    /**
     * Converts a character string into a list of integers.
     * @param s the string to convert
     * @return the expected list of integer
     */
    public int[] fromString(String s) throws IllegalArgumentException{
        if (s == null || s.isEmpty()) {
            return new int[0];
        }

        String[] parts = s.split(",");
        int[] result = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            result[i] = Integer.parseInt(parts[i].trim());
            if (result[i] != 0 && result[i] != 1) {
            	throw new IllegalArgumentException();
            }
        }
        return result;
    }
}
