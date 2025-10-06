package main;

public class Chiffrement {
	
	/**
	 * Triple DES encryption
	 * @param args
	 */
	public static void main(String[] args) {
		
		TripleDES tDES = new TripleDES();
		String message = "Je pense avoir fait du bon travail!";
		// Encrypt
		int[] encryptedMessage = tDES.crypteTripleDES(message);
		// Decipher
		String clearMessage = tDES.decryptTripleDES(encryptedMessage);
		
		System.out.println(clearMessage);
	}
}
