import java.io.*;

// command line invocation:
// java Caesar alphabetFileName messageFileName

public class Caesar {
	
	public String readFile(String filename) {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line = "", out = "";
			while ((line = br.readLine()) != null)
				out += line;
			br.close();
			fr.close();
			
			return out;
		} catch (Exception e) { }
	}

	public static void main(String[] args) {
		// read in alphabet and message
		String alphabet = readFile(args[0]), message = readFile(args[1]);
		
		// create alphabet and get every possible shift
		Alphabet a = new Alphabet(alphabet);
		String[] result = a.decipher(message);
		
		// read in dictionary
		String[] dictionary = new String[];
		String line = "", dict = "";
		try {
			FileReader fr = new FileReader("dict/words.txt");
			BufferedReader br = new BufferedReader(fr);
			while ((line = br.readLine()) != null)
				dict += line + "\n";
			br.close();
			fr.close();
			
			dictionary = dict.split("\n");
		} catch (Exception e) { }
		
		// initialize arrays
		
		int[] hits = new int[result.length()], shift = new int[result.length()];
		for (int k = 0; k < hits.length(); k++) {
			hits[k] = 0;
			shift[k] = k + 1;
		}
		
		for (int k = 0; k < dictionary.length(); k++) {
			for (int j = 0; j < result.length(); j++) {
				int index = result[j].indexOf(dictionary[k]);
				while (index >= 0) {
					index = result[j].indexOf(dictionary[k], index + 1);
					hits[j] += 1;
				}
			}
		}
		
		// sort ciphers by order of dictionary hits - most likely to be correct
		for (int k = 0; k < result.length() - 1; k++)
			for (int j = k + 1; j < result.length(); j++)
				if (hits[k] < hits[j]) {
					String temp = result[k];
					int stemp = shift[k];
					result[k] = result[j];
					shift[k] = shift[j];
					result[j] = temp;
					shift[j] = stemp;
				}
		
		// output
		for (int k = 0; k < result.length(); k++) {
			if (hits[k] > 0) {
				// only outputs if there is at least one dictionary hit
				
				System.out.println("==========");
				System.out.println("shift: +" + shift[k]);
				System.out.println("message: " + result[k]);
				System.out.println("==========\n");
			}
		}
		// change k < result.length to k < 1 to only print out the top result
	}
}
