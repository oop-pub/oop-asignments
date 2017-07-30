import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;

public class Parser {

	public TreeMap<String, TYPE> dictionary;
	String input_path;
	Scanner scanner;

	class Token {
		String value;
		TYPE type;

		public Token(String value, TYPE type) {
			this.value = value;
			this.type = type;
		}

		public String toString() {
			return "[value='" + this.value + "' , type='" + this.type + "']";
		}
	}

	

	public Parser(String wordlists_path, String input_path)
			throws FileNotFoundException {

		dictionary = new TreeMap<String, TYPE>();
		this.input_path = input_path;
		this.scanner = new Scanner(new File(input_path));

		File folder = new File(wordlists_path);
		File[] wordlists = folder.listFiles();
		Scanner sc;

		for (File f : wordlists) {
			sc = new Scanner(f);
			while (sc.hasNext()) {
				String token = sc.nextLine();
				dictionary.put(token.toLowerCase(), TYPE.valueOf(f.getName()));
			}
		}
	}

	public Token getNext() {

		Token t;
		if (scanner.hasNext()) {
			String v = scanner.next();
			if (dictionary.containsKey(v.toLowerCase())) {
				t = new Token(v, dictionary.get(v.toLowerCase()));
			} else {
				t = new Token(v, TYPE.irelevant);
			}

			return t;
		}

		return null;
	}

}
