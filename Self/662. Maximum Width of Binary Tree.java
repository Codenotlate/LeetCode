/*Thought
since we includes null nodes in between, we are actually comparing to a complete tree, thus we can use the way we label node id for complete tree. i.e. if current node is k, then its left child is 2 * k, and right child as 2*k+1.
Thus one way is to do BFS, put the id in the queue. Then for each level of BFS, we get id for firstnode and lastnode, then update width.
time O(n) space O(n)

M2: can also do DFS using same id logic. But we maintain a map, with level number as key and first node id at that level as the value. And we can only keep the first id, and keep updating the width each time we have another id at that level.
time O(n) space O(n)

*/




// BFS way (below Long can be changed to Integer, both have overflow problem, but that's not that meaningful for this algo itself)

class Solution {
    public int widthOfBinaryTree(TreeNode root) {
        Queue<Pair<TreeNode, Long>> queue = new LinkedList<>();
        int res = 0;
        if (root == null) {return res;}
        queue.add(new Pair(root, new Long(0)));
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            long firstid = Integer.MAX_VALUE;
            long lastid = 0;
            while(size-- > 0) {
                Pair<TreeNode, Long> cur = queue.poll();
                TreeNode curNode = cur.getKey();
                long id = cur.getValue();
                firstid = Math.min(firstid, id);
                lastid = Math.max(lastid, id);
                if (curNode.left != null) {queue.add(new Pair(curNode.left, 2 * id));}
                if (curNode.right != null) {queue.add(new Pair(curNode.right, 2 * id + 1));}
            }
            res = (int) Math.max(res, lastid-firstid + 1);
        }
        return res;
    }
}

// this posts solve part of the overflow issue with the testing cases in lc, but not handle all cases.
// https://leetcode.com/problems/maximum-width-of-binary-tree/discuss/106663/Java-O(n)-BFS-one-queue-clean-solution







// DFS way
class Solution {
    public int widthOfBinaryTree(TreeNode root) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] res = new int[1];
        dfs(root, map, 0, 0, res);
        return res[0];
    }
    
    private void dfs(TreeNode root, Map<Integer, Integer> map, int row, int col, int[] res) {
        if (root == null) {return;}
        if (map.containsKey(row)) {
            res[0] = Math.max(res[0], col - map.get(row) + 1);
        } else {
            map.put(row, col);
            res[0] = Math.max(res[0],1);
        }
        dfs(root.left, map, row+1, 2 * col, res);
        dfs(root.right, map, row+1, 2 * col + 1, res);
    }
}
