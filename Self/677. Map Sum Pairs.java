class MapSum {

	// private Node class
    private class Node {
        int isleaf = 0; // use isleaf to tell whether isleaf and the val simultaneously
        Node[] child = new Node[26]; // since a to z, can also use hashmap like in cs61B
    }

    // class var
    private Node root;


    /** Initialize your data structure here. */
    public MapSum() {
        root = new Node();
    }
    
    public void insert(String key, int val) {
        Node cur = this.root;
        for (char c: key.toCharArray()) {
            int idx = c - 'a';
            if (cur.child[idx] == null) {
                cur.child[idx] = new Node();
            }
            cur = cur.child[idx];
        }
        cur.isleaf = val;
    }
    
    public int sum(String prefix) {
        Node cur = this.root;
        for (char c: prefix.toCharArray()) {
            int idx = c - 'a';
            if (cur.child[idx] == null) {return 0;}
            cur = cur.child[idx];
        }
        return addSum(cur);
    }

    private int addSum(Node root) {
    	if (root == null) {return 0;}
    	int sum = root.isleaf;
    	for (Node n: root.child) {
    		sum += addSum(n);
    	}
    	return sum;
    }

	
}

/**
 * Your MapSum object will be instantiated and called as such:
 * MapSum obj = new MapSum();
 * obj.insert(key,val);
 * int param_2 = obj.sum(prefix);
 */


