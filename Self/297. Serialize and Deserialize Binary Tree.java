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
 // add: but this is not very doable in java stringbuilder data structure given the deliminator.
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


// self review
// BFS way
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        //use BFS way to serialize into string
        StringBuilder res = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
            while (!queue.isEmpty()){
                TreeNode cur = queue.poll();
                if (cur == null) {
                    res.append("n").append("#");
                } else {
                    res.append(cur.val).append("#");
                    queue.add(cur.left);
                    queue.add(cur.right); 
                }
                      
            }
        }
        if (res.length() > 0) {res.deleteCharAt(res.length() - 1);}
        return res.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        // use BFS way to build the tree
        if (data.length() == 0) {return null;}
        String[] nums = data.split("#");       
        TreeNode root = new TreeNode(Integer.valueOf(nums[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        int i = 1;
        queue.add(root);
        while ( i < nums.length) {
            TreeNode cur = queue.poll();
            if (!nums[i].equals("n")) {
                cur.left = new TreeNode(Integer.valueOf(nums[i]));
                queue.add(cur.left);
            }
            if (i + 1 < nums.length && !nums[i + 1].equals("n")) {
                cur.right = new TreeNode(Integer.valueOf(nums[i + 1]));
                queue.add(cur.right);
            }
            i += 2;
        }
        return root;
    }
}


// DFS preorder way
/* Split method:
data: "1#2#n#n#3#4#n#n#5#n#n#"
q: ["1","2","n","n","3","4","n","n","5","n","n"]
*/

public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder res = new StringBuilder();
        buildString(root, res);
        return res.toString();
    }
    
    private void buildString(TreeNode root, StringBuilder res) {
        if (root == null) {res.append("n#"); return;}
        res.append(root.val).append('#');
        buildString(root.left, res);
        buildString(root.right, res);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        Queue<String> q = new LinkedList<>(Arrays.asList(data.split("#")));
        TreeNode root = buildTree(q);
        return root;
    }
    
    
    private TreeNode buildTree(Queue<String> q) {
        String cur = q.poll();
        if (cur.equals("n")) {return null;}
        TreeNode root = new TreeNode(Integer.valueOf(cur));
        root.left = buildTree(q);
        root.right = buildTree(q);
        return root;        
    }
}




// Review self

/* Initial thought
most basic way is use the same way as leetcode, BFS way, use n for null. Given the val could be more than one digit, we need to use a deminitor to separate the val of each node. When deserialize, can split the string buy that deminitor.
Also if the node itself is null, we won't put its child in the string. And when deserialize, we can use a queue for the parent level nodes, then for each parent node, use the two value in the string to build as its left and right child. Also put these two nodes into the queue for next round. Repeat this process until no String left in teh serialized string.
serialize/deserialize: time O(n) space O(n)

Another slight optimize: can remove all the end n's in the serialized string, so that when deserialize, there's less to search. Though this won't change the time complexity.
*/

// BFS way
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder res = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (cur != null) {
                queue.add(cur.left);
                queue.add(cur.right);
                res.append(cur.val);
            } else {
                res.append("n");
            }
            res.append('#');
        }
        if (res.length() > 0) {res.deleteCharAt(res.length() - 1);}
        System.out.print(res.toString());
        return res.toString();
        
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] nodes = data.split("#");
        // need to consider special case when the original tree is [].
        if (nodes.length <= 1) {return null;}
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.valueOf(nodes[0]));
        queue.add(root);
        
        int i = 1;
        while (i < nodes.length) {
            TreeNode cur = queue.poll();
            TreeNode left = nodes[i].equals("n")? null : new TreeNode(Integer.valueOf(nodes[i]));
            TreeNode right = nodes[i+1].equals("n")? null : new TreeNode(Integer.valueOf(nodes[i+1]));
            cur.left = left;
            cur.right = right;
            if (left != null) {queue.add(left);}
            if (right != null) {queue.add(right);}
            i += 2;
        }
        return root;
    }
}


