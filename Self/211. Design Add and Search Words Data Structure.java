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















