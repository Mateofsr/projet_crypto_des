package main.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import main.chiffrement.DES;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DESTest {

	private DES des;
	private final String UTF_8 = "UTF-8";
	
	@BeforeAll
	void setUp() {
		des = new DES();
		des.setEncodage(UTF_8);
	}
	
	@Test
	void testStringToBits() {
		// GIVEN a plain text
		String message = "Hello world!";
		int[] expectedResult = {0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,
				0,0,1,1,0,1,1,0,0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,
				1,1,0,1,1,0,1,1,1,1,0,1,1,1,0,0,1,0,0,1,1,0,1,1,0,0,0,1,1,0,0,
				1,0,0,0,0,1,0,0,0,0,1};
		// WHEN the string is converted to bits in UTF-8
		// THEN
		assertArrayEquals(expectedResult, des.stringToBits(message));
	}
	
	@Test
	void testBitsToString() {
		// GIVEN a list of bits
		String expectedMessage = "Hello world!";
		int[] bytes = {0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,
				0,0,1,1,0,1,1,0,0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,
				1,1,0,1,1,0,1,1,1,1,0,1,1,1,0,0,1,0,0,1,1,0,1,1,0,0,0,1,1,0,0,
				1,0,0,0,0,1,0,0,0,0,1};
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
	
	@Test
	void testRecollageBloc() {
		// GIVEN a list of blocs
		int[][] blocs = {
				{0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,0,0,1,1,0,1,1,0,
				0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,1,1,0,1,1,0,1,1,
				1,1},
				{0,1,1,1,0,0,1,0,0,1,1,0,1,1,0,0,0,1,1,0,0,1,0,0,0,0,1,0,0,0,0,
				1,0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,0,0,1,1,0,1,1,
				0,0}};
		int[] expectedBloc  = {0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,0,0,1,
				1,0,1,1,0,0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,1,1,0,
				1,1,0,1,1,1,1,0,1,1,1,0,0,1,0,0,1,1,0,1,1,0,0,0,1,1,0,0,1,0,0,
				0,0,1,0,0,0,0,1,0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,
				0,0,1,1,0,1,1,0,0};
		// WHEN we stick the blocs to get just one
		// THEN
		assertArrayEquals(expectedBloc, des.recollageBloc(blocs));
		
		// GIVEN a list of blocs without the same size
		int[][] blocs2 = {
				{0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,0,0,1,1,0,1,1,0,
				0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,1,1,0,1,1,0,1,1,
				1,1},
				{0,1,1,1,0,0,1,0,0,1,1,0,1,1,0,0,0,1,1,0,0,1,0,0,0,0,1,0,0,0,0,
				1,0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0}};
		int[] expectedBloc2  = {0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,0,0,1,
				1,0,1,1,0,0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,1,1,0,
				1,1,0,1,1,1,1,0,1,1,1,0,0,1,0,0,1,1,0,1,1,0,0,0,1,1,0,0,1,0,0,
				0,0,1,0,0,0,0,1,0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,};
		// WHEN we stick the blocs to get just one
		// THEN
		assertArrayEquals(expectedBloc2, des.recollageBloc(blocs2));
		
		// GIVEN a list of blocs with an empty bloc
		int[][] blocs3 = {
				{0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,0,0,1,1,0,1,1,0,
				0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,1,1,0,1,1,0,1,1,
				1,1},
				{}};
		int[] expectedBloc3  = {0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,
				0,0,1,1,0,1,1,0,0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,
				1,1,0,1,1,0,1,1,1,1};
		// WHEN we stick the blocs to get just one
		// THEN
		assertArrayEquals(expectedBloc3, des.recollageBloc(blocs3));
	}
	
	@Test
	void testXor() {
		// GIVEN two blocs
		int[] bloc1 = {0,1,0,1,1,0};
		int[] bloc2 = {0,1,0,1,0,1};
		int[] expectedBloc = {0,0,0,0,1,1};
		// WHEN we XOR the blocs
		// THEN
		assertArrayEquals(expectedBloc, des.xor(bloc1, bloc2));
		
		// GIVEN two long blocs
		int[] bloc12 = {0,1,1,1,0,0,1,0,0,1,1,0,1,1,0,0,0,1,1,0,0,1,0,0,0,0,1,0,0,0,0,1,0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,0,0,1,1,0,1,1,0,0};
		int[] bloc22 = {0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,1,1,0,1,1,0,1,1,1,1,0,1,1,1,0,0,1,0,0,1,1,0,1,1,0,0,0,1,1,0,0,1,0,0,0,0,1,0,0,0,0,1};
		int[] expectedBloc1 = {0,0,0,1,1,1,0,1,0,1,0,0,1,1,0,0,0,0,0,1,0,0,1,1,0,1,0,0,1,1,1,0,0,0,1,1,1,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,1,1,0,1};
		// WHEN we XOR the blocs
		// THEN
		assertArrayEquals(expectedBloc1, des.xor(bloc12, bloc22));
		
		// GIVEN two blocs of different sizes
		int[] bloc13 = {0,1,0,1,1,0};
		int[] bloc23 = {0,1,0,1,0,1,1};
		// WHEN we XOR the blocs
		// THEN
		assertNull(des.xor(bloc13, bloc23));
	}
	
	@Test
	void testFonctionS() {
		// GIVEN blocs of 6 bits
		int[] bloc = {1,0,1,0,1,0,
					   1,0,1,0,1,1,
					   1,1,1,0,1,0,
					   0,0,1,0,1,0,
					   1,1,1,1,1,0,
					   1,0,0,0,1,0,
					   1,0,0,1,1,0,
					   1,0,0,1,0,1};
		int[] expectedResult = {0,1,1,0,1,0,0,1,1,0,1,0,1,1,1,1,0,0,0,0,0,0,0,1,1,0,0,0,1,0,0,0};
		// WHEN we applied the S function about it
		// THEN
		assertArrayEquals(expectedResult, des.fonctionS(bloc,0));
	}
	
	@Test
	void testCrypteDecrypte() {
		// GIVEN a plain text
		String message = "Hello world!";
		// WHEN the text is encrypted and deciphered
		int [] decipherMessage = des.decrypte(des.crypte(des.stringToBits(message)));
		// THEN the result is equals to the original text
		assertEquals(message, des.bitsToString(decipherMessage));
		
		// GIVEN a big plain text
		message = "Je ne suis pas, Messieurs, de ceux qui croient qu’on peut supprimer la souffrance en ce monde ; la souffrance est une loi divine ; mais je suis de ceux qui pensent et qui affirment qu’on peut détruire la misère. (Mouvements divers.)\r\n"
				+ "\r\n"
				+ "Remarquez-le bien, Messieurs, je ne dis pas diminuer, amoindrir, limiter, circonscrire, je dis détruire. (Nouveaux murmures à droite.) La misère est une maladie du corps social comme la lèpre était une maladie du corps humain ; la misère peut disparaître comme la lèpre a disparu (Oui ! oui ! à gauche). Détruire la misère ! Oui, cela est possible. (Mouvement. — Quelques voix : Comment ? Comment ?) Les législateurs et les gouvernants doivent y songer sans cesse ; car, en pareille matière, tant que le possible n’est pas fait, le devoir n’est pas rempli. (Très bien ! très bien !)\r\n"
				+ "\r\n"
				+ "La misère, Messieurs, j’aborde ici le vif de la question, voulez-vous savoir où elle en est, la misère ? Voulez-vous savoir jusqu’où elle peut aller, jusqu’où elle va, je ne dis pas en Irlande, je ne dis pas au moyen-âge, je dis en France, je dis à Paris, et au temps où nous vivons ? Voulez-vous des faits ?\r\n"
				+ "\r\n"
				+ "Il y a dans Paris… (L’orateur s’interrompt.)\r\n"
				+ "\r\n"
				+ "Mon Dieu, je n’hésite pas à les citer, ces faits. Ils sont tristes, mais nécessaires à révéler ; et tenez, s’il faut dire toute ma pensée, je voudrais qu’il sortît de cette Assemblée, et au besoin j’en ferai la proposition formelle, une grande et solennelle enquête sur la situation vraie des classes laborieuses et souffrantes en France. (Très bien !) Je voudrais que tous les faits éclatassent au grand jour. Comment veut-on guérir le mal si l’on ne sonde pas les plaies ? (Très bien ! très bien !)\r\n"
				+ "\r\n"
				+ "Voici donc ces faits.\r\n"
				+ "\r\n"
				+ "Il y a dans Paris, dans ces faubourgs de Paris que le vent de l’émeute soulevait naguère si aisément, il y a des rues, des maisons, des cloaques, où des familles, des familles entières, vivent pêle-mêle, hommes, femmes, jeunes filles, enfants, n’ayant pour lits, n’ayant pour couvertures, j’ai presque dit pour vêtements, que des monceaux infects de chiffons en fermentation, ramassés dans la fange du coin des bornes, espèce de fumier des villes, où des créatures s’enfouissent toutes vivantes pour échapper au froid de l’hiver. (Mouvement.)\r\n"
				+ "\r\n"
				+ "Voilà un fait. En voulez-vous d’autres ? Ces jours-ci, un homme, mon Dieu, un malheureux homme de lettres, car la misère n’épargne pas plus les professions libérales que les professions manuelles, un malheureux homme est mort de faim, mort de faim à la lettre, et l’on a constaté, après sa mort, qu’il n’avait pas mangé depuis six jours. (Longue interruption.) Voulez-vous quelque chose de plus douloureux encore ? Le mois passé, pendant la recrudescence du choléra, on a trouvé une mère et ses quatre enfants qui cherchaient leur nourriture dans les débris immondes et pestilentiels des charniers de Montfaucon ! (Sensation.)\r\n"
				+ "\r\n"
				+ "Eh bien, messieurs, je dis que ce sont là des choses qui ne doivent pas être ; je dis que la société doit dépenser toute sa force, toute sa sollicitude, toute son intelligence, toute sa volonté, pour que de telles choses ne soient pas ! Je dis que de tels faits, dans un pays civilisé, engagent la conscience de la société tout entière ; que je m’en sens, moi qui parle, complice et solidaire (mouvement), et que de tels faits ne sont pas seulement des torts envers l’homme, que ce sont des crimes envers Dieu ! (Sensation prolongée.)\r\n"
				+ "\r\n"
				+ "Voilà pourquoi je suis pénétré, voilà pourquoi je voudrais pénétrer tous ceux qui m’écoutent de la haute importance de la proposition qui vous est soumise. Ce n’est qu’un premier pas, mais il est décisif. Je voudrais que cette Assemblée, majorité et minorité, n’importe, je ne connais pas, moi de majorité et de minorité en de telles questions ; je voudrais que cette Assemblée n’eût qu’une seule âme pour marcher à ce grand but, à ce but magnifique, à ce but sublime, l’abolition de la misère ! (Bravo ! — Applaudissements.)\r\n"
				+ "\r\n"
				+ "Et, messieurs, je ne m’adresse pas seulement à votre générosité, je m’adresse à ce qu’il y a de plus sérieux dans le sentiment politique d’une assemblée de législateurs. Et, à ce sujet, un dernier mot : je terminerai par là.\r\n"
				+ "\r\n"
				+ "Messieurs, comme je vous le disais tout à l’heure, vous venez, avec le concours de la garde nationale, de l’armée et de toutes les forces vives du pays, vous venez de raffermir l’État ébranlé encore une fois. Vous n’avez reculé devant aucun péril, vous n’avez hésité devant aucun devoir. Vous avez sauvé la société régulière, le gouvernement légal, les institutions, la paix publique, la civilisation même. Vous avez fait une chose considérable… Eh bien ! Vous n’avez rien fait ! (Mouvement.)\r\n"
				+ "\r\n"
				+ "Vous n’avez rien fait, j’insiste sur ce point, tant que l’ordre matériel raffermi n’a point pour base l’ordre moral consolidé ! (Très bien ! très bien ! — Vive et unanime adhésion.) Vous n’avez rien fait tant que le peuple souffre ! (Bravos à gauche.) Vous n’avez rien fait tant qu’il y a au-dessous de vous une partie du peuple qui désespère ! Vous n’avez rien fait, tant que ceux qui sont dans la force de l’âge et qui travaillent peuvent être sans pain ! tant que ceux qui sont vieux et qui ne peuvent plus travailler sont sans asile ! tant que l’usure dévore nos campagnes, tant qu’on meurt de faim dans nos villes (mouvement prolongé), tant qu’il n’y a pas des lois fraternelles, des lois évangéliques qui viennent de toutes parts en aide aux pauvres familles honnêtes, aux bons paysans, aux bons ouvriers, aux gens de cœur ! (Acclamation.) Vous n’avez rien fait, tant que l’esprit de révolution a pour auxiliaire la souffrance publique ! Vous n’avez rien fait, rien fait, tant que, dans cette œuvre de destruction et de ténèbres, qui se continue souterrainement, l’homme méchant a pour collaborateur fatal l’homme malheureux !\r\n"
				+ "\r\n"
				+ "Vous le voyez, messieurs, je le répète en terminant, ce n’est pas seulement à votre générosité que je m’adresse, c’est à votre sagesse, et je vous conjure d’y réfléchir. Messieurs, songez-y, c’est l’anarchie qui ouvre les abîmes, mais c’est la misère qui les creuse. (C’est vrai ! c’est vrai !) Vous avez fait des lois contre l’anarchie, faites maintenant des lois contre la misère ! (Mouvement prolongé sur tous les bancs. — L’orateur descend de la tribune et reçoit les félicitations de ses collègues.)";
		// WHEN the text is encrypted and deciphered
		decipherMessage = des.decrypte(des.crypte(des.stringToBits(message)));
		// THEN the result is equals to the original text
		assertEquals(message, des.bitsToString(decipherMessage));
		
		// GIVEN an empty plain text
		message = "";
		// WHEN the text is encrypted and deciphered
		decipherMessage = des.decrypte(des.crypte(des.stringToBits(message)));
		// THEN the result is equals to the original text
		assertEquals(message, des.bitsToString(decipherMessage));
	}
}