// DFS recursive way (same as above DFS way, a very smart way)
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder res = new StringBuilder();
        buildString(root, res);
        return res.toString();
    }
    
    private void buildString(TreeNode root, StringBuilder res) {
        if (root == null) {res.append("n#"); return;}
        res.append(root.val).append("#");
        buildString(root.left, res);
        buildString(root.right, res);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] nodes = data.split("#");
        Queue<String> queue = new LinkedList(Arrays.asList(nodes));
        return buildTree(queue);
    }
    
    private TreeNode buildTree(Queue<String> queue) {
        if (queue.size() <= 1) {return null;}
        String cur = queue.poll();
        if (cur.equals("n")) {return null;}
        TreeNode root = new TreeNode(Integer.valueOf(cur));
        root.left = buildTree(queue);
        root.right = buildTree(queue);
        return root;
    }
}




// Review
/* Thoughts - BFS way, including some wrong idea below in description:
One note is the val of node can be multi-digits, thus we need a delimiter after value of each node in the string.
Store as a [Wrong! - using BFS, it won't be a complete since the child node of null node won't be added to the queue] tree using BFS, [Wrong!- Thus can't use below way: then for node in index i,  its parent will be in index i / 2, and i % 2 != 0 will be it left child otherwise right.]
When do serialize, we use BFS and also include null nodes in the queue. The poll out val will be appended to the string results.
When do deserialize, split the string using delimiter, then we iterate each char in the string. Again, we use a queue and the curIdx to iterate the string array. For each non-n string poll out of queue, we check the curIdx, and curIdx +1 as its left and right children.
time O(2^h) space O(2^h) same as above O(n) as n represents the node number of the tree.

 */
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) {return "";}
        StringBuilder res = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (cur.val == 10000) { // 10000 represents null node
                res.append("n,");
                continue;
            }
            res.append(String.valueOf(cur.val)).append(",");
            queue.add(cur.left == null ? (new TreeNode(10000)) : cur.left);
            queue.add(cur.right == null ? (new TreeNode(10000)) : cur.right);
            // cur.right == null ? queue.add(new TreeNode(10000)) : queue.add(cur.right);
        }
        return res.toString();
        
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.equals("")) {return null;}
        String[] s = data.split(",");
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.valueOf(s[0]));
        queue.add(root);
        int i = 1;
        while ( i < s.length - 1) { // the last one of s will be ""
            TreeNode curNode = queue.poll();
            if (!s[i].equals("n")) {
                TreeNode leftNode = new TreeNode(Integer.valueOf(s[i]));
                curNode.left = leftNode;
                queue.add(leftNode);
            }
            i++;
            if (!s[i].equals("n")) {
                TreeNode rightNode = new TreeNode(Integer.valueOf(s[i]));
                curNode.right = rightNode;
                queue.add(rightNode);
            }
            i++;
        }
        
        return root;
        
    }
}


/*Thoughts - DFS preorder way (instead of above DFS using a queue, we use the string and an index array that can be changed between recursive calls to achieve same idea)
 1, 2, n, n, 3, 4, n, n, 5, n, n,
 when do serialize: simply use recursion with preorder
 when do deserialize: again make it a recursive call, it returns the node of the tree from an string subarray, labeled using the starting index.
  */
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder res = new StringBuilder();
        buildString(root, res);
        return res.toString();
    }

    private void buildString(TreeNode node, StringBuilder res) {
        if (node == null) {res.append("n,"); return;}
        res.append(String.valueOf(node.val)).append(",");
        buildString(node.left, res);
        buildString(node.right, res);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] s = data.split(",");
        return buildTree(s, new int[]{0});
    }

    private TreeNode buildTree(String[] s, int[] idx) {
        int cur = idx[0];
        if (cur >= s.length - 1) {return null;}
        if (s[cur].equals("n")) {idx[0]++;return null;}
        TreeNode root = new TreeNode(Integer.valueOf(s[cur]));
        idx[0]++;
        root.left = buildTree(s, idx);
        root.right = buildTree(s, idx);
        return root;

    }
}























