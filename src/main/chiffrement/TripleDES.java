package main.chiffrement;

/**
 * TripleDES.class
 * Use the triple DES method with two keys: CryptedMessage = Encryptek3 (Decipherk2 (Encryptek1 (Message))).
 */
public class TripleDES {
	
	private DES desK1;
	private DES desK2;
	
	public TripleDES() {
		desK1 = new DES();
		desK2 = new DES();
	}
	

	/**
	 * Encrypt with the Triple DES with 2 keys (k1, k2, k3 and k1 = k3).
	 * @param message
	 * @return message ciphered
	 */
	public int[] crypteTripleDES(String message) {
		int[] messageCryteK1 = desK1.crypte(message);
		String messageCrypteK2 = desK2.decrypte(messageCryteK1);
		int[] messageCrypteK3 = desK1.crypte(messageCrypteK2);
		return messageCrypteK3;
	}
	
	/**
	 * Decipher with the Triple DES with 2 keys
	 * @param messageCrypteK3
	 * @return clear message
	 */
	public String decryptTripleDES(int[] messageCrypteK3) {
		String messageDecrypteK1 = desK1.decrypte(messageCrypteK3); 
		int[] messageDecrypteK2 = desK2.crypte(messageDecrypteK1);
		String messageDecrypteK3 = desK1.decrypte(messageDecrypteK2);
		return messageDecrypteK3;
	}
	
	/**
	 * Changes the encoding of the character strings used.
	 * @param encodage
	 */
	public void setEncodage(String encodage) {
		desK1.setEncodage(encodage);
		desK2.setEncodage(encodage);
	}
}
