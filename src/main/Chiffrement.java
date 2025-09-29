package main;

public class Chiffrement {
	
	/**
	 * DES encryption
	 * @param args
	 */
	public static void main(String[] args) {
		
		DES des = new DES();
		
		String message = "Je pense avoir fait du bon travail!";
		// Cryptage
		int[] messageCryte = des.crypte(message);
		
		// Decryptage
		String messageDecrypte = des.decrypte(messageCryte);
		System.out.println(messageDecrypte);
	}
}
