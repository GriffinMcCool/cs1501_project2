// author Griffin McCool
package cs1501_p2;
import java.util.ArrayList;

public class DLB implements Dict{
    private DLBNode root;
    // used for searchByChar()
    private DLBNode sbcNode;
    private String sbcString = "";

    /**
	 * Add a new word to the dictionary
	 *
	 * @param 	key New word to be added to the dictionary
	 */	
	public void add(String key){
        if (key != null){
            DLBNode cur = root;
            DLBNode addedNode = null;
            //adds each character
            for (int i = 0; i < key.length(); i++){
                addedNode = addDown(key.charAt(i), cur, addedNode);
                cur = addedNode.getDown();
            }
            //adds terminating character at end
            addedNode = addDown('#', cur, addedNode);
        }
    }

    /**
	 * Helper method for add() that adds node vertically
	 * 
     * @param   c       character we're adding for
     * @param   node     current node
     * @param   parent  overall parent node
     *
	 * @return	DLBNode2 added node
	 */
    private DLBNode addDown(char c, DLBNode node, DLBNode parent){
        //if the current node is null, make a new node there with c
        if (node == null){
            // initialize the root
            if (node == root){
                root = new DLBNode(c);
                return root;
            } else parent.setDown(new DLBNode(c));
            return parent.getDown();
        }
        //if node is full, add to right
        return addRight(c, node, null, parent);
    }

    /**
	 * Helper method for add() that adds node horizontally
	 * 
     * @param   c       character we're adding for
     * @param   cur     current node
     * @param   prev    previous node
     * @param   parent  overall parent node
     *
	 * @return	DLBNode2 added node or node that was found
	 */
    private DLBNode addRight(char c, DLBNode cur, DLBNode prev, DLBNode parent){
        // if we're at the end of the list, add there
        if (cur == null){
            prev.setRight(new DLBNode(c));
            return prev.getRight();
        }
        // if c is already in the list, just return that node
        if (c == cur.getLet()) return cur;
        // to maintain sorted order, we compare c to current node and see whether we need to insert right or left
        // if c is less than the current node's letter, we insert there
        if (c < cur.getLet()){
            DLBNode newNode = new DLBNode(c);
            // if we are inserting to the left of the root, we make the newNode the new root
            if (cur == root) root = newNode;
            // insert into list
            if (prev != null) prev.setRight(newNode);
            newNode.setRight(cur);
            //if we are inserting to the far left of a layer, update the parent's child 
            if ((prev == null) && (parent != null)) parent.setDown(newNode);
            return newNode;
        } else {
            return addRight(c, cur.getRight(), cur, parent);
        }
    }

    /**
	 * Check if the dictionary contains a word
	 *
	 * @param	key	Word to search the dictionary for
	 *
	 * @return	true if key is in the dictionary, false otherwise
	 */
	public boolean contains(String key){
        if (key == null) return false;
        DLBNode cur = root;
        // loop through each character and check to see if it's there
        for (int i = 0; i < key.length(); i++){
            cur = contains(key.charAt(i), cur);
            // if character is not found, immediately return false
            if (cur == null) return false;
            cur = cur.getDown();
        }
        //if the string is found, make sure there is a terminator so it's not just a prefix
        if ((cur != null) && (cur.getLet() == '#')) return true;
        return false;
    }

    /**
	 * Helper method for contains()
	 * 
     * @param   c       character we're searching for
     * @param   node    current node
     *
	 * @return	DLBNode2 node that word is found at (null if not found)
	 */
    private DLBNode contains(char c, DLBNode node){
        if (node == null) return null;
        // if c is less than the current character, we immediately exit because the list is sorted alphabetically
        // and we would have already discovered c by this point (saves a little time)
        if (c < node.getLet()) return null;
        if (c != node.getLet()){
            // if there is a node to the right, check recursively
            if (node.getRight() != null) return contains(c, node.getRight());
            else return null;
        }
        return node;
    }

    /**
	 * Check if a String is a valid prefix to a word in the dictionary
	 *
	 * @param	pre	Prefix to search the dictionary for
	 *
	 * @return	true if prefix is valid, false otherwise
	 */
	public boolean containsPrefix(String pre){
        // pretty much the same code as contains(), we just remove the check for '#' at the end
        // as a prefix doesn't need to have a terminator
        if (pre == null) return false;
        boolean cont;
        DLBNode cur = root;
        // loop through each character and check to see if it's there
        for (int i = 0; i < pre.length(); i++){
            cur = contains(pre.charAt(i), cur);
            // if character is not found, immediately return false
            if (cur == null) return false;
            cur = cur.getDown();
        }
        return true;
    }

