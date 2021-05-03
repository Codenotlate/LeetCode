/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

// M1 and M2 not optimized way: time O(n^2)
// M3 is optimized way using BST min and max range: time O(n)

// for BST, preOrder traversal corresponds to unique tree. 
// Thus use preOrder traversal result as serialize string result.
// phase 2 self
// time O(n^2)
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder serie = new StringBuilder();
        buildString(serie, root);
        return serie.toString();
    }

    private void buildString(StringBuilder serie, TreeNode root) {
    	if (root == null) {return;}
    	serie.append(root.val + ",");
    	buildString(serie, root.left);
    	buildString(serie, root.right);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
    	// don't forget this one!!
    	if (data.isEmpty()) {return null;}
        String[] valueStr = data.split(",");
        int len = valueStr.length;
        int[] values = new int[len];
        for (int i = 0; i < len; i++) {
        	values[i] = Integer.valueOf(valueStr[i]);
        }
        return buildTree(values, 0, len - 1);
    }

    private TreeNode buildTree(int[] values, int start, int end) {
    	if (start > end) {return null;}
    	if (start == end) {return new TreeNode(values[start]);}
    	TreeNode root = new TreeNode(values[start]);
    	if (start <= end - 1) {
    		int larger = start + 1;
    		while (larger <= end && values[larger] <= root.val) {
    			larger++;
    		}
    		if (larger <= end) {root.right = buildTree(values, larger, end);}
    		root.left = buildTree(values, start + 1, larger - 1);
    	}
    	return root;
    }
}

// can also use queue instead of array and  seperate queue to smallerqueue and the rest
// https://leetcode.com/problems/serialize-and-deserialize-bst/discuss/93175/Java-PreOrder-%2B-Queue-solution


// simialr idea, can simplify deserialize
// time O(n^2)
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder serie = new StringBuilder();
        buildString(serie, root);
        return serie.toString();
    }

    private void buildString(StringBuilder serie, TreeNode root) {
    	if (root == null) {return;}
    	serie.append(root.val + ",");
    	buildString(serie, root.left);
    	buildString(serie, root.right);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] valueStr = data.split(",");
        TreeNode root = null;
        for (String s: valueStr) {
        	if (s.length() > 0) { // deal with "" string
        		root = buildTree(root, Integer.valueOf(s));
        	}
        }
        return root;
    }

    private TreeNode buildTree(TreeNode root, int v) {
    	if (root == null) {return new TreeNode(v);}

    	if (root.val > v) {
    		root.left = buildTree(root.left, v);
    	} else {
    		root.right = buildTree(root.right, v);
    	}
    	return root;
    }
}

//M3: simialr idea as 297, time O(n)
//for deserialize just need to have min and max range to tell when to stop and return.
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder serie = new StringBuilder();
        buildString(serie, root);
        return serie.toString();
    }

    private void buildString(StringBuilder serie, TreeNode root) {
    	if (root == null) {return;}
    	serie.append(root.val + ",");
    	buildString(serie, root.left);
    	buildString(serie, root.right);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
    	if (data.isEmpty()) {
    		return null;
    	}
        Queue<String> values = new LinkedList<>(Arrays.asList(data.split(",")));
        return buildTree(values, Integer.MIN_VALUE, Integer.MAX_VALUE);
        
    }

    private TreeNode buildTree(Queue<String> values, int min, int max) {
    	if (values.isEmpty()) {return null;}
    	int val = Integer.valueOf(values.peek());
    	// this is the stop condition, replace the == 'n' condition in 297
    	if (val < min || val > max) {return null;} 
    	TreeNode root = new TreeNode(val);
    	values.poll(); // only poll when it's used
    	root.left = buildTree(values, min, val);
    	root.right = buildTree(values, val, max);
    	return root;
    }
}

// difference discussion with 297
// https://leetcode.com/problems/serialize-and-deserialize-bst/discuss/93177/what's-the-difference-between-this-and-297
/* self:
The main difference is in this problem, we want the string as compact as possible, since this is a BST, we can use preorder traversal to define a unique BST. Without using null as n in 297.
But this would cause a problem for deserialize, since now we can't use 'n' to tell us when to stop for the left subtree of a node, and move to right subtree, thus we use two ways to deal with this issue.
One way is to iterate the following values each time, to find the first value > root.val, and that is the stop between left and right subtrees.
But this way is slow, time O(n^2)
Optimized way is to use BST attributes, i.e. use min and max range of a BST to determine when to stop and return for the left subtree.

*/

















// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// String tree = ser.serialize(root);
// TreeNode ans = deser.deserialize(tree);
// return ans;