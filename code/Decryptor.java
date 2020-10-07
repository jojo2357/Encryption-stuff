import java.io.*;
import java.util.*;
import java.util.regex.*;

//mostly decrypts text before prompting the user to fill in some blanks
class Decryptor{
	private static final char[] cipher = {
		'e', 't', 'a', 'o', 'i', 'n', 's', 'r', 'h', 'd', 'l', 'u', 'c', 'm', 'f', 'y', 'w', 'g', 'p', 'b', 'v', 'k', 'x', 'q', 'j', 'z'
	};

	private static int replaces = 0;

	public static void main(String args[]) throws IOException{
		char[] arrey = new char[30000];
		char repChar = ' ';
		File inFile = new File("cipherCreated.txt");
		FileReader reader = new FileReader(inFile);
		StringBuilder start = new StringBuilder("");
		while (reader.read(arrey) != -1){
			//start.append(reader.next() + " ");
		}
		reader.close();
		String paragraph = new String(arrey);//start.toString().toLowerCase();
		paragraph = paragraph.toLowerCase().trim();
		arrey = null;
		System.gc();
		System.out.println(paragraph.length());
		int vals[] = new int[26];
		//for (char ch = 'a'; ch <= 'z'; ch++)
		//	cipher.add(new Integer(0), new Character(ch));
		int numsDone = 0;
		for (int i = 0; i < paragraph.length(); i++)
			if (Character.toLowerCase(paragraph.charAt(i)) >= 'a' && Character.toLowerCase(paragraph.charAt(i)) <= 'z'){
				numsDone++;
				vals[paragraph.charAt(i) - 97]++;
			}
		ArrayList<Integer> ordered = new ArrayList<Integer>();
		StringBuilder out = new StringBuilder(paragraph);
		System.out.println(numsDone);
		//StringBuilder start = new StringBuilder(paragraph);
		for (char first = 'a'; first <= 'd'; first++){
			int record = 0;
			Integer recDex = null;
			for (int i = 0; i < 26; i++)
				if ((recDex == null || vals[i] > record) && !ordered.contains(i)){
					record = vals[i];
					recDex = i;
				}
			if (recDex == null)
				continue;
			System.out.println((char)(recDex + 'a') + ": " + cipher[first - 'a']);
			ordered.add(recDex);
			for (int dex = 0; dex < paragraph.length(); dex++)
				if (paragraph.charAt(dex) == (char)('a' + recDex)){
					out.setCharAt(dex, Character.toUpperCase(cipher[first - 'a']));
					replaces++;
				}
		}
		System.out.println(paragraph);
		System.out.println(out);
		String words[] = out.toString().split(" ");
		int lastCharFreqs[] = new int[26];
		countItUp(lastCharFreqs, "(T.E)", words, 3);
		out = replaceIt(recordCharacter(lastCharFreqs), 'H', out);

		words = out.toString().split(" ");
		lastCharFreqs = new int[26];
		countItUp(lastCharFreqs, "(.T)", words, 2);
		out = replaceIt(recordCharacter(lastCharFreqs), 'I', out);

		words = out.toString().split(" ");
		lastCharFreqs = new int[26];
		countSuffix(lastCharFreqs, "(TIO.)", words, 5);
		out = replaceIt(recordCharacter(lastCharFreqs), 'N', out);

		words = out.toString().split(" ");
		lastCharFreqs = new int[26];
		countItUp(lastCharFreqs, "(THEI.)", words, 5);
		out = replaceIt(recordCharacter(lastCharFreqs), 'R', out);

		words = out.toString().split(" ");
		lastCharFreqs = new int[26];
		countItUp(lastCharFreqs, "(AN.)", words, 3);
		out = replaceIt(recordCharacter(lastCharFreqs), 'D', out);


		words = out.toString().split(" ");
		lastCharFreqs = new int[26];
		countItUp(lastCharFreqs, "(HA.E)", words, 4);
		out = replaceIt(recordCharacter(lastCharFreqs), 'V', out);

		words = out.toString().split(" ");
		lastCharFreqs = new int[26];
		countSuffix(lastCharFreqs, "(.)", words, 2);
		out = replaceIt(recordCharacter(lastCharFreqs), 'S', out);

		words = out.toString().split(" ");
		lastCharFreqs = new int[26];
		countSuffix(lastCharFreqs, "(.)", words, 2);
		out = replaceIt(recordCharacter(lastCharFreqs), 'Y', out);

		words = out.toString().split(" ");
		lastCharFreqs = new int[26];
		countItUp(lastCharFreqs, "(I.HT)", out.toString());
		out = replaceIt(recordCharacter(lastCharFreqs), 'G', out);

		int total = 0;
		for (int i : vals)
			total = total + i;
		ArrayList<Character> characters = new ArrayList<Character>();
		for (char thing = 'A'; thing <= 'Z'; thing++)
			if (out.toString().contains("" + thing))
				characters.add(new Character(thing));
		Scanner kb = new Scanner(System.in);
		System.out.println("I will give you some words, your job is to type the letter that the lowercase letter should be. If you do not know, enter ? and I will give you a different word");
		while (characters.size() < 26){
			int longest = 0;
			String recordWord = "";
			words = out.toString().split(" ");
			ArrayList<String> unsolvedWords = new ArrayList<String>();
			System.out.println(out);
			for (String word : words){
				int wrongs = 0;

					for (int charFindr = 0; charFindr < word.length(); charFindr++){
						if ((word.charAt(charFindr) <= 'z' && word.charAt(charFindr) >= 'a'))
							wrongs++;
					}
					if (wrongs == 1){
						if (!unsolvedWords.contains(word))
						unsolvedWords.add(word);
						if (word.length() > longest){
							recordWord = word;
							longest = word.length();
						}
					}
			}
			Collections.sort(unsolvedWords);
			char charIn = ' ';
			int printdex = 0;
			for (Character ch : characters)
				System.out.print(ch + ", ");
			System.out.println('\n' + unsolvedWords.get(printdex));
			while(!(charIn <= 'z' && charIn >= 'a')){
				charIn = Character.toLowerCase(kb.nextLine().charAt(0));
				if (charIn == '?'){
					System.out.println(unsolvedWords.get(++printdex));
				}else{
					if (!characters.contains(Character.toUpperCase(charIn))){
					characters.add(Character.toUpperCase(charIn));
					repChar = ' ';
					for (int charFindr = 0; charFindr < unsolvedWords.get(printdex).length(); charFindr++){
						if ((unsolvedWords.get(printdex).charAt(charFindr) <= 'z' && unsolvedWords.get(printdex).charAt(charFindr) >= 'a'))
							repChar = unsolvedWords.get(printdex).charAt(charFindr);
					}
					if (repChar == ' ')
						throw new RuntimeException("Shrug");
					for (int dex = 0; dex < paragraph.length(); dex++)
						if (paragraph.charAt(dex) == (char)(repChar)){
							out.setCharAt(dex, Character.toUpperCase(charIn));
							replaces++;
						}
					}else{
						System.out.println("No u dummy hed");
					}
				}
			}
		}
		System.out.println(/*start.toString()*/ + '\n' + (out.toString()));
		System.out.println(replaces + " : " + total);
		System.out.println(words.length);
		for (Character ch : characters)
			System.out.print(ch + ", ");
	}

