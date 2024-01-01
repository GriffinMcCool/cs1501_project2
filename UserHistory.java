// author Griffin McCool
package cs1501_p2;
import java.util.ArrayList;

public class UserHistory implements Dict{

    class DLBNode2{
        /**
        * Letter represented by this DLBNode2
        */
        private char let;

        /**
        * Frequency of word
        */
        private int val;

        /**
        * Lead to other alternatives for current letter in the path
        */
        private DLBNode2 right;

        /**
        * Leads to keys with prefixed by the current path
        */	
        private DLBNode2 down;

        /**
        * Constructor that accepts the letter for the new node to represent
        */
        public DLBNode2(char let) {
            this.let = let;
            this.val = 0;
            this.right = null;
            this.down = null;
        }

        /**
        * Getter for the letter this DLBNode2 represents
        *
        * @return	The letter
        */
        public char getLet() {
            return let;
        }

        /**
        * Getter for the next linked-list DLBNode2
        *
        * @return	Reference to the right DLBNode2
        */
        public DLBNode2 getRight() {
            return right;
        }

        /**
        * Getter for the child DLBNode2
        *
        * @return	Reference to the down DLBNode2
        */
        public DLBNode2 getDown() {
            return down;
        }

        /**
        * Setter for the next linked-list DLBNode2
        *
        * @param	r DLBNode2 to set as the right reference
        */
        public void setRight(DLBNode2 r) {
            right = r;
        }

        /**
        * Setter for the child DLBNode2
        *
        * @param	d DLBNode2 to set as the down reference
        */
        public void setDown(DLBNode2 d) {
            down = d;
        }

        public void incVal() {
            val++;
        }

        public int getVal() {
            return val;
        }

    }

    private DLBNode2 root;
    // used for searchByChar()
    private DLBNode2 sbcNode; 
    private DLBNode2 sbcNodeParent;
    private String sbcString = "";
    private ArrayList<DLBNode2> topFive;
    private ArrayList<String> topFiveStrings = new ArrayList();

    /**
	 * Add a new word to the dictionary
	 *
	 * @param 	key New word to be added to the dictionary
	 */	
	public void add(String key){
        if (key != null){
            DLBNode2 node = add(key, 0, root, null);
        }
    }

