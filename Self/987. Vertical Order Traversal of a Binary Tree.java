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



// Phase3 solution
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
class Solution {
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        // use dfs or bfs to traverse the tree, and record a map<colNum,PQ<TreeNode, int[]{rowNum, colNum}>>
        List<List<Integer>> res = new LinkedList<>();
        if (root == null) {return res;}
        Map<Integer, PriorityQueue<Pair<TreeNode,int[]>>> map = new HashMap<>();
        int minColNum = 0;
        int maxColNum = 0;
        
        // do bfs / dfs, here we try bfs
        Queue<Pair<TreeNode, int[]>> queue = new LinkedList<>();
        queue.add(new Pair(root, new int[]{0, 0}));
        while (!queue.isEmpty()) {
            Pair<TreeNode, int[]> curPair = queue.poll();
            TreeNode curNode = curPair.getKey();
            int[] pos = curPair.getValue();
            map.putIfAbsent(pos[1], new PriorityQueue<Pair<TreeNode,int[]>>((p1, p2) -> (p1.getValue()[0] == p2.getValue()[0]? p1.getKey().val - p2.getKey().val : p1.getValue()[0] - p2.getValue()[0])));
            map.get(pos[1]).add(curPair);
            if (curNode.left != null) {queue.add(new Pair(curNode.left, new int[]{pos[0] + 1, pos[1] - 1})); minColNum = Math.min(minColNum, pos[1] - 1);}
            if (curNode.right != null) {queue.add(new Pair(curNode.right, new int[]{pos[0] + 1, pos[1] + 1})); maxColNum = Math.max(maxColNum, pos[1] + 1);}
            
        }
        
        for (int i = minColNum; i <= maxColNum; i++) {
            PriorityQueue<Pair<TreeNode,int[]>> pq = map.get(i);
            List<Integer> curList = new LinkedList<>();
            while (pq.size() > 0) {
                curList.add(pq.poll().getKey().val);
            }
            res.add(curList);
        }
        
        return res;
        
        
        
        
    }
}



// Review without implementation
/* similar to 314, but here for nodes in same row and same same col, we order by value this time. Thus we can either use BFS or DFS to add to the map. We will always need to sort the value list by row number then node val when add to final result list. Thus the element in map value list should contain rowNum and root val.
time O(k * llogl) space O(n)
*/




// Review - 23/3/1 - shortly after review 314. First time didn't realize the sort by value is for same position ones not for same column ones. Thus add in rowNum the second time. And DFS way is good. Can try bug-free for both DFS and BFS way next time.
/* Thoughts
use a map with the key as the column label. We also need row label here as in the end the nodes in the same list will first sorted by its rowNum, then by its colNum and finally by its value.
We can do it using both DFS way, or BFS where queue store pair(treenode, colNum). Either way we should record down the min colNum after the traverse, and use it to convert to the final list.
time O(w*hlogh) where w is the number of cols (width of the tree), h is the average number of nodes sharing the same position.

 */
// DFS way
class Solution {
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        Map<Integer, List<int[]>> map = new HashMap<>();
        int[] minCol = new int[]{0};
        verticalSearch(root, 0, 0, map, minCol);
        List<List<Integer>> res = new ArrayList<>();
        int col = minCol[0];
        while(map.containsKey(col)) {
            List<int[]> curList = map.get(col);
            List<Integer> curResList = new LinkedList<>();
            Collections.sort(curList, 
                (l1,l2)->(l1[0]!=l2[0]? l1[0]-l2[0]:l1[1]-l2[1]));
            for (int i = 0; i < curList.size(); i++) {
                curResList.add(curList.get(i)[1]);
            }
            res.add(curResList);
            col++;
        }
        return res;
    }

    private void verticalSearch(TreeNode root, int curCol, int curRow, Map<Integer, List<int[]>> map, int[] minCol) {
        if (root == null) {return;}
        map.putIfAbsent(curCol, new ArrayList<>());
        map.get(curCol).add(new int[]{curRow,root.val});
        minCol[0] = Math.min(minCol[0], curCol);
        verticalSearch(root.left, curCol-1, curRow+1, map, minCol);
        verticalSearch(root.right, curCol+1, curRow+1, map, minCol);
    }
}








