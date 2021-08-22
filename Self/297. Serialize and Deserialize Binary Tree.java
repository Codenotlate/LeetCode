/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

// Level Order BFS way (iterative using queue)
public class Codec {

    // Encodes a tree to a single string.
    // use BFS, add to queue when its parent is popped out of queue (skip null)
    // add to string when add to queue
    public String serialize(TreeNode root) {
        StringBuilder serie = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        strAppend(serie, root);
        while (!queue.isEmpty()) {
        	TreeNode cur = queue.poll();
        	if (cur == null) {continue;}
        	queue.add(cur.left);
        	queue.add(cur.right);
        	strAppend(serie, cur.left);
        	strAppend(serie, cur.right);
        }
        //System.out.println(serie.toString());
        return serie.toString();
    }


    private void strAppend(StringBuilder serie, TreeNode cur) {
    	if (cur == null) {
    		serie.append("n");
    	} else {
    		serie.append(Integer.toString(cur.val));
    	}
    	serie.append(",");
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        Queue<TreeNode> queue = new LinkedList<>();
        String[] values = data.split(",");
        int len = values.length;
        if (len <= 1) {return null;}
        TreeNode root = new TreeNode(Integer.valueOf(values[0]));
        queue.add(root);
        int i = 1;
        while (i < len) {
        	TreeNode cur = queue.poll();
            // use equals for two chars
        	if (!values[i].equals("n")){
        		cur.left = new TreeNode(Integer.valueOf(values[i]));
        		queue.add(cur.left);
        	}
        	i++;
            if (!values[i].equals("n")){
        		cur.right = new TreeNode(Integer.valueOf(values[i]));
        		queue.add(cur.right);
        	}
        	i++;
        }
        return root;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));


// simplify serialize code, smimilar as above
public class Codec {

    // Encodes a tree to a single string.
    // use BFS, add to queue when its parent is popped out of queue (skip null)
    // add to string when add to queue
    public String serialize(TreeNode root) {
        StringBuilder serie = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
        	TreeNode cur = queue.poll();
        	if (cur == null) {
        		serie.append("n,");
        		continue;
        	}
        	serie.append(cur.val).append(",");
        	queue.add(cur.left);       	
        	queue.add(cur.right);
        }
        //System.out.println(serie.toString());
        return serie.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        Queue<TreeNode> queue = new LinkedList<>();
        String[] values = data.split(",");
        int len = values.length;
        if (len <= 1) {return null;}
        TreeNode root = new TreeNode(Integer.valueOf(values[0]));
        queue.add(root);
        int i = 1;
        while (i < len) {
        	TreeNode cur = queue.poll();
            // use equals for two chars
        	if (!values[i].equals("n")){
        		cur.left = new TreeNode(Integer.valueOf(values[i]));
        		queue.add(cur.left);
        	}
        	i++;
            if (!values[i].equals("n")){
        		cur.right = new TreeNode(Integer.valueOf(values[i]));
        		queue.add(cur.right);
        	}
        	i++;
        }
        return root;
    }
}



/*  review discussions
 1) eliminate all n for the last layer （save half place if complete tree）
 2) the implementation of  queue using linkedList allows null
 	Queue implementations generally do not allow insertion of null elements, although some implementations, such as LinkedList, do not prohibit insertion of null. Even in the implementations that permit it, null should not be inserted into a Queue, as null is also used as a special return value by the poll method to indicate that the queue contains no elements.

// https://leetcode.com/problems/serialize-and-deserialize-binary-tree/discuss/74264/Short-and-straight-forward-BFS-Java-code-with-a-queue
*/





// dfs way: pre-order way (recurisve)

public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder serie = new StringBuilder();
        buildString(root, serie);
        return serie.toString();
    }

    private void buildString(TreeNode root, StringBuilder serie) {
    	if (root == null) {
    		serie.append("n,");
    		return;
    	}
    	serie.append(root.val + ",");
    	buildString(root.left, serie);
    	buildString(root.right, serie);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        Queue<String> values = new LinkedList<>(Arrays.asList(data.split(",")));
        return buildTree(values);
    }

    private TreeNode buildTree(Queue<String> values) {
    	if (values.size() <= 1) {return null;}
    	String cur = values.poll();
        if (cur.equals("n")) {return null;}
    	TreeNode root = new TreeNode(Integer.valueOf(cur));
    	root.left = buildTree(values);
    	root.right = buildTree(values);
    	return root;
    }
}

// reference: https://leetcode.com/problems/serialize-and-deserialize-binary-tree/discuss/74253/Easy-to-understand-Java-Solution