package main;

import java.awt.List;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * DES (Data Encryption Standard) class
 */
public class DES {
	public final int TAILLE_BLOC = 64;
	private final int TAILLE_SOUS_BLOC = 32;
	private final int NB_RONDE = 1;
	
	public final int[] PERM_INITIAL = {
			57,49,41,33,25,17,9,1,
			59,51,43,35,27,19,11,3,
			61,53,45,37,29,21,13,5,
			63,55,47,39,31,23,15,7,
			56,48,40,32,24,16,8,0,
			58,50,42,34,26,18,10,2,
			60,52,44,36,28,20,12,4,
			62,54,46,38,30,22,14,6};
	public final int[] PERM_CHOICE_1 = {
			56,48,32,24,16,8,0,57,49,
			41,33,25,17,9,1,58,50,42,
			34,26,18,10,2,59,51,43,35,
			62,54,46,38,30,22,14,6,61,
			53,45,37,29,21,13,5,60,52,
			44,36,28,20,12,4,27,19,11,3};
	
	public final int[] PERM_CHOICE_2 = {
			13,16,10,23,0,4,2,27,14,
			5,20,9,22,18,11,3,25,7,
			15,6,26,19,12,1,40,51,30,
			36,46,54,29,39,50,44,32,
			47,43,48,38,55,33,52,45,
			41,49,35,28,31};
	
