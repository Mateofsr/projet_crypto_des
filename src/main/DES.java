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
		int fullBlocks = bloc.length / sizeBlocs;
	    int remainder = bloc.length % sizeBlocs;
	    int totalBlocks = fullBlocks + (remainder > 0 ? 1 : 0);
	    int[][] blocs = new int[totalBlocks][];

	    for (int i = 0; i < fullBlocks; i++) {
	        blocs[i] = new int[sizeBlocs];
	        System.arraycopy(bloc, i * sizeBlocs, blocs[i], 0, sizeBlocs);
	    }
	    if (remainder > 0) {
	        blocs[totalBlocks - 1] = new int[remainder];
	        System.arraycopy(bloc, fullBlocks * sizeBlocs,
	        		blocs[totalBlocks - 1], 0, remainder);
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
		int[] permutedBloc = new int[TAILLE_BLOC];
		for (int i = 0; i < TAILLE_BLOC; i++) {
			permutedBloc[i] = bloc[permutationTable[i]];
		}
		return permutedBloc;
	}
	
	private static void main(String[] args) {
		
	}
}