    /**
	 * Search for a word one character at a time
	 *
	 * @param	next Next character to search for
	 *
	 * @return	int value indicating result for current by-character search:
	 *				-1: not a valid word or prefix
	 *				 0: valid prefix, but not a valid word
	 *				 1: valid word, but not a valid prefix to any other words
	 *				 2: both valid word and a valid prefix to other words
	 */
	public int searchByChar(char next){
        sbcString += next;
        // resets sbcNode to be the root node as we will be searching the first
        // layer since there is only 1 char entered so far
        if (sbcString.length() == 1) resetByChar2();
        // return value
        int val;
        // we search for the next char to the right based on current position of sbcNode
        sbcNode = contains(next, sbcNode);
        if (sbcNode == null) return -1;
        // if we get to this point, we have a valid prefix in some form as the char was found
        // if the char below current node is a terminator, we have a valid word, so we'll be returning 1 or 2
        if (sbcNode.getDown().getLet() == '#'){
            // if there is a character to the right of the terminator, word is both valid and a prefix for another word
            if (sbcNode.getDown().getRight() != null) val = 2;
            // otherwise, it's a valid word but not a prefix for any other words
            else val = 1;
        }
        // otherwise, it's just a prefix and not a word
        else val = 0;
        // update sbcNode to move down a layer
        sbcNode = sbcNode.getDown();
        return val;
    }

    /**
	 * Reset the state of the current by-character search
	 */
	public void resetByChar(){
        sbcNode = root;
        sbcString = "";
    }

    private void resetByChar2(){
        sbcNode = root;
    }

    /**
	 * Suggest up to 5 words from the dictionary based on the current
	 * by-character search. Ordering should depend on the implementation.
	 * 
	 * @return	ArrayList<String> List of up to 5 words that are prefixed by
	 *			the current by-character search
	 */
	public ArrayList<String> suggest(){
        ArrayList list = new ArrayList();
        DLBNode node = sbcNode;
        // if searchByChar is empty, just return the first 5 nodes alphabetically from the root
        if (sbcString == "") return suggest(list, sbcString, root);
        return suggest(list, sbcString, node);
    }

    /**
	 * Helper method for suggest()
	 * 
     * @param   s string to be added to ArrayList
     * @param   node current node
     *
	 * @return	ArrayList<String> List of up to 5 words that are prefixed by
	 *			the current by-character search
	 */
    private ArrayList<String> suggest(ArrayList<String> L, String s, DLBNode node){
        if (node == null) return L;
        // once we have 5 elements, return
        if (L.size() == 5) return L;
        // if we can go down, do so until we get to the end of a string
        if (node.getLet() != '#'){
            // append the next letter to s
            s += node.getLet();
            L = suggest(L, s, node.getDown());
        } else L.add(s);
        // go right if possible
        if (node.getRight() != null){
            // prevents duplicate letters
            if (node.getLet() != '#') s = s.substring(0, s.length() - 1);
            L = suggest(L, s, node.getRight());
        } 
        return L;
    }

    /**
	 * List all of the words currently stored in the dictionary
	 * @return	ArrayList<String> List of all valid words in the dictionary
	 */
	public ArrayList<String> traverse(){
        ArrayList list = new ArrayList();
        return traverse(list, "", root);
    }

    /**
	 * Helper method for traverse()
	 * 
     * @param   L ArrayList that is modified/returned
     * @param   s string to be added to ArrayList
     * @param   node current node
     *
	 * @return	ArrayList<String> List of all words in DLB
	 */
    private ArrayList<String> traverse(ArrayList<String> L, String s, DLBNode node){
        if (node == null) return L;
        // if we can go down, do so until we get to the end of a string
        if (node.getLet() != '#'){
            // append the next letter to s
            s += node.getLet();
            L = traverse(L, s, node.getDown());
        } else L.add(s);
        // go right if possible
        if (node.getRight() != null){
            // prevents duplicate letters
            if (node.getLet() != '#') s = s.substring(0, s.length() - 1);
            L = traverse(L, s, node.getRight());
        } 
        return L;
    }

	/**
	 * Count the number of words in the dictionary
	 *
	 * @return	int, the number of (distinct) words in the dictionary
	 */
	public int count(){
        if (root == null) return 0;
        return count(0, root);
    }

    /**
	 * Helper method for count()
	 * 
     * @param   num     keeps track of count
     * @param   node    current node
     *
	 * @return	word count
	 */
    private int count(int num, DLBNode node){
        // go down until we find the end of a string
        if (node.getLet() == '#'){
            num++;
        } else {
            // call recursively with next node
            num = count(num, node.getDown());
        }
        // go right if possible
        if (node.getRight() != null){
            num = count(num, node.getRight());
        }
        return num;
    }
}