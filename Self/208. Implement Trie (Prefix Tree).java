class Trie {
    // private Node class
    private class Node {
        boolean isleaf = false;
        Node[] child = new Node[26]; // since a to z, can also use hashmap like in cs61B
    }

    // class var
    private Node root;

    /** Initialize your data structure here. */
    public Trie() {
        root = new Node();
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        Node cur = this.root;
        for (char c: word.toCharArray()) {
            int idx = c - 'a';
            if (cur.child[idx] == null) {
                cur.child[idx] = new Node();
            }
            cur = cur.child[idx];
        }
        cur.isleaf = true;
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        Node cur = this.root;
        for (char c: word.toCharArray()) {
            int idx = c - 'a';
            if (cur.child[idx] == null) {return false;}
            cur = cur.child[idx];
        }
        return cur.isleaf;
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        Node cur = this.root;
        for (char c: prefix.toCharArray()) {
            int idx = c - 'a';
            if (cur.child[idx] == null) {return false;}
            cur = cur.child[idx];
        }
        return true;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */




// This should be the best answer. The runtime in lc fluctuates with same answer.



// phase 3 self
class Trie {
    private class TrieNode {
        TrieNode[] links;
        boolean isEnd;
        int size = 26;
        
        public TrieNode() {
            links = new TrieNode[size];
            isEnd = false;
        }      
    }
    
    private TrieNode root;
    
    /** Initialize your data structure here. */
    public Trie() {
        root = new TrieNode();
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode cur = root;
        for (char c: word.toCharArray()) {
            int idx = c - 'a';
            if (cur.links[idx] == null) {
                cur.links[idx] = new TrieNode();
            }
            cur = cur.links[idx];
        }
        // don't forget this line
        cur.isEnd = true;
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode cur = root;
        
        for (char c: word.toCharArray()) {
            int idx = c - 'a';
            if (cur.links[idx] == null) {return false;}
            cur = cur.links[idx];
        }
        return cur.isEnd;
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        TrieNode cur = root;
        
        for (char c: prefix.toCharArray()) {
            int idx = c - 'a';
            if (cur.links[idx] == null) {return false;}
            cur = cur.links[idx];
        }
        return true;
    }
}

















