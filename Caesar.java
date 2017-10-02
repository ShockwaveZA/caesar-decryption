import java.io.*;

public class Caesar {

	public static void main(String[] args) {
		String alphabet = args[0], message = args[1];
		Alphabet a = new Alphabet(alphabet);
		String[] result = a.decode(message);
		String[] dictionary = new String[]; // load a dictionary from file;
		int[] hits = new int[result.length], shift = new int[result.length];
		for (int k = 0; k < hits.length; k++) {
			hits[k] = 0;
			shift[k] = k + 1;
		}
		
		for (int k = 0; k < dictionary.length; k++) {
			for (int j = 0; j < result.length; j++) {
				int index = result[j].indexOf(dictionary[k]);
				while (index >= 0) {
					index = result[j].indexOf(dictionary[k], index + 1);
					hits[j] += 1;
				}
			}
		}
		
		// sort ciphers by order of dictionary hits - most likely to be correct
		for (int k = 0; k < result.length - 1; k++)
			for (int j = k + 1; j < result.length; j++)
				if (hits[k] < hits[j]) {
					String temp = result[k];
					int stemp = shift[k];
					result[k] = result[j];
					shift[k] = shift[j]
					result[j] = temp;
					shift[j] = stemp;
				}
		
		// output
		for (int k = 0; k < result.length; k++) {
			System.out.println("==========");
			System.out.println("shift: +" + shift[k]);
			System.out.println("message: " + result[k]);
			System.out.println("==========\n");
		}
		// change k < result.length to k < 1 to only print out the top result
	}
}
