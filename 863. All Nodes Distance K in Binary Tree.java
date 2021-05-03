/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

// Phase2 self M1: build the whole graph(could be improved)
class Solution {
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        // step1: build the graph from a tree
       	treeToGraph(root, graph);

       	// step2: traverse the graph
       	Set<Integer> visited = new HashSet<>();
       	Queue<Integer> queue = new LinkedList<>();
       	int level = 0;

       	queue.add(target.val);
       	visited.add(target.val);
       	List<Integer> res = new LinkedList<>();
       	while (!queue.isEmpty()) {
       		int size = queue.size();
       		while (size-- > 0) {
       			int cur = queue.poll();
       			if (level == K) {
       				res.add(cur);
       			}
       			for (int next: graph.get(cur)) {
       				if (!visited.contains(next)) {
       					queue.add(next);
       					visited.add(next);
       				}
       			}
       		}
       		if (level == K) {return res;}
       		level++;
       	}
       	return res;

    }

    private void treeToGraph(TreeNode root, Map<Integer, List<Integer>> graph) {
    	if (root == null) {return;}
    	graph.putIfAbsent(root.val, new ArrayList());
    	if (root.left != null) {
    		graph.putIfAbsent(root.left.val, new ArrayList());
    		graph.get(root.val).add(root.left.val);
    		graph.get(root.left.val).add(root.val);
    		treeToGraph(root.left, graph);
    	}
    	if (root.right != null) {
    		graph.putIfAbsent(root.right.val, new ArrayList());
    		graph.get(root.val).add(root.right.val);
    		graph.get(root.right.val).add(root.val);
    		treeToGraph(root.right, graph);
    	}
    }


}


// phase2 self M2: only build map for parent node (alternative way is to build a new node class that contains info of parent)
class Solution {
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        Map<TreeNode, TreeNode> parent = new HashMap<>();
        // step1: build the parent from a tree
       	treeToParent(root, parent);

       	// step2: traverse the tree, need to be treenode here to get its child
       	Set<TreeNode> visited = new HashSet<>();
       	Queue<TreeNode> queue = new LinkedList<>();
       	int level = 0;

       	queue.add(target);
       	visited.add(target);
       	List<Integer> res = new LinkedList<>();
       	while (!queue.isEmpty()) {
       		int size = queue.size();
       		while (size-- > 0) {
       			TreeNode cur = queue.poll();
       			if (level == K) {
       				res.add(cur.val);
       			}
       			if (cur.left != null && !visited.contains(cur.left)) {
       				queue.add(cur.left);
       				visited.add(cur.left);
       			}
       			if (cur.right != null && !visited.contains(cur.right)) {
       				queue.add(cur.right);
       				visited.add(cur.right);
       			}
       			if (parent.get(cur) != null && !visited.contains(parent.get(cur))) {
       				queue.add(parent.get(cur));
       				visited.add(parent.get(cur));
       			}
       		}
       		if (level == K) {return res;}
       		level++;
       	}
       	return res;

    }

    private void treeToParent(TreeNode root, Map<TreeNode, TreeNode> parent) {
    	if (root == null) {return;}
    	parent.putIfAbsent(root, null);
    	if (root.left != null) {
    		parent.put(root.left, root);
    		treeToParent(root.left, parent);
    	}
    	if (root.right != null) {
    		parent.put(root.right, root);
    		treeToParent(root.right, parent);
    	}
    }


}

// another way using dfs and can without hashmap
// https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/discuss/143798/1ms-beat-100-simple-Java-dfs-with(without)-hashmap-including-explanation










