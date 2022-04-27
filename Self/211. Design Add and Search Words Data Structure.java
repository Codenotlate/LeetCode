// Phase3 self using Trie

class WordDictionary {
	private class TrieNode {
		TrieNode[] links;
		boolean isEnd;

		public TrieNode() {
			links = new TrieNode[26];
			isEnd = false;
		}
	}

	private TrieNode root;

    /** Initialize your data structure here. */
    public WordDictionary() {
    	root = new TrieNode();
    }
    
    // time O(len of word:m), space O(m)
    public void addWord(String word) {
        TrieNode cur = root;

        for (char c: word.toCharArray()) {
        	int idx = c - 'a';
        	if (cur.links[idx] == null) {
        		cur.links[idx] = new TrieNode();
        	}
        	cur = cur.links[idx];
        }
        cur.isEnd = true;
    }
    

    // time O(26^m) or O(S) where S = total number of chars in the Trie.
    public boolean search(String word) {
        return searchHelper(root, word, 0, word.length() - 1);
    }

    private boolean searchHelper(TrieNode cur, String word, int startPos, int endPos) {
    	for (int i = startPos; i <= endPos; i++) {
    		char c = word.charAt(i);
    		if (c == '.') {
    			for (TrieNode t: cur.links) {
    				if (t == null) {continue;}
    				if (searchHelper(t, word, i + 1, endPos)) {return true;}
    			}
    			return false;
    		} else {
    			if (cur.links[c - 'a'] == null) {return false;}
    			cur = cur.links[c - 'a'];
    		}    		
    	}
    	return cur.isEnd;
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */

// Phase3 from solution
class WordDictionary {
    private class TrieNode {
        TrieNode[] links = new TrieNode[26];
        boolean isEnd = false;
    }
    
    TrieNode root;

    public WordDictionary() {
        root = new TrieNode();
    }
    
    public void addWord(String word) {
        TrieNode cur = root;
        for (char c: word.toCharArray()) {
            if (cur.links[c-'a'] == null) {cur.links[c-'a'] = new TrieNode();}
            cur = cur.links[c-'a'];
        }
        cur.isEnd = true;
    }
    
    public boolean search(String word) {
        // considering the '.' case, if we encounter it, we will need to search every node in next level
        return searchHelper(word, root);
    }
    
    private boolean searchHelper(String word, TrieNode curNode) {
        for (int i = 0; i < word.length();i++) {
            char c = word.charAt(i);
            if (c == '.') {
                for (TrieNode nextNode: curNode.links) {
                    if (nextNode != null && searchHelper(word.substring(i+1), nextNode)) {
                        return true;   
                    }
                }
                return false;
            } else if (curNode.links[c-'a'] == null) {
                return false;
            }
            curNode = curNode.links[c - 'a'];
            
        }
        return curNode.isEnd;
    }
}







// Review- similar as above M1, better than M2 in searchHelp function
class WordDictionary {
    class TrieNode {
        boolean isEnd;
        TrieNode[] links;
        
        public TrieNode(){
            isEnd = false;
            links = new TrieNode[26];
        }
    }
    
    TrieNode root;

    public WordDictionary() {
        root = new TrieNode();
    }
    
    public void addWord(String word) {
        TrieNode cur = root;
        for (char c: word.toCharArray()) {
            if (cur.links[c-'a'] == null) {
                cur.links[c-'a'] = new TrieNode();
            }
            cur = cur.links[c-'a'];
        }
        cur.isEnd = true;
    }
    
    public boolean search(String word) {
        return searchHelp(word,0, root);
    }
    // time O(26^m) or O(S) where S = total number of chars in the Trie.
    private boolean searchHelp(String word, int pos, TrieNode cur) {
        if (cur == null) {return false;}
        if (pos == word.length()) {return cur.isEnd;}
        char c = word.charAt(pos);
        if (c != '.') {
            if (cur.links[c-'a'] != null) {return searchHelp(word, pos+1, cur.links[c-'a']);}
            return false;
        }
        for (TrieNode next: cur.links) {
            if (next != null && searchHelp(word, pos+1, next)) {return true;}
        }
        return false;
    }
    
    
    
}

