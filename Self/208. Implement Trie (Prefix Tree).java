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
    // O(m) time
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
    // O(m) time
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
    // O(m) time  
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



// Reveiw 
// https://leetcode.com/problems/implement-trie-prefix-tree/solution/
class Trie {
    private class TrieNode {
        TrieNode[] links = new TrieNode[26];
        boolean isEnd = false;
    }
    
    private TrieNode root;


    public Trie() {
        root = new TrieNode(); 
    }
    
    public void insert(String word) {
        TrieNode curNode = root;
        for (char c: word.toCharArray()) {
            if (curNode.links[c - 'a'] == null) {
                curNode.links[c- 'a'] = new TrieNode();
            }
            curNode = curNode.links[c - 'a'];
        }
        curNode.isEnd = true;
    }
    
    public boolean search(String word) {
        TrieNode resNode = searchHelper(word);
        return resNode != null && resNode.isEnd;
        
    }
    
    public boolean startsWith(String prefix) {
        TrieNode resNode = searchHelper(prefix);
        return resNode != null;
    }
    
    private TrieNode searchHelper(String prefix) {
        TrieNode curNode = root;
        for (char c: prefix.toCharArray()) {
            if (curNode.links[c - 'a'] == null) {return null;}
            curNode = curNode.links[c - 'a'];
        }
        return curNode;
    }
    

}



// Review
class Trie {
    
    private class TrieNode {
        TrieNode[] links = new TrieNode[26];
        boolean isEnd = false;
    }
    
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }
    
    public void insert(String word) {
        TrieNode cur = root;
        for (char c: word.toCharArray()) {
            if(cur.links[c-'a'] == null) {cur.links[c-'a'] = new TrieNode();}
            cur = cur.links[c-'a'];
        }
        cur.isEnd = true;
    }
    
    public boolean search(String word) {
        TrieNode cur = root;
        for (char c: word.toCharArray()) {
            if(cur.links[c-'a'] == null) {return false;}
            cur = cur.links[c-'a'];
        }
        return cur.isEnd;
    }
    
    public boolean startsWith(String prefix) {
        TrieNode cur = root;
        for (char c: prefix.toCharArray()) {
            if(cur.links[c-'a'] == null) {return false;}
            cur = cur.links[c-'a'];
        }
        return true;
    }
}




