	private static void countItUp(int[] vals, String regex, String paragraph){
		Pattern pattern = Pattern.compile(regex);
		int indexOfWildcard = regex.indexOf(".");
		Matcher matcher = pattern.matcher(paragraph);
		while (matcher.find()){
			if (matcher.group().charAt(indexOfWildcard - 1) <= 'z' && matcher.group().charAt(indexOfWildcard - 1) >= 'a')
			vals[matcher.group().charAt(indexOfWildcard - 1) - 'a']++;
		}
	}

	private static void countItUp(int[] vals, String regex, String[] words, int wordLength){
		for (String word : words){
		System.out.println(word);
		if (word.length() != wordLength)
			continue;
		Pattern pattern = Pattern.compile(regex);
		int indexOfWildcard = regex.indexOf(".");
		Matcher matcher = pattern.matcher(word);
		while (matcher.find()){
			if (matcher.group().charAt(indexOfWildcard - 1) <= 'z' && matcher.group().charAt(indexOfWildcard - 1) >= 'a')
				vals[matcher.group().charAt(indexOfWildcard - 1) - 'a']++;
		}
		}
	}

	private static void countSuffix(int vals[], String regex, String[] words, int minLength){
		for (String word : words){
		if (word.length() < minLength)
			continue;
		Pattern pattern = Pattern.compile(regex);
		int indexOfWildcard = regex.indexOf(".");
		Matcher matcher = pattern.matcher(word);
		while (matcher.find()){
			if (matcher.end() == word.length() && matcher.group().charAt(indexOfWildcard - 1) <= 'z' && matcher.group().charAt(indexOfWildcard - 1) >= 'a')
			vals[matcher.group().charAt(indexOfWildcard - 1) - 'a']++;
		}
		}
	}

	private static char recordCharacter(int[] vals){
		int rec = 0;
		char recChar = ' ';
		for (char maxFindr = 'a'; maxFindr <= 'z'; maxFindr++)
			if (vals[maxFindr - 'a'] > rec){
				rec = vals[maxFindr - 'a'];
				recChar = maxFindr;
			}
		if (recChar == ' ') 
			throw new RuntimeException();
		return recChar;
	}

	private static StringBuilder replaceIt(char old, char newChar, StringBuilder text){
		for (int dex = 0; dex < text.toString().length(); dex++)
			if (text.toString().charAt(dex) == (char)(old)){
				text.setCharAt(dex, newChar);
				replaces++;
			}
		return text;
	}
}