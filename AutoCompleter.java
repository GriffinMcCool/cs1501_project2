// author Griffin McCool
package cs1501_p2;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class AutoCompleter implements AutoComplete_Inter{
	private DLB dlb;
	private UserHistory userHistory;

	// constructor
	public AutoCompleter(String filename1, String filename2){
		dlb = new DLB();
		userHistory = new UserHistory();
		try{
			File file1 = new File(filename1);
			Scanner scan1 = new Scanner(file1);
			while (scan1.hasNextLine()){
				dlb.add(scan1.nextLine());
			}
			scan1.close();
		} catch (FileNotFoundException fnfe){
			System.out.println("File not found.");
		}

		try{
			File file2 = new File(filename2);
			Scanner scan2 = new Scanner(file2);
			while (scan2.hasNextLine()){
				userHistory.add(scan2.nextLine());
			}
			scan2.close();
		} catch (FileNotFoundException fnfe){
			System.out.println("File not found.");
		}
	}
	// overloaded constructor
	public AutoCompleter(String filename){
		dlb = new DLB();
		userHistory = new UserHistory();
		try {
			File file = new File(filename);
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()){
				dlb.add(scan.nextLine());
			}
			scan.close();
		} catch (FileNotFoundException fnfe){
			System.out.println("File not found.");
		}
	}
    /**
	 * Produce up to 5 suggestions based on the current word the user has
	 * entered These suggestions should be pulled first from the user history
	 * dictionary then from the initial dictionary. Any words pulled from user
	 * history should be ordered by frequency of use. Any words pulled from
	 * the initial dictionary should be in ascending order by their character
	 * value ("ASCIIbetical" order).
	 *
	 * @param 	next char the user just entered
	 *
	 * @return	ArrayList<String> List of up to 5 words prefixed by cur
	 */	
	public ArrayList<String> nextChar(char next){
		userHistory.searchByChar(next);
		dlb.searchByChar(next);
		ArrayList<String> list1 = userHistory.suggest();
		ArrayList<String> list3 = new ArrayList();
		int x = list1.size();
		// adds words from user history
		for (int i = 0; i < x; i++){
			list3.add(list1.get(i));
		}
		// adds remaining words from dictionary
		if (x < 5) {
			ArrayList<String> list2 = dlb.suggest();
			int g = 5-x;
			int z = list2.size();
			if (z < g) g = list2.size();
			boolean add;
			for (int h = 0; h < g; h++){
				add = true;
				// prevents duplicates
				for (int r = 0; r < list3.size(); r++){
					if (list3.get(r).compareTo(list2.get(h)) == 0){
						add = false;
						if (z != g) g++;
					}
				}
				if (add) list3.add(list2.get(h));
			}
		}
        return list3;
    }

	/**
	 * Process the user having selected the current word
	 *
	 * @param 	cur String representing the text the user has entered so far
	 */
	public void finishWord(String cur){
		userHistory.add(cur);
		dlb.resetByChar();
		userHistory.resetByChar();
        return;
    }

	/**
	 * Save the state of the user history to a file
	 *
	 * @param	fname String filename to write history state to
	 */
	public void saveUserHistory(String fname){
        try {
			File file = new File (fname);
			FileWriter fw = new FileWriter(file);
			PrintWriter pw = new PrintWriter(fw);
			ArrayList list = userHistory.traverseWithDuplicates();
			for (int i = 0; i < list.size(); i++){
				pw.println(list.get(i));
			}
			fw.close();
		} catch (IOException ioe){
			System.out.println("Error writing to " + fname);
		}
    }
}