package test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import main.DES;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DESTest {

	private DES des;
	
	@BeforeAll
	void setUp() {
		des = new DES();
	}
	
	@Test
	void testStringToBits() {
		// GIVEN a string
		String message = "Hello world!";
		int[] expectedResult = {0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,
				0,0,1,1,0,1,1,0,0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,
				1,1,0,1,1,0,1,1,1,1,0,1,1,1,0,0,1,0,0,1,1,0,1,1,0,0,0,1,1,0,0,
				1,0,0,0,0,1,0,0,0,0,1};
		// WHEN the string is converted to bits
		// THEN
		assertArrayEquals(expectedResult, des.stringToBits(message));
	}
	
	@Test
	void testBitsToString() {
		// GIVEN a list of bits
		int[] bytes = {0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,
				0,0,1,1,0,1,1,0,0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,
				1,1,0,1,1,0,1,1,1,1,0,1,1,1,0,0,1,0,0,1,1,0,1,1,0,0,0,1,1,0,0,
				1,0,0,0,0,1,0,0,0,0,1};
		String expectedMessage = "Hello world!";
		// WHEN the list is converted to a string
		// THEN
		assertEquals(expectedMessage, des.bitsToString(bytes));
	}
	
	@Test
	void testDecoupage() {
		// GIVEN bloc of bits
		int[] bytes = {0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,
				0,0,1,1,0,1,1,0,0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,
				1,1,0,1,1,0,1,1,1,1,0,1,1,1,0,0,1,0,0,1,1,0,1,1,0,0,0,1,1,0,0,
				1,0,0,0,0,1,0,0,0,0,1,0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,
				0,0,1,1,0,1,1,0,0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,
				1,1,0,1,1,0,1,1,1,1,0,1,1,1,0,0,1,0,0,1,1,0,1,1,0,0,0,1,1,0,0,
				1,0,0,0,0,1,0,0,0,0,1,0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,
				0,0,1,1,0,1,1,0,0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,
				1,1,0,1,1,0,1,1,1,1,0,1,1,1,0,0,1,0,0,1,1,0,1,1,0,0,0,1,1,0,0,
				1,0,0,0,0,1,0,0,0,0,1};
		int[][] expectedBlocs = {
				{0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,0,0,1,1,0,1,1,0,
				0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,1,1,0,1,1,0,1,1,
				1,1},
				{0,1,1,1,0,0,1,0,0,1,1,0,1,1,0,0,0,1,1,0,0,1,0,0,0,0,1,0,0,0,0,
				1,0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,0,0,1,1,0,1,1,
				0,0},
				{0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,1,1,0,1,1,0,1,1,1,
				1,0,1,1,1,0,0,1,0,0,1,1,0,1,1,0,0,0,1,1,0,0,1,0,0,0,0,1,0,0,0,
				0,1},
				{0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,0,0,1,1,0,1,1,0,
				0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,1,1,0,1,1,0,1,1,
				1,1,},
				{0,1,1,1,0,0,1,0,0,1,1,0,1,1,0,0,0,1,1,0,0,1,0,0,0,0,1,0,0,0,0,
				1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0}};
		// WHEN the bloc is cut 64 by 64
		// THEN
		assertArrayEquals(expectedBlocs, des.decoupage(bytes, des.TAILLE_BLOC));
	
	
		// GIVEN a bloc of the same length of the bloc size to cut
		int[] bytes2 = {0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,0,0,1,1,0,1,1,0,
				0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,1,1,0,1,1,0,1,1,
				1,1};
		int[][] expectedBlocs2 = {{0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,0,0,1,1,0,1,1,0,
				0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,1,1,0,1,1,0,1,1,
				1,1}};
		// WHEN the bloc is cut
		// THEN 
		assertArrayEquals(expectedBlocs2, des.decoupage(bytes2, des.TAILLE_BLOC));
	}
	
	@Test
	void testPermutation() {
		// GIVEN a bloc of 64 elements
		// and a permutation table with a length of 64
		int[] bloc = {0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,0,0,1,1,0,
				1,1,0,0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,1,1,0,1,1,
				0,1,1,1,1};
		int[] expectedPermutedBloc = {1,1,0,1,1,1,1,1,0,1,0,0,0,0,0,0,1,1,0,1,
				1,1,1,0,1,1,0,1,0,0,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,1,0,0,
				1,1,1,0,1,1,1,0,1,0,0,0,0};
		// WHEN the bloc is permuted by the permutation table
		// THEN
		assertArrayEquals(expectedPermutedBloc, des.permutation(des.PERM_INITIAL,bloc));
	}
	
	@Test
	void testSetMasterKey() {
		// GIVEN a DES
		// WHEN the master key is set
		des.setMasterKey();
		// THEN all the value of the master key are 0 or 1
		for (int value : des.getMasterKey()) {
			assertTrue(value == 0 | value == 1);
		}
		assertEquals(des.TAILLE_BLOC, des.getMasterKey().length);
	}
	
	@Test
	void testDecalleGauche() {
		// GIVEN a bloc of n integer
		int[] bloc = {0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,0,0,1,1,0,
				1,1,0,0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,1,1,0,1,1,
				0,1,1,1,1};
		int shifter = 1;
		int [] expectedBloc = {1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,0,0,1,1,0,
				1,1,0,0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,1,1,0,1,1,
				0,1,1,1,1,0};
		// WHEN we shift to the left the bloc
		// THEN
		assertArrayEquals(expectedBloc, des.decalleGauche(bloc,shifter));
		
		// GIVEN a bloc of a size < of the shifter
		int[] bloc2 = {0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,0,0,1,1,0,
				1,1,0,0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,1,1,0,1,1,
				0,1,1,1,1};
		int shifter2 = 132;
		int [] expectedBloc2 = {1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,0,0,1,1,0,
				1,1,0,0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,1,1,0,1,1,
				0,1,1,1,1,0,1,0,0};
		// WHEN we shift to the left the bloc
		// THEN
		assertArrayEquals(expectedBloc2, des.decalleGauche(bloc2,shifter2));
		
		// GIVEN a bloc of a size = of the shifter
		int[] bloc3 = {0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,0,0,1,1,0,
				1,1,0,0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,1,1,0,1,1,
				0,1,1,1,1};
		int shifter3 = 64;
		int [] expectedBloc3 = {0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,
				0,0,1,1,0,1,1,0,0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,
				1,1,0,1,1,0,1,1,1,1};
		// WHEN we shift to the left the bloc
		// THEN
		assertArrayEquals(expectedBloc3, des.decalleGauche(bloc3,shifter3));
	}
}
