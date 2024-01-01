/**
 * A driver for CS1501 Project 2
 * @author	Dr. Farnan
 */
package cs1501_p2;

import java.io.File;
import java.util.ArrayList;

public class App {
	public static void main(String[] args) {
		String eng_dict_fname = "build/resources/main/dictionary.txt";
		String uhist_state_fname = "build/resources/main/uhist_state.p2";

		AutoCompleter ac;
		File check = new File(uhist_state_fname);
		if (check.exists()) {
			ac = new AutoCompleter(eng_dict_fname, uhist_state_fname);
		}
		else {
			ac = new AutoCompleter(eng_dict_fname);
		}

		printPredictions(ac, 't');
		printPredictions(ac, 'h');
		printPredictions(ac, 'e');
		printPredictions(ac, 'r');
		printPredictions(ac, 'e');

		String word = "thereabout";
		System.out.printf("Selected: %s\n\n", word);
		ac.finishWord(word);

		printPredictions(ac, 't');
		printPredictions(ac, 'h');
		printPredictions(ac, 'e');
		printPredictions(ac, 'r');
		printPredictions(ac, 'e');		

		word = "thought";
		System.out.printf("Selected: %s\n\n", word);
		ac.finishWord(word);

		printPredictions(ac, 't');
		printPredictions(ac, 'h');
		printPredictions(ac, 'e');
		printPredictions(ac, 'r');
		printPredictions(ac, 'e');

		word = "thought";
		System.out.printf("Selected: %s\n\n", word);
		ac.finishWord(word);

		printPredictions(ac, 't');
		printPredictions(ac, 'h');
		printPredictions(ac, 'e');
		printPredictions(ac, 'r');
		printPredictions(ac, 'e');

		word = "cloth";
		System.out.printf("Selected: %s\n\n", word);
		ac.finishWord(word);

		printPredictions(ac, 'c');
		printPredictions(ac, 'l');
		printPredictions(ac, 'e');
		printPredictions(ac, 'a');
		printPredictions(ac, 'r');

		ac.saveUserHistory(uhist_state_fname);

		// String dict_fname = "build/resources/test/dictionary.txt";
		// String uhist_state_fname = "build/resources/test/uhist_state.p2";
		// AutoCompleter ac = new AutoCompleter(dict_fname);
		// //ArrayList<String> sugs = ac.nextChar('d');
		// printPredictions(ac, 'd');

		// String word = "dictionary";
		// System.out.printf("Selected: %s\n\n", word);
		// ac.finishWord(word);
		// printPredictions(ac, 'd');
		// word = "dip";
		// ac.finishWord(word);
		// System.out.printf("Selected: %s\n\n", word);
		// word = "dip";
		// ac.finishWord(word);
		// System.out.printf("Selected: %s\n\n", word);
		// printPredictions(ac, 'd');

		// ac.saveUserHistory(uhist_state_fname);

		// ac = new AutoCompleter(dict_fname, uhist_state_fname);
		// printPredictions(ac, 'd');

		// AutoCompleter testac = new AutoCompleter("build/resources/main/dictionary.txt");
		// System.out.println(testac.dlb.count());
		// testac.userHistory.add("able");
		// testac.userHistory.add("absent");
		// testac.userHistory.add("abbot");
		// testac.userHistory.add("absent");
		// testac.userHistory.add("absent");
		// testac.userHistory.add("abbot");
		// testac.userHistory.add("naan");
		// testac.userHistory.add("aah");
		// testac.userHistory.add("aah");
		// testac.userHistory.add("aah");
		// testac.userHistory.add("aah");
		// System.out.println(testac.userHistory.traverseWithDuplicates());

		// System.out.println(testac.nextChar('a'));

		// String s = "acc";
		// char c1 = 'a';
		// char c2 = 's';
		// char c3 = 'g';
		// char c4 = 't';
		// UserHistory test = new UserHistory();
		// test.add("shoe");
		// test.add("s");
		// test.add("s");
		// test.add("fort");
		// test.add("fort");
		// test.add("fort");
		// test.add("fort");
		// test.add("fort");
		// test.add("fort");
		// test.add("g");
		// test.add("g");
		// test.add("sun");
		// test.add("td");
		// test.add("short");
		// test.add("slug");
		// test.add("sz");
		// test.add("shop");
		// test.add("shoes");
		// test.add("g");
		// test.add("at");
		// test.add("ar");
		// test.add("ac");
		// test.add("ar");
		// test.add("ac");
		// test.add("td");
		// test.add("td");
		// test.add("ar");
		// test.add("ar");
		// test.add("ar");
		// test.add("ar");
		// test.add("ar");
		// test.add("ar");
		// test.add("ar");
		// test.add("army");
		// test.add("army");
		// test.add("army");
		// test.add("army");
		// test.add("army");
		// test.add("army");
		// test.add("arts");
		// test.add("arts");
		// test.add("arts");
		// test.add("arts");
		// test.add("arts");

		// System.out.println(test.traverse().toString());

		// test.searchByChar(c1);
		// System.out.println(test.suggest());

		// test.resetByChar();
		// test.searchByChar(c2);
		// System.out.println(test.suggest());

		// test.resetByChar();
		// test.searchByChar('a');
		// test.searchByChar('t');
		// System.out.println(test.suggest());

		// if (test.contains(s)) System.out.println("test contains " + s + " as full string.");
		// else System.out.println(s + " not found as full string.");
		// if (test.containsPrefix(s)) System.out.println("Prefix: " + s + " was found.");
		// else System.out.println("Prefix: " + s + " was not found.");
		//int h = test.searchByChar(c1);
		// test.searchByChar(c2);
		// test.searchByChar('o');
		// test.searchByChar('f');
		// test.resetByChar();
		// test.searchByChar('f');
		// test.searchByChar('l');
		// test.resetByChar();
		// test.searchByChar('s');
		// if (h == -1) System.out.println(c1 + " is not a valid word or prefix.");
		// else if (h == 0) System.out.println(c1 + " is a valid prefix but is not a valid word.");
		// else if (h == 1) System.out.println(c1 + " is a valid word but is not a prefix to any other words.");
		// else if (h == 2) System.out.println(c1 + " is a valid prefix to other words and a valid word.");

		//System.out.println(test.suggest().toString());
		//System.out.println("Number of elements: " + test.count());



		// AutoCompleter test2 = new AutoCompleter("build/resources/main/dictionary.txt");
		// System.out.println("test2 dlb size: " + test2.dlb.count());
		// System.out.println("test2 ush size: " + test2.userHistory.count());
		//System.out.println("dictionary entries: " + test2.dlb.count());
		// h = test.searchByChar(c2);
		// if (h == -1) System.out.println(c1 + "" + c2 + " is not a valid word or prefix.");
		// else if (h == 0) System.out.println(c1 + "" + c2 + " is a valid prefix but is not a valid word.");
		// else if (h == 1) System.out.println(c1 + "" + c2 + " is a valid word but is not a prefix to any other words.");
		// else if (h == 2) System.out.println(c1 + "" + c2 + " is a valid prefix to other words and a valid word.");

		// test.resetByChar();

		// h = test.searchByChar(c3);
		// if (h == -1) System.out.println(c3 + " is not a valid word or prefix.");
		// else if (h == 0) System.out.println(c3 + " is a valid prefix but is not a valid word.");
		// else if (h == 1) System.out.println(c3 + " is a valid word but is not a prefix to any other words.");
		// else if (h == 2) System.out.println(c3 + " is a valid prefix to other words and a valid word.");

		// h = test.searchByChar(c4);
		// if (h == -1) System.out.println(c3 + "" + c4 + " is not a valid word or prefix.");
		// else if (h == 0) System.out.println(c3 + "" + c4 + " is a valid prefix but is not a valid word.");
		// else if (h == 1) System.out.println(c3 + "" + c4 + " is a valid word but is not a prefix to any other words.");
		// else if (h == 2) System.out.println(c3 + "" + c4 + " is a valid prefix to other words and a valid word.");

		// test.add("hho");
		// test.add("fong");
		// test.add("trust");
		// test.add("haa");
		// DLBNode first = test.root;
		// DLBNode second = first.getDown();
		// DLBNode third = second.getDown();
		// DLBNode fourth = third.getDown();
		// DLBNode right = first.getRight();
		// DLBNode right2 = right.getDown();
		// DLBNode right3 = right2.getDown();
		// DLBNode nextRight = right.getRight();
		// DLBNode nextRightDown = nextRight.getDown();
		// DLBNode secondLayerRight = right2.getRight();
		// DLBNode secondLayerRightDown = secondLayerRight.getDown();
		// System.out.println("root: " + first.getLet());
		// System.out.println("second: " + second.getLet());
		// System.out.println("third: " + third.getLet());
		// System.out.println("fourth: " + fourth.getLet());
		// System.out.println("first right: " + right.getLet());
		// System.out.println("right down: " + right2.getLet());
		// System.out.println("second right down: " + right3.getLet());
		// System.out.println("right of right: " + nextRight.getLet());
		// System.out.println("right of right down: " + nextRightDown.getLet());
		// System.out.println("second layer right: " + secondLayerRight.getLet());
		// System.out.println("second layer right down: " + secondLayerRightDown.getLet());
	} 

	private static void printPredictions(AutoCompleter ac, char next) {
		System.out.printf("Entered: %c\n", next);

		ArrayList<String> preds = ac.nextChar(next);

		System.out.println("Predictions:");
		int c = 0;
		for (String p : preds) {
			System.out.printf("\t%d: %s\n", ++c, p);
		}
		System.out.println();
	}
}
