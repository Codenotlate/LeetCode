/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */


// M1: self
class Solution {
	private class Node {
		public TreeNode treeNode;
		public int row;
		public int col;

		Node(TreeNode n, int r, int c) {
			treeNode = n;
			row = r;
			col = c;
		}
	}


    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();
        if (root == null) {return res;}
        Stack<Node> stack = new Stack<>();
        stack.add(new Node(root, 0, 0));
        // since col numbers are consecutive, thus we can track the min and the max
        // so that we don't need to sort map.keys later when add what we have in map to res
        int min_col = 0;
        int max_col = 0;
        // map store the priorityqueue according to column numbers
        Map<Integer, PriorityQueue<Node>> map = new HashMap<>();

        // traverse the tree and store to map
        while (!stack.isEmpty()) {
        	Node cur = stack.pop();
        	if (!map.containsKey(cur.col)) {
        		// if diff row, then smaller row means smaller, if same row, then smaller val means smaller
        		PriorityQueue<Node> pq = new PriorityQueue<>(new Comparator<Node>() {
        			public int compare(Node n1, Node n2) {
        				if (n1.row != n2.row) {
        					return n1.row - n2.row;
        				} else {
        					return n1.treeNode.val - n2.treeNode.val;
        				}
        			}
        		});
        		// below one is more precise than above one
        		// (a, b) -> a.y != b.y ? Integer.compare(b.y, a.y) : Integer.compare(a.node.val, b.node.val));
                map.put(cur.col, pq);
        	}
        	map.get(cur.col).add(cur);

        	
        	if (cur.treeNode.left != null) {
        		stack.push(new Node(cur.treeNode.left, cur.row + 1, cur.col - 1));
        		min_col = Math.min(min_col, cur.col - 1);
        	}
        	if (cur.treeNode.right != null) {
        		stack.push(new Node(cur.treeNode.right, cur.row + 1, cur.col + 1));
        		max_col = Math.max(max_col, cur.col + 1);
        	}
        }

        //after the traversal, add what we have in the map in col order to res
        for (int i = min_col; i <= max_col; i++) {
        	List<Integer> colRes = new LinkedList<>();
        	PriorityQueue<Node> pq = map.get(i);
        	while (pq.size() > 0) {
        		colRes.add(pq.poll().treeNode.val);
        	}
        	res.add(colRes);
        }
        return res;

    }
}

// M2: simialr idea as M1, but use ArrayList for map value first and sort later when put into res
// https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/discuss/231148/Java-TreeMap-Solution
// first comment solution





// M3: using only one priority queue to store result of traversal
// then use node.col to tell when to start a new List in res
// https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/discuss/231425/Java-Solution-using-Only-PriorityQueue

