    /**
	 * Helper method for add()
	 * 
     * @param   key     string to be added
     * @param   i       loop variable
     * @param   cur     current node
     * @param   parent  overall parent node
     *
	 * @return	DLBNode2 added node
	 */
    private DLBNode2 add(String key, int i, DLBNode2 cur, DLBNode2 parent){
        // if we're at the end of the String, return parent (previous addedNode)
        if (i == key.length()) return parent;
        DLBNode2 addedNode;
        addedNode = addDown(key.charAt(i), cur, parent);
        if (i == key.length() - 1){
            // adds word
            addedNode.incVal();
        }
        // adds next char before reordering
        cur = add(key, i+1, addedNode.getDown(), addedNode);
        // if there is a node to the right, reorder based on frequency (alphabetically to break ties)
        if (addedNode.getRight() != null){
            addedNode = swap(addedNode, addedNode.getRight(), null, parent);
        }
        return addedNode;
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
    private DLBNode2 addDown(char c, DLBNode2 node, DLBNode2 parent){
        //if the current node is null, make a new node there with c
        if (node == null){
            // initialize the root
            if (node == root){
                root = new DLBNode2(c);
                return root;
            } else parent.setDown(new DLBNode2(c));
            return parent.getDown();
        } 
        //if node is full, add below and update right
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
    private DLBNode2 addRight(char c, DLBNode2 cur, DLBNode2 prev, DLBNode2 parent){
        DLBNode2 newNode;
        // if we find c, move it to front and return the node
        if (c == cur.getLet()){
            // if we're at the top layer (parent is null) update root
            if (parent == null){
                // if we're already on the left, no need to update
                if (prev == null) return root;
                prev.setRight(cur.getRight());
                cur.setRight(root);
                root = cur;
                return cur;
            } 
            // if we're already on the left, no need to update
            if (prev == null) return cur;
            prev.setRight(cur.getRight());
            cur.setRight(parent.getDown());
            parent.setDown(cur);
            return cur;
        }
		// if we're at the end of the list and didn't find c, add it at the front
        if (cur.getRight() == null){
            newNode = new DLBNode2(c);
            // if we're at the top layer (parent is null) update root
            if (parent == null){
                newNode.setRight(root);
                root = newNode;
            } else {
                newNode.setRight(parent.getDown());
                parent.setDown(newNode);
            }
            return newNode;
        }
        return addRight(c, cur.getRight(), cur, parent);
    }

    /**
	 * Helper method for add()
	 * 
     * @param   max     holds max frequency of a word in the list from current node
     * @param   node    current node
     *
	 * @return	int max
	 */
    private int maxFreq(int max, DLBNode2 node){
        max = node.getVal();
        if (node.getDown() != null) max = maxFreq(max, node.getDown());
        return max;
    }

    /**
	 * Helper method for add() to maintain order by frequency (reduces runtime for suggest() as less nodes need to be searched)
	 * 
     * @param   leftNode    left node to be swapped
     * @param   rightNode   right node to be swapped
     * @param   prev        previous node
     * @param   parent      overall parent node to level
     *
	 * @return	DLBNode2    returns original left node after swap
	 */
    private DLBNode2 swap(DLBNode2 leftNode, DLBNode2 rightNode, DLBNode2 prev, DLBNode2 parent){
        int max1, max2;
        // left node's max
        max1 = maxFreq(0, leftNode);
        // right node's max
        max2 = maxFreq(0, rightNode);
        // if both nodes have the same frequency, order alphabetically, or order by highest frequency
        if ((max2 == max1 && rightNode.getLet() < leftNode.getLet()) || (max2 > max1)){
            if (prev != null) prev.setRight(rightNode);
            leftNode.setRight(rightNode.getRight());
            rightNode.setRight(leftNode);
            if (parent != null){
                if (parent.getDown() == leftNode) parent.setDown(rightNode);
            }
            if (leftNode == root){
                root = rightNode;
            }
            // if there is another right node, check if those two need swapped
            if (leftNode.getRight() != null) swap(leftNode, leftNode.getRight(), rightNode, parent);
        }
        return leftNode;
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
        DLBNode2 cur = root;
        DLBNode2 next = root;
        // loop through each character and check to see if it's there
        for (int i = 0; i < key.length(); i++){
            cur = contains(key.charAt(i), next);
            // if character is not found, immediately return false
            if (cur == null) return false;
            next = cur.getDown();
        }
        //if the string is found, make sure there is a terminator so it's not just a prefix
        if ((cur != null) && (cur.getVal() != 0)) return true;
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
    private DLBNode2 contains(char c, DLBNode2 node){
        if (node == null) return null;
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
        // pretty much the same code as contains(), we just remove the check for a val other than 0
        // as prefixes can have a val of 0
        if (pre == null) return false;
        boolean cont;
        DLBNode2 cur = root;
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
        if (sbcNode.getVal() != 0){
            // if there is a character to the right of the terminator, word is both valid and a prefix for another word
            if (sbcNode.getDown() != null) val = 2;
            // otherwise, it's a valid word but not a prefix for any other words
            else val = 1;
        }
        // otherwise, it's just a prefix and not a word
        else val = 0;
        // update sbcNode to move down a layer
        sbcNodeParent = sbcNode;
        sbcNode = sbcNode.getDown();
        return val;
    }

    /**
	 * Reset the state of the current by-character search
	 */
	public void resetByChar(){
        sbcNode = root;
        sbcNodeParent = null;
        sbcString = "";
    }

    /**
    * Helper method for searchByChar()
    */
    private void resetByChar2(){
        sbcNode = root;
        sbcNodeParent = null;
    }

    /**
	 * Suggest up to 5 words from the dictionary based on the current
	 * by-character search. Ordering should depend on the implementation.
	 * 
	 * @return	ArrayList<String> List of up to 5 words that are prefixed by
	 *			the current by-character search
	 */
	public ArrayList<String> suggest(){
        // initialize topFive
        if (topFive == null){
            topFive = new ArrayList();
            for (int i = 0; i < 5; i++){
                topFive.add(null);
                topFiveStrings.add("");
            }
        }
        ArrayList<String> list1;
        ArrayList<String> list2 = new ArrayList();
        // reset topFiveStrings & topFive
        for (int i = 0; i < 5; i++){
            topFive.set(i, null);
            topFiveStrings.set(i, "");
        }
        // if searchByChar is empty, just return the list starting from the root
        if (sbcString == "") return suggest(sbcString, root);
        if (sbcNodeParent != null && sbcNodeParent.getVal() != 0){
            topFive.set(0, sbcNodeParent);
            topFiveStrings.set(0, sbcString);
        }
        list1 = suggest(sbcString, sbcNode);
        for (int h = 0; h < 5; h++){
            if (list1.get(h) != "") list2.add(list1.get(h));
        }
        return list2;
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
    private ArrayList<String> suggest(String s, DLBNode2 node){
        if (node == null) return topFiveStrings;
        // if we can go down, do so until we get to the end of a string
        if (node.getVal() == 0){
            // append the next letter to s
            s += node.getLet();
            suggest(s, node.getDown());
        } else {
            s += node.getLet();
            // if we are at the end of a word...
            // add node to topFive if there is space
            for (int x = 0; x < 5; x++){
                // if the string is already in the top 5, don't add it again
                if (topFive.get(x) != null && topFiveStrings.get(x) == s) break;
                if (topFive.get(x) == null){
                    topFive.set(x, node);
                    topFiveStrings.set(x, s);
                    break;
                }
            }
            // check if node goes in the top5
            top5(node, 5, s);
            suggest(s, node.getDown());
        }
        // go right if possible
        if (node.getRight() != null){
            s = s.substring(0, s.length() - 1);
            suggest(s, node.getRight());
        }
        return topFiveStrings;
    }

    /**
	 * Helper method for suggest()
	 * 
     * @param   node    current node
     * @param   i       current iteration of call
     * @param   s       key used to sort alphabetically
     *
	 */
    private void top5(DLBNode2 node, int i, String s){
        int z = 4;
        while ((i-1) >= 0 && (topFive.get(i-1) == null || node.getVal() >= topFive.get(i-1).getVal())){
            // if they have equal frequencies, sort alphabetically
            if (topFive.get(i-1) != null && (node.getVal() == topFive.get(i-1).getVal()) && (s.compareTo(topFiveStrings.get(i-1)) > 0)){
                // if s > current string being compared, s goes to the right of the current string
                break;
            }
            // keeps track of if string is already in top 5
            if (s.compareTo(topFiveStrings.get(i-1)) == 0) z = i-1;
            i--;
        }
        if ((i < 5) && (s.compareTo(topFiveStrings.get(i)) != 0)){
            int j = z;
            while (j > i){
                topFive.set(j, topFive.get(j-1));
                topFiveStrings.set(j, topFiveStrings.get(j-1));
                j--;
            }
            topFive.set(i, node);
            topFiveStrings.set(i, s);
        }
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
	 * @return	ArrayList<String> List of all words in UserHistory
	 */
    private ArrayList<String> traverse(ArrayList<String> L, String s, DLBNode2 node){
        if (node == null) return L;
        // if we can go down, do so until we get to the end of a string
        if (node.getVal() == 0){
            // append the next letter to s
            s += node.getLet();
            L = traverse(L, s, node.getDown());
        } else {
            s += node.getLet();
            L.add(s);
            L = traverse(L, s, node.getDown());
        }
        // go right if possible
        if (node.getRight() != null){
            // prevents duplicate letters when moving to right
            s = s.substring(0, s.length() - 1);
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
    private int count(int num, DLBNode2 node){
        if (node == null) return num;
        // go down until we find the end of a string
        if (node.getVal() != 0){
            num++;
        }
        // call recursively with next node
        num = count(num, node.getDown());
        // go right if possible
        if (node.getRight() != null){
            num = count(num, node.getRight());
        }
        return num;
    }

    /**
	 * List all of the words currently stored in the dictionary
	 * @return	ArrayList<String> List of all valid words in the dictionary including duplicates
	 */
	public ArrayList<String> traverseWithDuplicates(){
        ArrayList list = new ArrayList();
        return traverseWithDuplicates(list, "", root);
    }

    /**
	 * Helper method for traverse()
	 * 
     * @param   L ArrayList that is modified/returned
     * @param   s string to be added to ArrayList
     * @param   node current node
     *
	 * @return	ArrayList<String> List of all words in UserHistory including duplicates
	 */
    private ArrayList<String> traverseWithDuplicates(ArrayList<String> L, String s, DLBNode2 node){
        if (node == null) return L;
        // if we can go down, do so until we get to the end of a string
        if (node.getVal() == 0){
            // append the next letter to s
            s += node.getLet();
            L = traverseWithDuplicates(L, s, node.getDown());
        } else {
            s += node.getLet();
            for (int i = 0; i < node.getVal(); i++){ 
                L.add(s);
            }
            L = traverseWithDuplicates(L, s, node.getDown());
        }
        // go right if possible
        if (node.getRight() != null){
            // prevents duplicate letters when moving to right
            s = s.substring(0, s.length() - 1);
            L = traverseWithDuplicates(L, s, node.getRight());
        }
        return L;
    }
}