	public final int[][] S = {
			{14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
			{0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,6},
			{4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0},
			{15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13}};
	
	public final int[] E = {
			31,0,1,2,3,4,3,4,5,6,7,8,
			7,8,9,10,11,12,11,12,13,14,15,16,
			15,16,17,18,19,20,19,20,21,22,23,24,
			23,24,25,26,27,28,27,28,29,30,31,0};
	
	public final int[] P = {
			15,6,19,20,28,11,27,16,
			0,14,22,25,4,17,30,9,
			1,7,23,13,31,26,2,8,
			18,12,29,5,21,10,3,24
	};
	
	public int[] masterKey;
	public int[][] tabCles;
	
	public DES() {
		masterKey  = new int[TAILLE_BLOC];
		for (int i = 0; i < masterKey.length; i++) {
            masterKey[i] = -1;
        }
		tabCles = new int[1][48];
	}
	
	/**
	 * Convert a String into bits and insert them in a list of
	 * integer (only 0 and 1).
	 * @param message to convert
	 * @return bits of the converted string
	 */
	public int[] stringToBits(String message) {
		byte[] bytes = message.getBytes();
		int[] bits = new int[bytes.length * 8];
		for (int i = 0; i < bytes.length; i++) {
	        for (int j = 0; j < 8; j++) {
	        	bits[i * 8 + j] = (bytes[i] >> (7 - j)) & 1;
	        }
	    }
		return bits;
	}
	
	/**
	 * Convert a list of integer into bits then string.
	 * @param bloc of 0 and 1
	 * @return the cleared message
	 */
	public String bitsToString(int[] bloc) {
		int length = bloc.length / 8;
        byte[] bytes = new byte[length];

        for (int i = 0; i < length; i++) {
            int value = 0;
            for (int j = 0; j < 8; j++) {
                value = (value << 1) | bloc[i * 8 + j];
            }
            bytes[i] = (byte) value;
        }
        return new String(bytes, StandardCharsets.UTF_8);
	}
	
	/**
	 * Cut a list of integer into part of the specified length
	 * @param bloc of integer to cut
	 * @param sizeBlocs the size of the new blocs
	 * @return blocs of list of the specified length
	 */
	public int[][] decoupage(int[] bloc, int sizeBlocs) {
		if (sizeBlocs == 0  || bloc.length == 0) {
			// TODO 
		}
		int remainer = bloc.length % sizeBlocs;
		int nbBlocs = bloc.length / sizeBlocs + (remainer != 0 ? 1 : 0);
		
	    int[][] blocs = new int[nbBlocs][];
	    int useBlocs = remainer == 0 ? nbBlocs : nbBlocs - 1;
    	for (int i = 0; i < useBlocs; i++) {
	        blocs[i] = new int[sizeBlocs];
	        for (int y = 0; y < sizeBlocs; y++) {
	        	blocs[i][y] = bloc[i*sizeBlocs + y];
	        }
	    }
    	if (remainer != 0) {
    		int rang = nbBlocs - 1;
            blocs[rang] = new int[sizeBlocs];
            for (int i = 0; i < remainer; i++) {
            	blocs[rang][i] = bloc[rang * sizeBlocs + i];
            }
    	}
		return blocs;
	}
	
	/**
	 * Swaps the block list on the indices of the permutation table.
	 * @param permutationTable list of the indices use to swap the bloc
	 * @param bloc swapped
	 * @return permuteBloc the bloc after the swapping
	 */
	public int[] permutation(int[] permutationTable, int[] bloc) {
		int[] permutedBloc = new int[permutationTable.length];
		for (int i = 0; i < permutationTable.length; i++) {
			permutedBloc[i] = bloc[permutationTable[i]];
		}
		return permutedBloc;
	}
	
	public void setMasterKey() {
		for (int i = 0; i < TAILLE_BLOC; i++) {
			masterKey[i] = (int) (Math.random() * 2);
		}
	}
	
	public int[] getMasterKey() {
		return masterKey;
	}
	
	/**
	 * Shift the bloc by nbCran on the left
	 * @param bloc to shift
	 * @param nbCran the number we have to shift
	 * @return the bloc shifted
	 */
	public int[] decalleGauche(int[] bloc, int nbCran) {
		int shifter = nbCran % bloc.length;
		int[] blocShifted = new int[bloc.length];
		
		for (int i = shifter; i < bloc.length; i ++) {
			blocShifted[i - shifter] = bloc[i];
		}
		for (int i = 0; i < shifter; i++) {
			blocShifted[bloc.length-shifter+i] = bloc[i];
		}
		return blocShifted;
	}
	
	/**
	 * Stick the bloc in the specified list.
	 * @param blocs to stick
	 * @return the bloc sticked
	 */
	public int[] recollageBloc(int[][] blocs) {
		int finalSize = 0;
		for (int[] b : blocs) {
			finalSize += b.length;
		}
		int[] bloc = new int[finalSize];

		int rangInFinalBloc = 0;
		
		for (int[] b : blocs) {
			for (int i = 0; i < b.length; i++) {
				bloc[rangInFinalBloc] = b[i];
				rangInFinalBloc++;
			}
		}
		return bloc;
	}
	
	/**
	 * XOR bloc1 and bloc2. Create a new list, at each element if they are
	 * equals add a 0 in the new list, 1 else;
	 * @param bloc1 to XOR with bloc2
	 * @param bloc2 to XOR with bloc1
	 * @return the result of the XOR operation about the lists.
	 */
	public int[] xor(int[] bloc1, int[] bloc2) {
		if (bloc1.length != bloc2.length) {
			return null;
		}
		int[] newBloc = new int[bloc1.length];
		for (int i = 0; i < bloc1.length; i++) {
			newBloc[i] = bloc1[i] == bloc2[i] ? 0 : 1;
		}
		return newBloc;
	}
	
	/**
	 * Use the 1st and 6th bits of the block to determine the row. Use the 2nd,
	 * 3rd, 4th, and 5th bits of the block to determine the column. With the
	 * row and column, look up the corresponding value in the substitution
	 * table. Encode this value as a 4-bit binary number.
	 * @param blocs of bits
	 * @return new blocs after the S step of the DES algorithms
	 */
	public int[] fonctionS(int[] blocs) {
		int[] newBloc = new int[32];
		int[] indicesLigne = {0,5};
		int[] indicesColonne = {1,2,3,4};
		
		int indiceColonne;
		int indiceLigne;
		int value;
		int indiceNewBloc = 0;
		for (int i = 0; i < blocs.length; i+=6) {
			indiceColonne = 0;
			for (int y = 0; y < indicesColonne.length; y++) {
				indiceColonne = (indiceColonne << 1) | blocs[i + indicesColonne[y]];
	        }
			
			indiceLigne = 0;
			for (int y = 0; y < indicesLigne.length; y++) {
				indiceLigne = (indiceLigne << 1) | blocs[i + indicesLigne[y]];
	        }
			
			value = S[indiceLigne][indiceColonne];
		    for (int y = 3; y >= 0; y--) {
		    	newBloc[indiceNewBloc+y] = value & 1;
		        value >>= 1;
		    }
		    indiceNewBloc += 4;
		}
		return newBloc;
	}
	
	/**
	 * Generate the key for the first iteration.
	 */
	public void genereCle() {
		// permutation choice 1
		int[][] pc1 = decoupage(permutation(PERM_CHOICE_1,masterKey),28);
		// shifting left
		pc1[0] = decalleGauche(pc1[0],1);
		pc1[1] = decalleGauche(pc1[1],1);
		// sticking and permutation choice 2
		// add to the list
		tabCles[0] = permutation(PERM_CHOICE_2,recollageBloc(pc1));
	}
	
	public int[] fonctionF(int[] blocD) {
		return permutation(P,fonctionS(xor(permutation(E,blocD),tabCles[0])));
	}
}
